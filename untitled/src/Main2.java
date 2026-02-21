import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/*

[[7,8],[12,16],[12,5],[1,8],[4,19],[3,15],[4,10],[9,16]]


[12,16] [3,15] [1,8]



 */
public class Main2 {
    public static void main(String[] args) {
        var solution = new Solution();
        var res = solution.maxEnvelopes(new int[][]{{7,8},{12,16},{12,5},{1,8},{4,19},{3,15},{4,10},{9,16}});
        System.out.println(res);
    }
}



class Solution{
    public int maxEnvelopes(int[][] envelopes) {
        var bt = new BinaryTree();
        for (var envelope : envelopes) {
            bt.insert(envelope);
        }
        bt.printOrdered(bt.root);
        return 0;
    }
}

class BinaryTree{
    Node root;

    public void insert(int[] newValue){
        if(root == null){
            root = new Node(null, newValue, null);
            return;
        }

        var node = root;
        while (node != null){
            var valueStored = node.values.get(0);
            if(isSimilar(valueStored, newValue)){
                node.values.add(newValue);
                node = null;
            } else if (isBigger(valueStored, newValue)) {
                if(node.right != null){
                    node = node.right;
                } else {
                    node.right = new Node(null, newValue, null);
                    node = null;
                }
            } else if (isSmaller(valueStored, newValue)) {
                if(node.left != null){
                    node = node.left;
                } else {
                    node.left = new Node(null, newValue, null);
                    node = null;
                }
            } else if(!isEquals(valueStored, newValue)){
                node.values.add(newValue);
                node = null;
            } else {
                throw new RuntimeException("Can't insert values");
            }
        }
    }

    public void printOrdered(Node node){
       if(node.left != null){
           printOrdered(node.left);
       }
        System.out.println(getStr(node.values));
       if(node.right != null){
           printOrdered(node.right);
       }
    }

    private String getStr(List<int[]> toPrint){
        var cnt = 0;
        var str = new StringBuilder();
        for(var v : toPrint){
            if(cnt == 0){
                str.append("["+v[0]+","+v[1]+"]");
                cnt++;
            }else{
                str.append(" -> ["+v[0]+","+v[1]+"]");
            }
        }
        return str.toString();
    }

    private boolean isSmaller(int[] savedEnvelop, int[] newEnvelop) {

        if(savedEnvelop == null){
            return true;
        }

        var sX = savedEnvelop[0];
        var sY = savedEnvelop[1];
        var nX = newEnvelop[0];
        var nY = newEnvelop[1];

        if (nX < sX && nY < sY) {
            return true;
        }
        return false;
    }

    private boolean isBigger(int[] savedEnvelop, int[] newEnvelop) {

        if(savedEnvelop == null){
            return true;
        }

        var sX = savedEnvelop[0];
        var sY = savedEnvelop[1];
        var nX = newEnvelop[0];
        var nY = newEnvelop[1];

        if (nX > sX && nY > sY) {
            return true;
        }
        return false;
    }

    private boolean isSimilar(int[] savedEnvelop, int[] newEnvelop) {

        if(savedEnvelop == null){
            return false;
        }

        var sX = savedEnvelop[0];
        var sY = savedEnvelop[1];
        var nX = newEnvelop[0];
        var nY = newEnvelop[1];

        if (nX == sX && nY != sY) {
            return true;
        }
        if(nX != sX && nY == sY){
            return true;
        }
        return false;
    }

    private boolean isEquals(int[] savedEnvelop, int[] newEnvelop) {

        if(savedEnvelop == null){
            return false;
        }

        var sX = savedEnvelop[0];
        var sY = savedEnvelop[1];
        var nX = newEnvelop[0];
        var nY = newEnvelop[1];

        if (nX == sX && nY == sY) {
            return true;
        }
        return false;
    }


}

class Node{
    Node left, right;
    ArrayList<int[]> values;
    ArrayList<int[]> pending;
    public Node(Node left, int[] value, Node right) {
        this.left = left;
        this.values = new ArrayList<>(50);
        this.values.add(value);
        this.right = right;
        this.pending = new ArrayList<>(50);
    }
}