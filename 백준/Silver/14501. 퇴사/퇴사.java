import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int daysLeft = Integer.parseInt(reader.readLine());
        // 초기화

        int[] dp = new int[daysLeft + 1];

        // DP
        for (int i = 0; i < daysLeft; i++) {
            if (i > 1) {
                dp[i] = Math.max(dp[i], dp[i - 1]);
            }
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int afterDays = Integer.parseInt(tokenizer.nextToken());
            int price = Integer.parseInt(tokenizer.nextToken());
            if (i + afterDays <= daysLeft) {
                dp[i + afterDays] = Math.max(dp[i + afterDays], dp[i] + price);
            }
        }

        // 결과 출력
        System.out.println(Math.max(dp[dp.length - 1], dp[dp.length - 2]));

        // 종료 작업
        reader.close();
    }
}