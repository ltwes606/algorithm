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

    public static void main(String[] args) {
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        Coordinates[] coordinatesArray = new Coordinates[size];
        for (int i = 0; i < coordinatesArray.length; i++) {
            int x = reader.nextInt();
            int y = reader.nextInt();
            coordinatesArray[i] = new Coordinates(x, y);
        }

        Arrays.sort(coordinatesArray);

        printResult(coordinatesArray);
        close();
    }

    private static void printResult(Coordinates[] coordinatesArray) {
        for (int i = 0; i < coordinatesArray.length; i++) {
            writer.println(coordinatesArray[i].toString());
        }
    }

    private static void close() {
        reader.close();
        writer.close();
    }

    static class Coordinates implements Comparable<Coordinates> {
        private int x;
        private int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public int compareTo(Coordinates otherCoordinates) {
            if (x == otherCoordinates.getX()) {
                return y - otherCoordinates.getY();
            }
            return x - otherCoordinates.getX();
        }

        @Override
        public String toString() {
            return String.format("%d %d", x, y);
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