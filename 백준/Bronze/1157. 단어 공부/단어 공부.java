import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        char[] chars = reader.readLine().toCharArray();

        // 모든 값 대문자로 변환
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLowerCase(chars[i])) {
                chars[i] = Character.toUpperCase(chars[i]);
            }
        }

        // 알파벳 사용 횟수 구하기
        int[] alphabetsCount = new int['Z' - 'A' + 1];
        for (int i = 0; i < chars.length; i++) {
            alphabetsCount[chars[i] - 'A']++;
        }

        // 사용 많이 된 알파벳 구하기
        Stack<Integer> indexes = new Stack<>();
        for (int i = 0; i < alphabetsCount.length; i++) {
            while (!indexes.isEmpty() && alphabetsCount[indexes.peek()] < alphabetsCount[i]) {
                indexes.pop();
            }

            if (indexes.isEmpty() || alphabetsCount[indexes.peek()] == alphabetsCount[i]) {
                indexes.add(i);
            }
        }

        // 결과 출력
        char result = '?';
        if (indexes.size() == 1) {
            result = (char) ('A' + indexes.pop());
        }
        System.out.println(result);

        // 종료 작업
        reader.close();
    }
}