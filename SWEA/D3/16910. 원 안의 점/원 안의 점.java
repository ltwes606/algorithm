import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

class Solution {

    private static Scanner scanner;

    public static void main(String args[]) throws Exception {
        // 입력 버퍼 설정
        scanner = new Scanner(System.in);

        int[] pointCounts = new int[201];
        Arrays.fill(pointCounts, 1);
        for (int n = 1; n < pointCounts.length; n++) {
            int squaredRadius = (int) Math.pow(n, 2);
            for (int x = 0; x <= n; x++) {
                for (int y = 1; y <= n; y++) {
                    int value = (int) (Math.pow(x, 2) + Math.pow(y, 2));
                    if (value <= squaredRadius) {
                        pointCounts[n] += 4;
                    }
                }
            }
        }

        int testCount = scanner.nextInt();
        int[] result = new int[testCount];
        for (int i = 0; i < testCount; i++) {
            int number = scanner.nextInt();
            result[i] = pointCounts[number];
        }

        for (int i = 0; i < result.length; i++) {
            System.out.printf("#%d %d\n", i + 1, result[i]);
        }
        // 입력 버퍼 해제
        scanner.close();
    }
}