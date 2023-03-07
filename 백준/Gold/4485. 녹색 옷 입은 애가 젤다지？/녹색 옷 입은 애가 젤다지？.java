import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;
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

class Point {

    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean equals(int x, int y) {
        if (this.x == x && this.y == y) {
            return true;
        }
        return false;
    }
}

class Vertex extends Point implements Comparable<Vertex> {

    private int weight;

    public Vertex(Point point) {
        super(point);
        weight = Integer.MAX_VALUE;
    }

    public Vertex(Point point, int weight) {
        this(point);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Vertex v) {
        return weight - v.weight;
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
        ArrayList<int[][]> maps = new ArrayList<>();
        while (true) {
            int size = reader.nextInt();
            if (size == 0) {
                break;
            }

            // 맵 생성
            int[][] map = new int[size][size];
            for (int iRow = 0; iRow < map.length; iRow++) {
                for (int iCol = 0; iCol < map[iRow].length; iCol++) {
                    map[iRow][iCol] = reader.nextInt();
                }
            }

            // 맵 추가
            maps.add(map);
        }

        // 다익스트라
        int[] result = new int[maps.size()];
        for (int i = 0; i < maps.size(); i++) {
            int[][] map = maps.get(i);
            Point startPoint = new Point(0, 0);
            Point endPoint = new Point(map[map.length - 1].length - 1, map.length - 1);

            result[i] = dijkstra(map, startPoint, endPoint);
        }

        // 결과 출력
        printResult(result);

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static void printResult(int[] result) {
        for (int i = 0; i < result.length; i++) {
            writer.println(String.format("Problem %d: %d", i + 1, result[i]));
        }
    }

    private static int dijkstra(int[][] map, Point startPoint, Point endPoint) {
        PriorityQueue<Vertex> pq = new PriorityQueue();
        pq.offer(new Vertex(startPoint, map[startPoint.getY()][startPoint.getX()]));

        boolean[][] visited = new boolean[map.length][map[map.length - 1].length];
        while (!pq.isEmpty()) {
            Vertex vertex = pq.poll();
            int x = vertex.getX();
            int y = vertex.getY();
            if (visited[y][x]) {
                continue;
            }
            if (endPoint.equals(x, y)) {
                return vertex.getWeight();
            }

            visited[y][x] = true;

            for (Point direct : DIRECTS) {
                Point nextPoint = new Point(x + direct.getX(), y + direct.getY());
                if (!validRange(visited, nextPoint)) {
                    continue;
                }

                pq.offer(new Vertex(nextPoint,
                        vertex.getWeight() + map[nextPoint.getY()][nextPoint.getX()]));
            }
        }
        return -1;
    }

    private static boolean validRange(boolean[][] visited, Point nextPoint) {
        int x = nextPoint.getX();
        int y = nextPoint.getY();

        if (y < 0 || visited.length <= y) {
            return false;
        }
        if (x < 0 || visited[visited.length - 1].length <= x) {
            return false;
        }
        return true;
    }
}