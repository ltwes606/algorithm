class Solution {
    public int[] solution(int brown, int yellow) {
        int[] result = new int[2];
        
        int colSize = 0;
        int sum = brown + yellow;
        while (true) {
            colSize++;
            int rowSize = sum / colSize;
            if (rowSize * colSize != sum) {
                continue;
            }
            
            if (balance(brown, yellow, rowSize, colSize)) {
                result[0] = rowSize;
                result[1] = colSize;
                break;
            }
        }
        return result;
    }
    
    private static boolean balance(int brown, int yellow, int rowSize, int colSize) {
        brown -= colSize * 2;
        brown -= (rowSize - 2) * 2;
        yellow -= (rowSize - 2) * (colSize - 2);
        return (brown == 0) && (yellow == 0);
    }
}