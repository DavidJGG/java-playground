import java.io.*;
import java.nio.file.*;
import java.util.*;

public class MemoryFileList<T> implements List<T> {

    private static final int MEMORY_LIMIT = 100000; // Memory limit
    private final LinkedList<T> inMemoryList;  // Stores the elements in memory (up to MEMORY_LIMIT)
    private final Path filePath;               // Path to the file for storing newer data

    public MemoryFileList(Path filePath) {
        this.inMemoryList = new LinkedList<>();
        this.filePath = filePath;
    }

    @Override
    public int size() {
        try {
            return inMemoryList.size() + (int) getFileSize(); // Size in memory + file size
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return inMemoryList.isEmpty() && getFileSize() == 0;
    }

    @Override
    public boolean contains(Object o) {
        if (inMemoryList.contains(o)) {
            return true;
        }
        return containsInFile(o);
    }

    private boolean containsInFile(Object o) {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(filePath))) {
            while (true) {
                try {
                    @SuppressWarnings("unchecked")
                    T element = (T) in.readObject();
                    if (element.equals(o)) {
                        return true;
                    }
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private final Iterator<T> inMemoryIterator = inMemoryList.iterator();
            private final ObjectInputStream fileStream;
            private T nextFileElement;
            private boolean hasMoreFileElements = true;

            {
                try {
                    fileStream = new ObjectInputStream(Files.newInputStream(filePath));
                    advanceToNextFileElement();
                } catch (IOException e) {
                    throw new RuntimeException("Error initializing file iterator", e);
                }
            }

            private void advanceToNextFileElement() {
                try {
                    if (fileStream.available() > 0) {
                        @SuppressWarnings("unchecked")
                        nextFileElement = (T) fileStream.readObject();
                    } else {
                        hasMoreFileElements = false;
                    }
                } catch (IOException | ClassNotFoundException e) {
                    hasMoreFileElements = false;
                }
            }

            @Override
            public boolean hasNext() {
                return inMemoryIterator.hasNext() || hasMoreFileElements;
            }

            @Override
            public T next() {
                if (inMemoryIterator.hasNext()) {
                    return inMemoryIterator.next();
                } else if (hasMoreFileElements) {
                    T result = nextFileElement;
                    advanceToNextFileElement();
                    return result;
                }
                throw new NoSuchElementException();
            }
        };
    }

    @Override
    public Object[] toArray() {
        List<T> result = new ArrayList<>(inMemoryList);
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(filePath))) {
            while (true) {
                try {
                    @SuppressWarnings("unchecked")
                    T element = (T) in.readObject();
                    result.add(element);
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return (T1[]) toArray();
    }

    @Override
    public boolean add(T element) {
        if (inMemoryList.size() < MEMORY_LIMIT) {
            return inMemoryList.add(element);
        } else {
            writeToFile(element);
            return true;
        }
    }

    private void writeToFile(T element) {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filePath, 
                StandardOpenOption.CREATE, 
                StandardOpenOption.APPEND))) {
            out.writeObject(element);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean remove(Object o) {
        if (inMemoryList.remove(o)) {
            return true;
        }
        return removeFromFile(o);
    }

    private boolean removeFromFile(Object o) {
        List<T> tempList = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(filePath))) {
            while (true) {
                try {
                    @SuppressWarnings("unchecked")
                    T element = (T) in.readObject();
                    if (!element.equals(o)) {
                        tempList.add(element);
                    }
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Rewriting the file without the removed element
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filePath, 
                StandardOpenOption.CREATE, 
                StandardOpenOption.TRUNCATE_EXISTING))) {
            for (T element : tempList) {
                out.writeObject(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }

        if (index < inMemoryList.size()) {
            return inMemoryList.get(index);
        } else {
            return getFromFile(index - inMemoryList.size());
        }
    }

    private T getFromFile(int index) {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(filePath))) {
            int currentIndex = 0;
            T element;
            while (true) {
                try {
                    @SuppressWarnings("unchecked")
                    element = (T) in.readObject();
                    if (currentIndex == index) {
                        return element;
                    }
                    currentIndex++;
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new IndexOutOfBoundsException("Index: " + index);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return inMemoryList.containsAll(c) || containsAllInFile(c);
    }

    private boolean containsAllInFile(Collection<?> c) {
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(filePath))) {
            while (true) {
                try {
                    @SuppressWarnings("unchecked")
                    T element = (T) in.readObject();
                    if (c.contains(element)) {
                        return true;
                    }
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        if (inMemoryList.size() + c.size() <= MEMORY_LIMIT) {
            return inMemoryList.addAll(c);
        } else {
            for (T element : c) {
                writeToFile(element);
            }
            return true;
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        if (index < MEMORY_LIMIT) {
            return inMemoryList.addAll(index, c);
        } else {
            for (T element : c) {
                writeToFile(element);
            }
            return true;
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean modified = inMemoryList.removeAll(c);
        modified |= removeAllFromFile(c);
        return modified;
    }

    private boolean removeAllFromFile(Collection<?> c) {
        List<T> tempList = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(filePath))) {
            while (true) {
                try {
                    @SuppressWarnings("unchecked")
                    T element = (T) in.readObject();
                    if (!c.contains(element)) {
                        tempList.add(element);
                    }
                } catch (EOFException e) {
                    break; // End of file reached
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Rewriting the file without removed elements
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filePath, 
                StandardOpenOption.CREATE, 
                StandardOpenOption.TRUNCATE_EXISTING))) {
            for (T element : tempList) {
                out.writeObject(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    // Other methods to implement from List interface, like retainAll, set, etc.

    // Helper method to get the size of the file in terms of bytes
    private long getFileSize() throws IOException {
        return Files.size(filePath);
    }
}
