import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main {

    private static class Node {

        private String value;
        private Node left;
        private Node right;

        public Node(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public Node getLeft() {
            return left;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getRight() {
            return right;
        }

        public void setRight(Node right) {
            this.right = right;
        }
    }

    private static class Tree {

        private Node root;

        public void setRoot(Node root) {
            this.root = root;
        }

        public void addLeft(String parentValue, Node node) {
            Node parent = getNode(root, parentValue);
            parent.setLeft(node);
        }

        public void addRight(String parentValue, Node node) {
            Node parent = getNode(root, parentValue);
            parent.setRight(node);
        }

        private Node getNode(Node node, String value) {
            // 베이스 조건
            if (node == null || node.getValue().equals(value)) {
                return node;
            }

            // 재귀
            Node left = getNode(node.getLeft(), value);
            if (left != null && left.getValue().equals(value)) {
                return left;
            }
            return getNode(node.getRight(), value);
        }

        public void traversePreOrder() {
            traversePreOrderRecursive(root);
            System.out.println();
        }

        private void traversePreOrderRecursive(Node node) {
            if (node == null) {
                return;
            }

            System.out.print(node.getValue());
            traversePreOrderRecursive(node.left);
            traversePreOrderRecursive(node.right);
        }

        public void traverseInOrder() {
            traverseInOrderRecursive(root);
            System.out.println();
        }

        private void traverseInOrderRecursive(Node node) {
            if (node == null) {
                return;
            }

            traverseInOrderRecursive(node.left);
            System.out.print(node.getValue());
            traverseInOrderRecursive(node.right);
        }

        public void traversePostOrder() {
            traversePostOrderRecursive(root);
            System.out.println();
        }

        private void traversePostOrderRecursive(Node node) {
            if (node == null) {
                return;
            }

            traversePostOrderRecursive(node.left);
            traversePostOrderRecursive(node.right);
            System.out.print(node.getValue());
        }
    }

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 줄 길이 입력
        int lines = Integer.parseInt(reader.readLine());

        // 초기화
        Tree tree = new Tree();
        init(tree, new StringTokenizer(reader.readLine()));

        // 값 삽입
        for (int i = 1; i < lines; i++) {
            StringTokenizer st = new StringTokenizer(reader.readLine());
            String parentValue = st.nextToken();
            String leftValue = st.nextToken();
            String rightValue = st.nextToken();

            if (!leftValue.equals(".")) {
                tree.addLeft(parentValue, new Node(leftValue));
            }
            if (!rightValue.equals(".")) {
                tree.addRight(parentValue, new Node(rightValue));
            }
        }

        // 전위 순회
        tree.traversePreOrder();

        // 중위 순회
        tree.traverseInOrder();

        // 후위 순회
        tree.traversePostOrder();


        // 종료 작업
        reader.close();
    }

    private static void init(Tree tree, StringTokenizer st) {
        String parentValue = st.nextToken();
        tree.setRoot(new Node(parentValue));

        String leftValue = st.nextToken();
        if (!leftValue.equals(".")) {
            tree.addLeft(parentValue, new Node(leftValue));
        }
        String rightValue = st.nextToken();
        if (!rightValue.equals(".")) {
            tree.addRight(parentValue, new Node(rightValue));
        }
    }
}