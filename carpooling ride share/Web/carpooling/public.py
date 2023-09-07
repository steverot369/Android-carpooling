from flask import *
from database import *
public=Blueprint('public',__name__)

@public.route('/',methods=['post','get'])
def publichome():
	return render_template('publichome.html')

@public.route('/login',methods=['post','get'])
def login():
	if 'submit' in request.form:
		username=request.form['username']
		passw=request.form['password']

		q="select * from tbl_login where uname='%s' and password='%s'"%(username,passw)
		res=select(q)
		print(res)
		if res:
			session['logid']=res[0]['loginid']
			if res[0]['login_type']=="admin":
				return redirect(url_for("admin.adminhome"))

			elif res[0]['login_type']=="owner":
				# flash(session['logid'])
				q="select * from owner where loginid='%s'"%(session['logid'])
				res=select(q)
				session['owid']=res[0]['owner_id']

				return redirect(url_for("owner.ownerhome"))
			
		else:
			flash('Invalid username or password !!!')
	return render_template('login.html')

@public.route('/ownerreg',methods=['get','post'])
def ownerreg():
	data={}
	if 'reg' in request.form:
		
		fname=request.form['fname']
		lname=request.form['lname']
		phone=request.form['phone']
		place=request.form['place']
		email=request.form['email']
		uname=request.form['uname']
		passw=request.form['passw']

		q="select * from tbl_login where uname='%s'"%(uname)
		res=select(q)
		q1="select * from owner where email='%s'"%(email)
		res1=select(q1)
		q2="select * from owner where phone='%s'"%(phone)
		res2=select(q2)
		if res:
			flash("username already exist.....")
			return redirect(url_for('public.ownerreg'))
		elif res1:
			flash("email already exist.....")
			return redirect(url_for('public.ownerreg'))
		elif res2:
			flash("phone number already exist.....")
			return redirect(url_for('public.ownerreg'))
		else:
			q="insert into tbl_login values(null,'%s','%s','owner')"%(uname,passw)
			id=insert(q)
			q="insert into owner values(null,'%s','%s','%s','%s','%s','%s')"%(id,fname,lname,place,phone,email)
			insert(q)
			flash("Registration successfull...")
			return redirect(url_for('public.login'))


	return render_template('owner_reg.html')


# @public.route('/forgotpassword',methods=['get','post'])
# def forgotpassword():
# 	if 'for' in request.form:
# 		uname=request.form['uname']
# 		password=request.form['password']
# 		q="SELECT * FROM tbl_login WHERE uname='%s'"%(uname)
# 		res=select(q)
# 		if res:
# 			q1="update tbl_login set password='%s' where uname='%s'"%(password,uname)
# 			res1=update(q1)
# 			flash("updated password successfully")
# 			return redirect(url_for('public.login'))
# 	return render_template('owner_forgot_password.html')

# @public.route('/forgotpassword')
# def forgotpassword():    
#     return render_template('owner_forgot_password.html')

@public.route('/forgotpassword',methods=['get','post'])
def forgotpassword():
	if 'for' in request.form:
		uname=request.form['uname']
		phone=request.form['phone']
		email=request.form['email']
		password=request.form['password']
		q="SELECT tbl_login.uname,owner.email,owner.phone FROM tbl_login,owner WHERE tbl_login.uname='%s' AND owner.phone='%s' AND owner.email='%s'"%(uname,phone,email)
		res=select(q)
		if res:
			
			q1="update tbl_login set password='%s' where uname='%s'"%(password,uname)
		
			res1=update(q1)
			flash("updated password successfully")
			return redirect(url_for('public.login'))
		else:
			flash("wrong username or phone no or email")

	return render_template('owner_forgot_password.html')