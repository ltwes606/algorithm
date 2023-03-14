import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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

    static class BuilderWriter {

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

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    private static int result = Integer.MIN_VALUE;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        int[] inputs = new int[size];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = reader.nextInt();
        }

        boolean[] visited = new boolean[size];
        dfs(inputs, visited, 0);

        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static void dfs(int[] array, boolean[] visited, int currentValue) {
        if (countTrue(visited) == array.length - 2) {
            result = Math.max(result, currentValue);
            return;
        }

        for (int i = 1; i < array.length - 1; i++) {
            if (visited[i]) {
                continue;
            }
            visited[i] = true;

            int addValue = array[findIndex(visited, i, false, -1)]
                    * array[findIndex(visited, i, false, 1)];

            dfs(array, visited, currentValue + addValue);

            visited[i] = false;
        }
    }

    private static int countTrue(boolean[] visited) {
        int result = 0;
        for (int i = 0; i < visited.length; i++) {
            if (!visited[i]) {
                continue;
            }
            result++;
        }
        return result;
    }

    private static int findIndex(boolean[] visited, int startIndex, boolean state,
            int increase) {
        for (int i = startIndex; i >= 0 && i < visited.length; i += increase) {
            if (visited[i] != state) {
                continue;
            }
            return i;
        }
        return -1;
    }
}