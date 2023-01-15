import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Integer.parseInt(reader.readLine());
        int[] inputNumbers = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        reader.close();

        int[] sequenceSums = new int[inputNumbers.length];
        for (int i = 0; i < inputNumbers.length; i++) {
            for (int j = 0; j < i; j++) {
                if (inputNumbers[j] < inputNumbers[i]) {
                    sequenceSums[i] = Math.max(sequenceSums[i], sequenceSums[j]);
                }
            }
            sequenceSums[i] += inputNumbers[i];

        }

        int result = 0;
        for (int i = 0; i < sequenceSums.length; i++) {
            if (result < sequenceSums[i]) {
                result = sequenceSums[i];
            }
        }
        System.out.println(result);
    }
}
