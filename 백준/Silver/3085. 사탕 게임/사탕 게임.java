import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;

class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        Integer size = Integer.parseInt(reader.readLine());
        char[][] inputs = new char[size][];
        for (int i = 0; i < inputs.length; i++) {
            inputs[i] = reader.readLine().toCharArray();
        }

        // 최대 개수 구하기
        int result = findMaxCountOfCandyGame(inputs);

        // 결과 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
    }

    private static int findMaxCountOfCandyGame(char[][] array) {
        int result = 1;
        for (int i = 0; i < array.length - 1; i++) {
            for (int j = 0; j < array[i].length; j++) {
                // 아무것도 바꾸지 않았을 때
                result = Math.max(result, findMaxCountOfRow(array[i]));
                result = Math.max(result, findMaxCountOfRow(array[i + 1]));
                result = Math.max(result, findMaxCountOfColumn(array, i));
                result = Math.max(result, findMaxCountOfColumn(array, i + 1));

                // 인접 행의 문자를 바꿨을 때
                swap(array, i, j, i + 1, j);
                result = Math.max(result, findMaxCountOfRow(array[i]));
                result = Math.max(result, findMaxCountOfRow(array[i + 1]));
                result = Math.max(result, findMaxCountOfColumn(array, j));
                swap(array, i, j, i + 1, j);

                // 인접 열의 문자를 바꿨을 때
                swap(array, j, i, j, i + 1);
                result = Math.max(result, findMaxCountOfColumn(array, i));
                result = Math.max(result, findMaxCountOfColumn(array, i + 1));
                result = Math.max(result, findMaxCountOfRow(array[j]));
                swap(array, j, i, j, i + 1);
            }
        }
        return result;
    }

    private static int findMaxCountOfColumn(char[][] array, int column) {
        int result = 1;
        int currentCount = 1;
        for (int row = 0; row < array.length - 1; row++) {
            if (array[row][column] != array[row + 1][column]) {
                currentCount = 1;
                continue;
            }

            currentCount++;
            result = Math.max(result, currentCount);
        }
        return result;
    }

    private static int findMaxCountOfRow(char[] chars) {
        int result = 1;
        int currentCount = 1;
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] != chars[i + 1]) {
                currentCount = 1;
                continue;
            }

            currentCount++;
            result = Math.max(result, currentCount);
        }
        return result;
    }

    private static void swap(char[][] array, int aRow, int aCol, int bRow, int bCol) {
        char tmp = array[aRow][aCol];
        array[aRow][aCol] = array[bRow][bCol];
        array[bRow][bCol] = tmp;
    }
}
