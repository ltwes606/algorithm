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

    private static int ALPHABETS_SIZE = 'z' - 'a' + 1;

    public static String START_WORD = "anta";
    public static String END_WORD = "tica";

    private static int result = 0;


    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int wordCount = reader.nextInt();
        int checkedMaxCount = reader.nextInt();

        String[] words = new String[wordCount];
        for (int i = 0; i < wordCount; i++) {
            String str = reader.next();
            str = str.substring(START_WORD.length(), str.length() - END_WORD.length());
            words[i] = str;
        }

        // 단어의 개수 최대값 구하기
        boolean[] checked = getChecked();
        int startCount = countChecked(checked);
        List<Character> characters = getCharacters(checked);

        if (startCount <= checkedMaxCount) {
            dfs(words, checked, characters, 0, startCount, checkedMaxCount);
        }

        // 결과 출력
        printResult();

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static boolean[] getChecked() {
        boolean[] checked = new boolean[ALPHABETS_SIZE];

        for (int i = 0; i < START_WORD.length(); i++) {
            char c = START_WORD.charAt(i);
            int index = convertIndex(c);
            checked[index] = true;
        }

        for (int i = 0; i < END_WORD.length(); i++) {
            char c = END_WORD.charAt(i);
            int index = convertIndex(c);
            checked[index] = true;
        }
        return checked;
    }

    private static int convertIndex(char c) {
        return c - 'a';
    }

    private static int countChecked(boolean[] checked) {
        int result = 0;
        for (int i = 0; i < checked.length; i++) {
            if (checked[i]) {
                result++;
            }
        }
        return result;
    }

    private static List<Character> getCharacters(boolean[] checked) {
        List<Character> characters = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            if (checked[convertIndex(c)]) {
                continue;
            }
            characters.add(c);
        }
        return characters;
    }

    private static void dfs(String[] words, boolean[] checked,
            List<Character> characters, int index, int checkedCount, int checkedMaxCount) {
        if (characters.size() == index || checkedCount == checkedMaxCount) {
            result = Math.max(result, countLearnedWords(words, checked));
            return;
        }

        dfs(words, checked, characters, index + 1, checkedCount, checkedMaxCount);
        char c = characters.get(index);
        int checkedIndex = convertIndex(c);
        checked[checkedIndex] = true;
        dfs(words, checked, characters, index + 1, checkedCount + 1, checkedMaxCount);
        checked[checkedIndex] = false;
    }

    private static int countLearnedWords(String[] words, boolean[] checked) {
        int result = 0;
        for (int i = 0; i < words.length; i++) {
            if (canLearn(words[i], checked)) {
                result++;
            }
        }
        return result;
    }

    private static boolean canLearn(String word, boolean[] checked) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            int index = convertIndex(c);
            if (!checked[index]) {
                return false;
            }
        }
        return true;
    }

    private static void printResult() {
        if (result < 0) {
            result = 0;
        }
        writer.println(String.valueOf(result));
    }
}