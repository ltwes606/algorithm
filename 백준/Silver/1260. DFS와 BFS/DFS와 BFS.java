import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static int vertexCount;

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 전체 구조 입력
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        vertexCount = Integer.parseInt(tokenizer.nextToken());
        int edgeCount = Integer.parseInt(tokenizer.nextToken());
        int startVertex = Integer.parseInt(tokenizer.nextToken());

        // 정점 초기화
        ArrayList<Integer>[] vertices = new ArrayList[vertexCount + 1];
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = new ArrayList<>();
        }

        // 간선 관계 입력
        for (int i = 0; i < edgeCount; i++) {
            tokenizer = new StringTokenizer(reader.readLine());
            int vertexNumber1 = Integer.parseInt(tokenizer.nextToken());
            int vertexNumber2 = Integer.parseInt(tokenizer.nextToken());
            vertices[vertexNumber1].add(vertexNumber2);
            vertices[vertexNumber2].add(vertexNumber1);
        }

        // 정렬
        for (int i = 0; i < vertices.length; i++) {
            Collections.sort(vertices[i]);
        }

        StringBuilder stringBuilder = new StringBuilder();

        // dfs
        dfs(vertices, startVertex, new ArrayList<>(), stringBuilder);
        stringBuilder.append("\n");

        // bfs
        bfs(vertices, startVertex, new ArrayList<>(), stringBuilder);
        System.out.println(stringBuilder);

        // 종료 작업
        reader.close();
    }

    private static void bfs(ArrayList<Integer>[] vertices, Integer startVertex,
            ArrayList<Integer> visited, StringBuilder stringBuilder) {
        Queue<Integer> queue = new LinkedList<Integer>();
        queue.add(startVertex);

        while (!queue.isEmpty()) {
            Integer searchVertex = queue.remove();
            if (visited.contains(searchVertex)) {
                continue;
            }
            visited.add(searchVertex);
            stringBuilder.append(searchVertex + " ");

            for (Integer vertexNumber : vertices[searchVertex]) {
                queue.add(vertexNumber);
            }
        }
    }

    private static void dfs(ArrayList<Integer>[] vertices, Integer startVertex,
            ArrayList<Integer> visited, StringBuilder stringBuilder) {
        if (visited.contains(startVertex)) {
            return;
        }
        visited.add(startVertex);
        stringBuilder.append(startVertex + " ");

        for (Integer vertexNumber : vertices[startVertex]) {
            dfs(vertices, vertexNumber, visited, stringBuilder);
        }
    }
}