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

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        boolean[] startStates = createStates(size);
        boolean[] endStates = createStates(size);

        // 연산
        boolean[] firstClickStates = startStates.clone();
        click(firstClickStates, 0);
        int result1 = action(firstClickStates, endStates);
        if (result1 != -1) {
            result1++;
        }

        boolean[] firstNotClickStates = startStates.clone();
        int result2 = action(firstNotClickStates, endStates);

        // 결과 출력
        printResult(result1, result2);

        // 종료 작업
        close();
    }

    private static boolean[] createStates(int size) {
        boolean[] result = new boolean[size];
        String line = reader.next();
        for (int i = 0; i < result.length; i++) {
            if (line.charAt(i) == '1') {
                result[i] = true;
            }
        }
        return result;
    }

    private static void click(boolean[] states, int index) {
        if (validRange(states, index - 1)) {
            toggle(states, index - 1);
        }
        toggle(states, index);
        if (validRange(states, index + 1)) {
            toggle(states, index + 1);
        }
    }

    private static boolean validRange(boolean[] states, int index) {
        if (index < 0 || states.length <= index) {
            return false;
        }
        return true;
    }

    private static void toggle(boolean[] states, int index) {
        states[index] ^= true;
    }

    private static int action(boolean[] destinationStates, boolean[] endStates) {
        if (destinationStates.length != endStates.length) {
            return -1;
        }

        int result = 0;
        for (int i = 1; i < destinationStates.length; i++) {
            if (destinationStates[i - 1] != endStates[i - 1]) {
                click(destinationStates, i);
                result++;
            }
        }
        if (destinationStates[destinationStates.length - 1]
                != endStates[destinationStates.length - 1]) {
            return -1;
        }
        return result;
    }

    private static void printResult(int result1, int result2) {
        int result = Math.min(result1, result2);
        if (result == -1) {
            result = result1;
            if (result1 == -1) {
                result = result2;
            }
        }
        writer.print(String.valueOf(result));
    }

    private static void close() {
        reader.close();
        writer.close();
    }
}