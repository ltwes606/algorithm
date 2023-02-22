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

    static class BuilderWriter {

        private BufferedWriter writer;
        private final StringBuilder builder = new StringBuilder();

        public BuilderWriter(OutputStream outputStream) {
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
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

    public static final int ROW = 0;
    public static final int COLUMN = 1;

    public static void main(String[] args) {
        // 입출력 설정
        TokenizerReader reader = new TokenizerReader(System.in);
        BuilderWriter writer = new BuilderWriter(System.out);

        // 입력
        int rowSize = reader.nextInt();
        int columnSize = reader.nextInt();
        int[][] inputs = new int[rowSize][columnSize];
        for (int iRow = 0; iRow < inputs.length; iRow++) {
            String numbers = reader.next();
            for (int iCol = 0; iCol < inputs[iRow].length; iCol++) {
                inputs[iRow][iCol] = numbers.charAt(iCol) - '0';
            }
        }

        // 초기화
        int[][] bitMask = new int[rowSize][columnSize];
        int result = 0;

        // 최대값 구하기
        for (int i = 0; i < Math.pow(2, rowSize * columnSize); i++) {
            result = Math.max(result, sumMap(inputs, bitMask));
            nextBitMask(bitMask);
        }

        // 결과 출력
        writer.println(String.valueOf(result));

        // 종료
        reader.close();
        writer.close();
    }

    private static void nextBitMask(int[][] bitMask) {
        int value = convertValue(bitMask) + 1;

        for (int iRow = bitMask.length - 1; iRow >= 0; iRow--) {
            for (int iCol = bitMask[iRow].length - 1; iCol >= 0; iCol--) {
                int weight = (int) Math.pow(2, iRow * bitMask[iRow].length + iCol);
                bitMask[iRow][iCol] = value / weight;
                value %= weight;
            }
        }
    }

    private static int convertValue(int[][] bitMask) {
        int result = 0;
        for (int iRow = 0; iRow < bitMask.length; iRow++) {
            for (int iCol = 0; iCol < bitMask[iRow].length; iCol++) {
                result +=
                        (int) Math.pow(2, iRow * bitMask[iRow].length + iCol) * bitMask[iRow][iCol];
            }
        }
        return result;
    }

    private static int sumMap(int[][] map, int[][] bitMask) {
        boolean[][] visited = new boolean[bitMask.length][bitMask[0].length];
        int result = 0;
        for (int iRow = 0; iRow < bitMask.length; iRow++) {
            for (int iCol = 0; iCol < bitMask[iRow].length; iCol++) {
                if (visited[iRow][iCol]) {
                    continue;
                }

                if (bitMask[iRow][iCol] == ROW) {
                    result += sumMapBasedRow(map, iRow, iCol, bitMask, visited);
                } else { // COLUMN
                    result += sumMapBasedColumn(map, iRow, iCol, bitMask, visited);
                }
            }
        }
        return result;
    }

    private static int sumMapBasedColumn(int[][] map, int row, int col, int[][] bitMask,
            boolean[][] visited) {
        int result = 0;
        for (int iCol = col; iCol < map[row].length; iCol++) {
            if (visited[row][iCol] || bitMask[row][iCol] != COLUMN) {
                break;
            }

            visited[row][iCol] = true;
            result = result * 10 + map[row][iCol];
        }
        return result;
    }

    private static int sumMapBasedRow(int[][] map, int row, int col, int[][] bitMask,
            boolean[][] visited) {
        int result = 0;
        for (int iRow = row; iRow < map.length; iRow++) {
            if (visited[iRow][col] || bitMask[iRow][col] != ROW) {
                break;
            }

            visited[iRow][col] = true;
            result = result * 10 + map[iRow][col];
        }
        return result;
    }
}