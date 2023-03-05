import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
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

class Vertex implements Comparable<Vertex> {

    private int index;
    private int weight;

    public Vertex(int index, int weight) {
        this.index = index;
        this.weight = weight;
    }

    public int getIndex() {
        return index;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int compareTo(Vertex vertex) {
        return weight - vertex.getWeight();
    }
}

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    private static int MAX_VALUE = 1_000 * 10_000;
    private static int MIN_VERTEX_NUMBER = 1;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 전체 구조 입력
        int vertexSize = reader.nextInt();
        int edgeSize = reader.nextInt();
        int startVertex = reader.nextInt();

        // 초기화
        int[][] directedGraph = new int[vertexSize + 1][vertexSize + 1];
        for (int i = 0; i < directedGraph.length; i++) {
            Arrays.fill(directedGraph[i], MAX_VALUE + 1);
            directedGraph[i][i] = 0;
        }
        int[][] reverseDirectedGraph = new int[vertexSize + 1][vertexSize + 1];
        for (int i = 0; i < reverseDirectedGraph.length; i++) {
            Arrays.fill(reverseDirectedGraph[i], MAX_VALUE + 1);
            reverseDirectedGraph[i][i] = 0;
        }

        // 가중치 입력
        for (int i = 0; i < edgeSize; i++) {
            int vertex1 = reader.nextInt();
            int vertex2 = reader.nextInt();
            int weight = reader.nextInt();

            directedGraph[vertex1][vertex2] = weight;
            reverseDirectedGraph[vertex2][vertex1] = weight;
        }

        int[] distance = dijkstra(directedGraph, startVertex);
        int[] reverseDistance = dijkstra(reverseDirectedGraph, startVertex);

        int result = -1;
        for (int i = MIN_VERTEX_NUMBER; i < distance.length; i++) {
            result = Math.max(result, distance[i] + reverseDistance[i]);
        }

        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static int[] dijkstra(int[][] directedGraph, int startVertex) {
        int[] result = new int[directedGraph.length];
        Arrays.fill(result, MAX_VALUE + 1);
        result[startVertex] = 0;

        PriorityQueue<Vertex> pq = new PriorityQueue<>();
        pq.add(new Vertex(startVertex, result[startVertex]));
        boolean[] visited = new boolean[directedGraph.length];
        while (!pq.isEmpty()) {
            Vertex vertex = pq.poll();
            int index = vertex.getIndex();
            if (visited[index]) {
                continue;
            }
            visited[index] = true;

            for (int i = 0; i < visited.length; i++) {
                if (result[i] < result[index] + directedGraph[index][i]) {
                    continue;
                }

                result[i] = result[index] + directedGraph[index][i];
                pq.add(new Vertex(i, result[i]));
            }
        }
        return result;
    }
}