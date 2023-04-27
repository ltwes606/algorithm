import java.util.*;

class Solution
{
    public int solution(int []A, int []B)
    {
        int[] sortedA = A.clone();
        Arrays.sort(sortedA);
        int[] sortedB = B.clone();
        Arrays.sort(sortedB);
        
        int result = 0;
        for (int i = 0; i < sortedA.length; i++) {
            result += sortedA[i] * sortedB[sortedB.length - 1 - i];
        }
        return result;
    }
}