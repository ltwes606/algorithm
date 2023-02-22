import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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

        // 입력
        int friendSize = reader.nextInt();
        HashMap<Integer, Set<Integer>> friendship = new HashMap<>();
        for (int i = 0; i < friendSize; i++) {
            friendship.put(i, new HashSet<>());
        }
        int inputSize = reader.nextInt();
        for (int i = 0; i < inputSize; i++) {
            int friend1 = reader.nextInt();
            int friend2 = reader.nextInt();

            friendship.get(friend1).add(friend2);
            friendship.get(friend2).add(friend1);
        }

        // 관계 존재여부 확인
        int result = dfs(friendship, friendship.keySet(), new HashSet<Integer>(), 5);

        // 결과 출력
        writer.println(String.valueOf(result));

        // 종료
        reader.close();
        writer.close();
    }

    private static int dfs(HashMap<Integer, Set<Integer>> friendship, Set<Integer> friendSet,
            HashSet<Integer> visited, int count) {
        if (count == 0) {
            return 1;
        }

        int result = 0;
        for (Integer friend : friendSet) {
            if (visited.contains(friend)) {
                continue;
            }

            visited.add(friend);
            result = dfs(friendship, friendship.get(friend), visited, count - 1);
            if (result == 1) {
                break;
            }
            visited.remove(friend);
        }
        return result;
    }
}