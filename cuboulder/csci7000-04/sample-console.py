#!/usr/bin/env python
##
## Client that accepts messages encoded as integers
## computes the fibonacci of those numbers and returns
## a string representation
##

import pika, boto
import threading,time,sys
import fib_pb2

#RabbitMQ Instance IP Address goes here.
QHost = "sdr.cs.colorado.edu"

connection = pika.BlockingConnection(pika.ConnectionParameters(host=QHost))
recvchan = connection.channel()
sendchan = connection.channel()

recvchan.queue_declare(queue='fib_to_compute', durable=True)
recvchan.queue_declare(queue='fib_from_compute', durable=True)

def receive_fib(ch, method, properties, body):
    ch.basic_ack(delivery_tag = method.delivery_tag)
    try:
        fiblist = fib_pb2.FibList()
        fiblist.ParseFromString(body)
        for fibInstance in fiblist.fibs:
            print "Fib(",fibInstance.n,") = ", fibInstance.response
    finally:
        pass

class RecvThread(threading.Thread):
    def run(self):
        recvchan.basic_qos(prefetch_count=1)
        recvchan.basic_consume(receive_fib, queue='fib_from_compute', no_ack=False)
        recvchan.start_consuming()

recvThread = RecvThread()
recvThread.daemon = True
recvThread.start()

while True:
    try:
        n = raw_input("Fib of what number (0 to exit)? > ")
        n = int(n)
    except ValueError:
        print "Enter an integer"
        n = 0
    except EOFError:
        n = 0

    if  n == 0 :
        print "Done, exiting..."
        break

    fiblist = fib_pb2.FibList()
    fib = fiblist.fibs.add()
    try:
        fib.n = n
    except TypeError:
        fib.n = 0
    sendchan.basic_publish(exchange='',
                           routing_key='fib_to_compute',
                           body=fiblist.SerializeToString(),
                           properties=pika.BasicProperties(delivery_mode = 2))
    print "Sent.."
sys.exit(0)
