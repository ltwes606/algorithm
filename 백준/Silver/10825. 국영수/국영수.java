import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static void main(String[] args) {
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        Student[] students = new Student[size];
        for (int i = 0; i < students.length; i++) {
            String name = reader.next();
            int korean = reader.nextInt();
            int math = reader.nextInt();
            int english = reader.nextInt();

            students[i] = new Student(name, korean, math, english);
        }

        Arrays.sort(students);

        printResult(students);
        close();
    }

    private static void printResult(Student[] students) {
        for (int i = 0; i < students.length; i++) {
            writer.println(students[i].toString());
        }
    }

    private static void close() {
        reader.close();
        writer.close();
    }

    static class Student implements Comparable<Student> {

        private String name;
        private int korean;
        private int math;
        private int english;

        public Student(String name, int korean, int english, int math) {
            this.name = name;
            this.korean = korean;
            this.english = english;
            this.math = math;
        }

        public String getName() {
            return name;
        }

        public int getKorean() {
            return korean;
        }

        public int getMath() {
            return math;
        }

        public int getEnglish() {
            return english;
        }

        @Override
        public int compareTo(Student otherStudent) {
            // 국어 점수가 감소하는 순서로
            if (korean != otherStudent.getKorean()) {
                return otherStudent.getKorean() - korean;
            }
            // 국어 점수가 같으면 영어 점수가 증가하는 순서로
            if (english != otherStudent.getEnglish()) {
                return english - otherStudent.getEnglish();
            }
            // 국어 점수와 영어 점수가 같으면 수학 점수가 감소하는 순서로
            if (math != otherStudent.getMath()) {
                return otherStudent.getMath() - math;
            }
            // 모든 점수가 같으면 이름이 사전 순으로 증가하는 순서로
            return name.compareTo(otherStudent.getName());
        }

        @Override
        public String toString() {
//            return String.format("%s %d %d %d", name, korean, english, math);
            return name;
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