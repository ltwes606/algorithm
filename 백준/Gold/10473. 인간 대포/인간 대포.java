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

    private float x;
    private float y;

    public Point(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Point(Point point) {
        this.x = point.getX();
        this.y = point.getY();
    }

    public float getX() {
        return x;
    }

    public float getY() {
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

    private float weight;
    private int index;

    public Vertex(float x, float y, int index) {
        super(x, y);
        this.index = index;
        weight = Float.MAX_VALUE;
    }

    public Vertex(float x, float y, int index, float weight) {
        super(x, y);
        this.index = index;
        this.weight = weight;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int compareTo(Vertex v) {
        if (weight > v.weight) {
            return 1;
        } else if (weight < v.weight) {
            return -1;
        }
        return 0;
    }
}

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static final float METER_PER_SECOND = 5.0F;
    public static final float PREPARATION_TIME = 2.0F;
    public static final float MAX_LAUNCH_RANGE = 50.0F;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        ArrayList<Vertex> vertices = new ArrayList<>();

        // 시작 점 입력
        Point startPoint = new Point(
                Float.parseFloat(reader.next()), Float.parseFloat(reader.next()));
        Vertex startVertex = new Vertex(
                startPoint.getX(), startPoint.getY(), -1
        );

        // 목표 지점 입력
        Vertex endVertex = new Vertex(
                Float.parseFloat(reader.next()), Float.parseFloat(reader.next()), 0
        );
        vertices.add(endVertex);

        // 나머지 좌표 입력
        int size = reader.nextInt();
        for (int i = 0; i < size; i++) {
            vertices.add(
                    new Vertex(Float.parseFloat(reader.next()), Float.parseFloat(reader.next()),
                            1 + i)
            );
        }

        // 다익스트라
        ArrayList<Float> results = new ArrayList<>();
        for (int i = 0; i < vertices.size(); i++) {

            for (int j = 0; j < vertices.size(); j++) {
                vertices.get(i).setWeight(Float.MAX_VALUE);
            }

            Vertex vertex = vertices.get(i);
            vertex.setWeight(findDistance(startVertex, vertex) / METER_PER_SECOND);

            dijkstra(vertices, vertex.getIndex(), endVertex.getIndex());
            results.add(endVertex.getWeight());
        }

        // 결과 출력
        float result = findResult(results);
        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static float findResult(ArrayList<Float> list) {
        float result = Float.MAX_VALUE;
        for (Float value : list) {
            result = Math.min(result, value);
        }
        return result;
    }

    private static void dijkstra(ArrayList<Vertex> vertices, int startIndex, int endIndex) {
        PriorityQueue<Vertex> pq = new PriorityQueue();
        pq.offer(vertices.get(startIndex));

        boolean[] visited = new boolean[vertices.size()];
        while (!pq.isEmpty()) {
            Vertex currentVertex = pq.poll();
            if (currentVertex.getIndex() == endIndex) {
                return;
            }

            if (visited[currentVertex.getIndex()]) {
                continue;
            }
            visited[currentVertex.getIndex()] = true;

            for (Vertex vertex : vertices) {
                float distance = findDistance(currentVertex, vertex);

                float travelTime = findTravelTime(distance);
                if (vertex.getWeight() <= currentVertex.getWeight() + travelTime) {
                    continue;
                }

                vertex.setWeight(currentVertex.getWeight() + travelTime);
                pq.offer(vertex);
            }
        }
    }

    private static float findTravelTime(float distance) {
        float withCannonDistance = Math.abs(distance - MAX_LAUNCH_RANGE);
        float withCannonTime =
                PREPARATION_TIME + withCannonDistance / METER_PER_SECOND;

        float onlyRunTime = distance / METER_PER_SECOND;

        return Math.min(withCannonTime, onlyRunTime);
    }

    private static float findDistance(Vertex vertex1, Vertex vertex2) {
        return (float) Math.sqrt(Math.pow(vertex1.getX() - vertex2.getX(), 2.0)
                + Math.pow(vertex1.getY() - vertex2.getY(), 2.0));
    }
}