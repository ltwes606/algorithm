class Solution {
    public int[] solution(String str) {
        int conversionCount = 0;
        int zeroCount = 0;
        while (str.length() != 1) {
            zeroCount += countZero(str);
            str = removeZero(str);
            str = toBinary(str.length());
            conversionCount++;
        }
        return new int[]{conversionCount, zeroCount};
    }
    
    static private int countZero(String str) {
        int result = 0;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '0') {
                result++;
            }
        }
        return result;
    }
    
    static private String removeZero(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (ch == '0') {
                continue;
            }
            
            result.append(ch);
        }
        return result.toString();
    }
    
    static private String toBinary(int length) {
        StringBuilder reverse = new StringBuilder();
        while (length != 0) {
            reverse.append((char)('0' + (length % 2)));
            length /= 2;
        }
        
        StringBuilder result = new StringBuilder();
        for (int i = reverse.length() - 1; i >= 0; i--) {
            result.append(reverse.charAt(i));
        }
        return result.toString();
    }
}

// "110010101001" -> "111111" -> "110" -> "11" -> "10" -> "1"