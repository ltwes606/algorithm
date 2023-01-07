import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int repeat = Integer.parseInt(reader.readLine());
        int[] cases = new int[11];
        cases[0] = 0;
        cases[1] = 1;
        cases[2] = 2;
        cases[3] = 4;
        for (int i = 4; i < cases.length; i++) {
            cases[i] = cases[i - 3] + cases[i - 2] + cases[i - 1];
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            int input = Integer.parseInt(reader.readLine());
            result.append(cases[input]);
            result.append('\n');
        }
        reader.close();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        writer.write(result.toString());
        writer.close();
    }
}