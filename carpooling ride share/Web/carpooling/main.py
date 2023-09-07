
from flask import Flask
from owner import owner
from public import public
from admin import admin
from api import api

app=Flask(__name__)
app.secret_key="secretkey"
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(api,url_prefix='/api')
app.register_blueprint(owner,url_prefix='/owner')

app.run(debug=True,host="0.0.0.0",port=5020)



