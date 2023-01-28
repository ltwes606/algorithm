import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

class Main {

    private static final StringBuilder outputStatement = new StringBuilder();
    private static List<Integer> sequence;

    private static void dfs(int start, int size,
            ArrayList<Integer> included) {
        // size만큼 추가되었다면 출력문에 추가
        if (included.size() == size) {
            outputStatement.append(included.stream().map(Object::toString)
                    .collect(Collectors.joining(" ")) + "\n");
            return;
        }

        // 중복되지 않는 수만 추출
        List<Integer> onlyNumbers = new ArrayList<>(new HashSet<>(sequence));
        Collections.sort(onlyNumbers);

        // 숫자 추가
        for (int i = start; i < onlyNumbers.size(); i++) {
            Integer numberToAdd = onlyNumbers.get(i);

            included.add(numberToAdd);
            sequence.remove(numberToAdd);
            dfs(0, size, included);
            // 탐색이 끝났다면 추가한 숫자 제거
            included.remove(included.size() - 1);
            sequence.add(numberToAdd);
        }
    }

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

        // 받은 수열 정렬
        Collections.sort(sequence);

        dfs(0, size, new ArrayList<Integer>());
        System.out.print(outputStatement);
    }
}