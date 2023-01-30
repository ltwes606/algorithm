import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.StringTokenizer;

class Main {

    public static void main(String[] args) throws IOException {
        // 입력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        // 입력
        int range = Integer.parseInt(reader.readLine());

        // 조합 구하기
        int[] result = new int[range];
        for (Integer i = 0; i < result.length; i++) {
            HashMap<String, Integer> hashMap = getHashMap(
                    Integer.parseInt(reader.readLine()), reader
            );
            result[i] = getCombinationCount(hashMap);
        }

        // 출력
        for (int i = 0; i < result.length; i++) {
            System.out.println(result[i]);
        }

        // 종료 작업
        reader.close();
    }

    private static int getCombinationCount(HashMap<String, Integer> hashMap) {
        int result = 1;
        for (Integer value : hashMap.values()) {
            result *= value + 1;
        }
        return result - 1;
    }

    private static HashMap<String, Integer> getHashMap(int size, BufferedReader reader)
            throws IOException {
        HashMap<String, Integer> result = new HashMap<>();
        for (int i = 0; i < size; i++) {
            // 입력
            StringTokenizer tokenizer = new StringTokenizer(reader.readLine());
            String element = tokenizer.nextToken();
            String key = tokenizer.nextToken();

            if (!result.containsKey(key)) {
                result.put(key, 0);
            }
            result.replace(key, result.get(key) + 1);
        }
        return result;
    }
}