import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static void main(String[] args) {
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int arr1Size = reader.nextInt();
        int arr2Size = reader.nextInt();

        int[] arr1 = createArray(arr1Size);
        int[] arr2 = createArray(arr2Size);

        List<Integer> result = concatenate(arr1, arr2);

        printResult(result);
        close();
    }

    private static int[] createArray(int arrSize) {
        int[] result = new int[arrSize];

        for (int i = 0; i < arrSize; i++) {
            result[i] = reader.nextInt();
        }
        return result;
    }

    private static List<Integer> concatenate(int[] arr1, int[] arr2) {
        List<Integer> result = new ArrayList<>();
        int arr1Index = 0;
        int arr2Index = 0;
        while (arr1Index < arr1.length && arr2Index < arr2.length) {
            if (arr1[arr1Index] < arr2[arr2Index]) {
                result.add(arr1[arr1Index++]);
            } else {
                result.add(arr2[arr2Index++]);
            }
        }

        while (arr1Index < arr1.length) {
            result.add(arr1[arr1Index++]);
        }
        while (arr2Index < arr2.length) {
            result.add(arr2[arr2Index++]);
        }
        return result;
    }

    private static void printResult(List<Integer> result) {
        for (int i = 0; i < result.size(); i++) {
            writer.print(result.get(i) + " ");
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