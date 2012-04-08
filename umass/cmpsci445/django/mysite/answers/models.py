from django.db import models as db

# Create your models here.
class Question(db.Model):
    qid = db.AutoField(primary_key = True)
    userid = db.CharField(max_length = 10)
    username = db.CharField (max_length = 20)
    posted = db.DateTimeField(auto_now = True)
    question = db.TextField(max_length = 100)
    
    def __unicode__ (self):
        return self.question

class Answer (db.Model):
    aid = db.AutoField (primary_key = True)
    qid = db.ForeignKey('Question')
    answer = db.TextField (max_length = 300)
    userid = db.CharField(max_length = 10)
    username = db.CharField (max_length = 20)
    posted = db.DateTimeField (auto_now = True)

    def __unicode__ (self):
        return self.answer

class Comment (db.Model):
    cid = db.AutoField (primary_key = True)
    qid = db.ForeignKey('Question')
    comment = db.TextField (max_length = 200)
    userid = db.CharField (max_length = 10)
    username = db.CharField (max_length = 20)
    posted = db.DateTimeField (auto_now = True)

    def __unicode__ (self):
        return self.comment
    
class Tag (db.Model):
    tid = db.AutoField (primary_key = True)
    qid = db.ForeignKey('Question')
    tag = db.CharField (max_length = 50)
    userid = db.CharField (max_length = 10)
    username = db.CharField (max_length = 20)
    posted = db.DateTimeField (auto_now = True)    


    def __unicode__ (self):
        return self.tag


