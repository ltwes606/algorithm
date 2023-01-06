import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] input = reader.readLine().split(" ");
        reader.close();
        String numberString = input[0];
        int radix = Integer.parseInt(input[1]);

        int result = 0;
        for (int i = 0; i < numberString.length(); i++) {
            char digitChar = numberString.charAt(i);
            result = result * radix + convertDigit(digitChar);
        }

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(result));
        writer.close();
    }

    private static int convertDigit(char digitChar) {
        if (Character.isDigit(digitChar)){
            return digitChar - '0';
        }
        return 10 + (digitChar - 'A');
    }
}