import java.io.*;
import java.util.Stack;

public class Main {
    public static double calculation(double operand1, double operand2, char operator) {
        switch (operator) {
            case '+':
                return (operand1 + operand2);
            case '-':
                return (operand1 - operand2);
            case '*':
                return (operand1 * operand2);
            case '/':
                return (operand1 / operand2);
            default:
                return (0.0);
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        int alphaSize = Integer.parseInt(reader.readLine());
        StringBuilder buffer = new StringBuilder(reader.readLine());
        int[] alphaIntoNbr = new int[alphaSize];
        Stack<Double> calculator = new Stack<Double>();

        for (int i = 0; i < alphaSize; i++){
            alphaIntoNbr[i] = Integer.parseInt(reader.readLine());
        }

        for (int i = 0; i < buffer.length(); i++){
            char ch = buffer.charAt(i);

            if ('A' <= ch && ch <= 'Z') {
                calculator.add((double)alphaIntoNbr[ch - 'A']);
            } else{
                double operand2 = calculator.pop();
                double operand1 = calculator.pop();
                calculator.add(calculation(operand1, operand2, ch));
            }
        }

        System.out.printf("%.2f", calculator.pop());
        writer.close();
        reader.close();
    }
}
