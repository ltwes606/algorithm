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

    public static final int MAX_SIZE = 20;
    public static final int MAX_VALUE = 100_000;
    private static final boolean[] checked = new boolean[MAX_VALUE * MAX_SIZE + 1];

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

        // 만들 수 있는 숫자 확인
        dfs(inputs, 0, 0);

        // 결과
        int result = findMinUncheckedNumber();

        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static int findMinUncheckedNumber() {
        for (int i = 1; i < checked.length; i++) {
            if (!checked[i]) {
                return i;
            }
        }
        return -1;
    }

    private static void dfs(int[] array, int currentIndex, int currentSum) {
        if (currentIndex == array.length) {
            return;
        }
        checked[currentSum + array[currentIndex]] = true;

        dfs(array, currentIndex + 1, currentSum);
        dfs(array, currentIndex + 1, currentSum + array[currentIndex]);
    }

}