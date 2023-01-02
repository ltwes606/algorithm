import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int input = Integer.parseInt(reader.readLine());
        int result = 1;

        for (int i = 1; i <= input; i++) {
            result *= i;
        }

        writer.write(String.valueOf(result));
        writer.flush();
        writer.close();
        reader.close();
    }
}
