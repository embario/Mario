import types

#Class for encoding vertex-related information.
class Vertex:

    #The values of the vertex
    __val = None
    __color = None
    __distance = None
    __pred = None

    def __init__ (self, val):
        self.__val = val
        self.__color = "white" 
        self.__distance = 0
        
    def get_value(self):
        return self.__val

    def get_color(self):
        return self.__color

    def get_distance(self):
        return self.__distance

    def get_predecessor(self):
        return self.__pred

    def set_color(self, f):
        self.__color = f

    def set_distance(self, f):
        self.__distance = f
    
    def set_predecessor(self, f):
        self.__pred = f

    def __repr__(self):
        return "(" + str(self.__val) + ")"
