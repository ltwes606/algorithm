import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    private static int result = 0;

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int size = Integer.parseInt(tokenizer.nextToken());
        int targetNumber = Integer.parseInt(tokenizer.nextToken());
        int[] sequence = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        // dfs
        dfs(sequence, 0, 0, targetNumber);

        // 결과 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
    }

    private static void dfs(int[] sequence, int startIndex, int sum, int targetNumber) {
        for (int i = startIndex; i < sequence.length; i++) {
            sum += sequence[i];
            if (sum == targetNumber) {
                result++;
            }
            dfs(sequence, i + 1, sum, targetNumber);
            sum -= sequence[i];
        }
    }
}