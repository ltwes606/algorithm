import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int[] inputInt = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt)
                .toArray();
        reader.close();

        int[][] sumExpressions = new int[inputInt[1] + 1][inputInt[0] + 1];
        for (int i = 0; i < sumExpressions[1].length; i++) {
            sumExpressions[1][i] = 1;
        }

        for (int idx1 = 2; idx1 < sumExpressions.length; idx1++) {
            sumExpressions[idx1][0] = 1;
            for (int idx2 = 1; idx2 < sumExpressions[idx1].length; idx2++) {
                sumExpressions[idx1][idx2] =
                        (sumExpressions[idx1 - 1][idx2] + sumExpressions[idx1][idx2 - 1])
                                % 1_000_000_000;
            }
        }
        System.out.println(sumExpressions[inputInt[1]][inputInt[0]]);
    }
}