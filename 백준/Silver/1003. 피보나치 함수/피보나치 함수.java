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

    public static int MAX_VALUE = 40;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        int[][] results = new int[size][2];

        int[][] fibonacciNumbers = getFibonacciNumbers();

        // 결과 입력
        for (int i = 0; i < results.length; i++) {
            int n = reader.nextInt();

            results[i][0] = fibonacciNumbers[n][0];
            results[i][1] = fibonacciNumbers[n][1];
        }

        // 결과 출력
        printResults(results);

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static int[][] getFibonacciNumbers() {
        int[][] fibonacciNumbers = new int[MAX_VALUE + 1][2];
        fibonacciNumbers[0][0] = 1;
        fibonacciNumbers[0][1] = 0;

        fibonacciNumbers[1][0] = 0;
        fibonacciNumbers[1][1] = 1;

        for (int i = 2; i < fibonacciNumbers.length; i++) {
            fibonacciNumbers[i][0] = fibonacciNumbers[i - 1][0] + fibonacciNumbers[i - 2][0];
            fibonacciNumbers[i][1] = fibonacciNumbers[i - 1][1] + fibonacciNumbers[i - 2][1];
        }
        return fibonacciNumbers;
    }

    private static void printResults(int[][] results) {
        for (int i = 0; i < results.length; i++) {
            printResult(results[i]);
        }
    }

    private static void printResult(int[] result) {
        writer.println(result[0] + " " + result[1]);
    }
}