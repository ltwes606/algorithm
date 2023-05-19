import java.util.*;

class Solution {
    public String solution(int[] numbers) {
        String[] strings = Arrays.stream(numbers).mapToObj(String::valueOf).toArray(String[]::new);
        sort(strings);

        StringBuilder result = new StringBuilder();
        for (String str : strings) {
            result.append(str);
        }
        
        if (isAllZero(result)) {
            return "0";
        }
        return result.toString();
    }

    private static void sort(String[] strings) {
        Arrays.sort(strings, (str1, str2) -> {
                    String firstStr1 = str1 + str2;
                    String firstStr2 = str2 + str1;
                    return firstStr2.compareTo(firstStr1);
                }
        );
    }
    
    private static boolean isAllZero(StringBuilder sb) {
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) != '0') {
                return false;
            }
        }
        return true;
    }
}