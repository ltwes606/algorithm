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

    static class Coordinate {

        private int row;
        private int col;

        public Coordinate(int row, int col) {
            this.row = row;
            this.col = col;
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
    }

    public static final Coordinate[] directions = {
            new Coordinate(-1, -2),
            new Coordinate(-2, -1),
            new Coordinate(-2, 1),
            new Coordinate(-1, 2),
            new Coordinate(1, 2),
            new Coordinate(2, 1),
            new Coordinate(2, -1),
            new Coordinate(1, -2),
    };


    public static void main(String[] args) {
        // 입출력 설정
        TokenizerReader reader = new TokenizerReader(System.in);
        BuilderWriter writer = new BuilderWriter(System.out);

        int caseCount = reader.nextInt();

        int[] result = new int[caseCount];
        for (int i = 0; i < caseCount; i++) {
            int size = reader.nextInt();
            int[][] chessboard = new int[size][size];

            Coordinate startPoint = new Coordinate(
                    reader.nextInt(), reader.nextInt()
            );
            Coordinate endPoint = new Coordinate(
                    reader.nextInt(), reader.nextInt()
            );

            bfs(chessboard, startPoint, endPoint);
            result[i] = chessboard[endPoint.getRow()][endPoint.getCol()];
        }

        // 출력
        for (int i = 0; i < result.length; i++) {
            writer.println(String.valueOf(result[i]));
        }

        // 종료 작업
        writer.close();
        reader.close();
    }

    private static void bfs(int[][] chessboard, Coordinate startPoint, Coordinate endPoint) {
        // 초기화
        boolean[][] visited = new boolean[chessboard.length][chessboard[0].length];
        Queue<Coordinate> queue = new LinkedList<>();
        visited[startPoint.getRow()][startPoint.getCol()] = true;
        queue.add(new Coordinate(startPoint.getRow(), startPoint.getCol()));

        while (!queue.isEmpty() && !visited[endPoint.getRow()][endPoint.getCol()]) {
            Coordinate coordinate = queue.remove();

            for (Coordinate direction : directions) {
                int row = coordinate.getRow() + direction.getRow();
                int col = coordinate.getCol() + direction.getCol();
                if (!validRange(visited, row, col) || visited[row][col]) {
                    continue;
                }

                visited[row][col] = true;
                chessboard[row][col] = chessboard[coordinate.getRow()][coordinate.getCol()] + 1;
                queue.add(new Coordinate(row, col));
            }
        }
    }

    private static boolean validRange(boolean[][] visited, int row, int col) {
        if (row < 0 || visited.length <= row) {
            return false;
        }
        if (col < 0 || visited[row].length <= col) {
            return false;
        }
        return true;
    }
}