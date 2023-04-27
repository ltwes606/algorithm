-- 코드를 입력하세요
select CAR_ID, if (CAR_ID IN (select CAR_ID
                              from CAR_RENTAL_COMPANY_RENTAL_HISTORY
                              where START_DATE <= '2022-10-16' and '2022-10-16' <= END_DATE)
                   , '대여중'
                   , '대여 가능') as AVAILABILITY
from CAR_RENTAL_COMPANY_RENTAL_HISTORY
group by CAR_ID
order by CAR_ID desc;