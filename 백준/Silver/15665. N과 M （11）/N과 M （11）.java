import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

class Main {

    private static final StringBuilder outputStatement = new StringBuilder();

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        // numbers size
        tokenizer.nextToken();
        int sequenceSize = Integer.parseInt(tokenizer.nextToken());
        int[] numbers = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        // 수열 정렬
        Arrays.sort(numbers);

        // dfs
        dfs(0, sequenceSize, new ArrayList<>(), numbers);

        // 출력
        System.out.print(outputStatement);

        // 종료 작업
        reader.close();
    }

    private static void dfs(
            int currentSize, int sequenceSize, ArrayList<Integer> sequence, int[] numbers) {
        if (currentSize == sequenceSize) {
            for (int i = 0; i < sequence.size(); i++) {
                outputStatement.append(sequence.get(i) + " ");
            }
            outputStatement.append("\n");
            return;
        }

        int prev = -1;
        for (int i = 0; i < numbers.length; i++) {
            if (prev == numbers[i]) {
                continue;
            }

            sequence.add(numbers[i]);
            dfs(currentSize + 1, sequenceSize, sequence, numbers);
            prev = sequence.get(currentSize);
            sequence.remove(currentSize);
        }
    }
}