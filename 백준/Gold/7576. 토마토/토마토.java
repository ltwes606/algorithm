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

    static class Coordinate {

        private int x;
        private int y;

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
    }

    public static final int ROTTEN = -1;
    public static final int UNRIPE = 0;
    public static final int RIPE = 1;

    public static void main(String[] args) {
        // 입출력 설정
        TokenizerReader reader = new TokenizerReader(System.in);
        BuilderWriter writer = new BuilderWriter(System.out);

        // 입력
        int column = reader.nextInt();
        int row = reader.nextInt();
        int[][] map = new int[row][column];
        for (int iRow = 0; iRow < row; iRow++) {
            for (int iCol = 0; iCol < column; iCol++) {
                map[iRow][iCol] = reader.nextInt();
            }
        }

        // bfs
        bfs(map, getRipeTomatoes(map));
        int result = getMaxValue(map) - 1;
        if (existElement(map, UNRIPE)) {
            result = -1;
        }

        // 결과 출력
        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static int getMaxValue(int[][] map) {
        int result = 0;
        for (int iRow = 0; iRow < map.length; iRow++) {
            for (int iCol = 0; iCol < map[iRow].length; iCol++) {
                if (map[iRow][iCol] > result) {
                    result = map[iRow][iCol];
                }
            }
        }
        return result;
    }

    private static void bfs(int[][] map, ArrayList<Coordinate> startList) {
        Queue<Coordinate> queue = new LinkedList<>();
        copyList(queue, startList);
        boolean[][] visited = new boolean[map.length][map[0].length];

        while (!queue.isEmpty()) {
            Coordinate coordinate = queue.remove();
            int row = coordinate.getX();
            int col = coordinate.getY();
            if (visited[row][col] || map[row][col] == ROTTEN) {
                continue;
            }
            visited[row][col] = true;

            // 4방향 확장
            int value = map[row][col];
            if (changeCoordinate(map, row, col - 1, UNRIPE, value + 1)) {
                queue.add(new Coordinate(row, col - 1));
            }
            if (changeCoordinate(map, row, col + 1, UNRIPE, value + 1)) {
                queue.add(new Coordinate(row, col + 1));
            }
            if (changeCoordinate(map, row - 1, col, UNRIPE, value + 1)) {
                queue.add(new Coordinate(row - 1, col));
            }
            if (changeCoordinate(map, row + 1, col, UNRIPE, value + 1)) {
                queue.add(new Coordinate(row + 1, col));
            }
        }
    }

    private static void copyList(Queue<Coordinate> queue, List<Coordinate> list) {
        for (Coordinate element : list) {
            queue.add(element);
        }
    }

    private static boolean changeCoordinate(int[][] map, int row, int col, int oldValue,
            int newValue) {
        if (!validCoordinate(map, row, col) || map[row][col] != oldValue) {
            return false;
        }
        map[row][col] = newValue;
        return true;
    }

    private static boolean validCoordinate(int[][] map, int row, int col) {
        if (row < 0 || map.length <= row) {
            return false;
        }
        if (col < 0 || map[row].length <= col) {
            return false;
        }
        return true;
    }

    private static ArrayList<Coordinate> getRipeTomatoes(int[][] map) {
        ArrayList<Coordinate> result = new ArrayList<>();
        for (int iRow = 0; iRow < map.length; iRow++) {
            for (int iCol = 0; iCol < map[iRow].length; iCol++) {
                if (map[iRow][iCol] == RIPE) {
                    result.add(new Coordinate(iRow, iCol));
                }
            }
        }
        return result;
    }

    private static boolean existElement(int[][] map, int element) {
        for (int iRow = 0; iRow < map.length; iRow++) {
            for (int iCol = 0; iCol < map[iRow].length; iCol++) {
                if (map[iRow][iCol] == element) {
                    return true;
                }
            }
        }
        return false;
    }
}