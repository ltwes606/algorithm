import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {

    public static final int MODULAR = 9901;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int size = Integer.parseInt(reader.readLine());
        reader.close();

        int[][] dp = new int[size][3];
        for (int i = 0; i < dp[0].length; i++) {
            dp[0][i] = 1;
        }

        for (int i = 1; i < dp.length; i++) {
            dp[i][0] = (dp[i - 1][0] + dp[i - 1][1] + dp[i - 1][2]) % MODULAR;
            dp[i][1] = (dp[i - 1][0] + dp[i - 1][2]) % MODULAR;
            dp[i][2] = (dp[i - 1][0] + dp[i - 1][1]) % MODULAR;
        }
        System.out.println(
                (dp[dp.length - 1][0] + dp[dp.length - 1][1] + dp[dp.length - 1][2]) % MODULAR);
    }
}
