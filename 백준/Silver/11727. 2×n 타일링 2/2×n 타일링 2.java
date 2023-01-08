import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] methodCount = new int[scanner.nextInt() + 1];
        scanner.close();
        for (int i = 0; i < methodCount.length && i <= 1; i++) {
            methodCount[i] = i;
        }
        if (methodCount.length > 2) {
            methodCount[2] = 3;
        }

        for (int i = 3; i < methodCount.length; i++) {
            methodCount[i] = (methodCount[i - 1] + methodCount[i - 2] * 2) % 10007;
        }
        System.out.println(methodCount[methodCount.length - 1]);
    }
}