import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] methodCount = new int[scanner.nextInt() + 1];
        scanner.close();
        for (int i = 0; i < methodCount.length && i <= 2; i++) {
            methodCount[i] = i;
        }

        for (int i = 3; i < methodCount.length; i++) {
            methodCount[i] = (methodCount[i - 1] + methodCount[i - 2]) % 10007;
        }
        System.out.println(methodCount[methodCount.length - 1]);
    }
}