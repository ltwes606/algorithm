import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static void main(String[] args) {
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        String expression = reader.next();

        int result = action(expression);

        printResult(result);
        close();
    }

    private static int action(String expression) {
        int result = 0;
        int startIndex = 0;

        List<Integer> parsedValues = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '-') {
                String parsedExpression = expression.substring(startIndex, i);
                int value = parsePositiveValue(parsedExpression);
                startIndex = i + 1;

                parsedValues.add(value);
            }
        }

        parsedValues.add(parsePositiveValue(expression.substring(startIndex)));
        return calculateValues(parsedValues);
    }

    private static Integer parsePositiveValue(String expression) {
        int result = 0;
        int startIndex = 0;

        for (int i = 0; i < expression.length(); i++) {
            if (expression.charAt(i) == '+') {
                String stringValue = expression.substring(startIndex, i);
                int value = parseInt(stringValue);
                startIndex = i + 1;

                result += value;
            }
        }

        result += parseInt(expression.substring(startIndex));
        return result;
    }

    private static int calculateValues(List<Integer> parsedValues) {
        if (parsedValues.size() == 0) {
            return 0;
        }
        int result = parsedValues.get(0);

        for (int i = 1; i < parsedValues.size(); i++) {
            result -= parsedValues.get(i);
        }
        return result;
    }

    private static int parseInt(String stringValue) {
        if (stringValue.length() == 0) {
            return 0;
        }
        return Integer.parseInt(stringValue);
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