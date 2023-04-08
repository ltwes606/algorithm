import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static final int BLANK = 0;
    public static final int WALL = 1;
    public static final int VIRUS = 2;

    private static final Coordinates[] DIRECTS = new Coordinates[]{
            new Coordinates(-1, 0),
            new Coordinates(0, 1),
            new Coordinates(1, 0),
            new Coordinates(0, -1),
    };

    public static void main(String[] args) {
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int rowSize = reader.nextInt();
        int colSize = reader.nextInt();
        int[][] map = createMap(rowSize, colSize);

        List<Coordinates> allCoordinates = getAllCoordinates(map);
        List<Coordinates> viruses = findViruses(map);

        int result = 0;
        for (int first = 0; first < allCoordinates.size() - 2; first++) {
            for (int second = first + 1; second < allCoordinates.size() - 1; second++) {
                for (int third = second + 1; third < allCoordinates.size(); third++) {
                    if (isImpossible(map, allCoordinates.get(first))
                            || isImpossible(map, allCoordinates.get(second))
                            || isImpossible(map, allCoordinates.get(third))) {
                        continue;
                    }

                    set(map, allCoordinates, first, second, third, WALL);
                    result = Math.max(result, spreadViruses(map, viruses));
                    set(map, allCoordinates, first, second, third, BLANK);
                }
            }
        }
        printResult(result);
        close();
    }

    private static int[][] createMap(int rowSize, int colSize) {
        int[][] map = new int[rowSize][colSize];
        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                map[row][col] = reader.nextInt();
            }
        }
        return map;
    }

    private static List<Coordinates> getAllCoordinates(int[][] map) {
        List<Coordinates> result = new ArrayList<>();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                result.add(new Coordinates(row, col));
            }
        }
        return result;
    }

    private static List<Coordinates> findViruses(int[][] map) {
        List<Coordinates> result = new ArrayList<>();
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] == VIRUS) {
                    result.add(new Coordinates(row, col));
                }
            }
        }
        return result;
    }

    private static boolean isImpossible(int[][] map, Coordinates coordinates) {
        if (map[coordinates.getRow()][coordinates.getCol()] == BLANK) {
            return false;
        }
        return true;
    }

    private static void set(int[][] map, List<Coordinates> allCoordinates,
            int first, int second, int third, int value) {
        map[allCoordinates.get(first).getRow()][allCoordinates.get(first)
                .getCol()] = value;
        map[allCoordinates.get(second).getRow()][allCoordinates.get(second)
                .getCol()] = value;
        map[allCoordinates.get(third).getRow()][allCoordinates.get(third)
                .getCol()] = value;
    }

    private static int spreadViruses(int[][] map, List<Coordinates> viruses) {
        int[][] infectedMap = new int[map.length][map[0].length];
        for (int i = 0; i < map.length; i++) {
            infectedMap[i] = map[i].clone();
        }

        Queue<Coordinates> queue = new LinkedList<>();
        for (int i = 0; i < viruses.size(); i++) {
            queue.offer(viruses.get(i));
        }

        boolean[][] visited = new boolean[infectedMap.length][infectedMap[0].length];
        while (!queue.isEmpty()) {
            Coordinates coordinates = queue.poll();
            if (rangeExceeded(infectedMap, coordinates)
                    || visited[coordinates.getRow()][coordinates.getCol()]
                    || isValue(infectedMap, coordinates, WALL)) {
                continue;
            }
            visited[coordinates.getRow()][coordinates.getCol()] = true;
            infectedMap[coordinates.getRow()][coordinates.getCol()] = VIRUS;

            for (Coordinates direct : DIRECTS) {
                Coordinates nextCoordinates = new Coordinates(
                        coordinates.getRow() + direct.getRow(),
                        coordinates.getCol() + direct.getCol());

                queue.offer(nextCoordinates);
            }
        }

        return countValue(infectedMap, BLANK);
    }

    private static boolean rangeExceeded(int[][] infectedMap, Coordinates coordinates) {
        if (coordinates.getRow() < 0 || infectedMap.length <= coordinates.getRow()) {
            return true;
        }
        if (coordinates.getCol() < 0 || infectedMap[0].length <= coordinates.getCol()) {
            return true;
        }
        return false;
    }

    private static boolean isValue(int[][] infectedMap, Coordinates coordinates, int value) {
        if (infectedMap[coordinates.getRow()][coordinates.getCol()] == value) {
            return true;
        }
        return false;
    }

    private static int countValue(int[][] infectedMap, int value) {
        int result = 0;
        for (int row = 0; row < infectedMap.length; row++) {
            for (int col = 0; col < infectedMap[row].length; col++) {
                if (infectedMap[row][col] == value) {
                    result++;
                }
            }
        }
        return result;
    }

    private static void printResult(int result) {
        writer.println(String.valueOf(result));
    }

    private static void close() {
        reader.close();
        writer.close();
    }

    static class Coordinates {

        private int row;
        private int col;

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