import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

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

    static class Point {

        private Integer pastPath;
        private Integer time;

        public Point(Integer pastPath, Integer time) {
            this.pastPath = pastPath;
            this.time = time;
        }

        public Integer getPastPath() {
            return pastPath;
        }

        public void setPastPath(Integer pastPath) {
            this.pastPath = pastPath;
        }

        public Integer getTime() {
            return time;
        }

        public void setTime(Integer time) {
            this.time = time;
        }
    }

    public static final int MAX_NUMBER = 100_000;

    public static void main(String[] args) {
        // 입출력 설정
        TokenizerReader reader = new TokenizerReader(System.in);
        BuilderWriter writer = new BuilderWriter(System.out);

        // 입력
        int startPoint = reader.nextInt();
        int endPoint = reader.nextInt();

        int maxPoint = startPoint > endPoint ? startPoint : endPoint;
        Point[] points = new Point[MAX_NUMBER + 1];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(-1, MAX_NUMBER + 1);
        }
        points[startPoint].setTime(0);
        points[startPoint].setPastPath(-1);

        bfs(points, startPoint);

        // 출력
        writer.println(String.valueOf(points[endPoint].time));
        ArrayList<Integer> path = new ArrayList<>();
        getPath(path, points, endPoint);
        writer.println(
                path.stream().map(String::valueOf).collect(Collectors.joining(" "))
        );

        // 종료 작업
        writer.close();
        reader.close();
    }

    private static void getPath(ArrayList<Integer> path, Point[] points, int point) {
        if (points[point].getPastPath() == -1) {
            path.add(point);
            return;
        }

        getPath(path, points, points[point].pastPath);
        path.add(point);
    }

    private static void bfs(Point[] points, int startPoint) {
        // 초기화
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startPoint);

        while (!queue.isEmpty()) {
            int point = queue.remove();

            int visitPoint1 = point - 1;
            if (validRange(points, visitPoint1)) {
                visit(points, queue, point, visitPoint1);
            }

            int visitPoint2 = point + 1;
            if (validRange(points, visitPoint2)) {
                visit(points, queue, point, visitPoint2);
            }

            int visitPoint3 = point * 2;
            if (validRange(points, visitPoint3)) {
                visit(points, queue, point, visitPoint3);
            }
        }
    }

    private static void visit(Point[] points, Queue<Integer> queue,
            int originalPoint, int visitPoint) {
        if ((points[originalPoint].getTime() + 1 < points[visitPoint].getTime())) {
            points[visitPoint].setPastPath(originalPoint);
            points[visitPoint].setTime(points[originalPoint].getTime() + 1);
            queue.add(visitPoint);
        }
    }

    private static boolean validRange(Point[] points, int index) {
        if (index < 0 || points.length <= index) {
            return false;
        }
        return true;
    }
}