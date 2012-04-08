#! /usr/bin/env python

def slowFib(n, numCalls):

    numCalls +=  1
    print "slowFib called for number", n
    if(n <= 1):
        return 1
    else:
        return slowFib(n - 1, numCalls) + slowFib(n - 2, numCalls)

def fastFib(n, memo, numCalls):

    numCalls += 1
    print "fastFib called for number", n
    if (n not in memo):
        memo [n] = fastFib(n - 1, memo, numCalls) + fastFib (n - 2, memo, numCalls)
    return memo [n]

def reportFib (n, type):

    numCalls = 0
    if(type == "fast"):
        memo = {0:1, 1:1}
        answer = fastFib(n, memo, numCalls)
    elif(type == "slow"):
        answer = slowFib(n, numCalls)
    else:
        print "Error: What type of fibonnaci do you want? ('slow','fast')"

    print "The %dth Fibonnaci Number is:" % answer
    print "The Number of Calls to SlowFib: %d" % numCalls

