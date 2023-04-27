class Solution {
    public String solution(String str) {
        StringBuilder result = new StringBuilder();
        boolean wasPrevBlank = true;
        for (int i = 0; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (Character.isUpperCase(ch)) {
                ch = Character.toLowerCase(ch);
            }
            
            if (wasPrevBlank && Character.isLowerCase(ch)) {
                ch = Character.toUpperCase(ch);
            }
            
            result.append(ch);
            
            wasPrevBlank = false;
            if (ch == ' ') {
                wasPrevBlank = true;
            }
        }
        return result.toString();
    }
}