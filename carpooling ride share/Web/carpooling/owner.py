from flask import *
from database import *
import uuid
import demjson

owner=Blueprint('owner',__name__)

@owner.route('/ownerhome')
def ownerhome():
    data={}

    return render_template('owner_home.html')


@owner.route('/managevehicles',methods=['get','post'])
def managevehicles():
    data={}
    q="select * from o_vehicle where owner_id='%s'"%(session['owid'])
    data['myv']=select(q)
    
     
    if 'add' in request.form:
        vehicle=request.form['vehicle']
        i=request.files['img']
        img="static/vehicle/"+str(uuid.uuid4())+i.filename
        i.save(img)
        det=request.form['det']
        regno=request.form['regno']
        rate=request.form['rate']
        seat=request.form['seat']  
        q1="select * from o_vehicle where reg_no='%s'"%(regno)
        res=select(q1)  
        if res:
            flash("Register number already exist.....")
            return redirect(url_for('owner.managevehicles'))
        q="insert into o_vehicle values(null,'%s','%s','%s','%s','%s','%s','active','%s')"%(session['owid'],vehicle,img,det,regno,rate,seat)
        insert(q)
        flash("vehicle added successfully....")
        return redirect(url_for('owner.managevehicles'))  

    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    else:
        action=None
    if action=='upd':
        q="select * from o_vehicle where vehicle_id='%s'"%(id)
        data['upd']=select(q)

    if action=='delete':
        q="delete  from o_vehicle where vehicle_id='%s'"%(id)
        delete(q)
        flash("vehicle Removed Successfully....")
        return redirect(url_for('owner.managevehicles'))  

    if 'update' in request.form:
        
        vehicle=request.form['vehicle']
        i=request.files['img']
        # img="C:\\Ananthu anil\\py+android 2022\\kmm\\carpooling ride share\\Web\\carpooling\\static\\vehicle\\"+str(uuid.uuid4())+i.filename
        # i.save(img)
        img="static/vehicle/"+str(uuid.uuid4())+i.filename
        i.save(img)
        det=request.form['det']
        regno=request.form['regno']
        rate=request.form['rate']
        seat=request.form['seat']
        

        q="update o_vehicle set vehicle='%s',image='%s',details='%s',reg_no='%s',rate='%s',seat_no='%s' where vehicle_id='%s'"%(vehicle,img,det,regno,rate,seat,id)
        update(q)
        flash("vehicle updated successfully....")
        return redirect(url_for('owner.managevehicles')) 
    return render_template('owner_manage_vehicles.html',data=data)



@owner.route('/viewrequests',methods=['get','post'])
def viewrequests():
    data={}
    q="SELECT *,vehicle_request.status as status FROM `vehicle_request` INNER JOIN `tbl_renter` USING(renter_id) INNER JOIN `o_vehicle` USING(vehicle_id) WHERE owner_id='%s'"%(session['owid'])
    data['req']=select(q)


    if 'action' in request.args:
        action=request.args['action']
        id=request.args['id']
    else:
        action=None
    if action=='accept':
        q="update vehicle_request set status='Accept' where v_request_id='%s'"%(id)
        update(q)
        flash("succeefull..........")
        return redirect(url_for('owner.viewrequests'))

    if action=='reject':
        q="update vehicle_request set status='REject' where v_request_id='%s'"%(id)
        update(q)
        flash("Request  Deleted.......")
        return redirect(url_for('owner.viewrequests'))


    return render_template("owner_view_request.html",data=data)


@owner.route('/viewpayments')
def viewpayments():
    data={}
    q="SELECT * FROM rentpayment r,vehicle_request v,tbl_renter tr,o_vehicle ov WHERE r.v_request_id=v.v_request_id AND v.renter_id=tr.renter_id AND v.vehicle_id=ov.vehicle_id AND owner_id='%s'"%(session['owid'])
    data['myv']=select(q)
    return render_template("view_past_payments.html",data=data)

