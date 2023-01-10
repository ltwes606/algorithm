import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    private static BufferedReader reader;

    public static final int METHODS_SIZE = 100000;
    public static final int MODULAR = 1000000009;

    public static void main(String[] args) throws Exception {
        reader = new BufferedReader(new InputStreamReader(System.in));
        int count = Integer.parseInt(reader.readLine());

        long[][] methods = new long[METHODS_SIZE + 1][4];
        methods[0][0] = methods[1][1] = methods[2][2] = 1;
        methods[3][1] = methods[3][2] = methods[3][3] = 1;
        for (int i = 4; i < methods.length; i++) {
            methods[i][1] = (methods[i - 1][2] + methods[i - 1][3]) % MODULAR;
            methods[i][2] = (methods[i - 2][1] + methods[i - 2][3]) % MODULAR;
            methods[i][3] = (methods[i - 3][1] + methods[i - 3][2]) % MODULAR;
        }


        long[] result = new long[count];
        for (int i = 0; i < result.length; i++) {
            int index = Integer.parseInt(reader.readLine());
            result[i] = ((methods[index][1] + methods[index][2] + methods[index][3]) % MODULAR);
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = 0; i < result.length; i++) {
            writer.write(String.format("%d\n", result[i]));
        }
        writer.close();
    }
}