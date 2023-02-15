import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    public static void main(String[] args) {
        // 입력 설정
        TokenizerReader reader = new TokenizerReader(System.in);

        // 구조 입력
        int vertexCount = reader.nextInt();
        int edgeCount = reader.nextInt();

        // 구조 초기화
        ArrayList<Integer>[] vertices = new ArrayList[vertexCount + 1];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new ArrayList<>();
        }

        // 연결 입력
        for (int i = 0; i < edgeCount; i++) {
            int vertex1 = reader.nextInt();
            int vertex2 = reader.nextInt();

            vertices[vertex1].add(vertex2);
            vertices[vertex2].add(vertex1);
        }

        // bfs
        Queue<Integer> visitable = new LinkedList<>();
        for (int i = 1; i < vertices.length; i++) {
            visitable.add(i);
        }
        int result = 0;
        while (!visitable.isEmpty()) {
            bfs(vertices, visitable, visitable.peek());
            result++;
        }

        // 결과 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
    }

    private static void bfs(ArrayList<Integer>[] vertices, Queue<Integer> visitable, int startVertex) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startVertex);
        visitable.remove(startVertex);

        while (!queue.isEmpty()) {
            int searchVertex = queue.remove();

            for (int elementVertex : vertices[searchVertex]) {
                if (!visitable.contains(elementVertex)) {
                    continue;
                }

                queue.add(elementVertex);
                visitable.remove(elementVertex);
            }
        }
    }
}