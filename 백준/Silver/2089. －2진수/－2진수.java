import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        int input = Integer.parseInt(reader.readLine());
        reader.close();
        if (input == 0) {
            System.out.println("0");
            return;
        }

        StringBuilder reverseResult = new StringBuilder();
        int negativeBase = -2;
        int quotient = input;
        while(quotient != 1) {
            int remainder = quotient % negativeBase;
            if (remainder < 0) {
                remainder -= negativeBase;
                quotient += negativeBase;
            }
            quotient /= negativeBase;
            reverseResult.append(remainder);
        }
        reverseResult.append('1');

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.valueOf(reverseResult.reverse()));
        writer.close();
    }
}
