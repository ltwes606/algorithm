import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
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


    public static void main(String[] args) {
        // 입출력 설정
        TokenizerReader reader = new TokenizerReader(System.in);
        BuilderWriter writer = new BuilderWriter(System.out);

        // 초기화
        HashMap<Integer, ArrayList<Integer>> edgeMap = new HashMap<>();
        edgeMap.put(1, new ArrayList<>());

        // 입력
        int count = reader.nextInt();
        for (int i = 1; i < count; i++) {
            int number1 = reader.nextInt();
            int number2 = reader.nextInt();

            ArrayList<Integer> list1 = edgeMap.get(number1);
            if (list1 == null) {
                edgeMap.put(number1, new ArrayList<>());
            }
            edgeMap.get(number1).add(number2);

            ArrayList<Integer> list2 = edgeMap.get(number2);
            if (list2 == null) {
                list2 = edgeMap.put(number2, new ArrayList<>());
            }
            edgeMap.get(number2).add(number1);
        }

        // bfs
        HashMap<Integer, Integer> parentMap = new HashMap<>();
        bfs(edgeMap, 1, parentMap);

        // 출력
        Integer[] integers = parentMap.keySet().toArray(new Integer[parentMap.size()]);
        Arrays.sort(integers);
        for (int i = 1; i < integers.length; i++) {
            writer.println(String.valueOf(parentMap.get(integers[i])));
        }

        // 종료
        reader.close();
        writer.close();
    }

    private static void bfs(HashMap<Integer, ArrayList<Integer>> edgeMap, int rootNumber,
            HashMap<Integer, Integer> parentMap) {
        // 초기화
        Queue<Integer> queue = new LinkedList<>();
        queue.add(rootNumber);
        parentMap.put(rootNumber, -1);

        while (!queue.isEmpty()) {
            int parent = queue.remove();

            ArrayList<Integer> list = edgeMap.get(parent);
            for (Integer child : list) {
                if (parentMap.containsKey(child)) {
                    continue;
                }

                parentMap.put(child, parent);
                queue.add(child);
            }
        }
    }
}