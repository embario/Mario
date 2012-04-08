import types, random

class Ising:

	#instance parameters
	__n = None
	__temp = None
	__alpha = None
	__lattice = None
	__converged = False
	
	def __init__(self, n, t0, alpha):
		self.__n = n
		assert(n > 0 and n < 1000)
		self.__temp = t0
		self.__alpha = alpha
		self.__lattice = [[i for i in range(n)] for i in range (n)]
		
		for i in range (n):
			for j in range(n):
				self.__lattice[i][j] = self.generate_random_site()
		
	def get_n(self):
		return self.__n
	def get_temp(self):
		return self.__temp
	def get_alpha(self):
		return self.__alpha
	def get_lattice(self):
		return self.__lattice
	def has_converged(self):
		return self.__converged
		
	def simulate_annealing(self):
		(t,s) = (self.get_temp(), self.get_lattice())
		
	def generate_random_site(self):
		p = random.random()
		if (p <= 0.5):
			return 1
		else:
			return -1
		
	def accept(self, j, j_1, t):
		pass
		
	def calculate_energy(self):
		pass
		
	def neighbor(self, s):
		pass
		
	def __repr__ (self):
		repr_str = ""
		repr_str += '(n: ' + str(self.get_n()) + ', temp: ' + str(self.get_temp()) + ', alpha: ' + str(self.get_alpha()) + ', converged: ' + str(self.has_converged()) + ')\n'
		
		lattice = self.get_lattice()
		n = self.get_n()
		for i in range(n):
			repr_str += '['
			for j in range(n):
				repr_str += '%2s' % str(lattice[i][j])
				if j == (n - 1):
					repr_str += ']'
				else:
					repr_str += ',\t'
			repr_str += '\n'
		return repr_str
		