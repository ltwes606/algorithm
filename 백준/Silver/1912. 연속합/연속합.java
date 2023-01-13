import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.readLine();
        int[] numbers = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt)
                .toArray();
        reader.close();

        int[] dp = new int[numbers.length];

        dp[0] = numbers[0];
        int result = dp[0];
        for (int index = 1; index < dp.length; index++) {
            dp[index] = Math.max(dp[index - 1] + numbers[index], numbers[index]);
            result = Math.max(result, dp[index]);
        }

        System.out.println(result);
    }
}
