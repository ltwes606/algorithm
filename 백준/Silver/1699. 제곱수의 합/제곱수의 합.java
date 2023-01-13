import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int number = Integer.parseInt(reader.readLine());
        reader.close();

        int[] dp = new int[number + 1];
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) {
            dp[i] = i;
            int iSquareRoot = (int) Math.sqrt(i);
            for (int a = 1;  a <= iSquareRoot; a++) {
                int aSquare = (int) Math.pow(a, 2);
                dp[i] = Math.min(dp[i], dp[i - aSquare] + 1);
            }
        }

        System.out.println(dp[number]);
    }
}
