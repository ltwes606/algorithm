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

    public static final int MAX_COST = 1000;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(reader.readLine());

        int[][] inputs = new int[size + 1][];
        inputs[0] = new int[Color.values().length];
        for (int i = 1; i < inputs.length; i++) {
            inputs[i] = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt)
                    .toArray();
        }

        int[][][] dp = new int[Color.values().length][inputs.length][Color.values().length];
        for (Color firstColor : Color.values()) {
            for (Color color : Color.values()) {
                dp[firstColor.ordinal()][1][color.ordinal()] = inputs[1][color.ordinal()];
                if (firstColor.ordinal() != color.ordinal()) {
                    dp[firstColor.ordinal()][1][color.ordinal()] = 2 * MAX_COST + 1;
                }
            }

            for (int i = 2; i < inputs.length; i++) {
                dp[firstColor.ordinal()][i][Color.RED.ordinal()] =
                        inputs[i][Color.RED.ordinal()] + Math.min(
                                dp[firstColor.ordinal()][i - 1][Color.GREEN.ordinal()],
                                dp[firstColor.ordinal()][i - 1][Color.BLUE.ordinal()]);
                dp[firstColor.ordinal()][i][Color.GREEN.ordinal()] =
                        inputs[i][Color.GREEN.ordinal()] + Math.min(
                                dp[firstColor.ordinal()][i - 1][Color.RED.ordinal()],
                                dp[firstColor.ordinal()][i - 1][Color.BLUE.ordinal()]);
                dp[firstColor.ordinal()][i][Color.BLUE.ordinal()] =
                        inputs[i][Color.BLUE.ordinal()] + Math.min(
                                dp[firstColor.ordinal()][i - 1][Color.GREEN.ordinal()],
                                dp[firstColor.ordinal()][i - 1][Color.RED.ordinal()]);
            }
        }

        int result = (inputs.length - 1) * MAX_COST + 1;
        for (Color firstColor : Color.values()) {
            for (Color lastColor : Color.values()) {
                if (firstColor == lastColor) {
                    continue;
                }
                if (result > dp[firstColor.ordinal()][inputs.length - 1][lastColor.ordinal()]) {
                    result = dp[firstColor.ordinal()][inputs.length - 1][lastColor.ordinal()];
                }
            }
        }
        System.out.println(result);
    }
}