from django.conf.urls.defaults import *

# Uncomment the next two lines to enable the admin:
from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
    # Example:
    # (r'^mysite/', include('mysite.foo.urls')),

    # Uncomment the admin/doc line below and add 'django.contrib.admindocs' 
    # to INSTALLED_APPS to enable admin documentation:
    (r'^admin/doc/', include('django.contrib.admindocs.urls')),

    # Uncomment the next line to enable the admin:
    (r'^admin/', include(admin.site.urls)),
    (r"^$", 'mysite.answers.views.HomeHandler'),
    (r"^questionForm", 'mysite.answers.views.QuestionFormHandler'),
    (r"^answerForm", 'mysite.answers.views.AnswerFormHandler'),
    (r"^commentForm", 'mysite.answers.views.CommentFormHandler'),
                       
    (r"^submitQuestion", 'mysite.answers.views.SubmitQuestionHandler'),
    (r"^submitAnswer", 'mysite.answers.views.SubmitAnswerHandler'),
    (r"^submitComment", 'mysite.answers.views.SubmitCommentHandler'),

    (r"^listQuestions", 'mysite.answers.views.ListQuestionHandler'),                       
    (r"^showQuestion", 'mysite.answers.views.ShowQuestionHandler'),                       
    (r"^showLoginPage", 'mysite.answers.views.ShowLoginPage'),
    (r"^login", 'mysite.answers.views.LoginUser'),
    (r"^logout", 'mysite.answers.views.LogoutUser'),                       
    (r"^showRegistrationPage", 'mysite.answers.views.ShowRegistrationPage'),
    (r"^register", 'mysite.answers.views.RegisterUser'),
    (r"^showProfilePage", 'mysite.answers.views.ShowProfilePage'),
    (r'^accounts/login/$', 'django.contrib.auth.views.login'),                    
    #(r"^login", 'mysite.fbgrab.views.LoginHandler'),
    #(r"^logout", 'mysite.fbgrab.views.LogoutHandler'),
    #(r"^friends", 'mysite.fbgrab.views.FriendHandler'),

)
