import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int inputLines = 9;

        // 입력
        int[] inputs = new int[inputLines];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = Integer.parseInt(reader.readLine());
        }

        // 일곱 난쟁이 구하기
        int sum = sumArray(inputs);
        ArrayList<Integer> excludeIndexes = new ArrayList<>();
        for (int i = 0; i < inputs.length; i++) {
            int j = i + 1;
            for (; j < inputs.length; j++) {
                if (sum - inputs[i] - inputs[j] == 100) {
                    excludeIndexes.add(j);
                    break;
                }
            }
            if (!excludeIndexes.isEmpty()) {
                excludeIndexes.add(i);
                break;
            }
        }
        ArrayList<Integer> result = removeIndexes(inputs, excludeIndexes);

        // 일곱 난쟁이 정렬
        Collections.sort(result);

        // 결과 출력
        StringBuilder stringBuilder = new StringBuilder();
        for (Integer value : result) {
            stringBuilder.append(String.format("%d\n", value));
        }
        System.out.print(stringBuilder);

        // 종료 작업
        reader.close();
    }

    private static ArrayList<Integer> removeIndexes(int[] array, ArrayList<Integer> excludeIndexes) {
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (excludeIndexes.contains(i)) {
                continue;
            }
            result.add(array[i]);
        }
        return result;
    }

    private static int sumArray(int[] inputs) {
        int result = 0;
        for (int i = 0; i < inputs.length; i++) {
            result += inputs[i];
        }
        return result;
    }

}