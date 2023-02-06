import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Main {

    private static int[] inputArray;
    private static boolean[] selected;
    private static List<Integer> currentList;
    private static int result;

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        reader.readLine();
        inputArray = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();

        // n!개의 경우의 수를 돌며 최대값 탐색
        selected = new boolean[inputArray.length];
        currentList = new ArrayList<>();
        result = Integer.MIN_VALUE;
        dfs(0, inputArray.length);

        // 결과 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
    }

    private static void dfs(int currentCount, int length) {
        if (currentCount == length) {
            result = Math.max(result, sumDifferences(currentList));
            return;
        }

        for (int i = 0; i < inputArray.length; i++) {
            if (selected[i]) {
                continue;
            }

            currentList.add(inputArray[i]);
            selected[i] = true;
            dfs(currentCount + 1, length);
            currentList.remove(currentCount);
            selected[i] = false;
        }
    }

    private static int sumDifferences(List<Integer> list) {
        int result = 0;
        for (int i = 0; i + 1 < list.size(); i++) {
            result += Math.abs(list.get(i) - list.get(i + 1));
        }
        return result;
    }
}