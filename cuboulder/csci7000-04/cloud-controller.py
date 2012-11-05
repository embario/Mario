#!/usr/bin/env python
##
## Client that accepts messages encoded as integers
## computes the fibonacci of those numbers and returns
## a string representation
##
from utility import *
import pika, boto, threading, time, sys

print "========================== Cloud Controller.py =============================\n"

def output_update (i):  
    output = "[INFO]: Loading: %d iterations" % (i)
    sys.stdout.write("\r\x1b[K"+output.__str__())
    sys.stdout.flush()

def setup_queue_server():
    print "[INFO]: Creating the RabbitMQ Server instance...."
    
    user_data = ""
    ready = False

    for line in open("rabbitmq-cloudconfig.txt", 'r'):
        user_data += line

    if user_data.strip() != "":
        ready = True

    if ready == True:
        print user_data
        ec2connection = get_ec2_connection_from_env()
        reservation = ec2connection.run_instances(
            'ami-00000019',
            key_name='mbb-q',
            instance_type='m1.small',
            security_groups=['mmonaco-sg'],
            user_data=user_data)

        rmq = reservation.instances[0]
        print "[OK]: RabbitMQ Server instance is up and running at ID {%s}" % rmq
        print "[INFO]: Waiting for IP Address..."
        ip_addr = rmq.ip_address

        #Wait for IP Address...
        i = 1
        while (ip_addr == None or ip_addr == "" or ip_addr.split(".")[0] == "10"):
            time.sleep(10)
            rmq.update()
            ip_addr = rmq.ip_address
            output_update(i)
            i += 1

        print "\n[OK]: RabbitMQ Server instance is up and running at {%s}" % ip_addr
        return rmq

def setup_workers(rabbit):
    print "[INFO]: The Rabbit's Private DNS Name is {%s}" % rabbit.private_dns_name





    #TODO: 1) Create cloud-config file for RabbitMQ and pass it in to run_instances().
    # 2) Create worker instances, create their cloud-config file, and pass IP address of RabbitMQ instance into cloud-config file (Look at shar files)
    # 3) complete console.py file and accept requests to IP address of RabbitMQ instance.

#Start up RabbitMQ
rabbitmq_server = setup_queue_server()
setup_workers(rabbitmq_server)