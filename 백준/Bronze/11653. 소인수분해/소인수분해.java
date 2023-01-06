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

        ArrayList<String> result = new ArrayList<>();
        int quotient = input;
        int divisor = 2;
        while (quotient != 1) {
            while (quotient % divisor == 0) {
                result.add(Integer.toString(divisor));
                quotient /= divisor;
            }
            divisor++;
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(String.join("\n", result));
        writer.close();
    }
}