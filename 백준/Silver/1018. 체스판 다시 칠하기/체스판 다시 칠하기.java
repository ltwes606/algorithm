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

    static class Point {

        private final int row;
        private final int col;

        public Point(int row, int col) {
            this.row = row;
            this.col = col;
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

    private static int CHESS_BOARD_SIZE = 8;
    private static char WHITE = 'W';
    private static char BLACK = 'B';


    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 체스보드 입력
        int rowSize = reader.nextInt();
        int colSize = reader.nextInt();
        char[][] inputChessBoard = new char[rowSize][colSize];
        for (int iRow = 0; iRow < inputChessBoard.length; iRow++) {
            String line = reader.next();
            for (int iCol = 0; iCol < inputChessBoard[iRow].length; iCol++) {
                inputChessBoard[iRow][iCol] = line.charAt(iCol);
            }
        }

        int result = Integer.MAX_VALUE;
        char[][] startingWithWhiteChessBoard = getStartingWithWhiteChessBoard(
                CHESS_BOARD_SIZE, CHESS_BOARD_SIZE);
        char[][] startingWithBlackChessBoard = getStartingWithBlackChessBoard(
                CHESS_BOARD_SIZE, CHESS_BOARD_SIZE);

        for (int iRow = 0; iRow <= rowSize - CHESS_BOARD_SIZE; iRow++) {

            for (int iCol = 0; iCol <= colSize - CHESS_BOARD_SIZE; iCol++) {
                char[][] chessBoard = splitChessBoard(
                        inputChessBoard, iRow, CHESS_BOARD_SIZE, iCol, CHESS_BOARD_SIZE);

                result = Math.min(result, findDifference(chessBoard, startingWithWhiteChessBoard));
                result = Math.min(result, findDifference(chessBoard, startingWithBlackChessBoard));
            }

        }

        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static char[][] splitChessBoard(char[][] chessBoard, int startRow, int rowSize,
            int startCol, int colSize) {
        char[][] result = new char[rowSize][colSize];

        for (int iRow = 0; iRow < rowSize; iRow++) {
            for (int iCol = 0; iCol < colSize; iCol++) {
                result[iRow][iCol] = chessBoard[iRow + startRow][iCol + startCol];
            }
        }
        return result;
    }

    private static int findDifference(char[][] chessBoard1, char[][] chessBoard2) {
        int result = 0;
        for (int iRow = 0; iRow < chessBoard1.length; iRow++) {
            for (int iCol = 0; iCol < chessBoard1[iRow].length; iCol++) {
                if (chessBoard1[iRow][iCol] == chessBoard2[iRow][iCol]) {
                    continue;
                }
                result++;
            }
        }
        return result;
    }

    private static char[][] getStartingWithBlackChessBoard(int rowSize, int colSize) {
        char[][] result = new char[rowSize][colSize];
        char c = BLACK;
        for (int iRow = 0; iRow < result.length; iRow++) {
            if (iRow != 0) {
                c = reverseCharacter(result[iRow - 1][0]);
            }

            for (int iCol = 0; iCol < result[iRow].length; iCol++) {
                result[iRow][iCol] = c;
                c = reverseCharacter(c);
            }
        }
        return result;
    }

    private static char[][] getStartingWithWhiteChessBoard(int rowSize, int colSize) {
        char[][] result = new char[rowSize][colSize];
        char c = WHITE;
        for (int iRow = 0; iRow < result.length; iRow++) {
            if (iRow != 0) {
                c = reverseCharacter(result[iRow - 1][0]);
            }

            for (int iCol = 0; iCol < result[iRow].length; iCol++) {
                result[iRow][iCol] = c;
                c = reverseCharacter(c);
            }
        }
        return result;
    }

    private static char reverseCharacter(char c) {
        if (c == WHITE) {
            return BLACK;
        }
        return WHITE;
    }
}