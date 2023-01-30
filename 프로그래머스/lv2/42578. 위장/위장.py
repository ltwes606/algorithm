from collections import defaultdict

def solution(clothes):
    dict = defaultdict(int)
    for row in clothes:
        dict[row[1]] += 1
    
    answer = 1
    for value in dict.values():
        answer *= value + 1
    return answer - 1