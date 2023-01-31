import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

class Main {

    private static final int DEFAULT_CHANNEL = 100;

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = getReader(System.in);

        // 입력
        int channelMove = Integer.parseInt(reader.readLine());

        // 고장난 버튼의 크기
        int size = Integer.parseInt(reader.readLine());
        // 유효한 버튼 얻기
        int[] brokenButtons = null;
        if (size != 0) {
            brokenButtons = Arrays.stream(reader.readLine().split(" ")).mapToInt(Integer::parseInt)
                    .toArray();
        }
        boolean[] validButtons = getUncheckedIndexes(10, brokenButtons);

        int movableChannel = findMovable(channelMove, validButtons);

        // 최종 출력
        int countMoveNumber =
                Math.abs(channelMove - movableChannel) + String.valueOf(movableChannel).length();
        int countMoveHeight = Math.abs(channelMove - DEFAULT_CHANNEL);
        int result = countMoveNumber > countMoveHeight ? countMoveHeight : countMoveNumber;
        System.out.println(result);

        // 종료작업
        reader.close();
    }

    private static int findMovable(int number, boolean[] validButtons) {
        if (areAllFalse(validButtons)) {
            return DEFAULT_CHANNEL;
        }

        int distance = 0;
        while (true) {
            int valueCheck = number - distance;
            if (valueCheck >= 0 && canMove(valueCheck, validButtons)) {
                return valueCheck;
            }
            valueCheck = number + distance;
            if (canMove(valueCheck, validButtons)) {
                return valueCheck;
            }
            distance++;
        }
    }

    private static boolean canMove(int value, boolean[] validButtons) {
        while (true) {
            if (!validButtons[value % 10]) {
                return false;
            }

            value /= 10;
            if (value == 0) {
                break;
            }
        }
        return true;
    }

    private static boolean areAllFalse(boolean[] validButtons) {
        for (int idx = 0; idx < validButtons.length; idx++) {
            if (validButtons[idx]) {
                return false;
            }
        }
        return true;
    }

    private static boolean[] getUncheckedIndexes(int size, int[] indexes) {
        boolean[] result = new boolean[size];
        Arrays.fill(result, true);
        if (indexes == null) {
            return result;
        }

        for (int i = 0; i < indexes.length; i++) {
            result[indexes[i]] = false;
        }
        return result;
    }

    private static BufferedReader getReader(InputStream in) {
        return new BufferedReader(new InputStreamReader(in));
    }
}