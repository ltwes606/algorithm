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

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    private static int result = Integer.MIN_VALUE;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int chessboardSize = reader.nextInt();

        int result = countQueensInAllColumns(chessboardSize);

        printResult(result);

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static int countQueensInAllColumns(int chessboardSize) {
        int result = 0;

        boolean[][] chessboard = new boolean[chessboardSize][chessboardSize];
        boolean[] visitedRows = new boolean[chessboardSize];
        boolean[] visitedCols = new boolean[chessboardSize];
        for (int i = 0; i < chessboardSize; i++) {
            chessboard[i][0] = true;
            visitedRows[i] = true;

            result += dfs(chessboard, visitedRows, 1, 0);

            chessboard[i][0] = false;
            visitedRows[i] = false;
        }
        return result;
    }

    private static int dfs(boolean[][] chessboard, boolean[] visitedRows,
            int column, int count) {
        if (column == chessboard[0].length) {
            return count + 1;
        }

        for (int i = 0; i < chessboard.length; i++) {
            if (visitedRows[i]) {
                continue;
            }
            if (existsQueen(chessboard, i, column, -1, 1)
                    || existsQueen(chessboard, i, column, 1, 1)
                    || existsQueen(chessboard, i, column, 1, -1)
                    || existsQueen(chessboard, i, column, -1, -1)) {
                continue;
            }

            chessboard[i][column] = true;
            visitedRows[i] = true;

            count = dfs(chessboard, visitedRows, column + 1, count);

            chessboard[i][column] = false;
            visitedRows[i] = false;
        }
        return count;
    }

    private static boolean existsQueen(boolean[][] chessboard, int row, int column,
            int rowIncrease, int colIncrease) {
        int iRow = row + rowIncrease;
        int iCol = column + colIncrease;
        while (validRange(chessboard, iRow, iCol)) {
            if (chessboard[iRow][iCol]) {
                return true;
            }
            iRow += rowIncrease;
            iCol += colIncrease;
        }
        return false;
    }

    private static boolean validRange(boolean[][] chessboard, int row, int col) {
        if (row < 0 || chessboard.length <= row) {
            return false;
        }
        if (col < 0 || chessboard[row].length <= col) {
            return false;
        }
        return true;
    }

    private static void printResult(int result) {
        writer.println(String.valueOf(result));
    }
}