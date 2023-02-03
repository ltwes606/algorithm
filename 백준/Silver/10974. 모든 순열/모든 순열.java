import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.Collectors;

class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int size = Integer.parseInt(reader.readLine());
        int[] startSequence = new int[size];
        for (int i = 0; i < startSequence.length; i++) {
            startSequence[i] = i + 1;
        }

        // 오름차순으로 순차 출력
        int[] result = startSequence;
        while (result != null) {
            System.out.println(Arrays.stream(result).mapToObj(String::valueOf)
                    .collect(Collectors.joining(" ")));

            result = getNextPermutation(result);
        }

        // 종료 작업
        reader.close();
    }

    private static int[] getNextPermutation(int[] permutation) {
        // 변경할 인덱스 위치 찾기
        for (int i = permutation.length - 1; i >= 0; i--) {
            // 뒷 인덱스 중에 큰 값 탐색
            if (existsBiggerThanNumber(permutation[i], permutation, i + 1)) {
                int[] result = permutation.clone();
                swap(result, i, findMininumIndexBiggerThan(result, result[i], i + 1));
                Arrays.sort(result, i + 1, result.length);
                return result;
            }
        }
        return null;
    }

    private static int findMininumIndexBiggerThan(int[] ints, int value, int fromIndex) {
        int result = fromIndex;
        int minValue = Integer.MAX_VALUE;
        for (int i = fromIndex; i < ints.length; i++) {
            if (value < ints[i] && ints[i] < minValue) {
                result = i;
                minValue = ints[i];
            }
        }
        return result;
    }

    private static boolean existsBiggerThanNumber(int value, int[] ints, int fromIndex) {
        for (int i = fromIndex; i < ints.length; i++) {
            if (ints[i] > value) {
                return true;
            }
        }
        return false;
    }

    private static void swap(int[] x, int a, int b) {
        int t = x[a];
        x[a] = x[b];
        x[b] = t;
    }
}