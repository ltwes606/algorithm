import math

men_count, women_count, exclude_count = map(int, input().split())

team_count_make_men = int(men_count / 2)
team_count_make_women = women_count

team_count = min(team_count_make_men, team_count_make_women)

remain = (men_count - 2 * team_count) + (women_count - team_count)

exclude_count -= remain
if exclude_count > 0:
    team_count -= math.ceil(exclude_count / 3)
if team_count < 0:
    team_count = 0

print(int(team_count))