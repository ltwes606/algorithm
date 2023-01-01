import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

    public static void main(String[] args) throws IOException {
        int[] inputArray = Arrays.stream(read().split(" ")).mapToInt(Integer::parseInt).toArray();
        int gcd = euclid(inputArray[0], inputArray[1]);
        int lcm = inputArray[0] / gcd * inputArray[1];
        writer.write(String.valueOf(gcd));
        newLine();
        writer.write(String.valueOf(lcm));
        terminal();
    }

    private static String read() throws IOException {
        return reader.readLine();
    }

    private static void newLine() throws IOException {
        writer.write('\n');
    }

    private static int euclid(int number1, int number2) {
        while (number2 != 0) {
            int temp = number1 % number2;
            number1 = number2;
            number2 = temp;
        }
        return number1;
    }

    private static void terminal() throws IOException {
        reader.close();
        writer.close();
    }
}
