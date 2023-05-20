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
            // 문자열 입력
            String str = scanner.next();

            // 순서에 맞게 입력된 값 세기
            char matching = 'a';
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) != matching) {
                    break;
                }
                result[count]++;
                matching++;
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