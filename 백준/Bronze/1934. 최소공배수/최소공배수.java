import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        int repeat = Integer.parseInt(reader.readLine());
        for (int i = 0; i < repeat; i++) {
            int[] input = Arrays.stream(reader.readLine()
                    .split(" ")).mapToInt(Integer::parseInt)
                    .toArray();

            int lcm = input[0] * input[1] / euclid(input[0], input[1]);
            writer.write(String.format("%d\n", lcm));
        }

        reader.close();
        writer.flush();
        writer.close();
    }

    private static Integer euclid(Integer number1, Integer number2) {
        while (number2 != 0) {
            int tmp = number1 % number2;
            number1 = number2;
            number2 = tmp;
        }
        return number1;
    }
}
