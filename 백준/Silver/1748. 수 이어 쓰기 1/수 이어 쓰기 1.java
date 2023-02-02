import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int input = Integer.parseInt(reader.readLine());

        // 자릿수 구하기
        int number = input;
        int exponent = 1;
        int result = 0;
        while (number > 0) {
            int subtracted = (int) (Math.pow(10, exponent) / 10 * 9);
            int nextNumber = (number - subtracted);
            result += (number - (nextNumber < 0 ? 0 : nextNumber)) * exponent;
            number = nextNumber;
            exponent++;
        }

        // 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
    }
}