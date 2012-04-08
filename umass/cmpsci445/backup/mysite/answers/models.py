from django.db import models as db

# Create your models here.
class Question(db.Model):
    userid = db.CharField(max_length=50)
    posted = db.DateTimeField(auto_now=True)
    question = db.TextField(max_length=100)
    qid = db.AutoField(primary_key=True)

class Answer (db.Model):
    userid = db.CharField(max_length = 50)
    posted = db.DateTimeField (auto_now = True)
    answer = db.TextField (max_length = 200)
    qid = db.ForeignKey('Question')
    aid = db.AutoField (primary_key = True)

class Tag (db.Model):
    tag = db.CharField (max_length = 50)
    question = db.ForeignKey('Question')
    tid = db.AutoField (primary_key = True)
