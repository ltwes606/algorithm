import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

class Main {

    private static int[] slice(int startIndex, int endIndex, int[] array) {
        return IntStream.range(startIndex, endIndex).map(i -> array[i]).toArray();
    }

    private static void dfs(int[] list, int index, int currentWeight, int maximumWeight,
            ArrayList<Integer> result) {
        // 탈출 조건
        if (currentWeight > maximumWeight) {
            return;
        }

        // 리스트에 추가
        if (list.length == index) {
            result.add(currentWeight);
            return;
        }

        // 현재 값 추가한 dfs
        dfs(list, index + 1, currentWeight + list[index], maximumWeight, result);
        // 추가하지 않은 dfs
        dfs(list, index + 1, currentWeight, maximumWeight, result);
    }

    private static int binarySearch(ArrayList<Integer> sorted, int target) {
        // 초기 인덱스 값 설정
        int left = 0;
        int right = sorted.size() - 1;

        // 바이너리 서치
        int result = 0;
        while (left <= right) {
            // 탐색할 인덱스 설정
            int mid = (left + right) / 2;

            // 타겟보다 작거나 같은 경우
            if (sorted.get(mid) <= target) {
                result = mid;
                left = mid + 1;
            } else { // 타겟보다 클 경우
                right = mid - 1;
            }
        }
        // 반환
        return result;
    }

    public static void main(String[] args) throws IOException {
        // 입출력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 입력
        int[] input = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        int maximumWeight = input[1];
        input = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt).toArray();
        reader.close();

        // 입력 리스트 반으로 나누기
        int[] frontInput = slice(0, input.length / 2, input);
        int[] backInput = slice(input.length / 2, input.length, input);

        // 최대값을 넘지 않는 경우의 수 구하기
        ArrayList<Integer> frontSums = new ArrayList<>();
        ArrayList<Integer> backSums = new ArrayList<>();
        dfs(frontInput, 0, 0, maximumWeight, frontSums);
        dfs(backInput, 0, 0, maximumWeight, backSums);

        // 뒤쪽 경우의 수 오름차순으로 정렬하기
        Collections.sort(backSums);

        // 앞쪽 경우의 수와 뒤쪽 경우의 수를 조합하여 총 경우의 수 구하기
        int result = 0;
        for (int frontWeight : frontSums) {
            result += binarySearch(backSums, maximumWeight - frontWeight) + 1;
        }

        // 총 경우의 수 출력
        writer.write(String.valueOf(result));

        // 종료 루틴
        writer.flush();
        writer.close();
    }

}