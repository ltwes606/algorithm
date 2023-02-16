import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
        ArrayList<Integer[][]> maps = new ArrayList<>();
        while (true) {
            int col = reader.nextInt();
            int row = reader.nextInt();
            // 종료 조건
            if (col == 0 && row == 0) {
                break;
            }

            maps.add(getMap(row, col, reader));
        }

        // 섬 개수 구하기
        int[] result = new int[maps.size()];
        for (int i = 0; i < maps.size(); i++) {
            result[i] = findCount(maps.get(i));
        }

        // 결과 출력
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }

        // 종료 작업
        reader.close();
    }

    private static int findCount(Integer[][] map) {
        boolean[][] visited = new boolean[map.length][map[0].length];

        int result = 0;
        for (int iRow = 0; iRow < map.length; iRow++) {
            for (int iCol = 0; iCol < map[iRow].length; iCol++) {
                if (map[iRow][iCol] == 0 || visited[iRow][iCol]) {
                    continue;
                }

                dfs(map, iRow, iCol, visited);
                result++;
            }
        }
        return result;
    }

    private static void dfs(Integer[][] map, int row, int col, boolean[][] visited) {
        if (!validRange(map, row, col) || map[row][col] == 0 || visited[row][col]) {
            return;
        }

        visited[row][col] = true;
        // 가로
        dfs(map, row - 1, col, visited);
        dfs(map, row + 1, col, visited);

        // 세로
        dfs(map, row, col - 1, visited);
        dfs(map, row, col + 1, visited);

        // 대각
        dfs(map, row - 1, col - 1, visited);
        dfs(map, row - 1, col + 1, visited);
        dfs(map, row + 1, col - 1, visited);
        dfs(map, row + 1, col + 1, visited);
    }

    private static boolean validRange(Integer[][] map, int row, int col) {
        if (row < 0 || map.length <= row) {
            return false;
        }
        if (col < 0 || map[0].length <= col) {
            return false;
        }
        return true;
    }

    private static Integer[][] getMap(int row, int col, TokenizerReader reader) {
        Integer[][] result = new Integer[row][col];
        for (int iRow = 0; iRow < result.length; iRow++) {
            for (int iCol = 0; iCol < result[iRow].length; iCol++) {
                result[iRow][iCol] = reader.nextInt();
            }
        }
        return result;
    }
}