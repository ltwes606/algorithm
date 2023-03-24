import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {

    public static final int MIN_VALUE = -100_000;
    public static final int MAX_VALUE = 100_000;
    public static final char STAR = '*';

    public String[] solution(int[][] line) {
        // 교점(x, y) 구하기
        List<int[]> nodes = new ArrayList<>();
        for (int i1 = 0; i1 + 1 < line.length; i1++) {
            int[] le1 = line[i1];

            for (int i2 = i1 + 1; i2 < line.length; i2++) {
                int[] le2 = line[i2];

                int[] newNode = findNode(le1, le2);
                if (newNode == null) {
                    continue;
                }

                nodes.add(newNode);
            }
        }

        // x,y의 최소,최대값 뽑기
        int minX = getMinX(nodes);
        int maxX = getMaxX(nodes);
        int minY = getMinY(nodes);
        int maxY = getMaxY(nodes);

        // 최소,최대값을 이용하여 빈 맵 만들기
        char[][] chars = new char[maxY - minY + 1][maxX - minX + 1];

        // 생성한 빈 맵에 정수로 이루어진 교점과 최소,최대값을 이용하여 점 찍기
        for (int i = 0; i < nodes.size(); i++) {
            int[] node = nodes.get(i);
            int row = maxY - node[1];
            int col = node[0] - minX;
            chars[row][col] = STAR;
        }

        // 아무 것도 안 찍혀있다면 "." 입력
        for (int row = 0; row < chars.length; row++) {
            for (int col = 0; col < chars[row].length; col++) {
                if (chars[row][col] == STAR) {
                    continue;
                }
                chars[row][col] = '.';
            }
        }

        String[] result = new String[chars.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = String.valueOf(chars[i]);
        }
        return result;
    }

    private static int[] findNode(int[] le1, int[] le2) {
        long A = le1[0];
        long B = le1[1];
        long E = le1[2];

        long C = le2[0];
        long D = le2[1];
        long F = le2[2];

        long denominator = A * D - B * C;
        if (denominator == 0) {
            return null;
        }

        long xNumerator = B * F - E * D;
        long yNumerator = E * C - A * F;
        if (xNumerator % denominator != 0
                || yNumerator % denominator != 0) {
            return null;
        }
        return new int[]{(int) (xNumerator / denominator), (int) (yNumerator / denominator)};
    }

    private int getMinX(List<int[]> nodes) {
        int result = MAX_VALUE;
        for (int i = 0; i < nodes.size(); i++) {
            int x = nodes.get(i)[0];
            if (result > x) {
                result = x;
            }
        }
        return result;
    }

    private int getMaxX(List<int[]> nodes) {
        int result = MIN_VALUE;
        for (int i = 0; i < nodes.size(); i++) {
            int x = nodes.get(i)[0];
            if (result < x) {
                result = x;
            }
        }
        return result;
    }

    private int getMinY(List<int[]> nodes) {
        int result = MAX_VALUE;
        for (int i = 0; i < nodes.size(); i++) {
            int y = nodes.get(i)[1];
            if (result > y) {
                result = y;
            }
        }
        return result;
    }

    private int getMaxY(List<int[]> nodes) {
        int result = MIN_VALUE;
        for (int i = 0; i < nodes.size(); i++) {
            int y = nodes.get(i)[1];
            if (result < y) {
                result = y;
            }
        }
        return result;
    }
}
