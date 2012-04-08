#!/usr/bin/python

# skypeParser.py
# This module accepts a path to the Skype Application File Structure and parses User Profile information, contacts, and transfers/downloads.
#
# def getTextFromNodes(nodes) is a function from Python Reference: http://docs.python.org/library/xml.dom.minidom.html#dom-example that helped
# me to parse XML.
#
# <author> mbarrenecheajr </author>

import commands
import sys
import string
from xml.dom import minidom

#Helps to parse XML Nodes from xml document.
def getTextFromNodes(nodes):
    rc = ""
    for node in nodes:
        if node.nodeType == node.TEXT_NODE:
            rc = rc + node.data
    return rc


#Process Args
if len(sys.argv) != 2:
    print("Usage: ./skypeParser.py <absolute/path/to/Top/Level/Skype/File/Structure>")
    sys.exit(1)

#Current Path
currentPath = commands.getoutput('pwd')

#Path to Skype File Structure
path =  sys.argv[1]
if(path [len(path) - 1:] != '/'):
    path += '/'

#----------- Basic Skype Parsing -------------
try:
    xmldoc = minidom.parse(path + "shared.xml")
except IOError:
    print("shared.xml was not found. Aborting Skype Parsing.")
    sys.exit(1)

AccountDOM = xmldoc.getElementsByTagName("Default")[0]
userAccount = getTextFromNodes(AccountDOM.childNodes)

#Extract Personal User Information from profile.dbb
userDirectory = path + userAccount
profile = commands.getoutput("find \"" +  userDirectory + "\" -name profile*")

if profile == "":
    print("no profile database file found.")
else:
    profileStrings = commands.getoutput("strings \"" + profile + "\" -n 6 | tail -n 4")

#Extract Downloaded files from transfer.dbb
transfer = commands.getoutput("find \""  + userDirectory + "\" -name transfer*")
if transfer == "":
    print("no transfer database file found.")
else:
    transferStrings = commands.getoutput("strings \"" + transfer + "\" -n 10")

#Extract Contacts from config.xml
try:
    xmldoc = minidom.parse(userDirectory + "/config.xml")
except IOError:
    print("config.xml was not found. Aborting Skype Parsing")
    sys.exit(1)

userContacts = []
AccountDOM = xmldoc.getElementsByTagName("u")[0]
for node in AccountDOM.childNodes:
    if(node.nodeName == "#text"):
        continue
    userContacts.append(node.nodeName)


#-------------- Generate Report ----------------
print("\n---------- SkypeParse Results: ----------\n")
print("Skype User Name: " + userAccount)
print("Skype User Directory Structure " + userDirectory)
print("Skype User Contact Information: \n")
print(profileStrings + "\n")
print("Skype Location of Downloaded/Transferred Files: \n")
print(transferStrings + "\n")
print("Skype User Contacts: \n")

#Print out Contacts
for contact in userContacts:
    print(contact)

#Print Chat Directory
print("\nSkype Chat Directory: " + userDirectory + "/chatsync")


