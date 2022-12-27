import java.io.*;
import java.util.EmptyStackException;
import java.util.Stack;

public class Main {
    public static boolean validParenthesisString(String PS){
        Stack<Character> stack = new Stack<Character>();

        for (int i = 0; i < PS.length(); i++){
            try {
                char ch = PS.charAt(i);
                if (ch == '(')
                    stack.push(Character.valueOf(ch));
                else if (ch == ')')
                    stack.pop();
            } catch (EmptyStackException e){
                return (false);
            }
        }

        if (stack.empty())
            return (true);
        else
            return (false);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int count = Integer.parseInt(reader.readLine());
        for (int i = 0; i < count; i++){
            if (validParenthesisString(reader.readLine())){
                writer.write("YES");
            }
            else {
                writer.write("NO");
            }
            writer.newLine();
        }

        writer.close();
        reader.close();
    }
}
