import java.lang.*;

class Solution {
    public int solution(int[][] triangle) {
        for (int i1 = 1; i1 < triangle.length; i1++) {
            for (int i2 = 0; i2 < triangle[i1].length; i2++) {
                int prevValue1 = 0;
                if (i2 > 0) {
                    prevValue1 = triangle[i1 - 1][i2 - 1];
                }
                int prevValue2 = 0;
                if (i2 < triangle[i1 - 1].length) {
                    prevValue2 = triangle[i1 - 1][i2];
                }
                int maxValue = Math.max(prevValue1, prevValue2);
                triangle[i1][i2] += maxValue;
            }
        }
        
        int result = 0;
        for (int i = 0; i < triangle[triangle.length - 1].length; i++) {
            if (result < triangle[triangle.length - 1][i]) {
                result = triangle[triangle.length - 1][i];
            }
        }
        return result;
    }
}