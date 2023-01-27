import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

class Main {

    private static StringBuilder dfs(int start, int range, int size,
            ArrayList<Integer> included,
            StringBuilder outputStatement) {
        // size만큼 추가되었다면 출력문에 추가
        if (included.size() == size) {
            outputStatement.append(included.stream().map(Object::toString)
                    .collect(Collectors.joining(" ")) + "\n");
            return outputStatement;
        }

        // 숫자 추가
        for (int i = start; i <= range; i++) {
            included.add(i);
            dfs(i, range, size, included, outputStatement);
            // 탐색이 끝났다면 추가한 숫자 제거
            included.remove(included.size() - 1);
        }
        return outputStatement;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        reader.close();
        int range = Integer.parseInt(tokenizer.nextToken());
        int size = Integer.parseInt(tokenizer.nextToken());
        StringBuilder outputStatement =
                dfs(1, range, size, new ArrayList<Integer>(), new StringBuilder());
        System.out.print(outputStatement);
    }
}