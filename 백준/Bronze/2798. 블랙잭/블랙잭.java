import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.StringTokenizer;

class TokenizerReader {

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

class BuilderWriter {

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

class Vertex {

    private int weight;

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

public class Main {

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        int limit = reader.nextInt();
        int[] values = new int[size];
        for (int i = 0; i < values.length; i++) {
            values[i] = reader.nextInt();
        }

        Arrays.sort(values);

        int result = 0;
        result = findMaxBlackjackNumber(values, limit);

        // 결과 출력
        writer.println(String.valueOf(result));

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static int findMaxBlackjackNumber(int[] values, int limit) {
        int result = 0;
        for (int i = 0; i + 2 < values.length; i++) {
            for (int j = i + 1; j + 1 < values.length; j++) {
                for (int k = j + 1; k < values.length; k++) {
                    int tmp = sum(values[i], values[j], values[k]);
                    if (tmp > limit || result >= tmp) {
                        continue;
                    }
                    result = tmp;
                }
            }
        }
        return result;
    }

    private static int sum(int value1, int value2, int value3) {
        return value1 + value2 + value3;
    }
}