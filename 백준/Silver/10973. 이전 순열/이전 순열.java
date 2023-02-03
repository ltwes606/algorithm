import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int size = Integer.parseInt(reader.readLine());
        StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
        int[] input = new int[size];
        for (int i = 0; i < input.length; i++) {
            input[i] = Integer.parseInt(tokenizer.nextToken());
        }

        // 다음 수열 찾기
        Integer[] result = getNextPermutation(input);

        // 출력
        if (result == null) {
            System.out.println(-1);
        } else {
            System.out.println(
                    Arrays.stream(result).map(String::valueOf).collect(Collectors.joining(" ")));
        }

        // 종료 작업
        reader.close();
    }

    private static Integer[] getNextPermutation(int[] permutation) {
        // 변경할 인덱스 위치 찾기
        for (int i = permutation.length - 1; i >= 0; i--) {
            // 뒷 인덱스 중에 큰 값 탐색
            if (existsSmallerThanNumber(permutation[i], permutation, i + 1)) {
                Integer[] result = Arrays.stream(permutation.clone()).boxed()
                        .toArray(Integer[]::new);
                swap(result, i, findMaximumIndexSmallerThan(result, result[i], i + 1));
                Arrays.sort(result, i + 1, result.length, Collections.reverseOrder());
                return result;
            }
        }
        return null;
    }

    private static int findMaximumIndexSmallerThan(Integer[] integers, int value, int fromIndex) {
        int result = fromIndex;
        int minValue = Integer.MIN_VALUE;
        for (int i = fromIndex; i < integers.length; i++) {
            if (value > integers[i] && integers[i] > minValue) {
                result = i;
                minValue = integers[i];
            }
        }
        return result;
    }

    private static boolean existsSmallerThanNumber(int value, int[] ints, int fromIndex) {
        for (int i = fromIndex; i < ints.length; i++) {
            if (ints[i] < value) {
                return true;
            }
        }
        return false;
    }

    private static void swap(Integer[] x, int a, int b) {
        Integer t = x[a];
        x[a] = x[b];
        x[b] = t;
    }
}