import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        reader.readLine();
        int[] array = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();


        // 바이토닉 수열 초기화
        int[] bitonic = new int[array.length];
        for (int i = 0; i < bitonic.length; i++) {
            bitonic[i] = 1;
        }

        // 증가 수열
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[j] < array[i]) {
                    bitonic[i] = Math.max(bitonic[i], bitonic[j] + 1);
                }
            }
        }

        // 감소 수열
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < i; j++) {
                if (array[j] > array[i]) {
                    bitonic[i] = Math.max(bitonic[i], bitonic[j] + 1);
                }
            }
        }

        // 가장 긴 바이토닉 수열 찾기
        int result = 0;
        for (int i = 0; i < bitonic.length; i++) {
            if (result < bitonic[i]) {
                result = bitonic[i];
            }
        }

        // 결과 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
    }
}