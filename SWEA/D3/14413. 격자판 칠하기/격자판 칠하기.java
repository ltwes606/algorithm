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
        Arrays.fill(result, true);
        for (int i = 0; i < result.length; i++) {
            int row = scanner.nextInt();
            int col = scanner.nextInt();

            // 맵 만들기
            char[][] map = createMap(row, col);

            // 샾 위치 찾기
            int[] hashPoint = getPoint(map, '#');
            // 점 위치 찾기
            int[] dotPoint = getPoint(map, '.');

            int[] startPoint = hashPoint;
            if (startPoint == null) {
                startPoint = dotPoint;
            }
            if (startPoint == null) {
                continue;
            }

            // 샾 색칠
            boolean checked = paint(map, startPoint);
            if (checked) {
                result[i] = false;
            }
        }

        // 결과 출력
        for (int i = 0; i < result.length; i++) {
            String resultString = result[i] ? "possible" : "impossible";
            System.out.printf("#%d %s\n", i + 1, resultString);
        }
        // 입력 버퍼 해제
        scanner.close();
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