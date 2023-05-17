class Solution {
    public int[][] solution(int[][] arr1, int[][] arr2) {
        int[][] result = new int[arr1.length][arr2[0].length];
        for (int index1 = 0; index1 < result.length; index1++) {
            for (int index2 = 0; index2 < result[index1].length; index2++) {
                result[index1][index2] = product(arr1, arr2, index1, index2);
            }
        }
        return result;
    }
    
    private static int product(int[][] arr1, int[][] arr2, int index1, int index2) {
        int result = 0;
        for (int i = 0; i < arr1[index1].length; i++) {
            result += arr1[index1][i] * arr2[i][index2];
        }
        return result;
    }
}