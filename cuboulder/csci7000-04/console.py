import pika, boto, threading, time, sys

connection = pika.BlockingConnection(pika.ConnectionParameters(host=QHost))
recvchan = connection.channel()
sendchan = connection.channel()

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
