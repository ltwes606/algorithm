import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
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

    static class Coordinates {

        private final int row;
        private final int col;

        public Coordinates(int x, int y) {
            this.row = x;
            this.col = y;
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }
    }

    private static TokenizerReader reader;
    private static BuilderWriter writer;
    public static final int SUDOKU_SIZE = 9;

    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = SUDOKU_SIZE;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int[][] sudoku = getSudoku();

        // 빈 칸 찾기
        List<Coordinates> coordinatesList = getBlanks(sudoku);

        // dfs
        boolean[][][] checkedSquares = getCheckedSquares(sudoku);
        boolean[][] checkedRows = getCheckedRows(sudoku);
        boolean[][] checkedCols = getCheckedCols(sudoku);
        dfs(sudoku, coordinatesList, 0, checkedRows, checkedCols, checkedSquares);

        // 결과 출력
        printSudoku(sudoku);

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static int[][] getSudoku() {
        int[][] sudoku = new int[SUDOKU_SIZE][SUDOKU_SIZE];
        for (int iRow = 0; iRow < sudoku.length; iRow++) {
            for (int iCol = 0; iCol < sudoku[iRow].length; iCol++) {
                sudoku[iRow][iCol] = reader.nextInt();
            }
        }
        return sudoku;
    }

    private static List<Coordinates> getBlanks(int[][] sudoku) {
        List<Coordinates> result = new ArrayList<>();
        for (int iRow = 0; iRow < sudoku.length; iRow++) {
            for (int iCol = 0; iCol < sudoku[iRow].length; iCol++) {
                if (sudoku[iRow][iCol] == 0) {
                    result.add(new Coordinates(iRow, iCol));
                }
            }
        }
        return result;
    }

    private static boolean[][] getCheckedRows(int[][] sudoku) {
        boolean[][] result = new boolean[SUDOKU_SIZE][SUDOKU_SIZE + 1];

        for (int iRow = 0; iRow < sudoku.length; iRow++) {
            for (int iCol = 0; iCol < sudoku[iRow].length; iCol++) {
                int n = sudoku[iRow][iCol];
                result[iRow][n] = true;
            }
        }
        return result;
    }

    private static boolean[][] getCheckedCols(int[][] sudoku) {
        boolean[][] result = new boolean[SUDOKU_SIZE][SUDOKU_SIZE + 1];

        for (int iRow = 0; iRow < sudoku.length; iRow++) {
            for (int iCol = 0; iCol < sudoku[iRow].length; iCol++) {
                int n = sudoku[iRow][iCol];
                result[iCol][n] = true;
            }
        }
        return result;
    }

    private static boolean[][][] getCheckedSquares(int[][] sudoku) {
        int sudokuSizeSqrt = (int) Math.sqrt(SUDOKU_SIZE);
        boolean[][][] result = new boolean[sudokuSizeSqrt][sudokuSizeSqrt][MAX_NUMBER + 1];

        for (int iRow = 0; iRow < sudoku.length; iRow++) {
            for (int iCol = 0; iCol < sudoku[iRow].length; iCol++) {
                int n = sudoku[iRow][iCol];
                int sqrtRow = iRow / sudokuSizeSqrt;
                int sqrtCol = iCol / sudokuSizeSqrt;
                result[sqrtRow][sqrtCol][n] = true;
            }
        }
        return result;
    }

    private static boolean dfs(int[][] sudoku, List<Coordinates> list, int listIndex,
            boolean[][] checkedRows, boolean[][] checkedCols, boolean[][][] checkedSquares) {
        if (list.size() == listIndex) {
            return true;
        }

        boolean result = false;

        Coordinates coordinates = list.get(listIndex);
        int row = coordinates.getRow();
        int col = coordinates.getCol();
        int sudokuSizeSqrt = (int) Math.sqrt(SUDOKU_SIZE);
        int squareRow = row / sudokuSizeSqrt;
        int squareCol = col / sudokuSizeSqrt;

        for (int n = MIN_NUMBER; n <= MAX_NUMBER; n++) {
            if (checkedRows[row][n]
                    || checkedCols[col][n]
                    || checkedSquares[squareRow][squareCol][n]) {
                continue;
            }

            checkedRows[row][n] = true;
            checkedCols[col][n] = true;
            checkedSquares[squareRow][squareCol][n] = true;

            sudoku[row][col] = n;

            if (dfs(sudoku, list, listIndex + 1, checkedRows, checkedCols, checkedSquares)) {
                result = true;
                break;
            }

            checkedRows[row][n] = false;
            checkedCols[col][n] = false;
            checkedSquares[squareRow][squareCol][n] = false;
        }
        return result;
    }

    private static void printSudoku(int[][] sudoku) {
        for (int iRow = 0; iRow < sudoku.length; iRow++) {
            for (int iCol = 0; iCol < sudoku[iRow].length; iCol++) {
                writer.print(sudoku[iRow][iCol] + " ");
            }
            writer.println("");
        }
    }
}