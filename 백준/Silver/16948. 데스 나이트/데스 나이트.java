import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;
    private static Coordinates[] DIRECTS = new Coordinates[]{
            new Coordinates(-2, -1),
            new Coordinates(-2, 1),
            new Coordinates(0, -2),
            new Coordinates(0, 2),
            new Coordinates(2, -1),
            new Coordinates(2, 1)
    };

    public static void main(String[] args) {
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int chessBoardSize = reader.nextInt();
        Coordinates startCoordinates = getCoordinates();
        Coordinates endCoordinates = getCoordinates();

        int result = bfs(chessBoardSize, startCoordinates, endCoordinates);
        printResult(result);
        close();
    }

    private static Coordinates getCoordinates() {
        int row = reader.nextInt();
        int col = reader.nextInt();
        return new Coordinates(row, col);
    }

    private static int bfs(int chessBoardSize,
            Coordinates startCoordinates, Coordinates endCoordinates) {
        Queue<Object[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[chessBoardSize + 1][chessBoardSize + 1];

        queue.add(new Object[]{startCoordinates, 0});
        while (!queue.isEmpty()) {
            Object[] element = queue.poll();
            Coordinates coordinates = (Coordinates) element[0];
            int count = (Integer) element[1];

            if (!validRange(visited, coordinates)
                    || visited[coordinates.getRow()][coordinates.getCol()]) {
                continue;
            }

            if (endCoordinates.equals(coordinates)) {
                return count;
            }

            visited[coordinates.getRow()][coordinates.getCol()] = true;

            for (Coordinates direct : DIRECTS) {
                int nextRow = coordinates.getRow() + direct.getRow();
                int nextCol = coordinates.getCol() + direct.getCol();
                queue.add(new Object[]{
                        new Coordinates(nextRow, nextCol), count + 1
                });
            }
        }
        return -1;
    }

    private static boolean validRange(boolean[][] visited, Coordinates coordinates) {
        if (coordinates.getRow() < 0 || visited.length <= coordinates.getRow()) {
            return false;
        }
        if (coordinates.getCol() < 0
                || visited[coordinates.getRow()].length <= coordinates.getCol()) {
            return false;
        }
        return true;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coordinates that = (Coordinates) o;
            return getRow() == that.getRow() && getCol() == that.getCol();
        }

        @Override
        public int hashCode() {
            return Objects.hash(getRow(), getCol());
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