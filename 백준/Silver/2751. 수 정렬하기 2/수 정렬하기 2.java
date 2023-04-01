import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;
    public static final int MAX_VALUE = 1_000_000;

    public static void main(String[] args) {
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        boolean[] positiveVisited = new boolean[MAX_VALUE + 1];
        boolean[] negativeVisited = new boolean[MAX_VALUE + 1];
        for (int i = 0; i < size; i++) {
            int inputNumber = reader.nextInt();
            if (inputNumber < 0) {
                negativeVisited[-inputNumber] = true;
                continue;
            }
            positiveVisited[inputNumber] = true;
        }

        printResult(negativeVisited, positiveVisited);
        close();
    }

    private static void printResult(boolean[] negative, boolean[] positive) {
        for (int n = MAX_VALUE; n >= 0; n--) {
            if (negative[n]) {
                writer.println(String.valueOf(-n));
            }
        }
        
        for (int n = 0; n < positive.length; n++) {
            if (positive[n]) {
                writer.println(String.valueOf(n));
            }
        }
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