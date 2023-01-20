import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.Arrays;

class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(reader.readLine());
        int[][] inputs = new int[size][size];

        for (int i = 0; i < inputs.length; i++) {
            int[] input = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt).toArray();
            for (int j = 0; j <= i; j++) {
                inputs[i][j] = input[j];
            }
        }
        reader.close();

        int[][] dp = new int[size][size];
        dp[0][0] = inputs[0][0];
        for (int i = 1; i < inputs.length; i++) {
            dp[i][0] = dp[i - 1][0] + inputs[i][0];
            for (int j = 1; j < i; j++) {
                dp[i][j] = Math.max(dp[i - 1][j - 1], dp[i - 1][j]) + inputs[i][j];
            }
            dp[i][i] = dp[i - 1][i - 1] + inputs[i][i];
        }

        int result = Integer.MIN_VALUE;
        for (int i = 0; i < dp[dp.length - 1].length; i++) {
            result = Math.max(result, dp[dp.length - 1][i]);
        }
        System.out.println(result);
    }
}
