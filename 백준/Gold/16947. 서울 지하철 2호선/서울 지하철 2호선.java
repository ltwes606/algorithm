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
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

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

    static ArrayList<Integer>[] edgeMap;
    static boolean[] circularLines;

    public static void main(String[] args) {
        // 입출력 설정
        TokenizerReader reader = new TokenizerReader(System.in);
        BuilderWriter writer = new BuilderWriter(System.out);

        // 입력
        int count = reader.nextInt();
        edgeMap = new ArrayList[count + 1];
        for (int i = 0; i < count; i++) {
            int number1 = reader.nextInt();
            int number2 = reader.nextInt();

            if (edgeMap[number1] == null) {
                edgeMap[number1] = new ArrayList<>();
            }
            if (edgeMap[number2] == null) {
                edgeMap[number2] = new ArrayList<>();
            }

            edgeMap[number1].add(number2);
            edgeMap[number2].add(number1);
        }

        // 순환선 찾기
        circularLines = new boolean[count + 1];
        dfs(new boolean[count + 1], 0, 1);
        int[] result = bfs();

        for (int i = 1; i < circularLines.length; i++) {
            writer.println(String.valueOf(result[i]));
        }

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static int[] bfs() {
        int startNumber = IntStream.range(0, circularLines.length).filter(i -> circularLines[i])
                .findFirst().getAsInt();

        Queue<Integer> queue = new LinkedList<>();
        int[] result = new int[edgeMap.length];
        Arrays.fill(result, -1);

        result[startNumber] = 0;
        queue.add(startNumber);
        while (!queue.isEmpty()) {
            int searchNumber = queue.remove();

            for (int i = 0; i < edgeMap[searchNumber].size(); i++) {
                int foundNumber = edgeMap[searchNumber].get(i);
                if (result[foundNumber] != -1) {
                    continue;
                }
                queue.add(foundNumber);

                result[foundNumber] = result[searchNumber] + 1;
                if (circularLines[foundNumber]) {
                    result[foundNumber] = 0;
                }
            }
        }
        return result;
    }

    private static boolean dfs(boolean[] visited, int prevNumber, int currentNumber) {
        if (visited[currentNumber]) {
            circularLines[currentNumber] = true;
            return true;
        }
        visited[currentNumber] = true;

        for (int nextNumber : edgeMap[currentNumber]) {
            if (nextNumber == prevNumber) {
                continue;
            }

            if (dfs(visited, currentNumber, nextNumber)) {
                if (circularLines[currentNumber]) {
                    return false;
                }
                circularLines[currentNumber] = true;
                return true;
            }
        }
        return false;
    }
}