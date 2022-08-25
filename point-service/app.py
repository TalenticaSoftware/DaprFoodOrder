import flask
from flask import request, jsonify
from flask_cors import CORS
import sys
import os

appPort = os.getenv("APP_PORT","8084")
points = 0

app = flask.Flask(__name__)
CORS(app)

@app.route('/points', methods=['GET'])
def helloworld():
     data = {"points": points}
     return jsonify(data)

@app.route('/e/order-created', methods=['POST'])
def ordercreated():
     print(request.data)
     global points
     points += 1;
     return ""

@app.route('/e/order-cancelled', methods=['POST'])
def ordercancelled():
     print(request.data)
     global points
     points -= 1;
     return ""

app.run(host="0.0.0.0",port=appPort, debug=True)
