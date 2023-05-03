class Solution {
    public int solution(int angle) {
        int quotient = angle / 90;
        int remain = angle % 90;
        return quotient * 2 + (remain != 0 ? 1 : 0);
    }
}