from django.conf.urls.defaults import *

# Uncomment the next two lines to enable the admin:
# from django.contrib import admin
# admin.autodiscover()

urlpatterns = patterns('',
    # Example:
    # (r'^mysite/', include('mysite.foo.urls')),

    # Uncomment the admin/doc line below and add 'django.contrib.admindocs' 
    # to INSTALLED_APPS to enable admin documentation:
    # (r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    # (r'^admin/', include(admin.site.urls)),
    (r"^$", 'mysite.answers.views.HomeHandler'),
    (r"^questionform", 'mysite.answers.views.QuestionFormHandler'),
    (r"^answerform", 'mysite.answers.views.AnswerFormHandler'),
    (r"^submitquestion", 'mysite.answers.views.SubmitQuestionHandler'),
    (r"^listquestion", 'mysite.answers.views.ListQuestionHandler'),
    (r"^sortCategory", 'mysite.answers.views.sortCategory'),
    (r"^showanswers", 'mysite.answers.views.ShowAnswersHandler'),
    (r"^submitanswer", 'mysite.answers.views.SubmitAnswerHandler'),
    #(r"^login", 'mysite.fbgrab.views.LoginHandler'),
    #(r"^logout", 'mysite.fbgrab.views.LogoutHandler'),
    #(r"^friends", 'mysite.fbgrab.views.FriendHandler'),

)
