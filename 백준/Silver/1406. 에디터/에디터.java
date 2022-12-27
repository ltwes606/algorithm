import java.io.*;
import java.util.Stack;

public class Main {
    public static class Editor {
        public static Stack<Character> leftStack = new Stack<Character>();
        public static Stack<Character> rightStack = new Stack<Character>();

        public Editor() {
        }

        public Editor(String str) {
            addString(leftStack, str);
        }

        private void addString(Stack<Character> leftStack, String str) {
            for (int i = 0; i < str.length(); i++) {
                leftStack.push(str.charAt(i));
            }
        }

        public void L() {
            if (!leftStack.isEmpty()) {
                rightStack.push(leftStack.pop());
            }
        }

        public void D() {
            if (!rightStack.isEmpty()) {
                leftStack.push(rightStack.pop());
            }
        }

        public void B() {
            if (!leftStack.isEmpty()) {
                leftStack.pop();
            }
        }

        public void P(String str) {
            addString(leftStack, str);
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        Editor editor = new Editor(reader.readLine());
        int cases = Integer.parseInt(reader.readLine());
        for (int i = 0; i < cases; i++){
            String[] cmd = reader.readLine().split( " ");

            if (cmd[0].equals("L")){
                editor.L();
            } else if (cmd[0].equals("D")){
                editor.D();
            } else if (cmd[0].equals("B")){
                editor.B();
            } else if (cmd[0].equals("P")){
                editor.P(cmd[1]);
            }
        }

        while (!editor.leftStack.isEmpty()){
            editor.L();
        }

        while (!editor.rightStack.isEmpty()){
            writer.write(editor.rightStack.pop().charValue());
        }
        writer.close();
        reader.close();
    }
}
