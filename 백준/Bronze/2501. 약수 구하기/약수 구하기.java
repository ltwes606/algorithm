import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
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

    public static void main(String[] args) {
        // 입출력 설정
        TokenizerReader reader = new TokenizerReader(System.in);
        BuilderWriter writer = new BuilderWriter(System.out);

        // 입력
        int number = reader.nextInt();
        int sequence = reader.nextInt();

        // k번쩨 직은 약수
        int result = findSmallerDivisor(number, sequence);

        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static int findSmallerDivisor(int number, int sequence) {
        int count = 0;
        for (int divisor = 1; divisor <= number; divisor++) {
            if (number % divisor == 0) {
                count++;
            }
            if (count == sequence) {
                return divisor;
            }
        }
        return 0;
    }

    private static int sum(ArrayList<Integer> list) {
        int result = 0;
        for (int e : list) {
            result += e;
        }
        return result;
    }
}