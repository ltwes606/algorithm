import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.readLine();
        int[] numbers = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt)
                .toArray();
        reader.close();

        int[] sum = new int[numbers.length];
        sum[0] = numbers[0];
        for (int index = 1; index < sum.length; index++) {
            sum[index] = numbers[index] + sum[index - 1];
        }

        int result = sum[0];
        int min = sum[0] < 0 ? sum[0] : 0;
        for (int index = 1; index < sum.length; index++) {
            result = Math.max(result, sum[index] - min);
            if (min > sum[index]) {
                min = sum[index];
            }
        }

        System.out.println(result);
    }
}
