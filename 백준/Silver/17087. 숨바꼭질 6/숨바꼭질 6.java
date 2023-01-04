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

        int[][] inputs = new int[2][];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt).toArray();
        }
        reader.close();

        int myLocation = inputs[0][1];
        int[] locationOfSiblings = inputs[1];
        int result = Math.abs(myLocation - locationOfSiblings[0]);
        for (int i = 1; i < locationOfSiblings.length; i++) {
            result = gcd(result, Math.abs(locationOfSiblings[i - 1] - locationOfSiblings[i]));
        }
        writer.write(String.valueOf(result));
        writer.close();
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