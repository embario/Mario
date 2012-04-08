import sys

def stringIntersection(first, second):
    


def main (argv):    
    words = []
    map (words.append, argv[1:])

    #Make sure we have at least 2 strings to perform string intersection
    if len(words) >= 2:
        resultString = stringIntersection(words[0], words [1])
        for word in words[2:]:
            resultString = stringIntersection (resultString, word)

        print "The common string among these input strings is '" + resultString + "'"
    else:
        print "Not enough string arguments. Try again"
                            

# Call the Main Method, and then exit back here (to avoid other sys.exit() calls)
if __name__ == "__main__":
    sys.exit(main(sys.argv))



