import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

class Main {

    private static void dfs(int start, int range, int size, ArrayList<Integer> included) {
        // size만큼 추가되었다면 출력 및 return
        if (included.size() == size) {
            System.out.println(included.stream().map(Object::toString)
                    .collect(Collectors.joining(" ")));
            return;
        }

        // 포함되지 않은 숫자 추가
        for (int i = start; i <= range; i++) {
            // 포함된 숫자라면 다음 숫자
            if (included.contains(i)) {
                continue;
            }

            // 포함되지 않았다면 추가하여 탐색
            included.add(i);
            dfs(i + 1, range, size, included);
            // 탐색이 끝났다면 추가한 숫자 제거
            included.remove(included.size() - 1);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        reader.close();
        int range = Integer.parseInt(tokenizer.nextToken());
        int size = Integer.parseInt(tokenizer.nextToken());
        dfs(1, range, size, new ArrayList<Integer>());
    }
}