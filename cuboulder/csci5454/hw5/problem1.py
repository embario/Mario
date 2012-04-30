import types, graph, random, vertex
Graph = graph.Graph

class Problem:

	def __init__():
		pass


	@staticmethod
	def  li_subgraph(g, k):
		assert isinstance (g, Graph)
		
		degree_list = [0 for i in range(len(g.get_adjacency_list()))]
		for vpair in g.get_adjacency_list():
			v = vpair [0]
			degree_list[v.get_value()] = len(vpair[1])

		print degree_list
		print g

		for vpair in g.get_adjacency_list():
			if (degree_list[vpair[0].get_value()] < k):
				Problem.remove_vertex(g, vpair, degree_list, k)

		print degree_list
		print g


	@staticmethod
	def remove_vertex (g, v, degree_list, k):

		print "removing vertex" + str(v) + "here..."
		#if the length of the adj list for this vertex is empty...
		if len(v[1]) == 0:
			degree_list[v[0].get_value()] = 0
			return True

		degree_list[v[0].get_value()] = 0

		for neighbor in v[1]:

			degree_list[neighbor.get_value()] -= 1

			if (degree_list[neighbor.get_value()] < k):
				
				Problem.remove_vertex(g,neighbor, degree_list, k)





