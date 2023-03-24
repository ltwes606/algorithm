import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Solution {

    static class Coordinates {

        private final int x;
        private final int y;

        public Coordinates(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Coordinates that = (Coordinates) o;
            return x == that.x && y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }

    public static final int MIN_VALUE = Integer.MIN_VALUE;
    public static final int MAX_VALUE = Integer.MAX_VALUE;
    public static final char STAR = '*';
    public static final char DOT = '.';

    public String[] solution(int[][] line) {
        // 교점(x, y) 구하기
        Set<Coordinates> nodes = new HashSet<>();
        for (int i1 = 0; i1 + 1 < line.length; i1++) {
            int[] le1 = line[i1];

            for (int i2 = i1 + 1; i2 < line.length; i2++) {
                int[] le2 = line[i2];

                Coordinates newNode = findNode(le1, le2);
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
        boolean[][] stars = new boolean[(int) (maxY - minY + 1)][(int) (maxX - minX + 1)];

        // 생성한 빈 맵에 정수로 이루어진 교점과 최소,최대값을 이용하여 점 찍기
        for (Coordinates node : nodes) {
            int y = (maxY - node.getY());
            int x = (node.getX() - minX);
            stars[y][x] = true;
        }

        String[] result = new String[stars.length];
        for (int y = 0; y < stars.length; y++) {
            StringBuilder builder = new StringBuilder();
            for (int x = 0; x < stars[y].length; x++) {
                if (stars[y][x]) {
                    builder.append(STAR);
                    continue;
                }
                builder.append(DOT);
            }
            result[y] = builder.toString();
        }
        return result;
    }

    private static Coordinates findNode(int[] le1, int[] le2) {
        long A = le1[0];
        long B = le1[1];
        long E = le1[2];

        long C = le2[0];
        long D = le2[1];
        long F = le2[2];

        long denominator = A * D - B * C;
        if (denominator == 0L) {
            return null;
        }

        long xNumerator = B * F - E * D;
        long yNumerator = E * C - A * F;
        if (xNumerator % denominator != 0L
                || yNumerator % denominator != 0L) {
            return null;
        }
        return new Coordinates((int) (xNumerator / denominator), (int) (yNumerator / denominator));
    }

    private int getMinX(Set<Coordinates> nodes) {
        int result = MAX_VALUE;
        for (Coordinates node : nodes) {
            int x = node.getX();
            if (result > x) {
                result = x;
            }
        }
        return result;
    }

    private int getMaxX(Set<Coordinates> nodes) {
        int result = MIN_VALUE;
        for (Coordinates node : nodes) {
            int x = node.getX();
            if (result < x) {
                result = x;
            }
        }
        return result;
    }

    private int getMinY(Set<Coordinates> nodes) {
        int result = MAX_VALUE;
        for (Coordinates node : nodes) {
            int y = node.getY();
            if (result > y) {
                result = y;
            }
        }
        return result;
    }

    private int getMaxY(Set<Coordinates> nodes) {
        int result = MIN_VALUE;
        for (Coordinates node : nodes) {
            int y = node.getY();
            if (result < y) {
                result = y;
            }
        }
        return result;
    }
}