import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String example = scanner.nextLine();
        Stack<Integer> stack = new Stack<Integer>();

        int place = 0;
        for (int i = 0; i < example.length(); i++){
            char ch = example.charAt(i);
            switch (ch){
                case '(':
                    stack.add(i);
                    break;
                case ')':
                    int open = stack.pop();
                    if (open + 1 == i) {
                        place += stack.size();
                    } else {
                        place++;
                    }
                    break;
                default:
                    break;
            }
        }

        System.out.println(place);
        scanner.close();
    }
}
