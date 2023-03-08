import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
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

    private static TokenizerReader reader;
    private static BuilderWriter writer;

    public static void main(String[] args) {
        // 입출력 설정
        reader = new TokenizerReader(System.in);
        writer = new BuilderWriter(System.out);

        // 트리 구성
        int size = reader.nextInt();
        Map<Integer, Integer>[] tree = new HashMap[size + 1];
        for (int i = 1; i <= size; i++) {
            tree[i] = new HashMap<>();
        }

        for (int i = 1; i < size; i++) {
            int currentIndex = reader.nextInt();

            int adjacentIndex = reader.nextInt();

            int distance = reader.nextInt();
            tree[currentIndex].put(adjacentIndex, distance);
            tree[adjacentIndex].put(currentIndex, distance);
        }

        int startIndex = findStartIndex(tree);

        // dfs
        int result = 0;
        if (startIndex != -1) {
            result = measureDiameter(tree, -1, startIndex);
        }

        printResult(result);

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static void printResult(int result) {
        writer.println(String.valueOf(result));
    }

    private static int measureDiameter(Map<Integer, Integer>[] tree,
            int prevIndex, int currentIndex) {
        int result = 0;

        for (Integer index : tree[currentIndex].keySet()) {
            if (index == prevIndex) {
                continue;
            }

            result = Math.max(result,
                    tree[currentIndex].get(index) + measureDiameter(tree, currentIndex, index));
        }
        return result;
    }

    private static int findStartIndex(Map<Integer, Integer>[] tree) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[]{-1, 1, 0});

        int result = -1;
        int maxCumulativeSum = 0;
        while (!queue.isEmpty()) {
            int[] info = queue.poll();
            int prevIndex = info[0];
            int currentIndex = info[1];
            int currentCumulativeSum = info[2];

            if (maxCumulativeSum < currentCumulativeSum) {
                maxCumulativeSum = currentCumulativeSum;
                result = currentIndex;
            }

            for (Integer index : tree[currentIndex].keySet()) {
                if (prevIndex == index) {
                    continue;
                }

                queue.offer(new int[]{
                        currentIndex, index, tree[currentIndex].get(index) + currentCumulativeSum});
            }
        }
        return result;
    }
}