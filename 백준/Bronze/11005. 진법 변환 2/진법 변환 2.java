import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int[] input = Arrays.stream(
                        reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        reader.close();

        int decimal = input[0];
        int radix = input[1];

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(convertDecimal(decimal, radix));
        writer.close();
    }

    private static String convertDecimal(int decimal, int radix) {
        String result = "";
        while (decimal > 0) {
            result = getDigit(decimal % radix) + result;
            decimal /= radix;
        }
        return result;
    }

    private static char getDigit(int digit) {
        if (0 <= digit && digit <= 9) {
            return (char)(digit + '0');
        }
        return (char)((digit - 10) + 'A');
    }

}