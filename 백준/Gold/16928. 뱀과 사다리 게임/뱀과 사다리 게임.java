import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static void main(String[] args) {
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int laddersCount = reader.nextInt();
        int snakesCount = reader.nextInt();
        Map<Integer, Integer> ladderMap = getLadderMap(laddersCount);
        Map<Integer, Integer> snakeMap = getSnakeMap(snakesCount);

        int result = bfs(1, 100, ladderMap, snakeMap);
        printResult(result);
        close();
    }

    private static int bfs(int startPoint, int endPoint,
            Map<Integer, Integer> ladderMap, Map<Integer, Integer> snakeMap) {
        Queue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>() {
            @Override
            public int compare(int[] e1, int[] e2) {
                return e1[1] - e2[1];
            }
        });
        boolean[] visited = new boolean[endPoint + 1];

        queue.add(new int[]{startPoint, 0});
        while (!queue.isEmpty()) {
            int[] element = queue.poll();
            int point = element[0];
            int count = element[1];
            if (point > endPoint || visited[point]) {
                continue;
            }
            if (point == endPoint) {
                return count;
            }
            visited[point] = true;

            for (int dots = 1; dots <= 6; dots++) {
                int[] newElement = new int[]{point + dots, count + 1};

                newElement[0] = snakeMap.getOrDefault(newElement[0], newElement[0]);
                newElement[0] = ladderMap.getOrDefault(newElement[0], newElement[0]);

                queue.offer(newElement);
            }
        }
        return -1;
    }

    private static Map<Integer, Integer> getLadderMap(int laddersCount) {
        Map<Integer, Integer> ladderMap = new HashMap<>();
        for (int i = 0; i < laddersCount; i++) {
            int startPoint = reader.nextInt();
            int endPoint = reader.nextInt();

            ladderMap.put(startPoint, endPoint);
        }
        return ladderMap;
    }

    private static Map<Integer, Integer> getSnakeMap(int snakesCount) {
        Map<Integer, Integer> snakeMap = new HashMap<>();
        for (int i = 0; i < snakesCount; i++) {
            int startPoint = reader.nextInt();
            int endPoint = reader.nextInt();

            snakeMap.put(startPoint, endPoint);
        }
        return snakeMap;
    }

    private static void printResult(int result) {
        writer.println(String.valueOf(result));
    }

    private static void close() {
        reader.close();
        writer.close();
    }

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
}