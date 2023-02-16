import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
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

    public static final int MAX_RANGE = 100;
    public static final int MAX_VALUE = MAX_RANGE * MAX_RANGE;


    public static void main(String[] args) {
        // 입력 설정
        TokenizerReader reader = new TokenizerReader(System.in);

        // 입력
        int row = reader.nextInt();
        int col = reader.nextInt();
        int[][] inputs = new int[row][col];
        for (int iRow = 0; iRow < row; iRow++) {
            char[] line = reader.next().toCharArray();
            for (int iCol = 0; iCol < col; iCol++) {
                inputs[iRow][iCol] = line[iCol] - '0';
            }
        }

        // 초기화
        int[][] result = new int[row][col];
        for (int iRow = 0; iRow < result.length; iRow++) {
            Arrays.fill(result[iRow], MAX_VALUE);
        }
        result[0][0] = 1;

        // bfs
        bfs(inputs, result);

        // 결과 출력
        System.out.println(result[result.length - 1][result[result.length - 1].length - 1]);

        // 종료 작업
        reader.close();
    }

    private static void bfs(int[][] map, int[][] result) {
        Queue<Coordinates> queue = new LinkedList<>();
        queue.add(new Coordinates(0, 0));
        boolean[][] visited = new boolean[result.length][result[0].length];
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            Coordinates coordinates = queue.remove();

            int row = coordinates.getRow();
            int col = coordinates.getCol();
            // 최소칸
            result[row][col] = Math.min(result[row][col], getResult(result, row - 1, col) + 1);
            result[row][col] = Math.min(result[row][col], getResult(result, row, col - 1) + 1);
            result[row][col] = Math.min(result[row][col], getResult(result, row, col + 1) + 1);
            result[row][col] = Math.min(result[row][col], getResult(result, row + 1, col) + 1);

            enqueue(queue, map, row - 1, col, visited);
            enqueue(queue, map, row, col - 1, visited);
            enqueue(queue, map, row, col + 1, visited);
            enqueue(queue, map, row + 1, col, visited);
        }
    }

    private static void enqueue(Queue<Coordinates> queue, int[][] map, int row, int col,
            boolean[][] visited) {
        if (!validRange(map, row, col) || map[row][col] == 0 || visited[row][col]) {
            return;
        }

        visited[row][col] = true;
        queue.add(new Coordinates(row, col));
    }

    private static int getResult(int[][] map, int row, int col) {
        if (!validRange(map, row, col)) {
            return MAX_VALUE;
        }
        return map[row][col];
    }

    private static boolean validRange(int[][] map, int row, int col) {
        if (row < 0 || map.length <= row) {
            return false;
        }
        if (col < 0 || map[row].length <= col) {
            return false;
        }
        return true;
    }
}