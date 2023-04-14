select round(avg(DAILY_FEE), 0) AVERAGE_FEE
from CAR_RENTAL_COMPANY_CAR
where CAR_TYPE = 'SUV'
group by CAR_TYPE;