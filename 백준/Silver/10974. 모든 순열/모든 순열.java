import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

class Main {

    private static StringBuilder stringBuilder = new StringBuilder();

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int size = Integer.parseInt(reader.readLine());

        // default false
        boolean[] visited = new boolean[size];
        int[] sequence = new int[size];
        // dfs
        dfs(sequence, visited, 0);

        // 출력
        System.out.println(stringBuilder);

        // 종료 작업
        reader.close();
    }

    private static void dfs(int[] sequence, boolean[] visited, int depth) {
        if (depth == sequence.length) {
            stringBuilder.append(
                    Arrays.stream(sequence).mapToObj(String::valueOf)
                            .collect(Collectors.joining(" "))
                            + "\n"
            );
        }

        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                visited[i] = true;
                sequence[depth] = i + 1;
                dfs(sequence, visited, depth + 1);
                visited[i] = false;
            }
        }
    }
}