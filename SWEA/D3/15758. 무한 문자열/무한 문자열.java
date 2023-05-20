import java.util.Scanner;

class Solution {

    private static Scanner scanner;

    public static void main(String args[]) throws Exception {
        // 입력 버퍼 설정
        scanner = new Scanner(System.in);

        int testCount = scanner.nextInt();
        boolean[] result = new boolean[testCount];
        for (int i = 0; i < result.length; i++) {
            String str1 = scanner.next();
            String str2 = scanner.next();

            result[i] = equalsString(str1, str2);
        }

        for (int i = 0; i < result.length; i++) {
            String bool = "no";
            if (result[i]) {
                bool = "yes";
            }
            System.out.printf("#%d %s\n", i + 1, bool);
        }
        // 입력 버퍼 해제
        scanner.close();
    }

    private static boolean equalsString(String str1, String str2) {
        StringBuilder repeatStr1 = new StringBuilder();
        for (int i = 0; i < str2.length(); i++) {
            repeatStr1.append(str1);
        }

        StringBuilder repeatStr2 = new StringBuilder();
        for (int i = 0; i < str1.length(); i++) {
            repeatStr2.append(str2);
        }

        return repeatStr1.toString().equals(repeatStr2.toString());
    }
}