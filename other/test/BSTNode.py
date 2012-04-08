
class BSTNode:

    value = 0
    parent = None 
    left = None 
    right = None 

    def __init__(self, val):
        self.value = val

    def getParent(self):
        return self.parent
    
    def getLeftChild(self):
        return self.left
    
    def getRightChild(self):
        return self.right

    def setParent(self, val):
        self.parent = val

    def setLeftChild(self, val):
        self.left = val;

    def setRightChild(self, val):
        self.right = val;

        
