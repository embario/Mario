#! /usr/bin/python
import boto
import boto.s3
import boto.s3.connection
import os,sys,re
import utility

#The "set" content program for S3 using Python boto
#Mario Barrenechea - DSC Class, Fall 2012
connection = utility.get_s3_connection_from_env()
sysargs = len(sys.argv)
if sysargs >= 3 and sysargs <= 4:
	bucket_name = sys.argv[1]
	key_name = sys.argv[2]
	#If there does exist such a bucket instance...
	if utility.check_if_bucket_exists(connection, bucket_name) == True:
		bucket_instance = connection.get_bucket(bucket_name)
		bucket_key = bucket_instance.get_key(key_name)		
		#If there does exist such a bucket key...
		if utility.check_if_key_exists(bucket_instance, bucket_key.name) == True:
			#That means we should have <program name> <bucket> <key> 
			if sysargs == 3:
				print "[INFO]: We have <%s><%s><%s>" % (sys.argv[0], sys.argv[1], sys.argv[2])
				user_input = raw_input("Accepting input from stdin: ")
				bucket_key.set_contents_from_string(user_input)
				print "[OK]: The contents was successfully sent to %s:%s" % (bucket_instance, bucket_key)

			#That means we should have <program name> <bucket> <key> [file]
			elif sysargs == 4:
				print "[INFO]: We have <%s><%s><%s>[%s]" % (sys.argv[0], sys.argv[1], sys.argv[2], sys.argv[3])
				filename = sys.argv[3]
				if os.path.exists(filename) == True:
					bucket_key.set_contents_from_filename(filename)
				else:
					print"[ERROR]: Could not find file (%s)" % filename
		else:
			print "[ERROR]: Bucket Key %s does not exist within bucket instance %s" % (key_name, bucket_instance)		
	else:
		print "[ERROR]: Bucket %s doesn't exist" % bucket_name
else:
	print "[INFO]: Usage: %s: <bucket> <key:optional> [file:optional]" % (sys.argv[0])


