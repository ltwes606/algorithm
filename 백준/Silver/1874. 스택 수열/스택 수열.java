import java.io.*;
import java.util.EmptyStackException;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder buffer = new StringBuilder();
        Stack<Integer> inputStack = new Stack<Integer>();
        Stack<Integer> storeStack = new Stack<Integer>();

        int count = Integer.parseInt(reader.readLine());
        int[] sequenceArr = new int[count];
        for (int i = 0; i < count; i++){
            sequenceArr[i] = Integer.parseInt(reader.readLine());
        }

        for (int i = count; i > 0; i--){
            inputStack.push(Integer.valueOf(i));
        }

        for (int idx = 0; idx < sequenceArr.length; idx++) {
            try {
                while (storeStack.isEmpty() || sequenceArr[idx] != storeStack.peek()) {
                    storeStack.push(inputStack.pop());
                    buffer.append("+\n");
                }
                storeStack.pop();
                buffer.append("-\n");
            } catch (EmptyStackException e) {
                buffer.setLength(0);
                buffer.append("NO\n");
                break;
            }
        }

        writer.write(buffer.toString());
        writer.close();
        reader.close();
    }
}
