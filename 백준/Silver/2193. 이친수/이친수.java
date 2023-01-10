import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {

    private static BufferedReader reader;


    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        int digits = Integer.parseInt(reader.readLine());
        reader.close();

        long[] stepNumbers = new long[digits + 1];
        stepNumbers[0] = 0;
        stepNumbers[1] = 1;
        for (int i = 2; i <= digits; i++) {
            stepNumbers[i] = stepNumbers[i - 1] + stepNumbers[i - 2];
        }
        System.out.println(stepNumbers[digits]);
    }
}
