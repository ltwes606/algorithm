import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // cycle
        while (true) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int size = Integer.parseInt(tokenizer.nextToken());
            if (size == 0) {
                break;
            }

            int[] numbers = new int[size];
            for (int i = 0; i < numbers.length; i++) {
                numbers[i] = Integer.parseInt(tokenizer.nextToken());
            }

            StringBuilder result = new StringBuilder();
            dfs(numbers, 6, 0, new ArrayList<>(), result);

            // 결과 출력
            System.out.println(result);
        }

        // 종료 작업
        reader.close();
    }

    private static void dfs(int[] sources, int size, int startIndex,
            ArrayList<Integer> destination, StringBuilder result) {
        if (destination.size() == size) {
            result.append(destination.stream().map(String::valueOf)
                    .collect(Collectors.joining(" ")) + "\n");
        }

        for (int i = startIndex; i < sources.length; i++) {
            destination.add(sources[i]);
            dfs(sources, size, i + 1, destination, result);
            destination.remove(destination.size() - 1);
        }
    }
}