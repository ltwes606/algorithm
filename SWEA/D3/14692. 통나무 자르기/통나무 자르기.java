import java.util.ArrayList;
import java.util.Scanner;

class Solution {

    private static Scanner scanner;

    public static void main(String args[]) throws Exception {
        // 입력 버퍼 설정
        scanner = new Scanner(System.in);

        // 총 횟수 입력
        int testCount = scanner.nextInt();
        // 총 횟수 반복
        int[] result = new int[testCount];
        for (int i = 0; i < result.length; i++) {
            // 통나무 길이 입력
            int len = scanner.nextInt();

            result[i] = len;
        }

        // 결과 출력
        for (int i = 0; i < result.length; i++) {
            // Alice는 0, Bob은 1
            String name = "Alice";
            if (result[i] % 2 == 1) {
                name = "Bob";
            }
            System.out.printf("#%d %s\n", i + 1, name);
        }
        // 입력 버퍼 해제
        scanner.close();
    }

}