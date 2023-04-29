import java.util.*;

class Solution
{
    public int solution(String str) {
        if (str.length() == 0) {
            return 1;
        }
        
        Stack<Character> stack = new Stack<>();
        stack.push(str.charAt(0));
        
        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            
            if (!stack.empty() && stack.peek() == ch) {
                stack.pop();
                continue;
            }
            stack.push(ch);
        }
        
        if (stack.empty()) {
            return 1;
        }
        return 0;
    }
}