import types, vertex, random
Vertex = vertex.Vertex

#The Graph class that will be used to encode vertex and edge information.
class Graph:

    #The adjacency list
    __adjList = None
    #Number of operations for running time
    __time_op = 0
    #Number of operations for space
    __space_op = 0 

    def __init__ (self, adjlist):

        #Want to ensure that all elements within the list are themselves (Vertex, list [Vertex]); 
        #we expect an adjacency list to be a list of pairs.
        assert isinstance(adjlist, types.ListType)
        for sublist in adjlist:
            assert (len(sublist) == 2) == True
            assert isinstance (sublist [0], Vertex)
            assert isinstance (sublist [1], types.ListType)
            for v in sublist[1]:
                assert isinstance(v, Vertex)
    
        #Good to go.
        self.__adjList = adjlist

    #Get the Adjacency List
    def get_adjacency_list (self):
        return self.__adjList

    #Get the Time Operations count
    def get_time_operations(self):
        return self.__time_op

    #Get the Space Operations count 
    def get_space_operations(self):
        return self.__space_op

    #Compute the diameter (that is to say, the longest single-source shortest path of any two vertices) of the graph.
    @staticmethod
    def diameter(g):
         
        #Reset time and space operation counts
        g.__time_op = 0
        g.__space_op = 0
        assert isinstance (g, Graph)

        #Keep track of the largest diameter we find.
        max_diameter = 0

        #1 for declaring max_diameter
        #1 for initializing to 0
        g.__time_op += 2
        #1 for the max_diameter
        g.__space_op += 1

        #1 for declaring vpair
        #1 for looking up g
        #1 for calling get_adjacency_list()
        g.__time_op += 3
        #1 for the vpair
        g.__space_op += 1

        #Iterate through all nodes and perform BFS [O(V(V+E)) time]
        for vpair in g.get_adjacency_list():

            #1 for calling is_list_empty()
            #1 for looking up vpair
            #1 for looking up 1st index of vpair
            #1 for comparing against True
            g.__time_op += 4
            
            if (Graph.is_list_empty(vpair[1]) == True):
                #1 for continuing
                g.__time_op += 1
                continue

            #1 for looking up vpair
            #1 for looking up 0th index of vpair
            #1 for looking up v
            #1 for assigning to v
            #1 for calling Graph.bfs()
            #1 for looking up v
            #1 for looking up g
            #1 for looking up diameter_result
            #1 for assigning to diameter_result
            g.__time_op += 9
            #1 for the v
            #1 for the diameter_result
            g.__space_op += 2

            v = vpair [0]
            (diameter_result,lpv) = Graph.bfs (v, g)
           
            #1 for looking up diameter_result
            #1 for looking up max_diameter
            #1 for comparing both variables
            g.__time_op += 3
 
            #If we found a bigger diameter, store it
            if (diameter_result > max_diameter):

                max_diameter = diameter_result

                #1 for looking up diameter_result
                #1 for looking up max_diameter
                #1 for assigning to max_diameter
                g.__time_op += 3

            #1 for declaring vpair
            #1 for looking up g
            #1 for calling get_adjacency_list()
            g.__time_op += 3
            #1 for the vpair
            g.__space_op += 1

        #1 for looking up max_diameter
        #1 returning max_diameter
        g.__time_op += 2

        return max_diameter
            

    #Is the input vertex in the graph?
    def in_graph(self, v):
        assert isinstance (v, Vertex)
        for pair in self.get_adjacency_list():
            vertex = pair [0]
            if ((vertex.get_value() == v.get_value()) == True):
                return True
        return False

    #Find and return the pair whose vertex value is equal to the value of the input vertex, False otherwise.
    def find_pair (self, v):

        assert isinstance (v, Vertex)

        #1 for looking up pair
        #1 for calling get_adjacency_list()
        self.__time_op += 2
        #1 for storing pair
        #1 for storing adjacency_list()
        self.__space_op += 2

        for pair in self.get_adjacency_list():
            
            #1 for looking up vertex
            #1 for looking pair
            #1 for looking up 0th index of pair
            self.__time_op += 3
            #1 for storing vertex
            self.__space_op += 1

            vertex = pair [0]

            #1 for looking up vertex
            #1 for calling get_value()
            #1 for looking up v
            #1 for calling get_value()
            #1 for comparing values
            #1 for comparing against True
            self.__time_op += 6

            if ((vertex.get_value() == v.get_value()) == True):
                #1 for looking up pair
                #1 for returning pair
                self.__time_op += 2
                return pair

            #1 for looking up pair
            #1 for calling get_adjacency_list()
            self.__time_op += 2
            #1 for storing pair
            #1 for storing adjacency_list()
            self.__space_op += 2

        #1 for returning False
        self.__time_op += 1
        return False

    #Flip a coin to determine edge between 'u' and 'v'
    @staticmethod
    def random_edge(u, v, p):
       # print "random edge (" + str(u) + "," + str(v) +  ") with p = " + str(p)
        assert isinstance (u, Vertex)
        assert isinstance (v, Vertex)
        if (u.get_value() == v.get_value()):
            return False
        if (random.random() <= p):
         #   print "TRUE!"
            return True
        else:
          #  print "FALSE!"
            return False

    
    #Returns True if the input vertex is found within the vertex adj list.
    @staticmethod
    def in_vertex_list (vlist, v):
        for u in vlist:
            if (u.get_value() == v.get_value()):
                return True
        return False

    #Returns True if the input list is empty, False otherwise
    @staticmethod 
    def is_list_empty(thisList):
        for thing in thisList:
            return False
        return True

    #Create a random graph.
    @staticmethod
    def random_graph(n,p):

        # first, create n vertices in the inputlist
        inputlist = [(Vertex(i), []) for i in range(0,n)]
        for i in range (0,n):

            #Grab the pair
            pair = inputlist [i]
            #Grab the vertex
            v = pair [0]
            #Grab the list [Vertex]
            vlist = pair [1]

            #Now, let's randomize the construction of edges for this Vertex.
            for j in range (i+1,n):
                
                #Create a new vertex
                new_vertex_pair = inputlist[j]
                new_vertex = new_vertex_pair [0]

                #If we flip a p-coin and it comes out "True", AND the new vertex is not found within this vertex adj list,
                #then add the (pair[0], new_vertex) edge!
                if((Graph.random_edge(v, new_vertex, p) == True) and
                   (Graph.in_vertex_list(vlist, new_vertex) == False)):
                    
                    vlist.append(new_vertex)
                    #Do not forget to add the inverted edge (v,u) in the adjacency list, since this is indeed an undirected graph.
                    new_vertex_pair [1].append(v)
                    

        return Graph(inputlist)


    #Perform Breadth-First-Search (BFS) on the input graph.
    @staticmethod
    def bfs(source_node, g):
        assert g.in_graph(source_node)
        assert isinstance (g, Graph)
        
        #1 for looking up adjlist
        #1 for looking up g
        #1 for calling get_adjacency_list()
        #1 for assigning to adjlist
        #1 for looking up max_diameter
        #1 for assigning to max_diameter
        g.__time_op += 6
        #1 for storing adjlist
        #1 for storing max_diameter
        g.__space_op += 2
        
        #Grab the adjacency list
        adjlist = g.get_adjacency_list()

        #Keep track of longest diameter in the graph.
        max_diameter = 0

        #1 for looking up vertex_pair
        #1 for looking up adjlist
        g.__time_op += 2
        #1 for storing vertex_pair
        g.__space_op += 1

        lpv = None

        #For all nodes, reset them.
        for vertex_pair in adjlist:

            #1 for looking up vi
            #1 for looking up vertex_pair
            #1 for looking up 0th vertex_pair
            #1 for assigning to vi
            #1 for looking up vi
            #1 for calling set_color()
            #1 for looking up vi
            #1 for calling set_distance()
            #1 for looking up vi
            #1 for calling set_predecessor()
            g.__time_op += 10
            #1 for storing vi
            g.__space_op += 1

            vi = vertex_pair [0]
            vi.set_color('white')
            vi.set_distance(0)
            vi.set_predecessor(None)

        #1 for looking up srcpair
        #1 for looking up g
        #1 for calling find_pair
        #1 for looking up source_node
        #1 for looking up srcpair
        #1 for looking up 0th index of srcpair
        #1 for calling set_color()
        g.__time_op += 7
        #1 for storing srcpair
        g.__space_op += 1

        source_node.set_color('gray')

        #1 for looking up q
        #1 for assigning to q
        #1 for looking up 1
        #1 for calling append
        #1 for looking up srcpair
        #1 for looking up 0th index of srcpair
        g.__time_op += 6
        #1 for storing q
        g.__space_op += 1

        #Our impromptu queue data structure.
        q = []
        q.append(source_node)

        #1 for looking up q
        #1 for calling is_list_empty()
        #1 for comparing against False 
        g.__time_op += 3
        
        #While there are still more vertices in our queue...
        while (Graph.is_list_empty(q) == False):
            
            #1 for looking up q
            #1 for calling pop()
            #1 for looking up u
            #1 for assigning to u
            #1 for looking up g
            #1 for looking up u
            #1 for calling find_pair()
            #1 for looking up u_neighbors
            #1 for assigning to u_neighbors
            g.__time_op += 9
            #1 for storing u
            #1 for storing u_neighbors
            g.__space_op += 2

            #Grab the next vertex
            u = q.pop(0)

            #Grab the vertex adjacency list for this vertex. 
            u_neighbors = g.find_pair(u)

            #1 for looking up v
            #1 for looking up u_neighbors
            #1 for looking up 1st index of u_neighbors
            g.__time_op += 3
            #1 for storing v
            g.__space_op += 1

            for v in u_neighbors [1]:
                
                #1 for looking up v
                #1 for calling get_color()
                #1 for comparing against white
                g.__time_op += 3

                if (v.get_color() == 'white'):

                    #1 for looking up v
                    #1 for calling set_color()
                    #1 for looking up v
                    #1 for calling set_predecessor()
                    #1 for looking up vdistance
                    #1 for looking up u
                    #1 for calling get_distance()
                    #1 for adding to 1
                    #1 for assigning to vdistance
                    #1 for looking up v
                    #1 for calling set_distance()
                    #1 for looking up vdistance
                    g.__time_op += 12
                    #1 for storing vdistance
                    g.__space_op += 1

                    v.set_color ('gray')
                    v.set_predecessor(u)
                    vdistance = u.get_distance() + 1
                    v.set_distance(vdistance)

                    #1 for looking up max_diameter
                    #1 for looking up vdistance
                    #1 for comparing two variables
                    g.__time_op += 3
                    
                    #Check to see which distance is larger...
                    if (vdistance > max_diameter):

                        max_diameter = vdistance
                        lpv = v

                        #1 for looking up max_diameter
                        #1 for looking up vdistance
                        #1 for assigning to max_diameter
                        g.__time_op += 3
           
                    #1 for looking up q
                    #1 for looking up v
                    #1 for calling append()
                    g.__time_op += 3
                    q.append(v)

                    
                #If, however, the current neighbor is gray,
                #Check for shortest-path improvements.
                elif (v.get_color() == 'gray'):

                    vdistance = u.get_distance() + 1
                    if (vdistance < v.get_distance()):
                        v.set_distance(vdistance)

                #1 for looking up 
                #1 for looking up u_neighbors
                #1 for looking up 1st index of u_neighbors
                g.__time_op += 3
                #1 for storing v
                g.__space_op += 1

            #1 for looking up u
            #1 for calling set_color()
            g.__time_op += 2
            u.set_color('black')

            #1 for looking up q
            #1 for calling is_list_empty()
            #1 for comparing against False
            g.__time_op += 3
        return (max_diameter, lpv)

    #standard rep for graph
    def __repr__ (self):
        graphstr = ""
        adjList = self.get_adjacency_list()
        for pair in adjList:
            v = pair [0]
            vList = pair[1]
            graphstr += '[' + str(v.get_value()) + '] -> ' + str(vList) + "\n" 
        return graphstr

