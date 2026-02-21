import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/*

[[7,8],[12,16],[12,5],[1,8],[4,19],[3,15],[4,10],[9,16]]


[12,16] [3,15] [1,8]



 */
public class Main {
    public static void main(String[] args) {
        var solution = new _Solution();
        var res = solution.maxEnvelopes(new int[][]{{7,8},{12,16},{12,5},{1,8},{4,19},{3,15},{4,10},{9,16}});
        System.out.println(res);
    }
}



class _Solution{
    public int maxEnvelopes(int[][] envelopes) {
        int[] current = null;
        int[] temporal = null;
        int counter = 0;
        for (int i = 0; i < envelopes.length; i++) {
            var entered = false;
            for(var envelope : envelopes) {
                if(isSmaller(current, envelope) && isBigger(temporal, envelope)) {
                    temporal = envelope;
                    entered = true;
                }
            }
            if(entered) {
                counter++;
                current = temporal;
                temporal = null;
                System.out.println("["+current[0]+", "+current[1]+"]");
            }
        }

        return counter;
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

        if (sX == nX && sY == nY) {
            return false;
        }
        if (nX > sX && nY > sY) {
            return true;
        }
        if (nX == sX && nY > sY) {
            return true;
        }
        if (nX > sX) {
            return true;
        }


        return false;
    }
}