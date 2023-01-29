import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

class Main {

    private static List<List<Integer>> result;
    private static List<Integer> sequence;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int range = Integer.parseInt(tokenizer.nextToken());
        int size = Integer.parseInt(tokenizer.nextToken());
        sequence = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());
        reader.close();
        
        // 초기화
        Collections.sort(sequence);
        result = new ArrayList<>();

        dfs(0, size, new ArrayList<>());
        System.out.print(createOutputStatement());
    }

    private static void dfs(int start, int size,
            ArrayList<Integer> included) {
        // size만큼 추가되었다면 출력문에 추가
        if (included.size() == size) {
            if (!result.contains(included)) {
                result.add((List<Integer>) included.clone());
            }
            return;
        }

        // 숫자 추가
        for (int i = start; i < sequence.size(); i++) {
            Integer numberToAdd = sequence.get(i);

            included.add(numberToAdd);
            dfs(i + 1, size, included);
            // 탐색이 끝났다면 추가한 숫자 제거
            included.remove(included.size() - 1);
        }
    }

    private static String createOutputStatement() {
        StringBuilder outputStatement = new StringBuilder();
        for (List<Integer> line : result) {
            outputStatement.append(line.stream().map(Object::toString)
                    .collect(Collectors.joining(" ")) + "\n");
        }
        return outputStatement.toString();
    }
}