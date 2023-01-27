import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

class Main {

    private static final StringBuilder outputStatement = new StringBuilder();
    private static int[] sequence;

    private static void dfs(int start, int range, int size,
            ArrayList<Integer> included) {
        // size만큼 추가되었다면 출력문에 추가
        if (included.size() == size) {
            outputStatement.append(included.stream().map(Object::toString)
                    .collect(Collectors.joining(" ")) + "\n");
            return;
        }

        // 숫자 추가
        for (int i = start; i < range; i++) {
            Integer numberToAdd = sequence[i];
            if (included.contains(numberToAdd)) {
                continue;
            }

            included.add(numberToAdd);
            dfs(0, range, size, included);
            // 탐색이 끝났다면 추가한 숫자 제거
            included.remove(included.size() - 1);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int range = Integer.parseInt(tokenizer.nextToken());
        int size = Integer.parseInt(tokenizer.nextToken());
        sequence = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        reader.close();

        // 받은 수열 정렬
        Arrays.sort(sequence);

        dfs(0, range, size, new ArrayList<Integer>());
        System.out.print(outputStatement);
    }
}