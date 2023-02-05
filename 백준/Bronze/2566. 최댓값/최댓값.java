import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {

    public static final int SIZE = 9;

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 매트릭스 입력
        int[][] matrix = new int[SIZE][];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        // 최대값 행,열 찾기
        int maxRow = 0;
        int maxCol = 0;
        for (int iRow = 0; iRow < matrix.length; iRow++) {
            for (int iCol = 0; iCol < matrix[iRow].length; iCol++) {
                if (matrix[maxRow][maxCol] < matrix[iRow][iCol]) {
                    maxRow = iRow;
                    maxCol = iCol;
                }
            }
        }

        // 결과 출력
        System.out.println(matrix[maxRow][maxCol]);
        System.out.println((maxRow + 1) + " " + (maxCol + 1));

        // 종료 작업
        reader.close();
    }
}