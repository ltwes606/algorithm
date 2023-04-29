import java.util.*;

class Solution {
    public int[] solution(int n, String[] words) {
        int[] result = new int[2];
        if (words.length == 0) {
            return result;
        }
        
        Set<String> dup = new HashSet<String>();
        dup.add(words[0]);
        
        for (int i = 1; i < words.length; i++) {
            String currentWord = words[i];
            String prevWord = words[i - 1];
            
            if (dup.contains(currentWord) 
                || prevWord.charAt(prevWord.length() - 1) != currentWord.charAt(0)) {
                result[0] = (i % n) + 1;
                result[1] = (i / n) + 1;
                break;
            }
            dup.add(currentWord);
        }
        return result;
    }
}