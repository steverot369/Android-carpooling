from datetime import datetime

dob_string="2001,2,10"
def calculate_age(born):
    dob = [int(x) for x in dob_string.split(',')]
    date_of_birth = datetime(dob[0], dob[1], dob[2])
    today = datetime.today()
    return today.year - date_of_birth.year - ((today.month, today.day) < (date_of_birth.month, date_of_birth.day))

date_of_birth_string = dob_string
age = calculate_age(date_of_birth_string)
print(age)


toay=datetime.today()
b=(toay.year)


a=(dob_string[0:4])

c=int(b)-int(a)
print(c)