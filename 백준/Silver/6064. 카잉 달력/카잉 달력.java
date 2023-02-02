import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.IntStream;

class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int count = Integer.parseInt(reader.readLine());

        // 몇번째 해인지 계산
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            int M = Integer.parseInt(tokenizer.nextToken());
            int N = Integer.parseInt(tokenizer.nextToken());
            int x = Integer.parseInt(tokenizer.nextToken());
            int y = Integer.parseInt(tokenizer.nextToken());

            int years = x;
            int currentY = ((years - 1) % N) + 1;
            while (currentY != y) {
                if (years > M * N) {
                    years = -1;
                    break;
                }

                years += M;
                currentY = ((years - 1) % N) + 1;
            }
            result[i] = years;
        }

        // 출력
        IntStream.range(0, result.length).forEach(i ->
                System.out.println(result[i])
        );

        // 종료 작업
        reader.close();
    }
}