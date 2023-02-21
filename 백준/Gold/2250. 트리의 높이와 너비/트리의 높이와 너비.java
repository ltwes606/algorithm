import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Arrays;
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

    static class Node {

        int value;
        int column;
        int level;
        Node leftChild;
        Node rightChild;
        Node parent;

        public Node(int value) {
            this.value = value;
        }
        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public Node getLeftChild() {
            return leftChild;
        }

        public void setLeftChild(Node leftChild) {
            this.leftChild = leftChild;
        }

        public Node getRightChild() {
            return rightChild;
        }

        public void setRightChild(Node rightChild) {
            this.rightChild = rightChild;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {
        // 입출력 설정
        TokenizerReader reader = new TokenizerReader(System.in);
        BuilderWriter writer = new BuilderWriter(System.out);

        // 입력
        int size = reader.nextInt();
        Node[] tree = new Node[size + 1];
        for (int i = 0; i < size; i++) {
            int parent = reader.nextInt();
            if (validIndex(tree, parent) && tree[parent] == null) {
                tree[parent] = new Node(parent);
            }
            int leftChild = reader.nextInt();
            if (validIndex(tree, leftChild) && tree[leftChild] == null) {
                tree[leftChild] = new Node(leftChild);
            }
            int rightChild = reader.nextInt();
            if (validIndex(tree, rightChild) && tree[rightChild] == null) {
                tree[rightChild] = new Node(rightChild);
            }

            addChild(tree, parent, leftChild, rightChild);
        }

        // root 찾기
        Node root = null;
        for (int i = 1; i < tree.length; i++) {
            Node parent = tree[i].getParent();
            if (parent == null) {
                root = tree[i];
            }
        }

        // 중위 순회
        order(root, 0, 1);

        // 최대 최소값
        int maxLevel = getMaxLevel(tree);
        int[] minColumn = new int[maxLevel + 1];
        Arrays.fill(minColumn, tree.length - 1);
        int[] maxColumn = new int[maxLevel + 1];
        Arrays.fill(maxColumn, 1);
        for (int i = 1; i < tree.length; i++) {
            Node node = tree[i];
            int level = node.getLevel();
            int column = node.getColumn();
            if (minColumn[level] > column) {
                minColumn[level] = column;
            }
            if (maxColumn[level] < column) {
                maxColumn[level] = column;
            }
        }

        // 결과
        int resultLevel = 1;
        int resultWidth = 0;
        for (int level = 1; level <= maxLevel; level++) {
            int width = maxColumn[level] - minColumn[level] + 1;
            if (width > resultWidth) {
                resultWidth = width;
                resultLevel = level;
            }
        }

        // 출력
        writer.println(resultLevel + " " + resultWidth);

        // 종료
        reader.close();
        writer.close();
    }

    private static void addChild(Node[] tree, int parent, int leftChild, int rightChild) {
        if (validIndex(tree, leftChild)) {
            tree[parent].setLeftChild(tree[leftChild]);
            tree[leftChild].setParent(tree[parent]);
        }

        if (validIndex(tree, rightChild)) {
            tree[parent].setRightChild(tree[rightChild]);
            tree[rightChild].setParent(tree[parent]);
        }
    }

    private static boolean validIndex(Node[] tree, int index) {
        if (index < 0 || tree.length <= index) {
            return false;
        }
        return true;
    }

    private static int getMaxLevel(Node[] tree) {
        int result = 0;
        for (int i = 1; i < tree.length; i++) {
            if (result < tree[i].getLevel()) {
                result = tree[i].getLevel();
            }
        }
        return result;
    }

    private static int order(Node node, int column, int level) {
        if (node == null) {
            return column;
        }

        node.setLevel(level++);
        column = order(node.getLeftChild(), column, level);
        node.setColumn(++column);
        column = order(node.getRightChild(), column, level);
        return column;
    }
}