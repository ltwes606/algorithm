import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    private static final int MAX_SIZE = 30;

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int size = Integer.parseInt(reader.readLine());

        // 초기값 설정
        int[] dp = new int[MAX_SIZE + 1];
        dp[0] = 1;
        dp[2] = 3;

        /*
        DP
        i가 짝수일 때만 수행
        */
        for (int i = 4; i <= size; i += 2) {
            // 앞에 3x2 경우의 수가 나왔을 때
            dp[i] = dp[2] * dp[i - 2];

            // 앞에 3x2가 아닌 경우의 수가 나왔을 때
            for (int j = 4; j <= i; j += 2) {
                dp[i] += 2 * dp[i - j];
            }
        }

        // 결과 출력
        System.out.println(dp[size]);

        // 종료 작업
        reader.close();
    }
}