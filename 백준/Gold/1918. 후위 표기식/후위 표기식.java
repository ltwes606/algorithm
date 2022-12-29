import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        Stack<Character> stack = new Stack<>();

        Map<Character, Integer> operand = new HashMap<Character, Integer>();
        operand.put('(', -1);
        operand.put(')', -1);
        operand.put('+', 0);
        operand.put('-', 0);
        operand.put('*', 1);
        operand.put('/', 1);

        String prefixNotation = reader.readLine();

        for (int i = 0; i < prefixNotation.length(); i++) {
            char ch = prefixNotation.charAt(i);

            if (!operand.containsKey(Character.valueOf(ch))) {
                writer.write(String.valueOf(ch));
                continue;
            }

            if (operand.get(Character.valueOf(ch)).intValue() == -1) {
                if (ch == '(') {
                    stack.add(Character.valueOf(ch));
                } else {
                    while (stack.peek().charValue() != '(') {
                        writer.write(stack.pop().toString());
                    }
                    stack.pop();
                }
            } else {
                Integer chToValue = operand.get(Character.valueOf(ch));

                while (!stack.isEmpty() &&
                        chToValue.intValue() <= operand.get(stack.peek()).intValue()){
                    writer.write(stack.pop().toString());
                }
                stack.add(Character.valueOf(ch));
            }
        }

        while (!stack.isEmpty()) {
            writer.write(stack.pop().toString());
        }
        writer.flush();
        writer.close();
        reader.close();
    }
}
