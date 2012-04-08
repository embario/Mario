import sys


def isPalindrome(word):

    #Base Case
    if len(word) == 1:
        return True
    #Base Case
    elif len(word) == 2:
        if word[0] == word[1]:
            return True
        else:
            return False
    #Recursive Case
    elif len(word) > 2:
        if word[0] == word[len(word)-1]:
            newWord = word[1:-1]
            print newWord
            return isPalindrome(newWord)
        else:
            return False

def main (argv):    
    words = []
    map (words.append, argv[1:])
    
    for word in words:
        ispalindrome = isPalindrome(word)
        if ispalindrome == False:
            print "The word '" + word + "' is NOT a palindrome"
        elif ispalindrome == True:
            print "The word '" + word + "' IS a palindrome"
                

# Call the Main Method, and then exit back here (to avoid other sys.exit() calls)
if __name__ == "__main__":
    sys.exit(main(sys.argv))



