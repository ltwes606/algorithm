import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

public class Main {

    static class TokenizerReader {

        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public TokenizerReader(InputStream inputStream) {
            reader = new BufferedReader(new InputStreamReader(inputStream));
        }

        public String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        public int nextInt() {
            return Integer.parseInt(next());
        }

        public void close() {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // 입력 설정
        TokenizerReader reader = new TokenizerReader(System.in);

        // 입력
        int size = reader.nextInt();
        int[][] inputs = new int[size][size];
        for (int row = 0; row < inputs.length; row++) {
            char[] line = reader.next().toCharArray();
            for (int col = 0; col < inputs[row].length; col++) {
                inputs[row][col] = line[col] - '0';
            }
        }

        // 초기화
        int[][] result = inputs.clone();
        boolean[][] visited = new boolean[size][size];
        int maxValue = 0;

        // bfs
        for (int row = 0; row < result.length; row++) {
            for (int col = 0; col < result[row].length; col++) {
                if (result[row][col] == 0 || visited[row][col] == true) {
                    continue;
                }

                dfs(result, row, col, visited, ++maxValue);
            }
        }

        // 정렬
        ArrayList<Integer> counts = new ArrayList<>();
        for (int i = 1; i <= maxValue; i++) {
            counts.add(findCountByValue(result, i));
        }
        Collections.sort(counts);

        // 결과 출력
        System.out.println(counts.size());
        for (int count : counts) {
            System.out.println(count);
        }
        
        // 종료 작업
        reader.close();
    }

    private static int findCountByValue(int[][] list, int value) {
        int result = 0;

        for (int i = 0; i < list.length; i++) {
            for (int j = 0; j < list[i].length; j++) {
                if (list[i][j] == value) {
                    result++;
                }
            }
        }
        return result;
    }

    private static void dfs(int[][] list, int row, int col, boolean[][] visited, int value) {
        if (!validRange(list, row, col) || list[row][col] == 0 || visited[row][col]) {
            return;
        }

        visited[row][col] = true;
        list[row][col] = value;
        dfs(list, row - 1, col, visited, value);
        dfs(list, row, col - 1, visited, value);
        dfs(list, row, col + 1, visited, value);
        dfs(list, row + 1, col, visited, value);
    }

    private static boolean validRange(int[][] list, int row, int col) {
        if (row < 0 || list.length <= row) {
            return false;
        }
        if (col < 0 || list[row].length <= col) {
            return false;
        }
        return true;
    }
}