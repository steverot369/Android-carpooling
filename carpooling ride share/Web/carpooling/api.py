from flask import *
from database import *
import demjson
import uuid
from datetime import datetime

api = Blueprint('api',__name__)

@api.before_request
def beep():
	import winsound
	winsound.Beep(2500,100)


@api.route('/login',methods=['get','post'])
def login():
	data={}
	# data.update(request.args)
	username = request.args['uname']
	password = request.args['pass']
	q = "select * from tbl_login where uname='%s' and password='%s'" % (username,password)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)

@api.route('/user_register',methods=['get','post'])
def user_register():
	data={}
	uname = request.args['uname']
	password = request.args['pass']
	fname = request.args['fname']
	lname = request.args['lname']
	gender = request.args['gender']
	phone = request.args['phone']
	email = request.args['email']
	# q="SELECT * FROM tbl_login WHERE uname='%s' or phone='%s' or email='%s'"%(uname,phone,email)
	q="SELECT * FROM tbl_login WHERE uname='%s'"%(uname)
	res=select(q)
	q1="SELECT * FROM user WHERE phone='%s' or email='%s'"%(phone,email)
	
	res1=select(q1)
	if res:
		data['status']  = 'already1'
		data['method']  = 'user_register'
	elif res1:
		data['status']  = 'already2'
		data['method']  = 'user_register'
	else:

		q="insert into tbl_login(uname,password,login_type)values('%s','%s','user')" % (uname,password)
		id=insert(q)
		q="insert into user values(null,'%s','%s','%s','%s','%s','%s')" % (id,fname,lname,gender,phone,email)
		id=insert(q)

		if(id>0):
			data['status']  = 'success'
			data['method']  = 'user_register'
		else:
			data['status']	= 'failed'
			data['method']  = 'user_register'
	return demjson.encode(data)

@api.route('/finished_ride',methods=['get','post'])
def finished_ride():
	data={}
	riderid = request.args['riderid']
	q = "select * from tbl_booking,req_aprvl,request,tbl_renter,tbl_vehicle,tbl_location where tbl_location.renter_id=tbl_renter.renter_id and  tbl_booking.user_id=request.user_id and request.req_id=tbl_booking.req_id AND request.req_id=req_aprvl.req_id and req_aprvl.renter_id=tbl_renter.renter_id and tbl_booking.vehicle_id=tbl_vehicle.vehicle_id and tbl_vehicle.renter_id=tbl_renter.renter_id and request.status='droped'  and request.user_id=(SELECT user_id FROM user WHERE loginid='%s') and req_aprvl.status='drop'" % (riderid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'finished_ride'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'finished_ride'
	return  demjson.encode(data)

@api.route('/advance',methods=['get','post'])
def advance():
	data={}
	amt = request.args['amt']
	bookid = request.args['bookid']
	total = request.args['total']
	a=float(amt)
	t=float(total)
	if a<t:
		c=t-a
		q="update tbl_booking set amount='%s' where book_id='%s'"% (c,bookid)
		update(q)
		q="INSERT INTO tbl_advancepay(apr_id,amount,balance,date,time)VALUES('%s','%s','%s',curdate(),curtime())"% (bookid,amt,c)
		id=insert(q)
		if(id>0):
			data['status']  = 'success'
			data['method']  ='advance'
		else:
			data['status']	= 'failed'
			data['method']  ='advance'
	return demjson.encode(data)

@api.route('/addcomplaint',methods=['get','post'])
def addcomplaint():
	data={}
	riderid = request.args['riderid']
	msg = request.args['msg']
	
	q="INSERT INTO tbl_complaint(user_id,complaint,reply,date) VALUES((SELECT user_id FROM user WHERE loginid='%s'),'%s','pending',curdate())" % (riderid,msg)
	id=insert(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  = 'addcomplaint'
	else:
		data['status']	= 'failed'
		data['method']  = 'addcomplaint'
	return demjson.encode(data)

@api.route('/viewcomplaint',methods=['get','post'])
def viewcomplaint():
	data={}
	riderid = request.args['riderid']
	q = "SELECT complaint,reply FROM tbl_complaint where user_id=(SELECT user_id FROM user WHERE loginid='%s') and reply<>'pending' " % (riderid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'viewcomplaint'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'viewcomplaint'
	return  demjson.encode(data)

@api.route('/feedback',methods=['get','post'])
def feedback():
	data={}
	uid = request.args['uid']
	msg = request.args['msg']
	
	q="INSERT INTO tbl_feedback  VALUES(null,(SELECT user_id FROM user WHERE loginid='%s'),'%s',now())" % (uid,msg)
	id=insert(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  = 'feedback'
	else:
		data['status']	= 'failed'
		data['method']  = 'feedback'
	return demjson.encode(data)

@api.route('/check_req',methods=['get','post'])
def check_req():
	data={}
	uid = request.args['uid']
	q = "SELECT * FROM request WHERE user_id =(SELECT user_id FROM user WHERE loginid='%s') AND status= 'pending'" % (uid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'check_req'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'check_req'
	return  demjson.encode(data)

@api.route('/addrating',methods=['get','post'])
def addrating():
	data={}
	reqid = request.args['reqid']
	rating = request.args['rating']
	
	q="INSERT INTO rating (req_id,rating)values('%s','%s')" % (reqid,rating)
	id=insert(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  = 'addrating'
	else:
		data['status']	= 'failed'
		data['method']  = 'addrating'
	return demjson.encode(data)

@api.route('/sentrequest',methods=['get','post'])
def sentrequest():
	data={}
	travelto = request.args['travelto']
	fromplace = request.args['fromplace']
	noof = request.args['noof']
	flati = request.args['flati']
	flongi = request.args['flongi']
	tlati = request.args['tlati']
	tlongi = request.args['tlongi']
	uid = request.args['uid']
	q="insert into request values (null,(SELECT user_id FROM user WHERE loginid='%s'),'%s','%s','%s','%s','%s',curdate(),curtime(),'pending')" % (uid,flati,flongi,tlati,tlongi,noof)
	id=insert(q)
	q="insert into tbl_booking (user_id,vehicle_id,booking_from,booking_to,booking_date,booking_time,amount,booking_status,req_id)values((SELECT user_id FROM user WHERE loginid='%s'),'0','%s','%s',curdate(),curtime(),'0','pending',(select max(req_id) from request))" % (uid,fromplace,travelto)
	id=insert(q)

	if(id>0):
		data['status']  = 'success'
		data['method']  = 'sentrequest'
	else:
		data['status']	= 'failed'
		data['method']  = 'sentrequest'
	return demjson.encode(data)

@api.route('/req_status',methods=['get','post'])
def req_status():
	data={}
	riderid = request.args['riderid']
	# q = "select * from request,tbl_booking,req_aprvl,tbl_renter,tbl_vehicle,tbl_location where tbl_location.renter_id=tbl_renter.renter_id and  tbl_booking.user_id=request.user_id and request.req_id=tbl_booking.req_id AND request.req_id=req_aprvl.req_id and req_aprvl.renter_id=tbl_renter.renter_id and tbl_booking.vehicle_id=tbl_vehicle.vehicle_id and tbl_vehicle.renter_id=tbl_renter.renter_id  and request.user_id=(SELECT user_id FROM user WHERE loginid='%s')" % (riderid)
	q = "SELECT d.`driver_fname`,d.`driver_lname`,d.driver_phn,v.`vehicle_type`,v.`no_of_seats`,v.`vehicle_no`,l.`lattitude`,l.`longitude`,b.`book_id`,r.`status`,b.`amount`,apr_id FROM request r,tbl_booking b,req_aprvl,tbl_renter d,tbl_vehicle v,tbl_location l WHERE l.renter_id=d.renter_id AND  b.user_id=r.user_id AND r.req_id=b.req_id AND r.req_id=req_aprvl.req_id AND req_aprvl.renter_id=d.renter_id AND b.vehicle_id=v.vehicle_id AND v.renter_id=d.renter_id  AND r.user_id=(SELECT user_id FROM user WHERE loginid='%s') and req_aprvl.status='approved'" % (riderid)
	res = select(q)
	print(res)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'req_status'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'req_status'
	return  demjson.encode(data)

@api.route('/currentridetoshare',methods=['get','post'])
def currentridetoshare():
	data={}
	riderid = request.args['riderid']
	q = "SELECT * FROM request,req_aprvl,tbl_booking,user,tbl_vehicle WHERE user.user_id=request.user_id AND req_aprvl.req_id=request.req_id AND tbl_booking.req_id=request.req_id AND request.user_id=(SELECT user_id FROM user WHERE loginid='%s') AND request.status='picked'  AND  tbl_booking.vehicle_id=tbl_vehicle.vehicle_id AND req_aprvl.renter_id=tbl_vehicle.renter_id" % (riderid)
	print(q)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'currentridetoshare'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'currentridetoshare'
	return  demjson.encode(data)

@api.route('/sharetoothers',methods=['get','post'])
def sharetoothers():
	data={}
	reqid = request.args['reqid']
	riderid = request.args['riderid']
	
	q="update request set status='shared' where req_id='%s'" % (reqid)
	id=update(q)
	q="insert into shared_requests values(null,'%s',(SELECT user_id FROM user WHERE loginid='%s'),'active')"%(reqid,riderid)
	insert(q)
	if(id>0):
		data['status']  = 'success'
		data['method']  = 'sharetoothers'
	else:
		data['status']	= 'failed'
		data['method']  = 'sharetoothers'
	return demjson.encode(data)

@api.route('/view_shared_ride',methods=['get','post'])
def view_shared_ride():
	data={}
	riderid = request.args['riderid']
	lati = request.args['lati']
	longi = request.args['longi']

	q = "SELECT *,(3959 * ACOS ( COS ( RADIANS('%s') ) * COS( RADIANS( flatitude) ) * COS( RADIANS( flongitude ) - RADIANS('%s') ) + SIN ( RADIANS('%s') ) * SIN( RADIANS(flatitude ) ))) AS user_distance  FROM request,tbl_booking,user,tbl_vehicle WHERE  tbl_vehicle.vehicle_id=tbl_booking.vehicle_id AND tbl_booking.req_id=request.req_id AND tbl_booking.user_id=request.user_id AND user.user_id=request.user_id AND request.req_id=tbl_booking.req_id  AND request.status='shared' and request.user_id!=(SELECT user_id FROM user WHERE loginid='%s') HAVING user_distance<31.068 ORDER BY user_distance ASC" % (lati,longi,lati,riderid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'view_shared_ride'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'view_shared_ride'
	return  demjson.encode(data)

@api.route('/send_share_req',methods=['get','post'])
def send_share_req():
	data={}
	travelto = request.args['travelto']
	balseat = request.args['balseat']
	fromplace= request.args['fromplace']
	noof = request.args['noof']
	flati = request.args['flati']
	flongi = request.args['flongi']
	tlati = request.args['tlati']
	tlongi = request.args['tlongi']
	uid = request.args['uid']
	vehicleid= request.args['vehicleid']
	driverid= request.args['driverid']
	rqid=request.args['reqid']

	q="insert into request values (null,(SELECT user_id FROM user WHERE loginid='%s'),'%s','%s','%s','%s','%s',curdate(),curtime(),'share_request')" % (uid,flati,flongi,tlati,tlongi,noof)
	id=insert(q)
	q="insert into tbl_booking (user_id,vehicle_id,booking_from,booking_to,booking_date,booking_time,amount,booking_status,req_id)values((SELECT user_id FROM user WHERE loginid='%s'),'%s','%s','%s',curdate(),curtime(),'0','pending',(select max(req_id) from request))" % (uid,vehicleid,fromplace,travelto)
	id=insert(q)
	q="insert into req_aprvl(req_id,renter_id)values((select max(req_id) from request),'%s')" % (driverid)
	id=insert(q)
	q="update tbl_vehicle set availability='%s' where vehicle_id='%s'" % (noof,vehicleid)
	c=update(q)
	q="insert into shared_requests value(null,'%s',(SELECT user_id FROM user WHERE loginid='%s'),'active')"%(rqid,uid)
	insert(q)

	if(c>0):
		data['status']  = 'success'
		data['method']  = 'send_share_req'
	else:
		data['status']	= 'failed'
		data['method']  = 'send_share_req'
	return demjson.encode(data)

# -------------------------------------

@api.route('/actionshare',methods=['get','post'])
def actionshare():
	data={}
	req_id = request.args['req_id']
	status = request.args['status']
	total= request.args['total']
	if status=="accepted":
		q="UPDATE request SET status='%s' WHERE req_id='%s'" % (status,req_id)
		c=update(q)
		q="UPDATE tbl_booking SET amount='%s' WHERE req_id='%s'" % (total,req_id)
		c=update(q)
		q="update req_aprvl set status='approved', amount='%s' where req_id='%s'" % (total,req_id)
		c=update(q)
		if c>0:
			data['status']  = 'success'
			data['method']  = 'actionshare'
		else:
			data['status']  = 'failed'
			data['method']  = 'actionshare'
	else:
		q="update req_aprvl set status='%s' where  req_id='%s'" % (status,req_id)
		c=update(q)
		q="UPDATE request SET status='%s' WHERE req_id='%s'" % (status,req_id)
		c=update(q)
		if c>0:
			data['status']	= 'success'
			data['method']  = 'actionshare'
		else:
			data['status']  = 'failed'
			data['method']  = 'actionshare'
	return demjson.encode(data)

@api.route('/dropuser',methods=['get','post'])
def dropuser():
	data={}
	req_id = request.args['req_id']
	
	q="update request set status='droped' where req_id='%s'" % (req_id)
	c=update(q)
	q="update shared_requests set share_status='finnished' where req_id='%s'"%(req_id)
	update(q)
	q="update req_aprvl set status='drop' where req_id='%s'"%(req_id)
	update(q)
	if(c>0):
		data['status']  = 'success'
		data['method']  = 'dropuser'
	else:
		data['status']	= 'failed'
		data['method']  = 'dropuser'
	return demjson.encode(data)

@api.route('/picked_current_ride',methods=['get','post'])
def picked_current_ride():
	data={}
	renter_id = request.args['driver_id']
	q = "SELECT * FROM request,req_aprvl,tbl_booking,user WHERE user.user_id=request.user_id AND req_aprvl.req_id=request.req_id AND tbl_booking.req_id=request.req_id AND req_aprvl.renter_id=(select renter_id from tbl_renter where loginid='%s') AND (request.status='shared' OR request.status='picked' OR request.status='accepted')  ORDER BY request.req_id DESC" % (renter_id)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'picked_current_ride'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'picked_current_ride'
	return  demjson.encode(data)

@api.route('/updatelocation',methods=['get','post'])
def updatelocation():
	data={}
	latti = request.args['latti']
	longi = request.args['longi']
	uid = request.args['uid']
	q = "SELECT * FROM tbl_location WHERE renter_id=(select renter_id from tbl_renter where loginid='%s')" % (uid)
	res = select(q)
	if(len(res) > 0):
		q = "UPDATE tbl_location SET  lattitude='%s',longitude='%s',dates=curdate(),times=curtime() WHERE renter_id=(select renter_id from tbl_renter where loginid='%s')" % (latti,longi,uid)
		id = update(q)
		if id>0:
			data['status']  = 'success'
			data['method']  = 'updatelocation'
		else:
			data['status']  = 'failed'
			data['method']  = 'updatelocation'
	else:
		q = "INSERT INTO tbl_location(renter_id,lattitude,longitude,dates,times,availability)VALUES((select renter_id from tbl_renter where loginid='%s'),'%s','%s',curdate(),curtime(),'ok')" % (uid,latti,longi)
		id = insert(q)
		if id>0:
			data['status']  = 'success'
			data['method']  = 'updatelocation'
		else:
			data['status']  = 'failed'
			data['method']  = 'updatelocation'
	return  demjson.encode(data)

@api.route('/dlogin',methods=['get','post'])
def dlogin():
	data={}
	uname = request.args['uname']
	password = request.args['pass']
	q = "select l.loginid,l.login_type,d.r_status from tbl_login l,tbl_renter d where l.uname='%s' and l.password='%s' and l.login_type='renter' and l.loginid=d.loginid" % (uname,password)
	print(q)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'dlogin'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'dlogin'
	return  demjson.encode(data)



@api.route('/view_vehicle',methods=['get','post'])
def view_vehicle():
	data={}
	driverid = request.args['driverid']
	q = "SELECT * FROM tbl_vehicle WHERE renter_id=(select renter_id from tbl_renter where loginid='%s') and tbl_vehicle_status<>'not available'" % (driverid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'view_vehicle'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'view_vehicle'
	return  demjson.encode(data)



@api.route('/req_response',methods=['get','post'])
def req_response():
	data={}
	req_id = request.args['req_id']
	status = request.args['status']
	amount = request.args['amount']
	renter_id = request.args['driver_id']
	vehid = request.args['vehid']
	available_seats = request.args['available_seats']

	q="UPDATE request SET status='%s' WHERE req_id='%s'" % (status,req_id)
	c=update(q)
	q="UPDATE tbl_vehicle  SET availability='%s' WHERE renter_id=(select renter_id from tbl_renter where loginid='%s')" % (available_seats,renter_id)
	c=update(q)
	q="UPDATE tbl_booking SET vehicle_id='%s',amount='%s' WHERE req_id='%s'" % (vehid,amount,req_id)
	c=update(q)
	q="INSERT INTO req_aprvl VALUES(null,'%s',(select renter_id from tbl_renter where loginid='%s'),'%s','%s')" % (req_id,renter_id,amount,status)
	id=insert(q)

	if(id>0):
		data['status']  = 'success'
		data['method']  = 'req_response'
	else:
		data['status']	= 'failed'
		data['method']  = 'req_response'
	return demjson.encode(data)




@api.route('/view_payments',methods=['get','post'])
def view_payments():
	data={}
	logid = request.args['logid']
	# q = "SELECT req_aprvl.`amount` AS amt,request.*,tbl_booking.*,user.*,tbl_advancepay.* FROM req_aprvl,request,tbl_booking,user,tbl_advancepay WHERE tbl_advancepay.apr_id=tbl_booking.book_id and req_aprvl.req_id=request.req_id AND tbl_booking.user_id=user.user_id AND tbl_booking.req_id=request.req_id AND req_aprvl.renter_id=(select renter_id from tbl_renter where loginid='%s')" % (logid)
	q= "SELECT req_aprvl.`amount` AS amt,request.*,tbl_booking.*,user.* FROM req_aprvl,request,tbl_booking,USER WHERE req_aprvl.req_id=request.req_id AND tbl_booking.user_id=user.user_id AND tbl_booking.req_id=request.req_id AND req_aprvl.renter_id=(SELECT renter_id FROM tbl_renter WHERE loginid='%s')" % (logid)
	res = select(q)
	if(len(res) > 0):
		i=0
		for row in res:
			if row['status']=="shared" or row['status']=="accepted":
				tot = float(row['amt']) - ((float(row['amt'])) * 20 / 100)
				bal = round(((tot - ((tot * 10) / 100))),2)
				res[i]["amt"]=tot
				res[i]["balance"]=bal
				data['status']  = 'success'
				data['method']  = 'view_payments'
				data['data'] = res
			else :
				data['status']  = 'success'
				data['method']  = 'view_payments'
				data['data'] = res
			i=i+1
	else:
		data['status']	= 'failed'
		data['method']  = 'view_payments'
	return  demjson.encode(data)




@api.route('/current_ride',methods=['get','post'])
def current_ride():
	data={}
	renter_id = request.args['driver_id']
	q = "SELECT * FROM request,req_aprvl,tbl_booking,user WHERE user.user_id=request.user_id AND req_aprvl.req_id=request.req_id AND tbl_booking.req_id=request.req_id AND req_aprvl.renter_id=(select renter_id from tbl_renter where loginid='%s') AND (request.status='approved' or request.status='accepted') order by request.req_id desc" % (renter_id)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'current_ride'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'current_ride'
	return  demjson.encode(data)





@api.route('/pickuser',methods=['get','post'])
def pickuser():
	data={}
	req_id = request.args['req_id']
	
	q="update request set status='picked' where req_id='%s'" % (req_id)
	c=update(q)
	if(c>0):
		data['status']  = 'success'
		data['method']  = 'pickuser'
	else:
		data['status']	= 'failed'
		data['method']  = 'pickuser'
	return demjson.encode(data)




@api.route('/view_ratings',methods=['get','post'])
def view_ratings():
	data={}
	logid = request.args['logid']
	q = "SELECT * FROM request,req_aprvl,rating,user WHERE req_aprvl.req_id=request.req_id AND request.user_id=user.user_id AND req_aprvl.renter_id=(select renter_id from tbl_renter where loginid='%s') AND rating.req_id=req_aprvl.req_id" % (logid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'view_ratings'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'view_ratings'
	return  demjson.encode(data)





@api.route('/registration',methods=['get','post'])
def registration():
	data = {}	
	fname = request.form['fname']
	lname = request.form['lname']
	gender = request.form['gender']
	hname = request.form['hname']
	city = request.form['city']
	pincode=request.form['pincode']	
	email = request.form['email']
	dob=request.form['dob']	
	phone = request.form['phone']
	license = request.form['license']
	doj = request.form['doj']
	exp = request.form['exp']
	uname = request.form['uname']
	password = request.form['pass']
	print(dob)
	image=request.files['image']

	path='static/uploads/'+str(uuid.uuid4())+image.filename
	image.save(path)
	
	q="select * from tbl_renter inner join tbl_login using(loginid) where uname='%s' or driver_phn='%s' or driver_email='%s'"%(uname,phone,email)
	res=select (q)
	if res:
		data['status']='duplicate'
	else:
		toay=datetime.today()
		b=(toay.year)


		a=(dob[0:4])

		c=int(b)-int(a)
		print(c)
		
		if int(c)>18:
				
			q="insert into tbl_login values(null,'%s','%s','renter')"%(uname,password)
			res=insert(q)
			q="insert into tbl_renter values(null,'%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','%s','pending')"%(res,fname,lname,hname,city,pincode,gender,email,dob,phone,license,path,exp,doj)	
			id=insert(q)
			if id:
				data['data'] = id
				data['status'] = 'success'
			else:
				data['status'] = 'failed'
		else:
			data['status']='unverified'
	data['method']='registration'
	return demjson.encode(data)




@api.route('/d_view_route',methods=['get','post'])
def d_view_route():
	data={}
	drid = request.args['drid']
	q = "SELECT *  FROM request,tbl_booking,user,req_aprvl WHERE req_aprvl.req_id=request.req_id AND req_aprvl.renter_id=(select renter_id from tbl_renter where loginid='%s') AND tbl_booking.user_id=request.user_id AND user.user_id=request.user_id AND request.req_id=tbl_booking.req_id ORDER BY req_aprvl.req_id DESC " % (drid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'd_view_route'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'd_view_route'
	return  demjson.encode(data)






@api.route('/view_share_req',methods=['get','post'])
def view_share_req():
	data={}
	renter_id = request.args['driver_id']
	q = "SELECT tbl_vehicle.*,tbl_vehicle.`amount` AS amt,request.*,req_aprvl.*,tbl_booking.*,user.* FROM tbl_vehicle,request,req_aprvl,tbl_booking,user WHERE tbl_booking.vehicle_id=tbl_vehicle.vehicle_id AND user.user_id=request.user_id AND req_aprvl.req_id=request.req_id AND tbl_booking.req_id=request.req_id AND req_aprvl.renter_id=(select renter_id from tbl_renter where loginid='%s') AND request.status='share_request'  ORDER BY request.req_id DESC" % (renter_id)
	print(q)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'view_share_req'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'view_share_req'
	return  demjson.encode(data)





@api.route('/vehicle_reg',methods=['get','post'])
def vehicle_reg():
	data={}
	vname = request.args['vname']
	seats = request.args['seats']
	vno = request.args['vno']
	amount = request.args['amount']
	driverid = request.args['driverid']

	q="select * from tbl_vehicle where vehicle_no='%s'"%(vno)
	res=select(q)
	if res:
		data['status']='duplicate'
	else:

		q="INSERT INTO tbl_vehicle VALUES(null,(select renter_id from tbl_renter where loginid='%s'),'%s','%s','%s','%s','available','available')" % (driverid,vname,seats,vno,amount)
		id=insert(q)
		if(id>0):
			data['status']  = 'success'
			
		else:
			data['status']	= 'failed'
	data['method']  = 'vehicle_reg'
	return demjson.encode(data)





@api.route('/vehiclelist',methods=['get','post'])
def vehiclelist():
	data={}
	logid = request.args['logid']
	q = "SELECT * FROM tbl_vehicle WHERE renter_id=(select renter_id from tbl_renter where loginid='%s')  and tbl_vehicle_status<>'not available'" % (logid)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'vehiclelist'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'vehiclelist'
	return  demjson.encode(data)




@api.route('/notification',methods=['get','post'])
def notification():
	data={}
	lati = request.args['lati']
	longi = request.args['longi']
	logid = request.args['logid']

	q = "SELECT *,(3959 * ACOS ( COS ( RADIANS('%s') ) * COS( RADIANS( flatitude) ) * COS( RADIANS( flongitude ) - RADIANS('%s') ) + SIN ( RADIANS('%s') ) * SIN( RADIANS(flatitude ) ))) AS user_distance FROM request,tbl_booking,user WHERE tbl_booking.user_id=request.user_id AND user.user_id=request.user_id AND request.req_id=tbl_booking.req_id and tbl_booking.booking_status='pending' AND (request.status='pending' or request.status='rejected')  HAVING user_distance<31.068 ORDER BY user_distance ASC" % (lati,longi,lati)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['method']  = 'notification'
		data['data'] = res
	else:
		data['status']	= 'failed'
		data['method']  = 'notification'
	return  demjson.encode(data)




@api.route('/Driver_forgot_password',methods=['get','post'])
def Driver_forgot_password():
	data={}
	# data.update(request.args)
	uname = request.args['uname']
	email = request.args['email']
	phone = request.args['phone']

	q = "SELECT * FROM `tbl_login` INNER JOIN `tbl_renter` USING(`loginid`) WHERE `driver_email`='%s' AND `driver_phn`='%s' AND `uname`='%s' AND `login_type`='renter'"%(email,phone,uname)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)




@api.route('/Driver_set_new_password',methods=['get','post'])
def Driver_set_new_password():
	data={}
	loginid = request.args['loginid']
	password = request.args['password']
	c_password = request.args['c_password']

	if password==c_password:
		q="UPDATE `tbl_login` SET `password`='%s' WHERE `loginid`='%s'"%(c_password,loginid)
		update(q)
		data['status']  = 'success'
	else:
		data['status']	= 'failed'
	return demjson.encode(data)




@api.route('/Rider_forgot_password',methods=['get','post'])
def Rider_forgot_password():
	data={}
	# data.update(request.args)
	uname = request.args['uname']
	email = request.args['email']
	phone = request.args['phone']

	q = "SELECT * FROM `tbl_login` INNER JOIN `user` USING(`loginid`) WHERE `email`='%s' AND `phone`='%s' AND `uname`='%s' AND `login_type`='user'"%(email,phone,uname)
	res = select(q)
	if(len(res) > 0):
		data['status']  = 'success'
		data['data'] = res
	else:
		data['status']	= 'failed'
	return  demjson.encode(data)




@api.route('/Rider_set_new_password',methods=['get','post'])
def Rider_set_new_password():
	data={}
	loginid = request.args['loginid']
	password = request.args['password']
	c_password = request.args['c_password']

	if password==c_password:
		q="UPDATE `tbl_login` SET `password`='%s' WHERE `loginid`='%s'"%(c_password,loginid)
		update(q)
		data['status']  = 'success'
	else:
		data['status']	= 'failed'
	return demjson.encode(data)


@api.route('/viewcars')
def viewcars():
	data={}
	q="select * from o_vehicle where status='active'"
	print(q)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	data['smethod']='viewcars'
	return str(data)


@api.route('/bookcar')
def bookcar():
	data={}
	logid=request.args['logid']
	vid=request.args['vid']
	date=request.args['date']
	q="select * from vehicle_request where requested_date='%s' and status='Accept' and vehicle_id='%s'"%(date,vid)
	res=select(q)
	if res:
		data['status']="failed"
	else:
		q="insert into vehicle_request values(null,(select renter_id from tbl_renter where loginid='%s'),'%s',curdate(),'%s','pending')"%(logid,vid,date)
		print(q)
		insert(q)
		data['status']='success'
	return str(data)


@api.route('/Viewmycars')
def Viewmycars():
	data={}
	id=request.args['id']
	q="select *,vehicle_request.status as status from o_vehicle,vehicle_request where o_vehicle.vehicle_id=vehicle_request.vehicle_id AND renter_id=(select renter_id from tbl_renter where loginid='%s')"%(id)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='failed'
	data['smethod']='viewcars'
	return str(data)

@api.route('/viewpayment')
def viewpayment():
	data={}
	id=request.args['riderid']
	aprid=request.args['aprid']
	# q="SELECT * FROM payment,req_aprvl,request,tbl_renter,`user` WHERE payment.apr_id=req_aprvl.apr_id	AND req_aprvl.req_id=request.req_id AND req_aprvl.renter_id=tbl_renter.renter_id AND request.user_id=user.user_id AND payment.apr_id='%s'"%(aprid)
	q="SELECT payment.`amount` AS  amt,req_aprvl.*,request.*,tbl_renter.*,user.* FROM payment,req_aprvl,request,tbl_renter,`user` WHERE payment.apr_id=req_aprvl.apr_id	AND req_aprvl.req_id=request.req_id AND req_aprvl.renter_id=tbl_renter.renter_id AND request.user_id=user.user_id AND payment.apr_id ='%s'"%(aprid)
	res=select(q)
	q1="SELECT * FROM req_aprvl,request,tbl_renter,`user` WHERE req_aprvl.req_id=request.req_id AND req_aprvl.renter_id=tbl_renter.renter_id AND request.user_id=user.user_id AND req_aprvl.apr_id='%s'"%(aprid)
	res1=select(q1)
	if res:
		data['status']='success'
		data['data']=res
	elif res1:
		data['status']='ofpaid'
		data['data']=res1
	else:
		data['status']='failed'
	return str(data)

@api.route('/myshares')
def myshares():
	data={}
	riderid=request.args['riderid']
	req_id=request.args['req_id']
	# q="SELECT * FROM `shared_requests` INNER JOIN request USING(req_id) INNER JOIN `user` ON (shared_requests.shared_user_id=user.user_id) WHERE shared_requests.shared_user_id=(select user_id from user where loginid='%s') AND share_status='active'"%(riderid)
	q="SELECT * FROM `shared_requests` INNER JOIN request USING(req_id) INNER JOIN `user` ON (shared_requests.shared_user_id=user.user_id) WHERE shared_requests.shared_user_id<>(select user_id from user where loginid='%s')AND share_status='active'"%(riderid)
	res=select(q)
	if res:
		data['status']='success'
		data['data']=res
	else:
		data['status']='failed'
	return str(data)

@api.route('/addmoney')
def addmoney():
	data={}
	rider_id=request.args['riderid']
	money=request.args['money']
	q="select * from wallet where user_id=(select user_id from user where loginid='%s')"%(rider_id)
	res=select(q)
	if res:
		q="update wallet set amount=amount+'%s' where user_id=(select user_id from user where loginid='%s')"%(money,rider_id)
		update(q)
		data['status']='success'
		data['method']='addmoney'
	else:

		q="insert into wallet values(null,(select user_id from user where loginid='%s'),'%s',curdate(),'active')"%(rider_id,money)
		res=insert(q)
		data['status']='success'
		data['method']='addmoney'
	return str(data)


@api.route('/viewmoney')
def viewmoney():
	data={}
	id=request.args['riderid']
	q="select * from wallet where user_id=(select user_id from user where loginid='%s')"%(id)
	res=select(q)
	if res:
		data['money']=res[0]['amount']
		data['status']='success'
	else:
		data['money']='0'
		data['status']='failed'
	data['method']='viewmoney'
	return str(data)



@api.route('/fullpay',methods=['get','post'])
def fullpay():
	data={}
	amt = request.args['amt']
	lid = request.args['loginid']
	bookid = request.args['bookid']
	total = request.args['total']
	aprid=request.args['aprid']
	a=float(amt)
	t=float(total)
	p=0.01*a

	q="SELECT * FROM payment INNER JOIN `req_aprvl` USING(apr_id) INNER JOIN `request` USING(req_id) INNER JOIN `tbl_booking`  USING(req_id) WHERE tbl_booking.book_id='%s'"%(bookid)
	res1=select(q)
	if res1:
		data['status']='paid'
	else:
		q="select * from wallet where user_id=(select user_id from user where loginid='%s')"%(lid)
		res=select(q)
		if res:
			q="update wallet set amount=amount+'%s' where user_id=(select user_id from user where loginid='%s')"%(int(p),lid)
			update(q)
		else:
			q="insert into wallet values(null,(select user_id from user where loginid='%s'),'%s',curdate(),'active')"%(lid,int(p))
			insert(q)

		
		
		q="update tbl_booking set amount='%s' where book_id='%s'"% (t,bookid)
		update(q)
		q="INSERT INTO payment VALUES(null,'%s','%s',now())"% (aprid,amt)
		print(q)
		id=insert(q)
		if(id>0):
			data['status']  = 'success'
		else:
			data['status']	= 'failed'
	print(data['status'])
	data['method']  ='advance'
	return demjson.encode(data)



@api.route('/advancew',methods=['get','post'])
def advancew():
	data={}
	amt = request.args['amt']
	bookid = request.args['bookid']
	total = request.args['total']
	uid=request.args['loginid']
	a=float(amt)
	t=float(total)
	if a<t:
		c=t-a
		q="update tbl_booking set amount='%s' where book_id='%s'"% (c,bookid)
		update(q)
		q="INSERT INTO tbl_advancepay(apr_id,amount,balance,date,time)VALUES('%s','%s','%s',curdate(),curtime())"% (bookid,amt,c)
		id=insert(q)
		q="select * from wallet where user_id=(select user_id from user where loginid='%s')"%(uid)
		res=select(q)
		if res:
			cmoney=res[0]['amount']
			if a>float(cmoney):
				data['status']='failed'
				data['method']  ='advance'
			else:
				q="update wallet set amount=amount-'%s' where user_id=(select user_id from user where loginid='%s')"%(a,uid)
				update(q)
				data['status']  = 'success'
				data['method']  ='advance'
		if(id>0):
			data['status']  = 'success'
			data['method']  ='advance'
		else:
			data['status']	= 'failed'
			data['method']  ='advance'
	return demjson.encode(data)



@api.route('/fullpayw',methods=['get','post'])
def fullpayw():
	data={}
	amt = request.args['amt']
	bookid = request.args['bookid']
	total = request.args['total']
	uid=request.args['loginid']
	aprid=request.args['aprid']
	a=float(amt)
	
	# print("tfdsaghghgsfhdvbsajkdbfgvaksldbfkdjfgvsdkjbfgsdkjbvkjsfkajsdfk"+p);
	t=float(total)
	
	
	q="select * from wallet where user_id=(select user_id from user where loginid='%s')"%(uid)
	res=select(q)
	if res:
		cmoney=res[0]['amount']
		if float(cmoney)<20:
			data['status']='failed'
			# data['method']  ='advance'
		elif float(cmoney)>t:
			balence1=float(cmoney)-t
			q="update tbl_booking set amount='%s' where book_id='%s'"% (t,bookid)
			update(q)
		
			q="INSERT INTO payment VALUES(null,'%s','%s',now())"% (aprid,t)
			print(q)

			q="update wallet set amount=amount-'%s' where user_id=(select user_id from user where loginid='%s')"%(t,uid)
			update(q)
			data['status']='success'
		else:
			balence=t-float(cmoney)
			q="update tbl_booking set amount='%s' where book_id='%s'"% (balence,bookid)
			update(q)
			q="INSERT INTO payment VALUES(null,'%s','%s',now())"% (aprid,balence)
			print(q)
			id=insert(q)
			
			q="update wallet set amount='0' where user_id=(select user_id from user where loginid='%s')"%(uid)
			update(q)
			data['status']='success'
	
	return demjson.encode(data)


@api.route('/emergency')
def emergency():
	data={}
	flati=request.args['flati']
	flongi=request.args['flongi']
	uid=request.args['uid']
	q="insert into emergency values(null,(select user_id from user where loginid='%s'),'%s','%s')"%(uid,flati,flongi)
	insert(q)
	data['status']='sent'
	return str(data)

@api.route('/rentpayment')
def rentpayment():
	data={}
	amt=request.args['amt']
	vreqid=request.args['bookid']
	vid=request.args['vid']
	total=request.args['total']
	rid=request.args['loginid']

	q="insert into rentpayment values(null,'%s','%s',now())"%(vreqid,amt)
	insert(q)
	# q="select * from o_vehicle where vehicle_id='%s'"%(vid)
	# res=select(q)
	# type=res[0]['vehicle']
	# seat=res[0]['seat_no']
	q="insert into tbl_vehicle values(null,(select renter_id from tbl_renter where loginid='%s'),(select vehicle from o_vehicle where vehicle_id='%s'),(select seat_no from o_vehicle where vehicle_id='%s'),(select reg_no from o_vehicle where vehicle_id='%s'),50,'available','available')"%(rid,vid,vid,vid)
	insert(q)
	q="update o_vehicle set status='rented' where vehicle_id='%s' and status='active'"%(vid)
	print(q)
	update(q)
	q="update vehicle_request set status='rented' where vehicle_id='%s' and status='accept'"%(vid)
	update(q)
	data['status']='success'
	return str(data)




@api.route('/returnvehicle')
def returnvehicle():
	data={}
	
	id=request.args['id']
	vid=request.args['vid']
	
	q="update tbl_vehicle set tbl_vehicle_status='not available' where vehicle_no=(select reg_no from o_vehicle where vehicle_id='%s') and renter_id=(select renter_id from tbl_renter where loginid='%s')"%(vid,id)
	print(q)
	update(q)
	q="update o_vehicle set status='active' where vehicle_id='%s'"%(vid)
	update(q)
	q="update vehicle_request set status='Returned' where vehicle_id='%s'"%(vid)
	update(q)
	data['status']='return'
	return str(data)



@api.route('/Viewemergency')
def Viewemergency():
	data={}
	lati=request.args['lati']
	longi=request.args['longi']
	q="SELECT *,(3959 * ACOS ( COS ( RADIANS('%s') ) * COS( RADIANS( latitude) ) * COS( RADIANS( longitude ) - RADIANS('%s') ) + SIN ( RADIANS('%s') ) * SIN( RADIANS(latitude ) )))  FROM emergency,user WHERE emergency.user_id=user.user_id" % (lati,longi,lati)
	res=select(q)
	if res:
		data['status']="success"
		data['data']=res
	else:
		data['status']="failed"
	return str(data)


@api.route('/respond')
def respond():
	data={}
	id=request.args['id']
	q="delete from emergency where emergency_id='%s'"%(id)
	print(q)
	delete(q)
	data['status']='success'
	return str(data)