import java.security.cert.CertSelector;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

class Solution {

    private static Scanner scanner;
    private static final int[][] DIRECTIONS = {
            new int[]{-1, 0},
            new int[]{0, 1},
            new int[]{1, 0},
            new int[]{0, -1},
    };

    public static void main(String args[]) throws Exception {
        // 입력 버퍼 설정
        scanner = new Scanner(System.in);

        // 총 횟수 입력
        int testCount = scanner.nextInt();
        // 총 횟수 반복
        boolean[] result = new boolean[testCount];
        for (int count = 0; count < testCount; count++) {
            String numberString = scanner.next();

            int[] numberCount = new int[10];
            for (int i = 0; i < numberString.length(); i++) {
                numberCount[numberString.charAt(i) - '0']++;
            }

            int number = Integer.parseInt(numberString);
            int multiple = 2;
            while (true) {
                int currentNumber = number * multiple;
                String currentNumberString = String.valueOf(currentNumber);
                if (currentNumberString.length() > numberString.length()) {
                    break;
                }

                int[] currentNumberCount = numberCount.clone();
                for (int i = 0; i < currentNumberString.length(); i++) {
                    currentNumberCount[currentNumberString.charAt(i) - '0']--;
                }

                result[count] = checkNumberCount(currentNumberCount);
                if (result[count]) {
                    break;
                }
                multiple++;
            }
        }

        // 결과 출력
        for (int i = 0; i < result.length; i++) {
            String resultString = "impossible";
            if (result[i]) {
                resultString = "possible";
            }
            System.out.printf("#%d %s\n", i + 1, resultString);
        }
        // 입력 버퍼 해제
        scanner.close();
    }

    private static boolean checkNumberCount(int[] currentNumberCount) {
        for (int i = 0; i < currentNumberCount.length; i++) {
            if (currentNumberCount[i] < 0) {
                return false;
            }
        }
        return true;
    }

    private static char[][] createMap(int row, int col) {
        char[][] result = new char[row][col];
        for (int iRow = 0; iRow < row; iRow++) {
            String line = scanner.next();
            for (int iCol = 0; iCol < col; iCol++) {
                result[iRow][iCol] = line.charAt(iCol);
            }
        }
        return result;
    }

    private static int[] getPoint(char[][] map, char c) {
        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[row].length; col++) {
                if (map[row][col] == c) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }


    private static boolean paint(char[][] map, int[] startPoint) {
        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visited = new boolean[map.length][map[0].length];
        queue.offer(startPoint);
        visited[startPoint[0]][startPoint[1]] = true;

        while (!queue.isEmpty()) {

            int[] point = queue.poll();
            char currentCh = map[point[0]][point[1]];
            char nextCh = currentCh == '#' ? '.' : '#';
            for (int[] direction : DIRECTIONS) {
                int nextRow = point[0] + direction[0];
                int nextCol = point[1] + direction[1];
                if (!isValidIndex(map, nextRow, nextCol) ||
                        visited[nextRow][nextCol]) {
                    continue;
                }
                visited[nextRow][nextCol] = true;

                if (map[nextRow][nextCol] == currentCh) {
                    return true;
                }
                map[nextRow][nextCol] = nextCh;
                queue.offer(new int[]{nextRow, nextCol});
            }
        }
        return false;
    }

    private static boolean isValidIndex(char[][] map, int row, int col) {
        if (row < 0 || map.length <= row) {
            return false;
        }
        if (col < 0 || map[0].length <= col) {
            return false;
        }
        return true;
    }
}