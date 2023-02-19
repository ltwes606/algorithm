import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {

    static class TokenizerReader {

        private BufferedReader reader;
        private StringTokenizer tokenizer;

        public TokenizerReader(InputStream inputStream) {
            reader = new BufferedReader(new InputStreamReader(inputStream));
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

        public int nextInt() {
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

        private BufferedWriter writer;
        private final StringBuilder builder = new StringBuilder();

        public BuilderWriter(OutputStream outputStream) {
            writer = new BufferedWriter(new OutputStreamWriter(outputStream));
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

    public static final int MAX_NUMBER = 100_000;

    public static void main(String[] args) {
        // 입출력 설정
        TokenizerReader reader = new TokenizerReader(System.in);
        BuilderWriter writer = new BuilderWriter(System.out);

        // 입력
        int startPoint = reader.nextInt();
        int endPoint = reader.nextInt();

        int maxPoint = startPoint > endPoint ? startPoint : endPoint;
        int[] result = new int[MAX_NUMBER + 1];
        Arrays.fill(result, MAX_NUMBER);
        result[startPoint] = 0;
        bfs(result, startPoint);

        // 출력
        writer.println(String.valueOf(result[endPoint]));

        // 종료 작업
        writer.close();
        reader.close();
    }

    private static void bfs(int[] result, int startPoint) {
        // 초기화
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startPoint);

        while (!queue.isEmpty()) {
            int point = queue.remove();

            int visitPoint1 = point - 1;
            if (validRange(result, visitPoint1)) {
                visit(result, queue, point, visitPoint1, 1);
            }

            int visitPoint2 = point + 1;
            if (validRange(result, visitPoint2)) {
                visit(result, queue, point, visitPoint2, 1);
            }

            int visitPoint3 = point * 2;
            if (validRange(result, visitPoint3)) {
                visit(result, queue, point, visitPoint3, 0);
            }
        }
    }

    private static void visit(int[] result, Queue<Integer> queue,
            int originalPoint, int visitPoint, int weight) {
        if (result[originalPoint] + weight < result[visitPoint]) {
            result[visitPoint] = result[originalPoint] + weight;
            queue.add(visitPoint);
        }
    }

    private static boolean validRange(int[] visited, int index) {
        if (index < 0 || visited.length <= index) {
            return false;
        }
        return true;
    }
}