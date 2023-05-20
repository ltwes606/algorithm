import java.util.ArrayList;
import java.util.Scanner;

class Solution {

    private static Scanner scanner;

    public static void main(String args[]) throws Exception {
        // 입력 버퍼 설정
        scanner = new Scanner(System.in);

        // 총 횟수 입력
        int testCount = scanner.nextInt();
        // 총 횟수 반복
        boolean[] result = new boolean[testCount];
        for (int i = 0; i < result.length; i++) {
            // 체스보드 생성
            boolean[][] chessboard = createChessboard();

            // 룩 검사
            result[i] = validLook(chessboard);
        }

        // 결과 출력
        for (int i = 0; i < result.length; i++) {
            String bool = "no";
            if (result[i]) {
                bool = "yes";
            }
            System.out.printf("#%d %s\n", i + 1, bool);
        }
        // 입력 버퍼 해제
        scanner.close();
    }

    private static boolean[][] createChessboard() {
        boolean[][] result = new boolean[8][8];
        for (int row = 0; row < result.length; row++) {
            String line = scanner.next();
            for (int col = 0; col < result[0].length; col++) {
                if (line.charAt(col) == 'O') {
                    result[row][col] = true;
                }
            }
        }
        return result;
    }

    private static boolean validLook(boolean[][] chessboard) {
        // 룩 위치 얻기
        ArrayList<int[]> lookPoints = getLook(chessboard);
        if (lookPoints.size() != 8) {
            return false;
        }

        // 각 룩 위치에 row, col당 1개의 룩만 존재
        for (int[] lookPoint : lookPoints) {
            int rowCount = 0;
            for (int col = 0; col < chessboard[lookPoint[0]].length; col++) {
                if (chessboard[lookPoint[0]][col]) {
                    rowCount++;
                }
            }

            int colCount = 0;
            for (int row = 0; row < chessboard.length; row++) {
                if (chessboard[row][lookPoint[1]]) {
                    colCount++;
                }
            }
            if (rowCount != 1 || colCount != 1) {
                return false;
            }
        }
        return true;
    }

    private static ArrayList<int[]> getLook(boolean[][] chessboard) {
        ArrayList<int[]> result = new ArrayList<>();
        for (int row = 0; row < chessboard.length; row++) {
            for (int col = 0; col < chessboard[0].length; col++) {
                if (chessboard[row][col]) {
                    result.add(new int[]{row, col});
                }
            }
        }
        return result;
    }
}