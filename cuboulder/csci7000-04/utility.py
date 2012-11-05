from boto.ec2 import *
import boto, boto.s3, boto.s3.connection
import os, re, sys

def parse_url(url):
    "Extract the components of a URL path for EC2/S3"
    match = re.match("http://(.*?):(\d+)(/(.*$))?", url)
    result = None
    if match and match.groups >= 2:
        host = match.group(1)
        port = int(match.group(2))
        path = match.group(3)
        if path == None:
            path = ""
        result = (host, port, path)
    return result


def get_ec2_connection_from_env():

    aws_access_key = os.getenv("EC2_ACCESS_KEY")
    aws_secret_key = os.getenv("EC2_SECRET_KEY")
    ec2_url = os.getenv("EC2_URL")
    ec2_host, ec2_port, ec2_path = parse_url(ec2_url)
    region = boto.ec2.regioninfo.RegionInfo()
    region.endpoint = ec2_host

    connection = EC2Connection(
        aws_access_key_id = aws_access_key,
        aws_secret_access_key = aws_secret_key,
        port = ec2_port,
        path = ec2_path,
        is_secure = False,
        region = region)

    return connection

def get_s3_connection_from_env():
    ##
    ## A simple demo of the Boto S3 interface.
    ## connect to futuregrid or OpenStack using the environment variables
    ## and then list the S3 buckets
    ##

    EC2_ACCESS  = os.getenv('EC2_ACCESS_KEY')
    EC2_SECRET  = os.getenv('EC2_SECRET_KEY')
    S3_URL = parse_url(os.getenv('S3_URL'))
    EC2_URL =parse_url(os.getenv('EC2_URL'))

    calling_format=boto.s3.connection.OrdinaryCallingFormat()

    connection = boto.s3.connection.S3Connection(
        aws_access_key_id=EC2_ACCESS,
        aws_secret_access_key=EC2_SECRET,
        is_secure=False,
        host=S3_URL[0],
        port=S3_URL[1],
        calling_format=calling_format,
        path=S3_URL[2])

    return connection

def check_if_bucket_exists(connection,bucket):
    rs = connection.get_all_buckets()
    for b in rs:
        if b.name == bucket:
            return True
    return False

def check_if_key_exists(bucket_instance, key):
    rs = bucket_instance.get_all_keys()
    for k in rs:
        if k.name == key:
            return True
    return False
