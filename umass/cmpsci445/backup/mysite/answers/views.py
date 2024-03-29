from django.template import Context, loader, RequestContext
from django.core.context_processors import csrf
from django.http import HttpResponse
from django.http import HttpResponseRedirect
from mysite.answers.models import Question
from mysite.answers.models import Answer

# Create your views here.
def HomeHandler(request):
        args = dict()
	t = loader.get_template('index.html')
	c = Context({})
	return HttpResponse(t.render(c))

#Create a form to submit a new Question
def QuestionFormHandler(request):
	t = loader.get_template('question-form.html')
	c = RequestContext(request,{})
	return HttpResponse(t.render(c))

#Process the question form and save the question in database
def SubmitQuestionHandler(request):
	params = {}
	if request.method=='GET':
		params = request.GET
	elif request.method=='POST':
		params = request.POST
	
	ques = Question(userid=str(params["user"]),question=(params["question"]))
	ques.save()
        
	t = loader.get_template('post-submit.html')
	c = Context({})
	return HttpResponse(t.render(c))

def sortCategory (request):

	params = request.GET
	args = {}

	# Implement Sort Ascending and Descending!
	print params ['type']

	sortTerm = params ['sort']
	print sortTerm
	questions = Question.objects.order_by('-' + sortTerm)[:10]

	args['printdata'] = questions
	t = loader.get_template('list-template.html')
	c = RequestContext(request, args)
	return HttpResponse(t.render(c))

#Query the database to list latest 10 submitted questions
def ListQuestionHandler(request):
	
	params = request.GET
	args = {}
	
	#If there was a search query...
	if request.method == 'GET' and len(params) > 0:
		searchTerm = params['search']
		questions = Question.objects.filter(question = searchTerm)
		args ['numQuestions'] = len (questions)
		print "LENGTH oF QUESTIONS"
		print len (questions)
	else:
		questions = Question.objects.order_by('-posted')[:10]

	#Bring in the Result Questions!
	args['printdata'] = questions
	
	t = loader.get_template('list-template.html')
	c = RequestContext(request,args)
	return HttpResponse(t.render(c))

#List answers to the question identified by primary key qid
def ShowAnswersHandler(request):
	params= request.GET
	qid = params["qid"]

	answers = Answer.objects.select_related().filter(qid = qid)
	#answers = Answer.objects.select_related().get(qid = qid)
	print answers
	question = Question.objects.get(qid=qid)
	args = dict(printAnswers = answers, printQuestion = question, qid=qid)
	t = loader.get_template('show_answers.html')
	c = RequestContext(request, args)
	return HttpResponse(t.render(c))

#Create an Answer Form for Question identified by primary key qid
def AnswerFormHandler(request):
	params= request.GET
	qid = params["qid"]
	args = dict (qid = qid)
	t = loader.get_template('answer-form.html')
	c = RequestContext(request,args)
	return HttpResponse(t.render(c))

#Process the Answer Form and save it in the database
def SubmitAnswerHandler (request):

	params = {}
	if request.method == 'GET':
		params = request.GET
	elif request.method == 'POST':
		params = request.POST

	questionID = params ["qid"]
	question = Question.objects.get(qid = questionID)
	ans = Answer(userid = str(params["user"]), answer = str(params["answer"]), qid = question)
	ans.save()

	args = dict(qid = questionID)
	t = loader.get_template('answerpost-submit.html')
	c = RequestContext(request, args)
	return HttpResponse(t.render(c))
