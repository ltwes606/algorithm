import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Main {

    private static int[][] map;

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int size = Integer.parseInt(reader.readLine());
        map = new int[size][];
        for (int i = 0; i < map.length; i++) {
            map[i] = Arrays.stream(reader.readLine().split(" "))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        }

        // dfs
        int result = dfs(0, 0, new boolean[size], Integer.MAX_VALUE);

        // 결과 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
    }

    private static int dfs(int startIndex, int count, boolean[] visited, int result) {
        if (count == map.length / 2) {
            int startTeamTotalScore = getTotalScore(
                    IntStream.range(0, visited.length).filter(i -> visited[i]).toArray());
            int linkTeamTotalScore = getTotalScore(
                    IntStream.range(0, visited.length).filter(i -> !visited[i]).toArray());
            return Math.min(result, Math.abs(startTeamTotalScore - linkTeamTotalScore));
        }

        for (int i = startIndex; i < map.length; i++) {
            visited[i] = true;
            result = dfs(i + 1, count + 1, visited, result);
            visited[i] = false;
        }
        return result;
    }

    private static int getTotalScore(int[] indexes) {
        int result = 0;
        for (int i = 0; i < indexes.length; i++) {
            for (int j = i + 1; j < indexes.length; j++) {
                result += map[indexes[i]][indexes[j]];
                result += map[indexes[j]][indexes[i]];
            }
        }
        return result;
    }
}