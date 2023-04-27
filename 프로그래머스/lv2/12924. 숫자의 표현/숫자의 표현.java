class Solution {
    public int solution(int n) {
        int[] dp = new int[10_000 + 1];
        for (int i = 1; i <= n; i++) {
            dp[i] = dp[i - 1] + i;
        }
        
        int biggerIdx = 0;
        while (dp[biggerIdx] < n) {
            biggerIdx++;
        }
        int smallerIdx = 0;
        int result = 0;
        while(biggerIdx <= n && biggerIdx >= smallerIdx) {
            int value = dp[biggerIdx] - dp[smallerIdx];
            if (value == n) {
                result++;
                biggerIdx++;
                smallerIdx++;
            } else if (value < n) {
                biggerIdx++;
            } else { // value > n
                smallerIdx++;
            }
        }
        return result;
    }
}