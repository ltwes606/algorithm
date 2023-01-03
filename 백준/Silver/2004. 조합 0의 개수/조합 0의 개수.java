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

        int[] input = Arrays.stream(reader.readLine()
                        .split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();

        int sumOfFive =
                getCount(input[0], 5) - getCount(input[0] - input[1], 5) - getCount(input[1], 5);
        int sumOfTwo =
                getCount(input[0], 2) - getCount(input[0] - input[1], 2) - getCount(input[1], 2);

        writer.write(String.valueOf(Math.min(sumOfTwo, sumOfFive)));
        writer.flush();
        writer.close();
        reader.close();
    }

    private static int getCount(int number, int divisor) {
        int result = 0;

        while (number >= divisor) {
            result += number / divisor;
            number /= divisor;
        }
        return result;
    }
}
