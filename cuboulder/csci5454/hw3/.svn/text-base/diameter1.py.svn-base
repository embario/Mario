import graph, vertex

Graph = graph.Graph
Vertex = vertex.Vertex

#Controls---------------------
NUM_ITERATIONS = 10
#Range of n
nrange  = [8,16,32,64,128,256,512,1024,2048,4096]
#nrange = [10,20,40,80,160,320,640,1280,2560,5120]

#This will be our subject graph
g = None

#For each n in the nrange list...
for numN in nrange:

    #The average variables for this run.
    diameterAVG = 0.0
    time_operationsAVG = 0.0
    space_operationsAVG = 0.0
    
    #set up p, n, and c ----------
    n = numN #Number of vertices we would like in our graph.
    c = 5.0 # E(k): Expected value for vertex degree.
    p = c/(n-1) # Probability of producing a random edge.

    for i in range (NUM_ITERATIONS):
        
        #Create a random graph
        g = Graph.random_graph(n,p)
        
        #Run the diameter function and grab its result.
        diameter = Graph.diameter(g)
        
        #Aggregate average
        diameterAVG += diameter
        time_operationsAVG += g.get_time_operations()
        space_operationsAVG += g.get_space_operations()
                                                
    diameterAVG = diameterAVG/NUM_ITERATIONS
    time_operationsAVG = time_operationsAVG/NUM_ITERATIONS
    space_operationsAVG = space_operationsAVG/NUM_ITERATIONS
    
    print "Run [" + str(numN) + "]:"
    print (diameterAVG)
    print (time_operationsAVG)
    print (space_operationsAVG)
