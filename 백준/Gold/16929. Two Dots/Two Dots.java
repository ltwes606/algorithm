import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    static class TokenizerReader {

        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        public TokenizerReader(InputStream inputStream) {
            this.reader = new BufferedReader(new InputStreamReader(inputStream));
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

        public Integer nextInt() {
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

    static class BuilderWriter {

        private final BufferedWriter writer;
        private final StringBuilder builder;

        public BuilderWriter(OutputStream outputStream) {
            this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            this.builder = new StringBuilder();
        }

        public void print(String str) {
            builder.append(str);
        }

        public void println(String str) {
            print(str + "\n");
        }

        public void close() {
            try {
                writer.write(builder.toString());
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            builder.delete(0, builder.length());
        }
    }

    static int[] dyArray = {-1, 0, 0, 1};
    static int[] dxArray = {0, -1, 1, 0};

    public static void main(String[] args) {
        // 입출력 설정
        TokenizerReader reader = new TokenizerReader(System.in);
        BuilderWriter writer = new BuilderWriter(System.out);

        // 입력
        int row = reader.nextInt();
        int column = reader.nextInt();
        char[][] map = new char[row][column];
        for (int iRow = 0; iRow < row; iRow++) {
            String line = reader.next();
            for (int iCol = 0; iCol < column; iCol++) {
                map[iRow][iCol] = line.charAt(iCol);
            }
        }

        // dfs
        boolean result = false;
        for (int iRow = 0; iRow < map.length; iRow++) {
            for (int iCol = 0; iCol < map[iRow].length; iCol++) {
                boolean[][] visited = new boolean[map.length][map[iRow].length];
                result = dfs(map, iRow, iCol, iRow, iCol, visited);
                if (result) {
                    break;
                }
            }
            if (result) {
                break;
            }
        }

        // 결과 출력
        if (result) {
            writer.println("Yes");
        } else {
            writer.println("No");
        }

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static boolean dfs(char[][] map, int prevRow, int prevCol, int currentRow,
            int currentCol, boolean[][] visited) {
        if (visited[currentRow][currentCol]) {
            return true;
        }
        visited[currentRow][currentCol] = true;

        for (int i = 0; i < dyArray.length; i++) {
            int searchRow = currentRow + dyArray[i];
            int searchCol = currentCol + dxArray[i];
            if (!validRange(map, searchRow, searchCol)) {
                continue;
            }
            if ((prevRow == searchRow && prevCol == searchCol) ||
                    map[currentRow][currentCol] != map[searchRow][searchCol]) {
                continue;
            }
            if (dfs(map, currentRow, currentCol, searchRow, searchCol, visited)) {
                return true;
            }
        }
        visited[currentRow][currentCol] = false;
        return false;
    }

    private static boolean validRange(char[][] map, int row, int col) {
        if (row < 0 || map.length <= row) {
            return false;
        }
        if (col < 0 || map[row].length <= col) {
            return false;
        }
        return true;
    }
}