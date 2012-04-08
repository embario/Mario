from django.template import Context, loader, RequestContext
from django.core.context_processors import csrf
from django.http import HttpResponse
from django.http import HttpResponseRedirect
from django.contrib.auth import authenticate, login, logout
from django.contrib.auth.decorators import login_required
from django.contrib.auth.models import User
from mysite.answers.models import Question, Answer, Comment, Tag


# Create your views here.
def HomeHandler(request):
        args = dict()
	t = loader.get_template('index.html')
	c = RequestContext (request, args)
	return HttpResponse(t.render(c))

#Create a form to submit a new Question
@login_required
def QuestionFormHandler(request):
	t = loader.get_template('question-form.html')
	c = RequestContext(request)
	return HttpResponse(t.render(c))

#Show the Answer Form for Question identified by primary key qid
@login_required
def AnswerFormHandler(request):
	params= request.GET
	qid = params["qid"]
	args = dict (qid = qid)
	t = loader.get_template('answer-form.html')
	c = RequestContext(request,args)
	return HttpResponse(t.render(c))

#Show the Comment Form for Question identified by primary key qid
@login_required
def CommentFormHandler(request):
	params= request.GET
	qid = params["qid"]
	args = dict (qid = qid)
	t = loader.get_template('comment-form.html')
	c = RequestContext(request,args)
	return HttpResponse(t.render(c))

def SortCategory (request):
	params = request.GET
	args = {}

	# Implement Sort Ascending and Descending!

	sortTerm = params ['sort']
	print sortTerm
	questions = Question.objects.order_by('-' + sortTerm)[:10]

	args['printdata'] = questions

#Query the database to list latest 10 submitted questions
def ListQuestionHandler(request):
	
	params = request.GET
	args = {}

	#If we want to sort!
	if "sort" in params:
		print "SORTING!"
	
	#If there was a search query...
	if "search" in params:
		searchTerm = params['search']
		
		#TODO: Work on this query!
		questions = Question.objects.filter(question__icontains = searchTerm)

		#Render the Search Term when displaying the HTML (for User's reference).
		args ['searchTerm'] = searchTerm
		
	#Normal Case: Load up 10 Questions	
	else:
		questions = Question.objects.order_by('-posted')[:10]
		for q in questions:
			questionTags = Tag.objects.filter(qid = q)
			q.tags = questionTags

	#Bring in the Result Questions (and Search Term, if there was one)!
	args['questions'] = questions
	
	t = loader.get_template('list-template.html')
	c = RequestContext(request,args)
	return HttpResponse(t.render(c))

#List Answers and Comments to the Question identified by primary key qid
def ShowQuestionHandler(request):
	params= request.GET
	print params
	args = {}

	#If we found a submit parameter, display the success message!
	if "submit" in params:
		print params["submit"]
		args ["msg"] = "Answer Successfully Submitted!"
		
	# Get the Question ID
	qid = params["qid"]
	# Get the Answer(s)
	answers = Answer.objects.select_related().filter(qid = qid)
	# Get the Comment(s)
	comments = Comment.objects.select_related().filter(qid = qid)
	# Get the Question
	question = Question.objects.get(qid=qid)

	args ["printAnswers"] = answers
	args ["printComments"] = comments
	args ["printQuestion"] = question
	args ["qid"] = qid
	t = loader.get_template('show_question.html')
	c = RequestContext(request, args)
	return HttpResponse(t.render(c))

#Process the question form and save the question in database
@login_required
def SubmitQuestionHandler(request):
	params = request.POST

	print request.user.id
	print request.user.username
	
	ques = Question(userid=request.user.id, username=request.user.username, question=(params["question"]))
	ques.save()
	if "tags" in params:
		t = params["tags"]
		tags = t.split(',')
		print tags
		for thisTag in tags:
			thisTag = Tag(qid=ques, tag=thisTag)
			thisTag.save()

	t = loader.get_template('post-submit.html')
	c = RequestContext(request)
	return HttpResponse(t.render(c))

#Process the Answer Form and save it in the database
@login_required
def SubmitAnswerHandler (request):
	postParams = request.POST
	getParams = request.GET
	print postParams
	print getParams

	# Get the Question ID
	questionID = getParams["qid"]
	print questionID

	# Get the Question
	q = Question.objects.get(qid = questionID)
	print q

	# Create the Answer and Save it in the DB
	ans = Answer(qid = q, answer = postParams ["answer"], userid = request.user.id, username = request.user.username)
	ans.save()
	
	args = dict(msg = "Answer Successfully Submitted!", qid = questionID)
	return	HttpResponseRedirect("showQuestion?qid=" + args["qid"] + "&submit=true")

#Process the Comment Form and save it in the database
@login_required
def SubmitCommentHandler (request):
	postParams = request.POST
	getParams = request.GET
	print postParams
	print getParams

	# Get the Question ID
	questionID = getParams["qid"]
	print questionID

	# Get the Question
	q = Question.objects.get(qid = questionID)
	print q

	# Create the Comment and Save it in the DB
	com = Comment(qid = q, comment = postParams ["comment"], userid = request.user.id, username = request.user.username)
	com.save()

	print "HELLLLLLLOOOOOOOOOOOOOOOOOOO"
	args = dict(msg = "Comment Successfully Submitted!", qid = questionID)
	return	HttpResponseRedirect("showQuestion?qid=" + args["qid"] + "&submit=true")


#Show the Login Page
def ShowLoginPage (request):
	print "Showing Login Page"
	t = loader.get_template('login.html')
	c = RequestContext(request)
	return HttpResponse(t.render(c))

#Show the Registration Page
def ShowRegistrationPage (request):
	print "Showing Registration Page"
	t = loader.get_template('register.html')
	c = RequestContext(request)
	return HttpResponse(t.render(c))

#Login
def LoginUser (request):
	print "Logging in..."
	params = request.POST
	args = {}
	password = params ["pwd"]
	username = params ['uname']
	user = authenticate(username=username, password=password)
	if user is not None:
		if user.is_active:
			login(request, user)
			args ["msg"] = "Successfully signed on as: " + username
			return ListQuestionHandler(request)
		else :
			args ["msg"] = "Your Account is Disabled. Please Re-Register!"
			t = loader.get_template ('index.html')
	else:
		args ["msg"] = "Invalid Login. Please Check your Username or Password."
		t = loader.get_template ('login.html')

	print args
	c = RequestContext (request, args)
	return HttpResponse (t.render(c))

#Logout
@login_required
def LogoutUser (request):
	print "LOGGING OUT"
	args = {}
	logout(request)
	t = loader.get_template ('index.html')
	args ["msg"] = "Successfully Signed Out."
	c = RequestContext (request, args)
	return HttpResponse (t.render(c))

	
#Register
def RegisterUser (request):
	print "Registering..."
	params = request.POST
	args = {}
	username = params ["uname"]
	password = params ["pwd"]
	password2 = params ["pwd2"]
	email = params ["email"]
	firstname = params ["fname"]
	lastname = params ["lname"]

	#If the passwords do not match, have the user try again!
	if password != password2:
		t = loader.get_template ('register.html')
		args = {}
		args ["msg"] = "Passwords Do Not Match. Please Try Again."
		c = RequestContext(request, args)
		return HttpResponse (t.render(c))

	user = User.objects.create_user(username, email, password)
	user.first_name = firstname
	user.last_name = lastname
	user.save()

	#Registration was successful. Let the User know.
	args ["msg"] = "Successfully Registered. You can now login as " + username
	t = loader.get_template ('index.html')
	c = RequestContext (request, args)
	return HttpResponse (t.render(c))

#Show Profile Page for User
@login_required
def ShowProfilePage (request):
	print "Showing Profile Page..."
	args = {}
	args ["userQuestions"] = Question.objects.filter (userid = request.user.id)

	#If there is new Information to store...
	if "newInfo" in request.POST:
		params = request.POST
		firstname = params ["fname"]
		lastname = params ["lname"]
		email = params ["email"]
		username = params ["uname"]
		
		#Get the User and Update the respective fields
		user = request.user
		user.username = username
		user.first_name = firstname
		user.last_name = lastname
		user.email = email
		user.save()
		args ["msg"] = "Information Successfully Updated."
		
	#If The User Wants to Edit Profile Information...
	if "editInfo" in request.GET:
		print request.GET ["editInfo"]
		args ["editInfo"] = request.GET ["editInfo"]
	else:
		args ["editInfo"] = False
		
	print args ["userQuestions"]
	t = loader.get_template ('user-profile.html')
	c = RequestContext (request, args)
	return HttpResponse (t.render(c))
