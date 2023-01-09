import java.util.Scanner;

public class Main {

    public static final int MAX_INDEX = 1000000;
    public static final int MODULAR = 1000000009;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int repeat = Integer.parseInt(scanner.nextLine());

        long[] methods = new long[MAX_INDEX + 1];
        methods[0] = 0;
        methods[1] = 1;
        methods[2] = 2;
        methods[3] = 4;
        for (int i = 4; i < methods.length; i++) {
            methods[i] = (methods[i - 1] + methods[i - 2] + methods[i - 3]) % MODULAR;
        }

        long[] result = new long[repeat];
        for (int i = 0; i < result.length; i++) {
            int index = Integer.parseInt(scanner.nextLine());
            result[i] = methods[index];
        }
        scanner.close();

        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }
    }
}