import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static void main(String[] args) {
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int A = reader.nextInt();
        int B = reader.nextInt();
        int C = reader.nextInt();

        int result = 0;
        if ((A + B + C) % 3 == 0) {
            result = bfs(A, B, C);
        }

        printResult(result);
        close();
    }

    private static int bfs(int A, int B, int C) {
        Queue<int[]> queue = new LinkedList<>();

        int[] startElement = new int[]{A, B, C};
        Arrays.sort(startElement);
        queue.offer(startElement);

        boolean[][] visited = new boolean[1000][1000];
        while (!queue.isEmpty()) {
            int[] element = queue.poll();
            if (element[0] <= 0 || visited[element[0]][element[1]]) {
                continue;
            }

            if (element[0] == element[2]) {
                return 1;
            }

            visited[element[0]][element[1]] = true;

            int[] nextElement1 = new int[]{
                    element[0] + element[0],
                    element[1],
                    element[2] - element[0]};
            Arrays.sort(nextElement1);
            queue.offer(nextElement1);

            int[] nextElement2 = new int[]{
                    element[0] + element[0],
                    element[1] - element[0],
                    element[2]};
            Arrays.sort(nextElement2);
            queue.offer(nextElement2);

            int[] nextElement3 = new int[]{
                    element[0],
                    element[1] + element[1],
                    element[2] - element[1]};
            Arrays.sort(nextElement3);
            queue.offer(nextElement3);
        }
        return 0;
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