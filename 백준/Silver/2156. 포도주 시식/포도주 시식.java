import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int[][] dp = new int[Integer.parseInt(reader.readLine())][3];
        int firstAmount = Integer.parseInt(reader.readLine());
        dp[0][2] = firstAmount;
        dp[0][1] = firstAmount;
        for (int i = 1; i < dp.length; i++) {
            int amount = Integer.parseInt(reader.readLine());
            dp[i][2] = dp[i - 1][1] + amount;
            dp[i][1] = dp[i - 1][0] + amount;
            dp[i][0] = max(dp[i - 1][2], dp[i - 1][1], dp[i - 1][0]);
        }

        int result = Integer.MIN_VALUE;
        for (int i = 0; i < dp[dp.length - 1].length; i++) {
            result = result < dp[dp.length - 1][i] ? dp[dp.length - 1][i] : result;
        }
        System.out.println(result);
    }

    private static int max(int a, int b, int c) {
        if (a > b) {
            return a > c ? a : c;
        }
        return b > c ? b : c;
    }
}