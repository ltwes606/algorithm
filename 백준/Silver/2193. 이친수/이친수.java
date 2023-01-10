import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.IntStream;

public class Main {

    private static BufferedReader reader;



    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        int digits = Integer.parseInt(reader.readLine());
        reader.close();

        long[][] stepNumbers = new long[digits + 1][10];
        initializeStepNumbers(stepNumbers);
        for (int i = 2; i < stepNumbers.length; i++) {
            stepNumbers[i][0] = stepNumbers[i - 1][0] + stepNumbers[i - 1][1];
            stepNumbers[i][1] = stepNumbers[i - 1][0];
        }

        System.out.println(stepNumbers[digits][1]);
    }

    private static void initializeStepNumbers(long[][] stepNumbers) {
        for (int i = 0; i < stepNumbers[0].length; i++) {
            stepNumbers[1][i] = 1;
        }
    }
}