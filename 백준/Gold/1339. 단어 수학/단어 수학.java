import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
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

        // String -> char Array로 변환
        char[][] alphabets = new char[input.length][];
        for (int i = 0; i < alphabets.length; i++) {
            alphabets[i] = new char[input[i].length()];
            for (int j = 0; j < alphabets[i].length; j++) {
                alphabets[i][j] = input[i].charAt(j);
            }
        }

        // 높은 값이 필요한 값일수록 작은 index 존재하지 않는다면 value == 0
        Map<Character, Integer> alphabetMap = makeAlphabetMap(alphabets);

        int result = 0;
        for (char[] chars : alphabets) {
            int value = 0;
            for (Character c : chars) {
                value = value * 10 + alphabetMap.getOrDefault(c, 0);
            }
            result += value;
        }

        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static Map<Character, Integer> makeAlphabetMap(char[][] alphabets) {
        Set<Character> characters = new HashSet<>();
        for (char[] chars : alphabets) {
            for (Character c : chars) {
                characters.add(c);
            }
        }

        Map<Character, Integer> values = new HashMap<>();
        for (Character character : characters) {
            values.put(character, 0);
        }

        for (char[] chars : alphabets) {
            int value = 1;
            for (int i = chars.length - 1; i >= 0; i--) {
                char c = chars[i];
                values.replace(c, values.get(c) + value);
                value *= 10;
            }
        }

        // 작은 인덱스에 속한 값일수록 큰 숫자를 요구함
        List<Character> list = new ArrayList<>(characters);
        Collections.sort(list, new Comparator<Character>() {
            @Override
            public int compare(Character c1, Character c2) {
                return values.get(c2) - values.get(c1);
            }
        });

        Map<Character, Integer> result = new HashMap<>();
        for (int value = MAX_VALUE; value > 0 && MAX_VALUE - value < list.size(); value--) {
            result.put(list.get(MAX_VALUE - value), value);
        }
        return result;
    }
}