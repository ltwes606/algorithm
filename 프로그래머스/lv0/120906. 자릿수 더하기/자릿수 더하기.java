class Solution {

    public int solution(int n) {
        String stringN = String.valueOf(n);

        int result = 0;
        for (int i = 0; i < stringN.length(); i++) {
            result += stringN.charAt(i) - '0';
        }
        return result;
    }
}