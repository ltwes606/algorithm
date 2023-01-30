import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

        // 수열 정렬 및 중복값 제거
//        Arrays.sort(numbers);
        Integer[] integers = Arrays.stream(numbers).boxed().collect(Collectors.toSet())
                .stream().toArray(Integer[]::new);
        Arrays.sort(integers);

        // dfs
        dfs(0, 0, sequenceSize, new ArrayList<>(), integers);

        // 출력
        System.out.print(outputStatement);

        // 종료 작업
        reader.close();
    }

    private static void dfs(
            int start, int currentSize, int sequenceSize, ArrayList<Integer> sequence, Integer[] integers) {
        if (currentSize == sequenceSize) {
            for (int i = 0; i < sequence.size(); i++) {
                outputStatement.append(sequence.get(i) + " ");
            }
            outputStatement.append("\n");
            return;
        }

        for (int i = start; i < integers.length; i++) {
            sequence.add(integers[i]);

            dfs(i, currentSize + 1, sequenceSize, sequence, integers);
            sequence.remove(currentSize);
        }
    }
}