import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int size = Integer.parseInt(reader.readLine());
        int[][] ways = new int[size][];
        for (int i = 0; i < ways.length; i++) {
            ways[i] = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        // 순회
        int result = traverse(ways);

        // 결과 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
    }

    private static int traverse(int[][] matrix) {
        int result = Integer.MAX_VALUE;

        for (int i = 0; i < matrix.length; i++) {
            boolean[] visited = new boolean[matrix.length];
            visited[i] = true;
            result = Math.min(result, backtracking(i, matrix, visited, i, new ArrayList<>()));
        }
        return result;
    }

    private static int backtracking(int startPoint, int[][] matrix,
            boolean[] visited, int location, ArrayList<Integer> costs) {
        if (costs.size() == matrix.length - 1) {
            if (matrix[location][startPoint] == 0) {
                return Integer.MAX_VALUE;
            }
            return sum(costs) + matrix[location][startPoint];
        }

        int result = Integer.MAX_VALUE;
        for (int i = 0; i < matrix[location].length; i++) {
            if (visited[i] || matrix[location][i] == 0) {
                continue;
            }

            visited[i] = true;
            costs.add(matrix[location][i]);
            result = Math.min(result, backtracking(startPoint, matrix, visited, i, costs));
            costs.remove(costs.size() - 1);
            visited[i] = false;
        }
        return result;
    }

    private static int sum(ArrayList<Integer> list) {
        int result = 0;
        for (Integer element : list) {
            result += element;
        }
        return result;
    }
}