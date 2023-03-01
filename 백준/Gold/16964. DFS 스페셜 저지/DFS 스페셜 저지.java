import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
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

    static class Node {

        private int parentNumber;
        private int childCount;

        public Node(int parentNumber) {
            this.parentNumber = parentNumber;
        }

        public int getParentNumber() {
            return parentNumber;
        }

        public int getChildCount() {
            return childCount;
        }

        public void increaseChildCount() {
            childCount++;
        }

        public void decreaseChildCount() {
            childCount--;
        }
    }

    public static void main(String[] args) {
        // 입출력 설정
        TokenizerReader reader = new TokenizerReader(System.in);
        BuilderWriter writer = new BuilderWriter(System.out);

        // 입력
        int count = reader.nextInt();
        ArrayList<Integer>[] edgeMap = new ArrayList[count + 1];
        for (int i = 0; i < count - 1; i++) {
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
        Queue<Integer> dfsVisitOrder = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            dfsVisitOrder.add(reader.nextInt());
        }

        // dfs
        Node[] nodes = new Node[edgeMap.length];
        boolean[] visited = new boolean[edgeMap.length];
        dfs(edgeMap, -1, 1, visited, nodes);

        // 순서 검사
        String result = "0";
        if (dfsVisitOrder.peek() == 1 && validOrder(dfsVisitOrder, nodes)) {
            result = "1";
        }

        // 결과 출력
        writer.println(result);

        // 종료 작업
        reader.close();
        writer.close();
    }

    private static boolean validOrder(Queue<Integer> order, Node[] nodes) {
        int currentNumber = order.remove();
        while (nodes[currentNumber].getChildCount() != 0) {
            if (nodes[order.peek()].getParentNumber() != currentNumber) {
                return false;
            }
            if (!validOrder(order, nodes)) {
                return false;
            }
            nodes[currentNumber].decreaseChildCount();
        }
        return true;
    }

    private static void dfs(ArrayList<Integer>[] edgeMap, int parentNumber, int currentNumber,
            boolean[] visited, Node[] nodes) {
        if (visited[currentNumber]) {
            return;
        }
        visited[currentNumber] = true;
        nodes[currentNumber] = new Node(parentNumber);

        for (int childNumber : edgeMap[currentNumber]) {
            if (!visited[childNumber]) {
                nodes[currentNumber].increaseChildCount();
                dfs(edgeMap, currentNumber, childNumber, visited, nodes);
            }
        }
    }
}