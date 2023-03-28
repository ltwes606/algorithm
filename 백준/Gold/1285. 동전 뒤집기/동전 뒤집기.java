import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static final boolean H = false;
    public static final boolean T = false;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        boolean[][] map = createMap(size);

        int result = rowDfs(map, 0);

        printResult(result);
        // 종료 작업
        close();
    }

    private static boolean[][] createMap(int size) {
        boolean[][] map = new boolean[size][size];
        for (int row = 0; row < size; row++) {
            String line = reader.next();
            for (int col = 0; col < size; col++) {
                if (line.charAt(col) == 'T') {
                    map[row][col] = true;
                }
            }
        }
        return map;
    }

    private static int rowDfs(boolean[][] map, int row) {
        int rowSize = map.length;
        int colSize = map[0].length;

        int result = Integer.MAX_VALUE;
        if (rowSize == row) {
            result = 0;
            for (int col = 0; col < colSize; col++) {
                result += countTrueInColumn(map, col);
            }
            return result;
        }

        for (int col = 0; col < colSize; col++) {
            int trueCount = countTrueInColumn(map, col);
            if (trueCount > rowSize / 2) {
                flipCoinInColumn(map, col);
            }
        }
        result = Math.min(result, rowDfs(map, row + 1));

        flipCoinInRow(map, row);
        for (int col = 0; col < colSize; col++) {
            int trueCount = countTrueInColumn(map, col);
            if (trueCount > rowSize / 2) {
                flipCoinInColumn(map, col);
            }
        }
        result = Math.min(result, rowDfs(map, row + 1));
        return result;
    }

    private static int countTrueInColumn(boolean[][] map, int col) {
        int count = 0;
        for (int row = 0; row < map.length; row++) {
            if (map[row][col]) {
                count++;
            }
        }
        return count;
    }

    private static void flipCoinInColumn(boolean[][] map, int col) {
        for (int row = 0; row < map.length; row++) {
            map[row][col] = !map[row][col];
        }
    }

    private static void flipCoinInRow(boolean[][] map, int row) {
        for (int col = 0; col < map[row].length; col++) {
            map[row][col] = !map[row][col];
        }
    }

    private static void printResult(int result) {
        writer.println(String.valueOf(result));
    }

    private static void close() {
        reader.close();
        writer.close();
    }

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
                    throw new RuntimeException(e);
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
                throw new RuntimeException(e);
            }
        }
    }

    static class BuilderWriter {

        private final BufferedWriter writer;
        private final StringBuilder builder;

        public BuilderWriter(OutputStream outputStream) {
            this.writer = new BufferedWriter(new OutputStreamWriter(outputStream));
            builder = new StringBuilder();
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
                throw new RuntimeException(e);
            }
        }
    }
}