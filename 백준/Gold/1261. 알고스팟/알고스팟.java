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

class TokenizerReader {

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

class BuilderWriter {

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

class Wall {

    private boolean existence;
    private int breakCount;

    public Wall(boolean existence, int breakCount) {
        this.existence = false;
        this.breakCount = breakCount;
    }

    public boolean isExistence() {
        return existence;
    }

    public void setExistence(boolean existence) {
        this.existence = existence;
    }

    public int getBreakCount() {
        return breakCount;
    }

    public void setBreakCount(int breakCount) {
        this.breakCount = breakCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Wall wall = (Wall) o;
        return isExistence() == wall.isExistence() && getBreakCount() == wall.getBreakCount();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isExistence(), getBreakCount());
    }
}

class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    private static Point[] DIRECTS = {
            new Point(0, -1), new Point(1, 0),
            new Point(0, 1), new Point(-1, 0)
    };

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int colSize = reader.nextInt();
        int rowSize = reader.nextInt();
        Wall[][] map = new Wall[rowSize][colSize];
        for (int iRow = 0; iRow < rowSize; iRow++) {
            String line = reader.next();
            for (int iCol = 0; iCol < colSize; iCol++) {
                char existenceChar = line.charAt(iCol);
                map[iRow][iCol] = new Wall(false, Integer.MAX_VALUE);
                if (existenceChar == '1') {
                    map[iRow][iCol].setExistence(true);
                }
            }
        }

        // 테스트
//        printMap(map);

        // 연산
        Point startPoint = new Point(0, 0);
        map[startPoint.getY()][startPoint.getX()].setBreakCount(0);
        bfs(map, startPoint);

        // 결과 출력
        Point endPoint = new Point(map[map.length - 1].length - 1, map.length - 1);
        int result = map[endPoint.getY()][endPoint.getX()].getBreakCount();
        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static void bfs(Wall[][] map, Point startPoint) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(startPoint);

        while (!queue.isEmpty()) {
            Point point = queue.remove();

            for (Point direct : DIRECTS) {
                Point nextPoint = new Point(
                        point.getX() + direct.getX(),
                        point.getY() + direct.getY()
                );
                if (!validPoint(map, nextPoint)) {
                    continue;
                }

                int tmpBreakCount = map[point.getY()][point.getX()].getBreakCount();
                if (map[nextPoint.getY()][nextPoint.getX()].isExistence()) {
                    tmpBreakCount++;
                }
                if (tmpBreakCount >= map[nextPoint.getY()][nextPoint.getX()].getBreakCount()) {
                    continue;
                }

                map[nextPoint.getY()][nextPoint.getX()].setBreakCount(tmpBreakCount);
                queue.add(nextPoint);
            }
        }
    }

    private static boolean validPoint(Wall[][] map, Point nextPoint) {
        int x = nextPoint.getX();
        int y = nextPoint.getY();
        if (y < 0 || map.length <= y) {
            return false;
        }
        if (x < 0 || map[y].length <= x) {
            return false;
        }
        return true;
    }

    private static void printMap(Wall[][] map) {
        for (int iRow = 0; iRow < map.length; iRow++) {
            for (int iCol = 0; iCol < map[iRow].length; iCol++) {
                writer.print(map[iRow][iCol].isExistence() + " ");
            }
            writer.println("");
        }
    }
}