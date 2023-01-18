import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {

    public static final int MODULAR = 10_007;

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int digits = Integer.parseInt(reader.readLine());
        reader.close();

        int[][] ascentNumbers = new int[digits][10];
        for (int i = 0; i < ascentNumbers[0].length; i++) {
            ascentNumbers[0][i] = 1;
        }

        for (int i = 1; i < ascentNumbers.length; i++) {
            ascentNumbers[i][0] = 1;
            for (int j = 1; j < ascentNumbers[i].length; j++) {
                ascentNumbers[i][j] = (ascentNumbers[i][j - 1] + ascentNumbers[i - 1][j]) % MODULAR;
            }
        }

        int result = 0;
        for (int i = 0; i < ascentNumbers[ascentNumbers.length - 1].length; i++) {
            result = (result + ascentNumbers[ascentNumbers.length - 1][i]) % MODULAR;
        }
        System.out.println(result);
    }
}