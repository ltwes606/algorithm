import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

class Solution {

    private static Scanner scanner;

    public static void main(String args[]) throws Exception {
        // 입력 버퍼 설정
        scanner = new Scanner(System.in);

        int testCount = scanner.nextInt();
        int[] result = new int[testCount];
        for (int i = 0; i < testCount; i++) {
            int number = scanner.nextInt();
            result[i] = number * number;
        }

        for (int i = 0; i < result.length; i++) {
            System.out.printf("#%d %d\n", i + 1, result[i]);
        }
        // 입력 버퍼 해제
        scanner.close();
    }
}