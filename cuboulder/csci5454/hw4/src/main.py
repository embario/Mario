'''
Created on Apr 8, 2012

@author: Mario Barrenechea
'''

import ising
Ising = ising.Ising

n = 50
temp = 1000
alpha = 1-10e-4

i = Ising(n,temp,alpha)

print i

#now, run simulated_annealing()
i.simulate_annealing()

