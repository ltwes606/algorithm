import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        reader.readLine();
        String input = reader.readLine();

        // 합 구하기
        int result = 0;
        for (int i = 0; i < input.length(); i++) {
            result += input.charAt(i) - '0';
        }

        // 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
    }
}