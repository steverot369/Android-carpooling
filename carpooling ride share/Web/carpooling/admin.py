from flask import *
from database import *
import uuid
import demjson

admin=Blueprint('admin',__name__)
@admin.route('/adminhome',methods=['post','get'])
def adminhome():
	return render_template('adminhome.html')

@admin.route('/approve_driver',methods=['get','post'])
def approve_driver():
	data={}
	if "action" in request.args:
		action=request.args['action']
		id=request.args['id']
	else:
		action="none"
	if action=="approve":
		q="update tbl_renter set r_status='Active' where renter_id='%s'"%(id)
		update(q)
		flash('Approved successfully !')
		return redirect(url_for('admin.approve_driver'))
	if action=="reject":
		q="update tbl_renter set r_status='Reject' where renter_id='%s'"%(id)
		update(q)
		flash('Rejected successfully !')
		return redirect(url_for('admin.approve_driver'))
	q="select * from tbl_renter"
	res=select(q)
	data['driver']=res
	return render_template("admin_approve_driver.html",data=data)





@admin.route('/view_pendingbookings',methods=['post','get'])
def view_pendingbookings():
	data={}
	q="SELECT r.*,b.*,v.vehicle_no,v.vehicle_type,re.`status` FROM user r,tbl_booking b, tbl_vehicle v,request re WHERE b.user_id = r.user_id AND b.vehicle_id = v.vehicle_id AND r.`user_id`=re.`user_id` AND re.`status`!='pending'"
	res=select(q)
	data['view_pendingbookings']=res
	return render_template("admin_view_pendingbookings.html",data=data)




# @admin.route('/view_feedbacks',methods=['post','get'])
# def view_feedbacks():
# 	data={}
# 	q="select c.*,r.first_name,r.last_name from tbl_feedback c,user r where c.user_id=r.user_id"
# 	res=select(q)
# 	data['view_feedbacks']=res
# 	return render_template("admin_viewfeedback.html",data=data)	




@admin.route('/view_complaints',methods=['post','get'])
def view_complaints():
	data={}
	if "action" in request.args:
		action=request.args['action']
		id=request.args['comid']
	else:
		action="none"
	if action=="sendreply":
		data['sendreply']="sendreply"
	if "sendreply" in request.form:
		reply=request.form['reply']
		q="update tbl_complaint set reply='%s' where complaint_id='%s'"%(reply,id)
		update(q)
		flash('Updated successfully !!!')
		return redirect(url_for('admin.view_complaints'))
	q="select * from tbl_complaint inner join user using(user_id)"
	res=select(q)
	data['view_complaints']=res
	return render_template("admin_view_complaints.html",data=data)	
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
@admin.route('/logout')
def logout():
	session.clear()
	

	return redirect(url_for('public.publichome'))


@admin.route('/viewowner',methods=['post','get'])
def viewowner():
	data={}
	q="select * from owner "
	res=select(q)
	data['viewowner']=res
	return render_template("admin_view_registered_owners.html",data=data)



@admin.route('/viewuser',methods=['post','get'])
def viewuser():
	data={}
	q="select * from user "
	res=select(q)
	data['viewuser']=res
	return render_template("admin_view_registered_users.html",data=data)	




@admin.route('/view_feedbacks',methods=['post','get'])
def view_feedbacks():
	data={}
	q="SELECT * FROM tbl_feedback INNER JOIN USER USING(user_id) "
	res=select(q)
	data['viewfeed']=res
	return render_template("admin_view_feedbacks.html",data=data)	




@admin.route('/viewem',methods=['post','get'])
def viewem():
	data={}
	q="select * from emergency inner join user using(user_id) "
	res=select(q)
	data['viewem']=res
	return render_template("admin_view_emergency.html",data=data)	