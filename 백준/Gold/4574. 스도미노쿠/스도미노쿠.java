import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
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

    static class PairTile {

        private final Tile firstTile;
        private final Tile secondTile;

        public PairTile(Tile firstTile, Tile secondTile) {
            this.firstTile = firstTile;
            this.secondTile = secondTile;
        }

        public Tile getFirstTile() {
            return firstTile;
        }

        public Tile getSecondTile() {
            return secondTile;
        }
    }

    private static TokenizerReader reader;
    private static BuilderWriter writer;
    public static final int SUDOKU_SIZE = 9;
    public static final int SUDOKU_SIZE_SQRT = (int) Math.sqrt(SUDOKU_SIZE);

    public static final int MIN_NUMBER = 1;
    public static final int MAX_NUMBER = SUDOKU_SIZE;

    public static final int MIN_ROW = 1;
    public static final int MIN_COL = 1;

    private static boolean[][] usedPairs = new boolean[MAX_NUMBER + 1][MAX_NUMBER + 1];

    private static Coordinates[] DIRECTS = new Coordinates[]{
            new Coordinates(-1, 0), new Coordinates(0, 1),
            new Coordinates(1, 0), new Coordinates(0, -1)
    };

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        List<int[][]> result = new ArrayList<>();
        while (true) {
            int pairTileSize = reader.nextInt();
            if (pairTileSize == 0) {
                break;
            }

            initializeUsedPairs();

            List<PairTile> pairTiles = getPairTiles(pairTileSize);
            List<Tile> fixedTiles = getFixedTiles();

            int[][] sudoku = getSudoku(fixedTiles);

            // 빈 칸 채우기
            boolean[][] checkedRows = getCheckedRows(sudoku);
            boolean[][] checkedCols = getCheckedCols(sudoku);
            boolean[][][] checkedSquares = getCheckedSquares(sudoku);

            // 빈 칸 찾기
            List<Coordinates> blanks = getBlanks(fixedTiles, pairTiles);

            dfs(sudoku, pairTiles, 0, checkedRows, checkedCols, checkedSquares, blanks);

            result.add(sudoku);
        }

        // 결과 출력
        printResults(result);

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static List<PairTile> getPairTiles(int pairTileSize) {
        List<PairTile> pairTiles = new ArrayList<>();
        for (int i = 0; i < pairTileSize; i++) {
            Tile firstTile = getTile();
            Tile secondTile = getTile();

            PairTile pairTile = new PairTile(firstTile, secondTile);
            pairTiles.add(pairTile);

            usedPairs[firstTile.getNumber()][secondTile.getNumber()] = true;
            usedPairs[secondTile.getNumber()][firstTile.getNumber()] = true;
        }
        return pairTiles;
    }

    private static Tile getTile() {
        int number = reader.nextInt();
        String location = reader.next();

        int row = getRowOfLocation(location);
        int col = getColOfLocation(location);
        Coordinates coordinates = new Coordinates(row, col);

        Tile tile = new Tile(coordinates, number);
        return tile;
    }

    private static int getRowOfLocation(String location) {
        return location.charAt(0) - 'A' + 1;
    }

    private static int getColOfLocation(String location) {
        return location.charAt(1) - '0';
    }

    private static List<Tile> getFixedTiles() {
        List<Tile> fixedTiles = new ArrayList<>();
        for (int n = MIN_NUMBER; n <= MAX_NUMBER; n++) {
            String location = reader.next();

            int row = getRowOfLocation(location);
            int col = getColOfLocation(location);
            Coordinates coordinates = new Coordinates(row, col);

            Tile tile = new Tile(coordinates, n);
            fixedTiles.add(tile);
        }
        return fixedTiles;
    }

    private static int[][] getSudoku(List<Tile> tiles) {
        int[][] sudoku = new int[SUDOKU_SIZE + MIN_ROW][SUDOKU_SIZE + MIN_COL];
        for (Tile tile : tiles) {
            Coordinates coordinates = tile.getCoordinates();

            sudoku[coordinates.getRow()][coordinates.getCol()] = tile.getNumber();
        }
        return sudoku;
    }

    private static void initializeUsedPairs() {
        for (int i = 0; i < usedPairs.length; i++) {
            Arrays.fill(usedPairs[i], false);
        }
    }

    private static List<Coordinates> getBlanks(List<Tile> tiles, List<PairTile> pairTiles) {
        boolean[][] checked = new boolean[SUDOKU_SIZE + MIN_ROW][SUDOKU_SIZE + MIN_COL];

        checkCoordinates(checked, tiles);

        checkCoordinates(checked, getTiles(pairTiles));

        List<Coordinates> blanks = new ArrayList<>();
        for (int iRow = MIN_ROW; iRow < checked.length; iRow++) {
            for (int iCol = MIN_COL; iCol < checked[iRow].length; iCol++) {
                if (!checked[iRow][iCol]) {
                    blanks.add(new Coordinates(iRow, iCol));
                }
            }
        }
        return blanks;
    }

    private static void checkCoordinates(boolean[][] checked, List<Tile> tiles) {
        for (Tile tile : tiles) {
            Coordinates coordinates = tile.getCoordinates();
            int row = coordinates.getRow();
            int col = coordinates.getCol();

            checked[row][col] = true;
        }
    }

    private static List<Tile> getTiles(List<PairTile> pairTiles) {
        List<Tile> tiles = new ArrayList<>();
        for (PairTile pairTile : pairTiles) {
            tiles.add(pairTile.getFirstTile());
            tiles.add(pairTile.getSecondTile());
        }
        return tiles;
    }

    private static boolean[][] getCheckedRows(int[][] sudoku) {
        boolean[][] result = new boolean[SUDOKU_SIZE + MIN_ROW][MAX_NUMBER + 1];

        for (int iRow = MIN_ROW; iRow < sudoku.length; iRow++) {
            for (int iCol = MIN_COL; iCol < sudoku[iRow].length; iCol++) {
                int n = sudoku[iRow][iCol];
                result[iRow][n] = true;
            }
        }
        return result;
    }

    private static boolean[][] getCheckedCols(int[][] sudoku) {
        boolean[][] result = new boolean[SUDOKU_SIZE + MIN_COL][MAX_NUMBER + 1];

        for (int iRow = MIN_ROW; iRow < sudoku.length; iRow++) {
            for (int iCol = MIN_COL; iCol < sudoku[iRow].length; iCol++) {
                int n = sudoku[iRow][iCol];
                result[iCol][n] = true;
            }
        }
        return result;
    }

    private static boolean[][][] getCheckedSquares(int[][] sudoku) {
        boolean[][][] result = new boolean[SUDOKU_SIZE_SQRT][SUDOKU_SIZE_SQRT][MAX_NUMBER + 1];

        for (int iRow = 0; iRow < sudoku.length; iRow++) {
            for (int iCol = 0; iCol < sudoku[iRow].length; iCol++) {
                int n = sudoku[iRow][iCol];
                int sqrtRow = getSqrtRow(iRow);
                int sqrtCol = getSqrtCol(iCol);
                result[sqrtRow][sqrtCol][n] = true;
            }
        }
        return result;
    }

    private static int getSqrtRow(int row) {
        return (row - MIN_ROW) / SUDOKU_SIZE_SQRT;
    }

    private static int getSqrtCol(int col) {
        return (col - MIN_COL) / SUDOKU_SIZE_SQRT;
    }

    private static boolean dfs(int[][] sudoku, List<PairTile> pairTiles, int listIndex,
            boolean[][] checkedRows, boolean[][] checkedCols, boolean[][][] checkedSquares,
            List<Coordinates> blanks) {
        if (pairTiles.size() == listIndex) {
            if (dfs(sudoku, blanks, 0, checkedRows, checkedCols, checkedSquares)) {
                return true;
            }
            return false;
        }

        PairTile pairTile = pairTiles.get(listIndex);
        Tile firstTile = pairTile.getFirstTile();
        Tile secondTile = pairTile.getSecondTile();

        Coordinates firstCoordinates = firstTile.getCoordinates();
        int firstNumber = firstTile.getNumber();
        Coordinates secondCoordinates = secondTile.getCoordinates();
        int secondNumber = secondTile.getNumber();

        if (isBlank(firstCoordinates, firstNumber, checkedRows, checkedCols, checkedSquares)
                && isBlank(secondCoordinates, secondNumber,
                checkedRows, checkedCols, checkedSquares)) {
            check(checkedRows, firstCoordinates.getRow(), firstNumber);
            check(checkedCols, firstCoordinates.getCol(), firstNumber);
            check(checkedSquares, getSqrtRow(firstCoordinates.getRow()),
                    getSqrtCol(firstCoordinates.getCol()), firstNumber);

            check(checkedRows, secondCoordinates.getRow(), secondNumber);
            check(checkedCols, secondCoordinates.getCol(), secondNumber);
            check(checkedSquares, getSqrtRow(secondCoordinates.getRow()),
                    getSqrtCol(secondCoordinates.getCol()), secondNumber);

            sudoku[firstCoordinates.getRow()][firstCoordinates.getCol()] = firstNumber;
            sudoku[secondCoordinates.getRow()][secondCoordinates.getCol()] = secondNumber;

            if (dfs(sudoku, pairTiles, listIndex + 1,
                    checkedRows, checkedCols, checkedSquares, blanks)) {
                return true;
            }

            uncheck(checkedRows, firstCoordinates.getRow(), firstNumber);
            uncheck(checkedCols, firstCoordinates.getCol(), firstNumber);
            uncheck(checkedSquares, getSqrtRow(firstCoordinates.getRow()),
                    getSqrtCol(firstCoordinates.getCol()), firstNumber);

            uncheck(checkedRows, secondCoordinates.getRow(), secondNumber);
            uncheck(checkedCols, secondCoordinates.getCol(), secondNumber);
            uncheck(checkedSquares, getSqrtRow(secondCoordinates.getRow()),
                    getSqrtCol(secondCoordinates.getCol()), secondNumber);

            sudoku[firstCoordinates.getRow()][firstCoordinates.getCol()] = 0;
            sudoku[secondCoordinates.getRow()][secondCoordinates.getCol()] = 0;
        }

        if (isBlank(firstCoordinates, secondNumber, checkedRows, checkedCols, checkedSquares)
                && isBlank(secondCoordinates, firstNumber,
                checkedRows, checkedCols, checkedSquares)) {
            check(checkedRows, firstCoordinates.getRow(), secondNumber);
            check(checkedCols, firstCoordinates.getCol(), secondNumber);
            check(checkedSquares, getSqrtRow(firstCoordinates.getRow()),
                    getSqrtCol(firstCoordinates.getCol()), secondNumber);

            check(checkedRows, secondCoordinates.getRow(), firstNumber);
            check(checkedCols, secondCoordinates.getCol(), firstNumber);
            check(checkedSquares, getSqrtRow(secondCoordinates.getRow()),
                    getSqrtCol(secondCoordinates.getCol()), firstNumber);

            sudoku[firstCoordinates.getRow()][firstCoordinates.getCol()] = secondNumber;
            sudoku[secondCoordinates.getRow()][secondCoordinates.getCol()] = firstNumber;

            if (dfs(sudoku, pairTiles, listIndex + 1,
                    checkedRows, checkedCols, checkedSquares, blanks)) {
                return true;
            }

            uncheck(checkedRows, firstCoordinates.getRow(), secondNumber);
            uncheck(checkedCols, firstCoordinates.getCol(), secondNumber);
            uncheck(checkedSquares, getSqrtRow(firstCoordinates.getRow()),
                    getSqrtCol(firstCoordinates.getCol()), secondNumber);

            uncheck(checkedRows, secondCoordinates.getRow(), firstNumber);
            uncheck(checkedCols, secondCoordinates.getCol(), firstNumber);
            uncheck(checkedSquares, getSqrtRow(secondCoordinates.getRow()),
                    getSqrtCol(secondCoordinates.getCol()), firstNumber);
        }

        sudoku[firstCoordinates.getRow()][firstCoordinates.getCol()] = 0;
        sudoku[secondCoordinates.getRow()][secondCoordinates.getCol()] = 0;

        return false;
    }

    private static boolean isBlank(Coordinates coordinates, int number,
            boolean[][] checkedRows, boolean[][] checkedCols, boolean[][][] checkedSquares) {
        int row = coordinates.getRow();
        int col = coordinates.getCol();
        int sqrtRow = getSqrtRow(row);
        int sqrtCol = getSqrtCol(col);

        if (!checkedRows[row][number] && !checkedCols[col][number]
                && !checkedSquares[sqrtRow][sqrtCol][number]) {
            return true;
        }
        return false;
    }

    private static void check(boolean[][] checked, int index, int number) {
        checked[index][number] = true;
    }

    private static void check(boolean[][][] checked, int row, int col, int number) {
        checked[row][col][number] = true;
    }

    private static void uncheck(boolean[][] checked, int index, int number) {
        checked[index][number] = false;
    }

    private static void uncheck(boolean[][][] checked, int row, int col, int number) {
        checked[row][col][number] = false;
    }

    private static boolean dfs(int[][] sudoku, List<Coordinates> list, int listIndex,
            boolean[][] checkedRows, boolean[][] checkedCols, boolean[][][] checkedSquares) {
        if (list.size() == listIndex) {
            return true;
        }

        Coordinates coordinates = list.get(listIndex);
        int row = coordinates.getRow();
        int col = coordinates.getCol();
        int squareRow = getSqrtRow(row);
        int squareCol = getSqrtCol(col);

        if (sudoku[row][col] != 0) {
            if (dfs(sudoku, list, listIndex + 1, checkedRows, checkedCols, checkedSquares)) {
                return true;
            }
            return false;
        }

        for (int number1 = MIN_NUMBER; number1 <= MAX_NUMBER; number1++) {
            if (checkedRows[row][number1]
                    || checkedCols[col][number1]
                    || checkedSquares[squareRow][squareCol][number1]) {
                continue;
            }

            for (Coordinates direct : DIRECTS) {
                int number2Row = row + direct.getRow();
                int number2Col = col + direct.getCol();
                if (!validRange(sudoku, number2Row, number2Col)
                        || sudoku[number2Row][number2Col] != 0) {
                    continue;
                }

                for (int number2 = MIN_NUMBER; number2 < usedPairs[number1].length; number2++) {
                    int number2SqrtRow = getSqrtRow(number2Row);
                    int number2SqrtCol = getSqrtRow(number2Col);

                    if (checkedRows[number2Row][number2]
                            || checkedCols[number2Col][number2]
                            || checkedSquares[number2SqrtRow][number2SqrtCol][number2]) {
                        continue;
                    }
                    if (usedPairs[number1][number2]
                            || number1 == number2) {
                        continue;
                    }

                    usedPairs[number1][number2] = true;
                    usedPairs[number2][number1] = true;

                    checkedRows[row][number1] = true;
                    checkedCols[col][number1] = true;
                    checkedSquares[squareRow][squareCol][number1] = true;
                    sudoku[row][col] = number1;

                    checkedRows[number2Row][number2] = true;
                    checkedCols[number2Col][number2] = true;
                    checkedSquares[getSqrtRow(number2Row)][getSqrtCol(number2Col)][number2] = true;
                    sudoku[number2Row][number2Col] = number2;

                    if (dfs(sudoku, list, listIndex + 1, checkedRows, checkedCols,
                            checkedSquares)) {
                        return true;
                    }

                    checkedRows[row][number1] = false;
                    checkedCols[col][number1] = false;
                    checkedSquares[squareRow][squareCol][number1] = false;
                    sudoku[row][col] = 0;

                    checkedRows[number2Row][number2] = false;
                    checkedCols[number2Col][number2] = false;
                    checkedSquares[number2SqrtRow][number2SqrtCol][number2] = false;
                    sudoku[number2Row][number2Col] = 0;

                    usedPairs[number1][number2] = false;
                    usedPairs[number2][number1] = false;
                }
            }
        }
        return false;
    }

    private static boolean validRange(int[][] sudoku, int row, int col) {
        if (row < 0 || sudoku.length <= row) {
            return false;
        }
        if (col < 0 || sudoku.length <= col) {
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
        for (int iRow = MIN_ROW; iRow < sudoku.length; iRow++) {
            for (int iCol = MIN_COL; iCol < sudoku[iRow].length; iCol++) {
                writer.print(String.valueOf(sudoku[iRow][iCol]));
            }
            writer.println("");
        }
    }
}