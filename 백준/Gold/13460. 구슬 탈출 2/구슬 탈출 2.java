import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
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

        private int row;
        private int col;

        public Coordinates(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public Coordinates(Coordinates coordinates) {
            this.row = coordinates.getRow();
            this.col = coordinates.getCol();
        }

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }

        @Override
        public String toString() {
            return "Coordinates{" +
                    "row=" + row +
                    ", col=" + col +
                    '}';
        }
    }

    static class Element {

        private final Coordinates redBead;
        private final Coordinates blueBead;
        private int count;
        private boolean completed;

        public Element(Coordinates redBead, Coordinates blueBead, int count) {
            this.redBead = redBead;
            this.blueBead = blueBead;
            this.count = count;
        }

        public Element(Element element) {
            this.redBead = new Coordinates(element.getRedBead());
            this.blueBead = new Coordinates(element.getBlueBead());
            this.count = element.getCount();
        }

        public void addCount() {
            count++;
        }

        public Coordinates getRedBead() {
            return redBead;
        }

        public Coordinates getBlueBead() {
            return blueBead;
        }

        public int getCount() {
            return count;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        @Override
        public String toString() {
            return "Element{" +
                    "redBead=" + redBead +
                    ", blueBead=" + blueBead +
                    ", count=" + count +
                    ", completed=" + completed +
                    '}';
        }
    }

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static final char BLANK = '.';
    public static final char WALL = '#';
    public static final char RED_BEAD = 'R';
    public static final char BLUE_BEAD = 'B';
    public static final char HOLE = 'O';

    public static final int MAX_COUNT = 10;

    private static Coordinates[] DIRECTS = new Coordinates[]{
            new Coordinates(-1, 0), new Coordinates(0, 1),
            new Coordinates(1, 0), new Coordinates(0, -1)
    };

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 맵 입력
        int rowSize = reader.nextInt();
        int colSize = reader.nextInt();

        char[][] map = getMap(rowSize, colSize);
        Coordinates redBead = extractElement(map, RED_BEAD);
        Coordinates blueBead = extractElement(map, BLUE_BEAD);
        Coordinates hole = extractElement(map, HOLE);

        // 빨간 구슬이 구멍을 통과하는 최소 카운트 세기
        int result = bfs(map, redBead, blueBead, hole);

        // 결과 출력
        printResult(result);

        // 종료 작업
        close();
    }

    private static char[][] getMap(int rowSize, int colSize) {
        char[][] map = new char[rowSize][colSize];
        for (int iRow = 0; iRow < rowSize; iRow++) {
            String line = reader.next();

            for (int iCol = 0; iCol < colSize; iCol++) {
                map[iRow][iCol] = line.charAt(iCol);
            }
        }
        return map;
    }

    private static Coordinates extractElement(char[][] map, char element) {
        int rowSize = map.length;
        int colSize = map[0].length;
        for (int iRow = 0; iRow < rowSize; iRow++) {

            for (int iCol = 0; iCol < colSize; iCol++) {

                if (map[iRow][iCol] == element) {
                    map[iRow][iCol] = BLANK;
                    return new Coordinates(iRow, iCol);
                }
            }
        }
        return null;
    }

    private static int bfs(char[][] map,
            Coordinates redBead, Coordinates blueBead, Coordinates hole) {

        int rowSize = map.length;
        int colSize = map[0].length;

        // 첫번째는 빨간 구슬의 위치 두번째는 파란 구슬의 위치이다.
        boolean[][][][] visited = new boolean[rowSize][colSize][rowSize][colSize];

        Queue<Element> queue = new LinkedList<>();
        Element startElement = new Element(redBead, blueBead, 0);
        queue.offer(startElement);

        while (!queue.isEmpty()) {

            Element element = queue.poll();
            if (!visit(visited, element) || element.getCount() == 10) {
                continue;
            }

            for (Coordinates direct : DIRECTS) {

                Element nextElement = getElement(map, element, direct, hole);
                if (nextElement == null) {
                    continue;
                }
                if (nextElement.isCompleted()) {
                    return nextElement.getCount();
                }

                queue.offer(nextElement);
//                System.out.println("element = " + element);
//                System.out.println("nextElement = " + nextElement);
//                System.out.println();
            }
        }
        return -1;
    }

    private static boolean visit(boolean[][][][] visited, Element element) {
        Coordinates redBead = element.getRedBead();
        int redBeadRow = redBead.getRow();
        int redBeadCol = redBead.getCol();

        Coordinates blueBead = element.getBlueBead();
        int blueBeadRow = blueBead.getRow();
        int blueBeadCol = blueBead.getCol();

        if (visited[redBeadRow][redBeadCol][blueBeadRow][blueBeadCol]) {
            return false;
        }
        visited[redBeadRow][redBeadCol][blueBeadRow][blueBeadCol] = true;
        return true;
    }

    private static Element getElement(char[][] map, Element sourceElement, Coordinates direct,
            Coordinates hole) {
        Element result = new Element(sourceElement);

        Coordinates redBead = result.getRedBead();
        Coordinates blueBead = result.getBlueBead();

        while (movable(map, hole, blueBead, redBead, direct)
                || movable(map, hole, redBead, blueBead, direct)) {

            if (movable(map, hole, blueBead, redBead, direct)) {
                redBead.setRow(redBead.getRow() + direct.getRow());
                redBead.setCol(redBead.getCol() + direct.getCol());
            }

            if (movable(map, hole, redBead, blueBead, direct)) {
                blueBead.setRow(blueBead.getRow() + direct.getRow());
                blueBead.setCol(blueBead.getCol() + direct.getCol());
            }
        }
        result.addCount();
        if (equals(hole, redBead.getRow(), redBead.getCol())) {
            result.setCompleted(true);
        }

        if (equals(hole, blueBead.getRow(), blueBead.getCol())) {
            return null;
        }
        return result;
    }

    private static boolean movable(char[][] map, Coordinates hole, Coordinates otherBead,
            Coordinates bead, Coordinates direct) {
        int dRow = direct.getRow();
        int dCol = direct.getCol();

        int currentRow = bead.getRow();
        int currentCol = bead.getCol();

        int nextRow = currentRow + dRow;
        int nextCol = currentCol + dCol;

        if (isWall(map, nextRow, nextCol)) {
            return false;
        }
        if (equals(hole, currentRow, currentCol)
                || (!equals(hole, nextRow, nextCol) && equals(otherBead, nextRow, nextCol))) {
            return false;
        }
        return true;
    }

    private static boolean isWall(char[][] map, int row, int col) {
        if (map[row][col] == WALL) {
            return true;
        }
        return false;
    }

    private static boolean equals(Coordinates coordinates, int row, int col) {
        if (coordinates.getRow() == row && coordinates.getCol() == col) {
            return true;
        }
        return false;
    }

    private static void printResult(int result) {
        writer.println(String.valueOf(result));
    }

    private static void close() {
        reader.close();
        writer.close();
    }
}