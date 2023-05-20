import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Scanner;

class Solution {

    private static Scanner scanner;

    private static final int[][] DIRECTIONS = {
            new int[]{-1, 0},
            new int[]{0, 1},
            new int[]{1, 0},
            new int[]{0, -1}
    };

    public static void main(String args[]) throws Exception {
        // 입력 버퍼 설정
        scanner = new Scanner(System.in);

        // 총 반복 횟수 입력
        int testCount = scanner.nextInt();
        int[] result = new int[testCount];
        // 총 반복 횟수만큼 반복
        for (int i = 0; i < testCount; i++) {
            // 맵 크기 입력
            int mapSize = scanner.nextInt();

            // 맵 생성
            int[][] map = createMap(mapSize);

            // 길이 최솟값 구하기(bfs)
            int minCost = findMinCost(map);

            // 결과값 저장
            result[i] = minCost;
        }

        // 결과 출력
        printResult(result);
        // 입력 버퍼 해제
        scanner.close();
    }

    private static int[][] createMap(int mapSize) {
        int[][] result = new int[mapSize][mapSize];
        for (int row = 0; row < result.length; row++) {
            String line = scanner.next();
            for (int col = 0; col < result[row].length; col++) {
                result[row][col] = line.charAt(col) - '0';
            }
        }
        return result;
    }

    private static int findMinCost(int[][] map) {
        // 초기 큐 생성
        Queue<int[]> queue = new PriorityQueue<>((e1, e2) -> e1[2] - e2[2]);
        queue.offer(new int[]{0, 0, map[0][0]});
        // 해당 포인트를 지나간 배열 생성
        int[][] visitedMap = createVisitedMap(map);

        // bfs
        while (!queue.isEmpty()) {
            // 큐에서 앞의 원소 뽑기
            int[] element = queue.poll();

            // 목적지라면 반환
            if (isDestination(element, map)) {
                return element[2];
            }

            // 4방향 큐 삽입
            for (int[] direction : DIRECTIONS) {
                // 새로운 값 생성
                int[] newElement = new int[3];
                newElement[0] = element[0] + direction[0];
                newElement[1] = element[1] + direction[1];

                // 인덱스가 유효한지 검사
                if (!isValidIndex(newElement, map)) {
                    continue;
                }
                newElement[2] = element[2] + map[newElement[0]][newElement[1]];
                if (newElement[2] >= visitedMap[newElement[0]][newElement[1]]) {
                    continue;
                }
                visitedMap[newElement[0]][newElement[1]] = newElement[2];

                // 새로운 값 삽입
                queue.offer(newElement);
            }
        }
        return -1;
    }

    private static int[][] createVisitedMap(int[][] map) {
        int[][] result = new int[map.length][map[0].length];
        for (int row = 0; row < result.length; row++) {
            Arrays.fill(result[row], Integer.MAX_VALUE);
        }
        return result;
    }

    private static boolean isDestination(int[] element, int[][] map) {
        if (element[0] == map.length - 1 && element[1] == map[0].length - 1) {
            return true;
        }
        return false;
    }

    private static boolean isValidIndex(int[] newElement, int[][] map) {
        if (newElement[0] < 0 || map.length <= newElement[0]) {
            return false;
        }
        if (newElement[1] < 0 || map[0].length <= newElement[1]) {
            return false;
        }
        return true;
    }

    private static void printResult(int[] result) {
        for (int i = 0; i < result.length; i++) {
            System.out.printf("#%d %d\n", i + 1, result[i]);
        }
    }
}