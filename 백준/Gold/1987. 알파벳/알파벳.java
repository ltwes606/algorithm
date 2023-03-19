import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    private static Coordinates[] DIRECTS = new Coordinates[]{
            new Coordinates(-1, 0), new Coordinates(0, 1),
            new Coordinates(1, 0), new Coordinates(0, -1)
    };

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int rowSize = reader.nextInt();
        int colSize = reader.nextInt();

        char[][] map = getMap(rowSize, colSize);

        // 최대 칸 수 구하기
        Coordinates startCoordinates = new Coordinates(0, 0);
        int result = dfs(map, startCoordinates, new HashSet<>());

        // 결과 출력
        printResult(result);

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static char[][] getMap(int rowSize, int colSize) {
        char[][] map = new char[rowSize][colSize];
        for (int iRow = 0; iRow < map.length; iRow++) {
            String line = reader.next();
            for (int iCol = 0; iCol < map[iRow].length; iCol++) {
                map[iRow][iCol] = line.charAt(iCol);
            }
        }
        return map;
    }

    private static int dfs(char[][] map, Coordinates currentCoordinates, Set<Character> visited) {
        int row = currentCoordinates.getRow();
        int col = currentCoordinates.getCol();
        Character character = map[row][col];
        if (visited.contains(character)) {
            return visited.size();
        }
        visited.add(character);

        int result = visited.size();
        for (Coordinates direct : DIRECTS) {
            int nextRow = row + direct.getRow();
            int nextCol = col + direct.getCol();
            if (!validRange(map, nextRow, nextCol)) {
                continue;
            }

            Coordinates nextCoordinate = new Coordinates(nextRow, nextCol);
            result = Math.max(result, dfs(map, nextCoordinate, visited));
        }
        visited.remove(character);
        return result;
    }

    private static boolean validRange(char[][] map, int row, int col) {
        if (row < 0 || map.length <= row) {
            return false;
        }
        if (col < 0 || map[row].length <= col) {
            return false;
        }
        return true;
    }

    private static void printResult(int result) {
        writer.println(String.valueOf(result));
    }
}