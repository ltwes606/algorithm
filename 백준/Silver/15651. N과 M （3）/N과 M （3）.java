import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {
    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        reader.close();
        int range = Integer.parseInt(tokenizer.nextToken());
        int size = Integer.parseInt(tokenizer.nextToken());

        // 초기화
        int[] sequence = new int[size];
        init(sequence);
        StringBuilder result = new StringBuilder();

        // 브루트 포스 출력
        while (!terminationCondition(sequence, range)) {
            // 출력문 추가
            result.append(getOutputStatement(sequence));

            // 증가
            up(sequence, range);
        }
        // 마지막 출력문
        result.append(getOutputStatement(sequence));

        // 출력
        System.out.print(result);

        // 종료 작업
        reader.close();
    }

    private static void up(int[] result, int max) {
        result[result.length - 1]++;
        for (int i = result.length - 1; i > 0; i--) {
            if (result[i] <= max) {
                return;
            }
            result[i] = 1;
            result[i - 1]++;
        }
    }

    private static StringBuilder getOutputStatement(int[] sequence) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sequence.length; i++) {
            result.append(sequence[i] + " ");
        }
        result.append("\n");
        return result;
    }

    private static boolean terminationCondition(int[] result, int range) {
        for (int i = 0; i < result.length; i++) {
            if (result[i] != range) {
                return false;
            }
        }
        return true;
    }

    private static void init(int[] result) {
        for (int i = 0; i < result.length; i++) {
            result[i] = 1;
        }
    }
}