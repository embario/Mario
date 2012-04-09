import random, copy, math

class Ising:

	#instance parameters
	__n = 0
	__temp = 0
	__alpha = 0
	__lattice = None
	__converged = False
	__energy = 0
	__num_white = 0 # -1
	__num_black = 0 # +1
	
	def __init__(self, n, t0, alpha):
		self.__n = n
		assert(n > 0 and n < 1000)
		self.__temp = t0
		self.__alpha = alpha
		self.__lattice = [[i for i in range(n)] for i in range (n)]
	
		#Iterate through lattice and initialize all sites to {-1,+1} randomly.
		#Also, compute its initial energy (n^2 operation).
		for i in range (n):
			for j in range(n):
				site = self.generate_random_site()
				self.__lattice[i][j] = site
				
				#Compute the energy of the lattice by calculating energies of all sites.
				self.__energy += self.calculate_energy((i,j), self.__lattice)
		
	def get_n(self):
		return self.__n
	def get_temp(self):
		return self.__temp
	def get_alpha(self):
		return self.__alpha
	def get_lattice(self):
		return self.__lattice
	def get_energy(self):
		return self.__energy
		
	def set_energy(self, energy):
		self.__energy = energy
		
	#Check to see if the lattice has converged (i.e. we have n^2 rejected proposals)
	def has_converged(self, num_rejected_proposals):
		n = self.__n
		if (num_rejected_proposals >= n*n):
			self.__converged = True
			return True
		self.__converged = False
		return False

	#Update the temperature with alpha as a free parameter
	def update_temp(self, temp):
		self.__temp = self.get_alpha() * temp
		return self.__temp
	
	#SA algorithm for finding global optimum (minimum)
	def simulate_annealing(self):
	
		num_rejected_proposals = 0
		lattice = self.get_lattice()
		temp = self.get_temp()
		energy = self.__energy
		iteration_count = 0
		
		#while the lattice has yet converged, iterate through flipping random sites and determine whether to update state for global optima.
		while (self.has_converged(num_rejected_proposals) == False):
			
			if temp < 1.00:
				print 'here'
		
			#Propose a new state of the lattice.
			(new_lattice, (i,j)) = self.neighbor(lattice)
			
			#calculate the energy score for this new proposed lattice.
			new_lattice_score = energy + -1*self.calculate_energy((i,j), new_lattice) 
			
			#Propose the new lattice.
			if (self.accept(new_lattice_score, energy, temp) == True):
				num_rejected_proposals = 0
				lattice = new_lattice
				energy = new_lattice_score
				print 'new lattice score accepted (%s), NEW LATTICE\n' % str(new_lattice_score)
			else:
				num_rejected_proposals += 1
				
			#Update the temperature according to the cooling schedule
			temp = self.update_temp(temp)
			
			print 'energy: %s\n' % str(energy)
			print 'temperature: %s\n' % str(temp)
			print 'num_rejected_proposals: %s \n' % str(num_rejected_proposals)
			print 'iteration count: %s' % str(iteration_count)
			iteration_count += 1
			
		return (lattice, temp)
		
	def generate_random_site(self):
		p = random.random()
		if (p <= 0.5):
			return 1
		else:
			return -1
		
	#Based on the new lattice score, the old lattice score, and the current temperature,
	#determine if the newest configuration based upon the new lattice score will be accepted/denied.
	def accept(self, new_lattice_score, lattice_score, temp):
		if new_lattice_score < lattice_score:
			return True
		else:
			e = math.exp(-1* math.fabs(new_lattice_score - lattice_score)/temp)
			p = random.random()
			if (p <= e):
				return True
		return False
			
		
	def calculate_energy(self, (i,j), lattice):
		n = self.get_n()
		
		#Get this site's neighbors
		top = ((i - 1) % n, j)
		bottom = ((i + 1) % n, j)
		left = (i, (j - 1) % n)
		right = (i, (j + 1) % n)
		
		return -1 * lattice[i][j] *(lattice[top[0]][top[1]] + lattice[bottom[0]][bottom[1]] + lattice[left[0]][left[1]] + lattice[right[0]][right[1]])
			
	#choose a random site within the lattice, flip its value {-1,+1},
	#and then return the modified lattice and the new site coordinates.
	def neighbor(self, lattice):
	
		new_lattice = copy.deepcopy(lattice)
		n = self.get_n();
		i = random.randint(0, n - 1)
		j = random.randint(0, n - 1)
		site = new_lattice[i][j]
		if site == 1:
			new_lattice[i][j] = -1
		else:
			new_lattice[i][j] = 1
			
		print lattice[i][j]
		return (new_lattice, (i,j))
		
	def __repr__ (self):
		repr_str = ""
		repr_str += '(energy: ' + str(self.get_energy()) + ', n: ' + str(self.get_n()) + ', temp: ' + str(self.get_temp()) + ', alpha: ' + str(self.get_alpha()) + ')\n'
		
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
		
