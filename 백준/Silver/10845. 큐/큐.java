import java.io.*;

class Node {
    public int data;
    public Node prev;
    public Node next;

    public Node() {
        this.data = -1;
        this.prev = null;
        this.next = null;
    }

    public Node(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }

    public Node(int data, Node prev, Node next) {
        this.data = data;
        this.prev = prev;
        this.next = next;
    }
}

class DoublyLinkedList<T> {
    public Node header;
    public Node trailer;
    public int size;

    public DoublyLinkedList() {
        this.header = new Node();
        this.trailer = new Node();
        this.header.next = this.trailer;
        this.trailer.prev = this.header;
        this.size = 0;
    }

    public int front() {
        Node node = header.next;

        if (isEmpty()) return (-1);
        return (node.data);
    }

    public int back() {
        Node node = trailer.prev;

        if (isEmpty()) return (-1);
        return (node.data);
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        if (size == 0) return (true);
        return (false);
    }

    public boolean isFull() {
        if (this.size == Integer.MAX_VALUE) return (true);
        return (false);
    }

    public Node travelFrontToBack(int index) {
        Node node = this.header;

        for (int i = 0; i <= index; i++)
            node = node.next;

        return (node);
    }

    public Node travelBackToFront(int index) {
        Node node = this.trailer;

        for (int i = size - index; i > 0; i--) {
            node = node.prev;
        }

        return (node);
    }


    public Node get(int index) {
        if (this.size < index || 0 > index) return (null);

        Node node = null;
        if (index < this.size / 2) {
            node = travelFrontToBack(index);
        } else {
            node = travelBackToFront(index);
        }

        return (node);
    }

    public boolean insert(Node node, int index) {
        if (this.isFull()) return (false);

        Node nextNode = this.get(index);
        if (nextNode == null) return (false);

        node.prev = nextNode.prev;
        nextNode.prev.next = node;
        node.next = nextNode;
        nextNode.prev = node;
        size++;
        return (true);
    }

    public boolean push(int data) {
        if (this.isFull()) return (false);

        Node node = new Node(data);

        return (this.insert(node, size));
    }

    public int pop() {
        if (isEmpty()) return (-1);

        Node node = this.get(0);

        node.prev.next = node.next;
        node.next.prev = node.prev;
        size--;
        return (node.data);
    }

    public void inspect(){
        for(Node node = header; node != null; node = node.next){
            System.out.println(node.data);
        }
    }

}

public class Main {

    public static int compareTo(boolean bool) {
        return Boolean.compare(bool, false);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        DoublyLinkedList queue = new DoublyLinkedList();

        int cases = Integer.parseInt(reader.readLine());
        for (int i = 0; i < cases; i++) {
            String[] cmd = reader.readLine().split(" ");

            if (cmd[0].equals("push")) {
                queue.push(Integer.parseInt(cmd[1]));
            } else if (cmd[0].equals("pop")) {
                writer.write(String.valueOf(queue.pop()));
                writer.newLine();
            } else if (cmd[0].equals("size")) {
                writer.write(String.valueOf(queue.size()));
                writer.newLine();
            } else if (cmd[0].equals("empty")) {
                writer.write(String.valueOf(compareTo(queue.isEmpty())));
                writer.newLine();
            } else if (cmd[0].equals("front")) {
                writer.write(String.valueOf(queue.front()));
                writer.newLine();
            } else if (cmd[0].equals("back")) {
                writer.write(String.valueOf(queue.back()));
                writer.newLine();
            }
        }

        writer.close();
        reader.close();
    }
}
