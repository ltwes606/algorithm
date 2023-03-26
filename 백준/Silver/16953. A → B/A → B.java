import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

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

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int startNumber = reader.nextInt();
        int endNumber = reader.nextInt();

        int result = bfs(startNumber, endNumber);

        printResult(result);

        // 종료 작업
        close();
    }

    private static int bfs(int startNumber, int endNumber) {
        Queue<long[]> queue = new LinkedList<>();
        queue.add(new long[]{startNumber, 1});

        boolean[] visited = new boolean[endNumber];
        while (!queue.isEmpty()) {
            long[] element = queue.poll();
            long number = element[0];
            long count = element[1];
            if (number == endNumber) {
                return (int) count;
            }

            if (number > endNumber || visited[(int) number]) {
                continue;
            }

            long nextCount = count + 1;
            queue.add(new long[]{number * 2, nextCount});
            queue.add(new long[]{number * 10 + 1, nextCount});
        }
        return -1;
    }

    private static void printResult(int result) {
        writer.print(String.valueOf(result));
    }

    private static void close() {
        reader.close();
        writer.close();
    }
}