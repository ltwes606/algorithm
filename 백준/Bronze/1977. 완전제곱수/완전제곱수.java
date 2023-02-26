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
        int minNumber = reader.nextInt();
        int maxNumber = reader.nextInt();

        ArrayList<Integer> result = new ArrayList<>();
        for (int number = minNumber; number <= maxNumber; number++) {
            if (isPerfectSquare(number)) {
                result.add(number);
            }
        }

        // 결과출력
        if (result.isEmpty()) {
            writer.println("-1");
        } else {
            writer.println(String.valueOf(sum(result)));
            writer.println(String.valueOf(result.get(0)));
        }

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static int sum(ArrayList<Integer> list) {
        int result = 0;
        for (int e : list) {
            result += e;
        }
        return result;
    }

    private static boolean isPerfectSquare(int number) {
        int squareRoot = (int) Math.sqrt(number);
        if (squareRoot * squareRoot == number) {
            return true;
        }
        return false;
    }
}