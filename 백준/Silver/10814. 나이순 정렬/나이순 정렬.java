import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.StringTokenizer;

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;
    public static final int MAX_VALUE = 1_000_000;

    public static void main(String[] args) {
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        User[] users = new User[size];
        for (int i = 0; i < users.length; i++) {
            int age = reader.nextInt();
            String username = reader.next();
            users[i] = new User(username, age);
        }

        Arrays.sort(users);

        printResult(users);
        close();
    }

    private static void printResult(User[] result) {
        for (int i = 0; i < result.length; i++) {
            writer.println(String.format("%d %s", result[i].getAge(), result[i].getUsername()));
        }
    }

    private static void close() {
        reader.close();
        writer.close();
    }

    static class User implements Comparable<User>{

        private String username;
        private int age;

        public User(String username, int age) {
            this.username = username;
            this.age = age;
        }

        public String getUsername() {
            return username;
        }

        public int getAge() {
            return age;
        }

        @Override
        public int compareTo(User otherUser) {
            return age - otherUser.getAge();
        }
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