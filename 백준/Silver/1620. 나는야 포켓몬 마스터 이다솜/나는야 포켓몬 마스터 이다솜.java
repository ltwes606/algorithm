import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.HashMap;

class Main {

    public static void main(String[] args) throws IOException {
        // 입출력 설정
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));

        // 초기화
        HashMap<String, Integer> nameToOrderNumber = new HashMap<>();
        HashMap<Integer, String> orderNumberToName = new HashMap<>();

        // 입력
        int[] sizes = Arrays.stream(reader.readLine().split(" "))
                .mapToInt(Integer::parseInt)
                .toArray();
        for (int i = 1; i <= sizes[0]; i++) {
            String name = reader.readLine();
            nameToOrderNumber.put(name, i);
            orderNumberToName.put(i, name);
        }

        // 출력
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < sizes[1]; i++) {
            String name = reader.readLine();
            try {
                Integer orderNumber = Integer.parseInt(name);
                result.append(orderNumberToName.get(orderNumber));
            } catch (NumberFormatException exception) {
                result.append(nameToOrderNumber.get(name));
            } finally {
                result.append("\n");
            }
        }

        // 최종 출력
        System.out.println(result);

        // 종료 작업
        reader.close();
        writer.flush();
        writer.close();
    }
}