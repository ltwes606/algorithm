import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.EmptyStackException;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws IOException {
        Stack<Integer> stack = new Stack<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int count = Integer.parseInt(reader.readLine());
        for (int i = 0; i < count; i++) {
            String cmd = reader.readLine();
            String[] cmdArgs = cmd.split(" ");

            try {
                if (cmdArgs[0].equals("push")) {
                    stack.push(Integer.parseInt(cmdArgs[1]));
                    continue;
                } else if (cmdArgs[0].equals("top")) {
                    writer.write(String.valueOf(stack.peek()));
                } else if (cmdArgs[0].equals("size")) {
                    writer.write(String.valueOf(stack.size()));
                } else if (cmdArgs[0].equals("empty")) {
                    writer.write(String.valueOf(boolToInt(stack.empty())));
                } else if (cmdArgs[0].equals("pop")) {
                    writer.write(String.valueOf(stack.pop()));
                }
            } catch (EmptyStackException e) {
                writer.write("-1");
            }
            writer.write("\n");
        }
        writer.close();
        reader.close();
    }

    private static int boolToInt(boolean bool) {
        return Boolean.compare(bool, false);
    }

}
