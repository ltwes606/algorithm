class Solution {
    public int[] solution(int brown, int yellow) {
        int[] result = new int[2];
        
        int sum = brown + yellow;
        int colSize = 1;
        while (!balance(brown, yellow, sum / colSize, colSize)) {
            colSize++;
        }
        
        result[0] = sum / colSize;
        result[1] = colSize;
        return result;
    }
    
    private static boolean balance(int brown, int yellow, int rowSize, int colSize) {
        brown -= colSize * 2;
        brown -= (rowSize - 2) * 2;
        yellow -= (rowSize - 2) * (colSize - 2);
        return (brown == 0) && (yellow == 0);
    }
}