import java.util.*;

class Persona {
    private int id;
    private String nombre;

    public Persona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Persona{id=" + id + ", nombre='" + nombre + "'}";
    }
}

public class Ejemplo {
    public static void main(String[] args) {
        int numberOfElements = 12_000_000;
        Random random = new Random();

        // Generar datos
        ArrayList<Persona> listaPersonas = new ArrayList<>();
        listaPersonas.add(new Persona(5, "base"));
        for (int i = 11; i < 12; i++) {
            listaPersonas.add(new Persona(15, "Nombre" + i));
        }
        listaPersonas.add(new Persona(20, "fin"));


        long startTimeArrayList = System.nanoTime();
        // Usar un ArrayList que ya tiene los elementos
        //Collections.sort(listaPersonas, Comparator.comparingInt(Persona::getId));
        long endTimeArrayList = System.nanoTime();
        long durationArrayList = endTimeArrayList - startTimeArrayList;

        // Resultados
        System.out.println("Tiempo para ordenar ArrayList: " + durationArrayList / 1_000_000 + " ms");

        listaPersonas.forEach(System.out::println);
    }
}
