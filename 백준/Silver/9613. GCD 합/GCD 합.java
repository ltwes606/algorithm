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
        int[][] inputs = new int[repeat][];

        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }
        reader.close();

        long[] result = new long[inputs.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = getGcdSum(inputs[i]);
        }

        for (int i = 0; i < result.length; i++) {
            writer.write(String.format("%d\n", result[i]));
        }
        writer.close();
    }

    private static long getGcdSum(int[] array) {
        long result = 0;
        for (int i = 1; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                result += gcd(array[i], array[j]);
            }
        }
        return result;
    }

    private static int gcd(int a, int b) {
        while (b != 0) {
            int tmp = a % b;
            a = b;
            b = tmp;
        }
        return a;
    }
}
