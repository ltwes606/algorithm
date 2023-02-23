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

    public static final int WHITE = 0;
    public static final int RED = 1;
    public static final int BLUE = 2;

    public static void main(String[] args) {
        // 입출력 설정
        TokenizerReader reader = new TokenizerReader(System.in);
        BuilderWriter writer = new BuilderWriter(System.out);

        // 케이스 개수 입력
        int count = reader.nextInt();
        boolean[] result = new boolean[count];
        for (int i = 0; i < count; i++) {
            int vertexSize = reader.nextInt();
            int edgeSize = reader.nextInt();

            // 정점들 설정
            ArrayList<Integer>[] vertices = new ArrayList[vertexSize + 1];
            initializeVertices(vertices);

            // 간선들 설정
            for (int j = 0; j < edgeSize; j++) {
                int vertex1 = reader.nextInt();
                int vertex2 = reader.nextInt();

                vertices[vertex1].add(vertex2);
                vertices[vertex2].add(vertex1);
            }

            int[] colors = bfs(vertices, 1);
            result[i] = isBipartiteGraph(vertices, colors);
        }

        // 출력
        for (int i = 0; i < result.length; i++) {
            String output = "NO";
            if (result[i]) {
                output = "YES";
            }
            writer.println(output);
        }

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static boolean isBipartiteGraph(ArrayList<Integer>[] vertices, int[] colors) {
        for (int vertex = 1; vertex < vertices.length; vertex++) {
            for (Integer adjacentVertex : vertices[vertex]) {
                if (colors[vertex] == colors[adjacentVertex]) {
                    return false;
                }
            }
        }
        return true;
    }

    private static int[] bfs(ArrayList<Integer>[] vertices, int startVertex) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[vertices.length];
        int[] colors = new int[vertices.length];
        queue.add(startVertex);
        colors[startVertex] = RED;

        for (int i = 1; i < vertices.length; i++) {
            queue.add(i);
            while (!queue.isEmpty()) {
                int vertex = queue.remove();
                if (visited[vertex]) {
                    continue;
                }

                visited[vertex] = true;
                int invertedColor = invertColor(colors[vertex]);

                for (Integer uncoloredVertex : vertices[vertex]) {
                    colors[uncoloredVertex] = invertedColor;
                    queue.add(uncoloredVertex);
                }
            }
        }
        return colors;
    }

    private static int invertColor(int color) {
        if (color == RED) {
            return BLUE;
        }
        return RED;
    }

    private static void initializeVertices(ArrayList<Integer>[] vertices) {
        for (int i = 1; i < vertices.length; i++) {
            vertices[i] = new ArrayList<>();
        }
    }
}