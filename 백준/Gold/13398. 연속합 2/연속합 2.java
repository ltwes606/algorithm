import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        //입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        reader.readLine();
        int[] input = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        // DP
        int[][] dp = new int[2][input.length];
        dp[0][0] = dp[1][0] = input[0];

        for (int i = 1; i < input.length; i++) {
            dp[0][i] = Math.max(input[i] + dp[0][i - 1], input[i]);
            dp[1][i] = Math.max(dp[0][i - 1], dp[1][i - 1] + input[i]);
        }

        // 최대값 구하기
        int result = -1000;
        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++) {
                if (result < dp[i][j]) {
                    result = dp[i][j];
                }
            }
        }

        // 결과 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
    }

}