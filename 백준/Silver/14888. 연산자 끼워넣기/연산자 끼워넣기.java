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

    static interface Operator {

        public int calculate(int n1, int n2);
    }

    static class Addition implements Operator {

        @Override
        public int calculate(int n1, int n2) {
            return n1 + n2;
        }
    }

    static class Subtract implements Operator {

        @Override
        public int calculate(int n1, int n2) {
            return n1 - n2;
        }
    }

    static class Multiplication implements Operator {

        @Override
        public int calculate(int n1, int n2) {
            return n1 * n2;
        }
    }

    static class Division implements Operator {

        @Override
        public int calculate(int n1, int n2) {
            return n1 / n2;
        }
    }

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    private static int maxResult = Integer.MIN_VALUE;
    private static int minResult = Integer.MAX_VALUE;

    private static Operator[] operators = new Operator[]{
            new Addition(), new Subtract(), new Multiplication(), new Division()
    };

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int numberSize = reader.nextInt();
        int[] numbers = new int[numberSize];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = reader.nextInt();
        }

        int[] operatorCount = new int[4];
        for (int i = 0; i < operatorCount.length; i++) {
            operatorCount[i] = reader.nextInt();
        }

        // 계산
        dfs(numbers, operatorCount, 1, numbers[0]);

        writer.println(String.valueOf(maxResult));
        writer.println(String.valueOf(minResult));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static void dfs(int[] numbers, int[] operatorCount, int currentIndex,
            int currentValue) {
        if (numbers.length == currentIndex) {
            maxResult = Math.max(maxResult, currentValue);
            minResult = Math.min(minResult, currentValue);
            return;
        }

        for (int j = 0; j < operatorCount.length; j++) {
            if (operatorCount[j] == 0) {
                continue;
            }

            operatorCount[j]--;
            int nextValue = operators[j].calculate(currentValue, numbers[currentIndex]);
            dfs(numbers, operatorCount, currentIndex + 1, nextValue);
            operatorCount[j]++;
        }
    }
}