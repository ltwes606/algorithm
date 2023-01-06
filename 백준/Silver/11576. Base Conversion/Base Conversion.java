import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[][] lines = new String[3][];
        for (int i = 0; i < lines.length; i++) {
            lines[i] = reader.readLine().split(" ");
        }
        reader.close();
        int currentRadix = Integer.parseInt(lines[0][0]);
        int convertedRadix = Integer.parseInt(lines[0][1]);
        int digitCount = Integer.parseInt(lines[1][0]);
        int[] digits = Arrays.stream(lines[2]).mapToInt(Integer::parseInt).toArray();

        LinkedList<String> resultStack =
                convertDigits(digits, currentRadix, convertedRadix);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.join(" ", resultStack));
        writer.close();
    }

    private static LinkedList<String> convertDigits(int[] digits, int currentRadix,
            int convertedRadix) {
        int decimal = getDecimal(digits, currentRadix);
        return convertDecimal(decimal, convertedRadix);
    }

    private static int getDecimal(int[] digits, int radix) {
        int result = 0;
        for (int i = 0; i < digits.length; i++) {
            result = result * radix + digits[i];
        }
        return result;
    }

    private static LinkedList<String> convertDecimal(int decimal, int radix) {
        LinkedList<String> result = new LinkedList<>();
        while (decimal > 0) {
            result.addFirst(Integer.toString(decimal % radix));
            decimal /= radix;
        }
        return result;
    }
}