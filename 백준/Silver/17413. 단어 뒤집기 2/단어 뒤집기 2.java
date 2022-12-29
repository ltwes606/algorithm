import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String str;

        StringBuilder stack = new StringBuilder();
        StringBuilder queue = new StringBuilder();
        StringBuilder process = stack;

        str = scanner.nextLine();
        for (int i = 0; i < str.length(); i++){
            char ch = str.charAt(i);
            switch (ch){
                case ' ':
                    stack.reverse();
                    process.append(' ');
                    System.out.print(stack);
                    stack.setLength(0);
                    break;
                case '>':
                    process.append('>');
                    System.out.print(process);
                    process.setLength(0);
                    process = stack;
                    break;
                case '<':
                    System.out.print(stack.reverse());
                    stack.setLength(0);
                    process = queue;
                    process.append('<');
                    break;
                default:
                    process.append(ch);
                    break;
            }
        }

        System.out.println(stack.reverse());
        scanner.close();
    }
}
