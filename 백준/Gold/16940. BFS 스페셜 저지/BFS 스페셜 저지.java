import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
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

        private int parent;
        private int childCount;

        public Node(int parent) {
            this.parent = parent;
            this.childCount = 0;
        }

        public int getParent() {
            return parent;
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

        ArrayList<Integer> bfsVisitOrder = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            bfsVisitOrder.add(reader.nextInt());
        }

        // bfs
        Node[] nodes = bfs(edgeMap, 1);

        // 순서 검사
        String result = "0";
        if (validOrder(bfsVisitOrder, nodes)) {
            result = "1";
        }

        // 결과 출력
        writer.println(result);


        // 종료 작업
        reader.close();
        writer.close();
    }

    private static boolean validOrder(ArrayList<Integer> order, Node[] nodes) {
        Queue<Integer> parentQueue = new LinkedList<>();
        int parentNumber = order.get(0);

        for (int i = 1; i < order.size(); i++) {
            while (!parentQueue.isEmpty() && nodes[parentNumber].getChildCount() == 0) {
                parentNumber = parentQueue.remove();
            }

            int childNumber = order.get(i);
            Node childNode = nodes[childNumber];
            if (childNode.getParent() != parentNumber) {
                return false;
            }

            nodes[parentNumber].decreaseChildCount();
            parentQueue.add(childNumber);
        }
        return true;
    }

    private static Node[] bfs(List<Integer>[] edgeMap, int startNumber) {
        Queue<Integer> queue = new LinkedList<>();
        boolean[] visited = new boolean[edgeMap.length];
        queue.add(startNumber);
        visited[startNumber] = true;

        Node[] result = new Node[edgeMap.length];
        result[1] = new Node(-1);
        while (!queue.isEmpty()) {
            int parentNumber = queue.remove();

            for (int childNumber : edgeMap[parentNumber]) {
                if (visited[childNumber]) {
                    continue;
                }
                visited[childNumber] = true;

                result[parentNumber].increaseChildCount();
                result[childNumber] = new Node(parentNumber);
                queue.add(childNumber);
            }
        }
        return result;
    }
}