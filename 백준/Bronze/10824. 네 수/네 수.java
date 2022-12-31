import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] input = scanner.nextLine().split(" ");

        Long sum;
        sum = Long.valueOf(input[0].concat(input[1]));
        sum += Long.valueOf(input[2].concat(input[3]));

        System.out.println(sum.longValue());
        scanner.close();
    }
}
