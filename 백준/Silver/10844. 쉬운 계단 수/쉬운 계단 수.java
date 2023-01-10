import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    private static BufferedReader reader;

    public static final int MODULAR = 1000000000;

    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        int digits = Integer.parseInt(reader.readLine());
        reader.close();

        int[][] stepNumbers = new int[digits + 1][10];
        initializeStepNumbers(stepNumbers);
        for (int i = 2; i < stepNumbers.length; i++) {
            stepNumbers[i][0] = stepNumbers[i - 1][1];
            for (int j = 1; j <= 8; j++) {
                stepNumbers[i][j] = (stepNumbers[i - 1][j - 1] + stepNumbers[i - 1][j + 1]) % MODULAR;
            }
            stepNumbers[i][9] = stepNumbers[i - 1][8];
        }

        int result = 0;
        for (int i = 1; i < 10; i++) {
            result = (stepNumbers[digits][i] + result) % MODULAR;
        }
        System.out.println(result);
    }

    private static void initializeStepNumbers(int[][] stepNumbers) {
        for (int i = 0; i < stepNumbers[1].length; i++) {
            stepNumbers[1][i] = 1;
        }
    }
}