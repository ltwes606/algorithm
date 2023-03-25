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

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    private static int OPERATION_SIZE = 3;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int rowSize = reader.nextInt();
        int colSize = reader.nextInt();

        int[][] matrixA = createMatrix(rowSize, colSize);
        int[][] matrixB = createMatrix(rowSize, colSize);

        int count = operateMatrices(matrixA, matrixB);

        // 결과 출력
        if (!equalsMatrices(matrixA, matrixB)) {
            count = -1;
        }
        printResult(count);

        // 종료 작업
        close();
    }

    private static int[][] createMatrix(int rowSize, int colSize) {
        int[][] matrix = new int[rowSize][colSize];
        for (int row = 0; row < rowSize; row++) {
            String line = reader.next();
            for (int col = 0; col < colSize; col++) {
                matrix[row][col] = line.charAt(col) - '0';
            }
        }
        return matrix;
    }

    private static int operateMatrices(int[][] matrixA, int[][] matrixB) {
        int result = 0;
        for (int row = 0; row < matrixA.length; row++) {
            for (int col = 0; col < matrixA[row].length; col++) {
                if (!validRange(matrixA, row + OPERATION_SIZE - 1, col + OPERATION_SIZE - 1)) {
                    continue;
                }

                if (matrixA[row][col] != matrixB[row][col]) {
                    reverseMatrix(matrixA, row, col, OPERATION_SIZE);
                    result++;
                }
            }
        }
        return result;
    }

    private static boolean validRange(int[][] matrixA, int row, int col) {
        if (row < 0 || matrixA.length <= row) {
            return false;
        }
        if (col < 0 || matrixA[row].length <= col) {
            return false;
        }
        return true;
    }

    private static void reverseMatrix(int[][] matrix, int row, int col, int operationSize) {
        for (int iRow = 0; iRow < operationSize; iRow++) {
            for (int iCol = 0; iCol < operationSize; iCol++) {
                matrix[row + iRow][col + iCol] = reverseElement(matrix[row + iRow][col + iCol]);
            }
        }
    }

    private static int reverseElement(int element) {
        if (element == 1) {
            return 0;
        } else if (element == 0) {
            return 1;
        }
        return -1;
    }


    private static boolean equalsMatrices(int[][] matrixA, int[][] matrixB) {
        for (int row = 0; row < matrixA.length; row++) {
            for (int col = 0; col < matrixA[row].length; col++) {
                if (matrixA[row][col] != matrixB[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void printResult(int result) {
        writer.print(String.valueOf(result));
    }

    private static void close() {
        reader.close();
        writer.close();
    }
}