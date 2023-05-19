import java.util.*;

class Solution {
    public int[] solution(int[] array, int[][] commands) {
        int[] result = new int[commands.length];
        for (int i = 0; i < commands.length; i++) {
            int[] subArray = getsubArray(array, commands[i][0] - 1, commands[i][1] - 1);
            Arrays.sort(subArray);
            result[i] = subArray[commands[i][2] - 1];
        }
        return result;
    }
    
    private static int[] getsubArray(int[] array, int start, int end) {
        return Arrays.copyOfRange(array, start, end + 1);
    }
}