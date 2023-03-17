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

        public Coordinates(int row, int col) {
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

    static class Tile {

        private final Coordinates coordinates;
        private final int number;

        public Tile(Coordinates coordinates, int number) {
            this.coordinates = coordinates;
            this.number = number;
        }

        public Coordinates getCoordinates() {
            return coordinates;
        }

        public int getNumber() {
            return number;
        }
    }

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = 9;

    public static final int SUDOKU_SIZE = 9;
    public static final int SUDOKU_SIZE_SQRT = (int) Math.sqrt(SUDOKU_SIZE);

    private static Coordinates[] DIRECTS = new Coordinates[]{
            new Coordinates(0, 1), new Coordinates(1, 0)
    };

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        List<int[][]> results = new ArrayList<>();
        while (true) {
            int size = reader.nextInt();
            if (size == 0) {
                break;
            }

            // 입력
            List<Tile[]> tilePairs = getTilePairs(size);
            List<Tile> tiles = getTiles(MIN_NUMBER, MAX_NUMBER);

            // sudoku board 채우기
            int[][] sudokuBoard = getSudokuBoard(tilePairs, tiles);
            List<Coordinates> unvisited = getUnvisitedCoordinates(sudokuBoard);
            boolean[][] visitedTilePairs = new boolean[MAX_NUMBER + 1][MAX_NUMBER + 1];
            visit(visitedTilePairs, tilePairs);

            boolean[][] visitedRows = new boolean[SUDOKU_SIZE][MAX_NUMBER + 1];
            visitRows(visitedRows, sudokuBoard);
            boolean[][] visitedCols = new boolean[SUDOKU_SIZE][MAX_NUMBER + 1];
            visitCols(visitedCols, sudokuBoard);
            boolean[][][] visitedSquares =
                    new boolean[SUDOKU_SIZE_SQRT][SUDOKU_SIZE_SQRT][MAX_NUMBER + 1];
            visitSquares(visitedSquares, sudokuBoard);

            dfs(sudokuBoard, unvisited, 0,
                    visitedTilePairs, visitedRows, visitedCols, visitedSquares);

            results.add(sudokuBoard);
        }

        printResults(results);

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static List<Tile[]> getTilePairs(int size) {
        List<Tile[]> tilePairs = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int number1 = reader.nextInt();
            String location1 = reader.next();
            Coordinates coordinates1 = new Coordinates(
                    getRowOfLocation(location1), getColOfLocation(location1));
            Tile tile1 = new Tile(coordinates1, number1);

            int number2 = reader.nextInt();
            String location2 = reader.next();
            Coordinates coordinates2 = new Coordinates(
                    getRowOfLocation(location2), getColOfLocation(location2));
            Tile tile2 = new Tile(coordinates2, number2);

            tilePairs.add(new Tile[]{tile1, tile2});
        }
        return tilePairs;
    }

    private static int getRowOfLocation(String location) {
        return location.charAt(0) - 'A';
    }

    private static int getColOfLocation(String location) {
        return location.charAt(1) - '1';
    }

    private static List<Tile> getTiles(int minNumber, int maxNumber) {
        List<Tile> tiles = new ArrayList<>();
        for (int n = minNumber; n <= maxNumber; n++) {
            String location = reader.next();
            Coordinates coordinates = new Coordinates(
                    getRowOfLocation(location), getColOfLocation(location));

            tiles.add(new Tile(coordinates, n));
        }
        return tiles;
    }


    private static int[][] getSudokuBoard(List<Tile[]> tilePairs, List<Tile> tiles) {
        int[][] sudokuBoard = new int[SUDOKU_SIZE][SUDOKU_SIZE];

        fillSudokuBoardWith(sudokuBoard, getTiles(tilePairs));
        fillSudokuBoardWith(sudokuBoard, tiles);

        return sudokuBoard;
    }

    private static List<Tile> getTiles(List<Tile[]> tilePairs) {
        List<Tile> result = new ArrayList<>();
        for (Tile[] tiles : tilePairs) {
            for (Tile tile : tiles) {
                result.add(tile);
            }
        }
        return result;
    }

    private static void fillSudokuBoardWith(int[][] sudokuBoard, List<Tile> tiles) {
        for (Tile tile : tiles) {
            Coordinates coordinates = tile.getCoordinates();
            sudokuBoard[coordinates.getRow()][coordinates.getCol()] = tile.getNumber();
        }
    }

    private static List<Coordinates> getUnvisitedCoordinates(int[][] sudokuBoard) {
        List<Coordinates> unvisited = new ArrayList<>();
        for (int iRow = 0; iRow < SUDOKU_SIZE; iRow++) {
            for (int iCol = 0; iCol < SUDOKU_SIZE; iCol++) {
                if (sudokuBoard[iRow][iCol] != 0) {
                    continue;
                }
                unvisited.add(new Coordinates(iRow, iCol));
            }
        }
        return unvisited;
    }

    private static void visit(boolean[][] visitedTilePairs, List<Tile[]> tilePairs) {
        for (Tile[] tilePair : tilePairs) {
            int n1 = tilePair[0].getNumber();
            int n2 = tilePair[1].getNumber();

            visitedTilePairs[n1][n2] = true;
            visitedTilePairs[n2][n1] = true;
        }
        for (int i = 0; i < visitedTilePairs.length; i++) {
            visitedTilePairs[i][i] = true;
        }
    }

    private static void visitRows(boolean[][] visitedRows, int[][] sudokuBoard) {
        for (int iRow = 0; iRow < SUDOKU_SIZE; iRow++) {
            for (int iCol = 0; iCol < SUDOKU_SIZE; iCol++) {
                int n = sudokuBoard[iRow][iCol];

                visitedRows[iRow][n] = true;
            }
        }
    }

    private static void visitCols(boolean[][] visitedCols, int[][] sudokuBoard) {
        for (int iRow = 0; iRow < SUDOKU_SIZE; iRow++) {
            for (int iCol = 0; iCol < SUDOKU_SIZE; iCol++) {
                int n = sudokuBoard[iRow][iCol];

                visitedCols[iCol][n] = true;
            }
        }
    }

    private static void visitSquares(boolean[][][] visitedSquares, int[][] sudokuBoard) {
        for (int iRow = 0; iRow < SUDOKU_SIZE; iRow++) {
            for (int iCol = 0; iCol < SUDOKU_SIZE; iCol++) {
                int squareRow = getSquareRow(iRow);
                int squareCol = getSquareCol(iCol);
                int n = sudokuBoard[iRow][iCol];

                visitedSquares[squareRow][squareCol][n] = true;
            }
        }
    }

    private static int getSquareRow(int row) {
        return row / SUDOKU_SIZE_SQRT;
    }

    private static int getSquareCol(int col) {
        return col / SUDOKU_SIZE_SQRT;
    }


    private static boolean dfs(int[][] sudokuBoard, List<Coordinates> unvisited, int unvisitedIndex,
            boolean[][] visitedTilePairs, boolean[][] visitedRows, boolean[][] visitedCols,
            boolean[][][] visitedSquares) {
        if (unvisited.size() == unvisitedIndex) {
            return true;
        }

        Coordinates coordinates1 = unvisited.get(unvisitedIndex);
        int row1 = coordinates1.getRow();
        int col1 = coordinates1.getCol();
        if (sudokuBoard[row1][col1] != 0) {
            if (dfs(sudokuBoard, unvisited, unvisitedIndex + 1,
                    visitedTilePairs, visitedRows, visitedCols, visitedSquares)) {
                return true;
            }
            return false;
        }

        int squareRow1 = getSquareRow(row1);
        int squareCol1 = getSquareCol(col1);
        for (int n1 = MIN_NUMBER; n1 <= MAX_NUMBER; n1++) {
            if (visitedRows[row1][n1] || visitedCols[col1][n1]
                    || visitedSquares[squareRow1][squareCol1][n1]) {
                continue;
            }

            visitedRows[row1][n1] = true;
            visitedCols[col1][n1] = true;
            visitedSquares[squareRow1][squareCol1][n1] = true;
            sudokuBoard[row1][col1] = n1;

            for (Coordinates direct : DIRECTS) {
                int row2 = row1 + direct.getRow();
                int col2 = col1 + direct.getCol();
                if (!validRange(sudokuBoard, row2, col2)
                        || sudokuBoard[row2][col2] != 0) {
                    continue;
                }

                int squareRow2 = getSquareRow(row2);
                int squareCol2 = getSquareCol(col2);
                for (int n2 = MIN_NUMBER; n2 <= MAX_NUMBER; n2++) {
                    if (visitedTilePairs[n1][n2] || visitedRows[row2][n2] || visitedCols[col2][n2]
                            || visitedSquares[squareRow2][squareCol2][n2]) {
                        continue;
                    }

                    visitedTilePairs[n1][n2] = true;
                    visitedTilePairs[n2][n1] = true;

                    visitedRows[row2][n2] = true;
                    visitedCols[col2][n2] = true;
                    visitedSquares[squareRow2][squareCol2][n2] = true;
                    sudokuBoard[row2][col2] = n2;

                    if (dfs(sudokuBoard, unvisited, unvisitedIndex + 1,
                            visitedTilePairs, visitedRows, visitedCols, visitedSquares)) {
                        return true;
                    }

                    visitedTilePairs[n1][n2] = false;
                    visitedTilePairs[n2][n1] = false;

                    visitedRows[row2][n2] = false;
                    visitedCols[col2][n2] = false;
                    visitedSquares[squareRow2][squareCol2][n2] = false;
                    sudokuBoard[row2][col2] = 0;
                }
            }

            visitedRows[row1][n1] = false;
            visitedCols[col1][n1] = false;
            visitedSquares[squareRow1][squareCol1][n1] = false;
            sudokuBoard[row1][col1] = 0;
        }
        return false;
    }

    private static boolean validRange(int[][] sudokuBoard, int row, int col) {
        if (row < 0 || sudokuBoard.length <= row) {
            return false;
        }
        if (col < 0 || sudokuBoard[row].length <= col) {
            return false;
        }
        return true;
    }

    private static void printResults(List<int[][]> results) {
        for (int i = 0; i < results.size(); i++) {
            writer.println("Puzzle " + (i + 1));
            printSudoku(results.get(i));
        }
    }

    private static void printSudoku(int[][] sudoku) {
        for (int iRow = 0; iRow < SUDOKU_SIZE; iRow++) {
            for (int iCol = 0; iCol < SUDOKU_SIZE; iCol++) {
                writer.print(String.valueOf(sudoku[iRow][iCol]));
            }
            writer.println("");
        }
    }
}