import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(reader.readLine());

        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            int[][] scores = new int[2][];
            reader.readLine();
            for (int j = 0; j < scores.length; j++) {
                scores[j] = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt)
                        .toArray();
            }

            int[][] dp = new int[2][scores[0].length + 1];
            dp[0][1] = scores[0][0];
            dp[1][1] = scores[1][0];
            for (int j = 2; j < dp[0].length; j++) {
                dp[0][j] = scores[0][j - 1] + max(dp[1][j - 1], dp[0][j - 2], dp[1][j - 2]);
                dp[1][j] = scores[1][j - 1] + max(dp[0][j - 1], dp[0][j - 2], dp[1][j - 2]);
            }

            result[i] = getResult(dp);
        }
        reader.close();

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }

    private static int max(int a, int b, int c) {
        if (a > b) {
            return a > c ? a : c;
        }
        return b > c ? b : c;
    }

    private static int getResult(int[][] dp) {
        int[] maxValues = new int[2];
        for (int i = 0; i < maxValues.length; i++) {
            maxValues[i] = dp[i][dp[i].length - 1] > dp[i][dp[i].length - 2] ?
                    dp[i][dp[i].length - 1] : dp[i][dp[i].length - 2];
        }

        int result = Integer.MIN_VALUE;
        for (int i = 0; i < maxValues.length; i++) {
            result = maxValues[i] > result  ? maxValues[i] : result;
        }
        return result;
    }
}