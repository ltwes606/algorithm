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
        for (int count = 0; count < result.length; count++) {
            String line = scanner.next();
            for (int i = 0; i < line.length(); i++) {
                char prev = '.';
                if (i - 1 >= 0) {
                    prev = line.charAt(i - 1);
                }
                char current = line.charAt(i);
                char next = '.';
                if (i + 1 < line.length()) {
                    next = line.charAt(i + 1);
                }
                if ((prev == '(' || prev == '|') && current == ')') {
                    result[count]++;
                }
                if (current == '(' && (next == ')' || next == '|')) {
                    result[count]++;
                }
            }

            for (int i = 0; i + 1 < line.length(); i++) {
                char current = line.charAt(i);
                char next = line.charAt(i + 1);
                if (current == '(' && next == ')') {
                    result[count]--;
                }
            }
        }

        // 결과 출력
        for (int i = 0; i < result.length; i++) {
            System.out.printf("#%d %d\n", i + 1, result[i]);
        }
        // 입력 버퍼 해제
        scanner.close();
    }

}