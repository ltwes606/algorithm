class Solution {

    public boolean solution(String str) {
        if (!validLength(str)) {
            return false;
        }
        if (!isNumericString(str)) {
            return false;
        }
        return true;
    }

    private static boolean validLength(String str) {
        if (str.length() == 4 || str.length() == 6) {
            return true;
        }
        return false;
    }

    private static boolean isNumericString(String str) {
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }
}