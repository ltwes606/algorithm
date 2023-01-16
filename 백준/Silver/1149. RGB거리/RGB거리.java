import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

enum Color {
    RED,
    GREEN,
    BLUE;
}

class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(reader.readLine());

        int[][] dp = new int[size + 1][];
        dp[0] = new int[Color.values().length];

        for (int i = 1; i < dp.length; i++) {
            dp[i] = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt)
                    .toArray();
            dp[i][Color.RED.ordinal()] += Math.min(
                    dp[i - 1][Color.GREEN.ordinal()], dp[i - 1][Color.BLUE.ordinal()]);
            dp[i][Color.GREEN.ordinal()] += Math.min(
                    dp[i - 1][Color.RED.ordinal()], dp[i - 1][Color.BLUE.ordinal()]);
            dp[i][Color.BLUE.ordinal()] += Math.min(
                    dp[i - 1][Color.RED.ordinal()], dp[i - 1][Color.GREEN.ordinal()]);
        }
        reader.close();

        int result = dp[size][0];
        for (int i = 1; i < Color.values().length; i++) {
            if (result > dp[size][i]) {
                result = dp[size][i];
            }
        }
        System.out.println(result);
    }
}