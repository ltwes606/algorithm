import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

class Main {

    static class Score implements Comparable<Score> {

        private final Tetromino tetromino;
        private final int row;
        private final int column;
        private final int value;

        public Score(Tetromino tetromino, int row, int column, int value) {
            this.tetromino = tetromino;
            this.row = row;
            this.column = column;
            this.value = value;
        }

        public Tetromino getTetromino() {
            return tetromino;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public int getValue() {
            return value;
        }

        @Override
        public int compareTo(Score o) {
            return this.getValue() - o.getValue();
        }

        @Override
        public String toString() {
            return "Score{" +
                    "tetromino=" + tetromino +
                    ", row=" + row +
                    ", column=" + column +
                    ", value=" + value +
                    '}';
        }
    }

    static class Tetromino {

        private final List<Coordinate> coordinates;
        private final int minX;
        private final int maxX;
        private final int minY;
        private final int maxY;

        public Tetromino(List<Coordinate> coordinates) {
            this.coordinates = coordinates;
            this.minX = askMinX(coordinates);
            this.maxX = askMaxX(coordinates);
            this.minY = askMinY(coordinates);
            this.maxY = askMaxY(coordinates);
        }

        private int askMaxY(List<Coordinate> coordinates) {
            int result = Integer.MIN_VALUE;
            for (Coordinate coordinate : coordinates) {
                int y = coordinate.getY();
                if (result < y) {
                    result = y;
                }
            }
            return result;
        }

        private int askMinY(List<Coordinate> coordinates) {
            int result = Integer.MAX_VALUE;
            for (Coordinate coordinate : coordinates) {
                int y = coordinate.getY();
                if (result > y) {
                    result = y;
                }
            }
            return result;
        }

        private int askMaxX(List<Coordinate> coordinates) {
            int result = Integer.MIN_VALUE;
            for (Coordinate coordinate : coordinates) {
                int x = coordinate.getX();
                if (result < x) {
                    result = x;
                }
            }
            return result;
        }

        private int askMinX(List<Coordinate> coordinates) {
            int result = Integer.MAX_VALUE;
            for (Coordinate coordinate : coordinates) {
                int x = coordinate.getX();
                if (result > x) {
                    result = x;
                }
            }
            return result;
        }

        public List<Coordinate> getCoordinates() {
            return coordinates;
        }

        public int getMinX() {
            return minX;
        }

        public int getMaxX() {
            return maxX;
        }

        public int getMinY() {
            return minY;
        }

        public int getMaxY() {
            return maxY;
        }

        @Override
        public String toString() {
            return "Tetromino{" +
                    "coordinates=" + coordinates +
                    ", minX=" + minX +
                    ", maxX=" + maxX +
                    ", minY=" + minY +
                    ", maxY=" + maxY +
                    '}';
        }
    }

    static class Coordinate {

        private final int x;
        private final int y;

        public Coordinate(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            return "Coordinate{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int row = Integer.parseInt(tokenizer.nextToken());
        int col = Integer.parseInt(tokenizer.nextToken());
        int[][] inputs = new int[row][col];
        for (int iRow = 0; iRow < inputs.length; iRow++) {
            tokenizer = new StringTokenizer(reader.readLine());
            for (int iCol = 0; iCol < inputs[iRow].length; iCol++) {
                inputs[iRow][iCol] = Integer.parseInt(tokenizer.nextToken());
            }
        }

        // 테트로미노 조합 생성
        List<Tetromino> tetrominos = createTetrominos();

        // 각 좌표별 점수 구하기
        List<Score> scores = new ArrayList<>();
        for (int iRow = 0; iRow < inputs.length; iRow++) {
            for (int iCol = 0; iCol < inputs[iRow].length; iCol++) {
                scores.addAll(getScores(inputs, iRow, iCol, tetrominos));
            }
        }

        // 점수 내림차순으로 정렬
        Collections.sort(scores);
        Collections.reverse(scores);

        // 좌표가 겹치지 않는 최고 점수 구하기
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < scores.size(); i++) {
            int iSumMax = Integer.MIN_VALUE;
            for (int j = i + 1; j < scores.size(); j++) {
                iSumMax = add(scores.get(i), scores.get(j));
                if (iSumMax != Integer.MIN_VALUE) {
                    break;
                }
            }
            result = Math.max(result, iSumMax);
        }

        // 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
    }

    private static int add(Score score1, Score score2) {
        // 좌표가 겹친다면
        if (overlap(score1, score2)) {
            return Integer.MIN_VALUE;
        }

        return score1.getValue() + score2.getValue();
    }

    private static boolean overlap(Score score1, Score score2) {
        Tetromino tetromino1 = score1.getTetromino();
        Tetromino tetromino2 = score2.getTetromino();
        if (!overlap(tetromino1.minX + score1.getRow(), tetromino1.maxX + score1.getRow(),
                tetromino2.minX + score2.getRow(), tetromino2.maxX + score2.getRow())
        ) {
            return false;
        }
        if (!overlap(tetromino1.minY + score1.getColumn(), tetromino1.maxY + score1.getColumn(),
                tetromino2.minY + score2.getColumn(), tetromino2.maxY + score2.getColumn())) {
            return false;
        }
        return true;
    }

    private static boolean overlap(int range1Min, int range1Max, int range2Min, int range2Max) {
        if ((range1Min < range2Min && range1Max < range2Min) ||
                (range2Min < range1Min && range2Max < range1Min)) {
            return false;
        }
        return true;
    }

    private static List<Score> getScores(int[][] matrix, int row, int col,
            List<Tetromino> tetrominos) {
        List<Score> result = new ArrayList<>();
        for (Tetromino tetromino : tetrominos) {
            if (!validateRange(tetromino, row, col, matrix.length, matrix[0].length)) {
                continue;
            }

            int value = 0;
            List<Coordinate> coordinate = tetromino.getCoordinates();
            for (int i = 0; i < coordinate.size(); i++) {
                value += matrix[row + coordinate.get(i).getX()][col + coordinate.get(i).getY()];
            }

            result.add(new Score(tetromino, row, col, value));
        }
        return result;
    }

    private static boolean validateRange(
            Tetromino tetromino, int row, int col, int rowRange, int colRange) {
        if (tetromino.getMinX() + row < 0 || tetromino.getMinY() + col < 0 ||
                tetromino.getMaxX() + row >= rowRange || tetromino.getMaxY() + col >= colRange) {
            return false;
        }
        return true;
    }

    private static List<Tetromino> createTetrominos() {
        List<Tetromino> straightTetrominos = createStraightTetrominos();
        List<Tetromino> squareTetrominos = createSquareTetrominos();
        List<Tetromino> skewTetrominos = createSkewTetrominos();
        List<Tetromino> lTetrominos = createLTetrominos();
        List<Tetromino> tTetrominos = createTTetrominos();

        List<Tetromino> result = new ArrayList<>();
        result.addAll(straightTetrominos);
        result.addAll(squareTetrominos);
        result.addAll(skewTetrominos);
        result.addAll(lTetrominos);
        result.addAll(tTetrominos);
        return result;
    }

    private static List<Tetromino> createTTetrominos() {
        Tetromino T = new Tetromino(
                getCoordinates(new Coordinate(0, 0), new Coordinate(1, 0),
                        new Coordinate(2, 0), new Coordinate(1, 1))
        );
        Tetromino T90 = getRotated90(T);
        Tetromino T180 = getRotated90(T90);
        Tetromino T270 = getRotated90(T180);

        List<Tetromino> result = new ArrayList<>();
        result.add(T);
        result.add(T90);
        result.add(T180);
        result.add(T270);
        return result;
    }

    private static List<Tetromino> createLTetrominos() {
        Tetromino l = new Tetromino(
                getCoordinates(new Coordinate(0, 0), new Coordinate(0, 1),
                        new Coordinate(0, 2), new Coordinate(1, 2))
        );
        Tetromino l90 = getRotated90(l);
        Tetromino l180 = getRotated90(l90);
        Tetromino l270 = getRotated90(l180);
        Tetromino lSymmetry = getXAxisSymmetry(l);
        Tetromino lSymmetry90 = getRotated90(lSymmetry);
        Tetromino lSymmetry180 = getRotated90(lSymmetry90);
        Tetromino lSymmetry270 = getRotated90(lSymmetry180);

        List<Tetromino> result = new ArrayList<>();
        result.add(l);
        result.add(l90);
        result.add(l180);
        result.add(l270);
        result.add(lSymmetry);
        result.add(lSymmetry90);
        result.add(lSymmetry180);
        result.add(lSymmetry270);
        return result;
    }

    private static List<Tetromino> createSkewTetrominos() {
        Tetromino skew = new Tetromino(
                getCoordinates(new Coordinate(0, 0), new Coordinate(0, 1),
                        new Coordinate(1, 1), new Coordinate(1, 2))
        );
        Tetromino skew90 = getRotated90(skew);
        Tetromino skewSymmetry = getXAxisSymmetry(skew);
        Tetromino skewSymmetry90 = getRotated90(skewSymmetry);

        List<Tetromino> result = new ArrayList<>();
        result.add(skew);
        result.add(skew90);
        result.add(skewSymmetry);
        result.add(skewSymmetry90);
        return result;
    }

    private static Tetromino getXAxisSymmetry(Tetromino tetromino) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (Coordinate coordinate : tetromino.getCoordinates()) {
            coordinates.add(new Coordinate(coordinate.getX(), -coordinate.getY()));
        }
        return new Tetromino(coordinates);
    }

    private static List<Tetromino> createSquareTetrominos() {
        Tetromino square = new Tetromino(
                getCoordinates(new Coordinate(0, 0), new Coordinate(1, 0),
                        new Coordinate(0, 1), new Coordinate(1, 1))
        );
        List<Tetromino> result = new ArrayList<>();
        result.add(square);
        return result;
    }

    private static List<Tetromino> createStraightTetrominos() {
        Tetromino straight = new Tetromino(
                getCoordinates(new Coordinate(0, 0), new Coordinate(0, 1),
                        new Coordinate(0, 2), new Coordinate(0, 3))
        );
        Tetromino straight90 = getRotated90(straight);
        List<Tetromino> result = new ArrayList<>();
        result.add(straight);
        result.add(straight90);
        return result;
    }

    private static Tetromino getRotated90(Tetromino tetromino) {
        List<Coordinate> coordinates = new ArrayList<>();
        for (Coordinate coordinate : tetromino.getCoordinates()) {
            coordinates.add(new Coordinate(-coordinate.getY(), coordinate.getX()));
        }
        return new Tetromino(coordinates);
    }

    private static List<Coordinate> getCoordinates(Coordinate coordinate, Coordinate coordinate1,
            Coordinate coordinate2, Coordinate coordinate3) {
        List<Coordinate> result = new ArrayList<>();
        result.add(coordinate);
        result.add(coordinate1);
        result.add(coordinate2);
        result.add(coordinate3);
        return result;
    }
}