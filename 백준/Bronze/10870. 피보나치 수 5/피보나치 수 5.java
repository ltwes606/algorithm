import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {

    public static final int MAX_SIZE = 20;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int ordinal = Integer.parseInt(reader.readLine());
        reader.close();

        int[] dp = new int[MAX_SIZE + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= ordinal; i++) {
            dp[i] = dp[i - 2] + dp[i - 1];
        }
        System.out.println(dp[ordinal]);
    }
}