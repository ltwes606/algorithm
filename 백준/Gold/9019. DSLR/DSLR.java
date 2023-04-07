import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static final int MAX_VALUE = 10_000;
    public static final int REGISTER_SIZE = 4;

    public static void main(String[] args) {
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        List<Character>[] results = new List[size];
        for (int i = 0; i < results.length; i++) {
            int startValue = reader.nextInt();
            char[] startRegister = convertNumberToRegister(startValue);

            int endValue = reader.nextInt();
            char[] endRegister = convertNumberToRegister(endValue);

//            writer.println(String.valueOf(startRegister) + " " + String.valueOf(endRegister));
            results[i] = bfs(startRegister, endRegister);
        }

        printResults(results);
        close();
    }

    private static char[] convertNumberToRegister(int value) {
        char[] register = new char[REGISTER_SIZE];
        int registerIndex = REGISTER_SIZE - 1;
        for (int remain = value; remain != 0 && registerIndex >= 0; remain /= 10) {
            register[registerIndex] = (char) ((remain % 10) + '0');
            registerIndex--;
        }
        while (registerIndex >= 0) {
            register[registerIndex] = '0';
            registerIndex--;
        }
        return register;
    }

    private static List<Character> bfs(char[] startRegister, char[] endRegister) {
        Queue<Object[]> queue = new LinkedList<>();
        boolean[] visited = new boolean[MAX_VALUE + 1];

        queue.add(new Object[]{startRegister, new ArrayList<Character>()});
        while (!queue.isEmpty()) {
            Object[] element = queue.poll();

            char[] register = (char[]) element[0];
            int registerToValue = convertRegisterToNumber(register);
            List<Character> commands = (List<Character>) element[1];

            if (visited[registerToValue]) {
                continue;
            }

            if (equalsRegister(register, endRegister)) {
                return commands;
            }

            visited[registerToValue] = true;
            queue.offer(D(registerToValue, commands));
            queue.offer(S(registerToValue, commands));
            queue.offer(L(register, commands));
            queue.offer(R(register, commands));
        }
        return null;
    }

    private static int convertRegisterToNumber(char[] chars) {
        int result = 0;
        for (int i = 0; i < chars.length; i++) {
            result = result * 10 + (chars[i] - '0');
        }
        return result;
    }

    private static boolean equalsRegister(char[] srcRegister, char[] destRegister) {
        if (srcRegister.length != destRegister.length) {
            return false;
        }

        for (int i = 0; i < srcRegister.length; i++) {
            if (srcRegister[i] != destRegister[i]) {
                return false;
            }
        }
        return true;
    }

    private static Object[] D(int registerToValue, List<Character> commands) {
        int resultValue = 2 * registerToValue;
        char[] resultRegister = convertNumberToRegister(resultValue);

        List<Character> resultCommands = new ArrayList<>(commands);
        resultCommands.add('D');

        return new Object[]{resultRegister, resultCommands};
    }

    private static Object[] S(int registerToValue, List<Character> commands) {
        int resultValue = MAX_VALUE + registerToValue - 1;
        char[] resultRegister = convertNumberToRegister(resultValue);

        List<Character> resultCommands = new ArrayList<>(commands);
        resultCommands.add('S');

        return new Object[]{resultRegister, resultCommands};
    }

    private static Object[] L(char[] register, List<Character> commands) {
        char[] resultRegister = register.clone();
        char temp = resultRegister[0];
        resultRegister[0] = resultRegister[1];
        resultRegister[1] = resultRegister[2];
        resultRegister[2] = resultRegister[3];
        resultRegister[3] = temp;

        List<Character> resultCommands = new ArrayList<>(commands);
        resultCommands.add('L');

        return new Object[]{resultRegister, resultCommands};
    }

    private static Object[] R(char[] register, List<Character> commands) {
        char[] resultRegister = register.clone();
        char temp = resultRegister[3];
        resultRegister[3] = resultRegister[2];
        resultRegister[2] = resultRegister[1];
        resultRegister[1] = resultRegister[0];
        resultRegister[0] = temp;

        List<Character> resultCommands = new ArrayList<>(commands);
        resultCommands.add('R');

        return new Object[]{resultRegister, resultCommands};
    }

    private static void printResults(List<Character>[] results) {
        for (int i = 0; i < results.length; i++) {
            String str = results[i].stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining());

            writer.println(str);
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