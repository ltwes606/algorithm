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

        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static int UNDEFINED_VALUE = 1;
    public static Point[] DIRECTS = {new Point(0, -1), new Point(1, 0),
            new Point(0, 1), new Point(-1, 0)};

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();

        int[][] map = getMap(size);

        // 팀 나누기
        assignTeam(map, 2);

//        // 테스트
//        printMap(map);

        int result = getMinBridgeLength(map);

        // 결과 출력
        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static int getMinBridgeLength(int[][] map) {
        int result = Integer.MAX_VALUE;
        for (int iRow = 0; iRow < map.length; iRow++) {
            for (int iCol = 0; iCol < map[iRow].length; iCol++) {
                int value = map[iRow][iCol];
                if (value == 0) {
                    continue;
                }

                int pointMinLength = getMinBridgeLength(map, value, iRow, iCol);

                result = Math.min(result, pointMinLength);
            }
        }
        return result;
    }

    private static int getMinBridgeLength(int[][] map, int value, int row, int col) {
        int result = Integer.MAX_VALUE;
        for (int iRow = 0; iRow < map.length; iRow++) {
            for (int iCol = 0; iCol < map[iRow].length; iCol++) {
                int pointValue = map[iRow][iCol];
                if (pointValue == 0 || pointValue == value) {
                    continue;
                }

                result = Math.min(result,
                        Math.abs(row - iRow) + Math.abs(col - iCol) - 1);
            }
        }
        return result;
    }

    private static void assignTeam(int[][] map, int startValue) {
        int teamValue = startValue;

        for (int iRow = 0; iRow < map.length; iRow++) {
            for (int iCol = 0; iCol < map[iRow].length; iCol++) {
                if (map[iRow][iCol] != UNDEFINED_VALUE) {
                    continue;
                }

                assignTeam(map, iCol, iRow, teamValue++);
            }
        }
    }

    private static void assignTeam(int[][] map, int startCol, int startRow, int teamValue) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(startCol, startRow));

        while (!queue.isEmpty()) {
            Point point = queue.remove();
            if (!validCoordinate(map, point)
                    || map[point.getY()][point.getX()] != UNDEFINED_VALUE) {
                continue;
            }
            map[point.getY()][point.getX()] = teamValue;

            for (Point direct : DIRECTS) {
                queue.add(new Point(point.getX() + direct.getX(),
                        point.getY() + direct.getY()));
            }
        }
    }

    private static boolean validCoordinate(int[][] map, Point point) {
        int row = point.getY();
        int col = point.getX();

        if (row < 0 || map.length <= row) {
            return false;
        }
        if (col < 0 || map[row].length <= col) {
            return false;
        }
        return true;
    }

    private static void printMap(int[][] map) {
        for (int iRow = 0; iRow < map.length; iRow++) {
            for (int iCol = 0; iCol < map[iRow].length; iCol++) {
                writer.print(map[iRow][iCol] + " ");
            }
            writer.println("");
        }
    }

    private static int[][] getMap(int size) {
        int[][] result = new int[size][size];
        for (int iRow = 0; iRow < size; iRow++) {
            for (int iCol = 0; iCol < size; iCol++) {
                result[iRow][iCol] = reader.nextInt();
            }
        }
        return result;
    }
}