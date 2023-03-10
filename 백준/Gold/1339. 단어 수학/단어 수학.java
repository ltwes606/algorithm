import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    static class Result {

        private int infectedComputerCount;
        private int lastInfectedComputerTime;

        public Result() {
        }

        public int getInfectedComputerCount() {
            return infectedComputerCount;
        }

        public void setInfectedComputerCount(int infectedComputerCount) {
            this.infectedComputerCount = infectedComputerCount;
        }

        public int getLastInfectedComputerTime() {
            return lastInfectedComputerTime;
        }

        public void setLastInfectedComputerTime(int lastInfectedComputerTime) {
            this.lastInfectedComputerTime = lastInfectedComputerTime;
        }
    }

    static class Computer {

        private boolean infected;
        private final Map<Integer, Integer> connections;

        public Computer() {
            connections = new HashMap<>();
        }

        public void connectionComputer(int key, int value) {
            connections.put(key, value);
        }

        public boolean isInfected() {
            return infected;
        }

        public void setInfected(boolean infected) {
            this.infected = infected;
        }

        public Map<Integer, Integer> getConnections() {
            return connections;
        }
    }

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static final int MAX_VALUE = 9;

    public static int ALPHABETS_COUNT = 'Z' - 'A' + 1;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        String[] input = new String[size];
        for (int i = 0; i < input.length; i++) {
            input[i] = reader.next();
        }

        char[][] strings = convertToCharArray(input);

        int[] alphabetValues = new int[ALPHABETS_COUNT];
        assignAlphabetValues(alphabetValues, strings);

        Arrays.sort(alphabetValues);

        int result = 0;
        int lastIndex = alphabetValues.length - 1;
        for (int i = MAX_VALUE; i >= 0; i--) {
            result += i * alphabetValues[(lastIndex - (MAX_VALUE - i))];
        }

        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static void assignAlphabetValues(int[] destinationArray, char[][] strings) {
        for (char[] chars : strings) {
            int charsLen = chars.length;
            int digit = (int) Math.pow(10, charsLen - 1);

            for (Character c : chars) {
                destinationArray[c.charValue() - 'A'] += digit;
                digit /= 10;
            }
        }
    }

    private static char[][] convertToCharArray(String[] strings) {
        char[][] result = new char[strings.length][];

        for (int i = 0; i < result.length; i++) {
            result[i] = new char[strings[i].length()];
            for (int j = 0; j < result[i].length; j++) {
                result[i][j] = strings[i].charAt(j);
            }
        }
        return result;
    }
}