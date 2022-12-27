import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    public static int boolToInt(Boolean bool) {
        return bool.compareTo(false);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        Deque<Integer> deque = new ArrayDeque<Integer>();

        int count = Integer.parseInt(reader.readLine());
        for (int i = 0; i < count; i++) {
            String[] cmd = reader.readLine().split(" ");
            Integer result;

            if (cmd[0].equals("push_front")) {
                deque.addFirst(Integer.parseInt(cmd[1]));
            } else if (cmd[0].equals("push_back")) {
                deque.addLast(Integer.parseInt(cmd[1]));
            } else if (cmd[0].equals("pop_front")) {
                result = deque.pollFirst();
                if (result == null) writer.write("-1");
                else writer.write(result.toString());
                writer.newLine();
            } else if (cmd[0].equals("pop_back")) {
                result = deque.pollLast();
                if (result == null) writer.write("-1");
                else writer.write(result.toString());
                writer.newLine();
            } else if (cmd[0].equals("size")) {
                writer.write(String.valueOf(deque.size()));
                writer.newLine();
            } else if (cmd[0].equals("empty")) {
                writer.write(String.valueOf(boolToInt(deque.isEmpty())));
                writer.newLine();
            } else if (cmd[0].equals("front")) {
                result = deque.peekFirst();
                if (result == null) writer.write("-1");
                else writer.write(result.toString());
                writer.newLine();
            } else if (cmd[0].equals("back")) {
                result = deque.peekLast();
                if (result == null) writer.write("-1");
                else writer.write(result.toString());
                writer.newLine();
            }
        }
        writer.close();
        reader.close();
    }
}
