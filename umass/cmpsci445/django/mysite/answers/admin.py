from answers.models import Question, Answer, Comment, Tag
from django.contrib import admin

admin.site.register (Answer)
admin.site.register (Question)
admin.site.register (Comment)
admin.site.register (Tag)
