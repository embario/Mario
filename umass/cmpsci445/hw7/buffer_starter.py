#! /usr/bin/env python

import random
import sys

#  Sample Code for Homework 7
#  CMPSCI 445, Fall 2010
#

#  This is a class definition for a buffer manager simulator
#  It is instatiated with a size (bufferPoolSize) and a string describing an implemented policy (policyName)
#  Calling method 'setWorkload' will establish a workload
#  Calling method 'simulate' will run the workload under the chosen policy
#  Calling method 'missRate' will return the percentage of page accesses which were cache misses.
class BufferManager:
    def __init__(self, bufferPoolSize, policyName):
        self.size = bufferPoolSize
        self.policyName = policyName
        self.buffer = [None]*self.size   # buffer[i] stores the pageID of frame i; empty frames contain None.
        self.used = [0]*self.size        # used[i] contains the clock value of the last time the page in frame i was used.
        self.cacheHit = 0
        self.cacheMiss = 0
        self.clock = 0
        self.workload = [ ]
        self.policyList = {              # This dictionary associates policy names with policy functions
                'RANDOM': self.policyRANDOM, 
                'LRU': self.policyLRU,
                'MRU': self.policyMRU,
                'MYSTERY': self.policyMYSTERY,
                'FIFO': self.policyFIFO
        }
        
        self.replacementPolicy = self.policyList[self.policyName]  # this chooses the desired policy function and assigns it to 'replacementPolicy' which holds a function object 
        random.seed('blah') 		# set seed so results for random policy are repeatable
        
    def missRate(self):			# return the cache miss rate after a simulation 
        return float(self.cacheMiss) / (self.cacheHit + self.cacheMiss)
    
    def setWorkload(self, workload):
        self.workload = workload[:]
        
    def simulate(self):
        while len(self.workload) > 0:
            page = self.workload.pop(0)
            self.request(page)
            
    def request(self, pageID):		# this is the main logic in which the buffer manager responds to a request for 'pageID'
        if pageID in self.buffer: 	# page is already in buffer
            self.cacheHit += 1
            # record that the page was accessed in the 'used' dictionary
            frameUsed = self.buffer.index(pageID)		# List.index(element) returns the first position in the list where 'element' occurs
        else: 						# page is not present in buffer
            self.cacheMiss += 1
            if None in self.buffer:   	# there is free space
                frameUsed = self.buffer.index(None) 	# find first available frame
                self.buffer[ frameUsed ] = pageID  		# place pageID in the frame
            else:						# there is no free space -- something must be replaced
                frameUsed = self.replacementPolicy()	# use the policy to find the frame to replace
                self.buffer[ frameUsed ] = pageID
        self.used[ frameUsed ] = self.clock # no matter which case happened, record that page in the touched frame was accessed
        self.clock += 1						# move clock forward
    #################################################################################################
    #  Replacement Policies
    #
    def policyLRU(self):
        frame = self.used.index(min(self.used))
        return frame
    def policyMRU(self):
        frame = self.used.index(max(self.used))
        return frame
    def policyRANDOM(self):			# this policy chooses a random frame to be replaced
        return random.randint(0,self.size-1)

    #The Mystery policy iterates through the buffer and returns the index of the highest index in the buffer (two levels of indirection). If the frame
    #is not in the workload, then the index returned by the method will most probably return its index.
    def policyMYSTERY(self):		# mystery policy.  What is this doing?
        temp = []
        for page in self.buffer:
            if page in self.workload:
                temp.append( self.workload.index(page) )
            else:
                temp.append( sys.maxint )	# sys.maxint is the largest possible integer (think of it as Infinity)
                
            maxIndex = max( temp )
            return temp.index( maxIndex )		# this is the frame chosen for replacement
        
    def policyFIFO(self):

        #For each frame in the frame buffer, search for frame i in the ith position.
        #This match shows that, when scanning for the first element inserted into the buffer
        #at any given time (FIFO), the element that matches its index is the element to replace.
        for frameIndex, frame in enumerate(self.buffer):
            if (frame == frameIndex):
                return frameIndex

        #If no luck (most of the case), return the smallest frame number in the buffer.
        return self.buffer.index(min(self.buffer))

#################################################################################################
#  Workload generators
#

#  This function generates a workload of page accesses: that is, a sequence of pageIDs.
#  This workload is a random sequence of pageIDs of the specified length, workloadLength.
def randomWorkload( dataSize, workloadLength, seed ):
    random.seed(seed)
    workload = []
    for i in range(workloadLength):
        workload.append( random.randint(0,dataSize-1) )
    return workload

#  This function generates a workload of page accesses: that is, a sequence of pageIDs.
#  This workload is the sequence of pageIDs that would result from running a nested loops computation of a join or crossproduct 
#  over relations R and S where each relation has size m = (dataSize/2)
#  The length of the workload is 2(m^2)
def nestedLoopsWorkload( dataSize ):
    workload = []
    tableR = range(0,dataSize/2)
    tableS = range(dataSize/2,dataSize)
    for i in tableR:
        for j in tableS:
            workload.append(i)
            workload.append(j)
    return workload

###################################################################################################
#  Main
#

def main():

    # This is the number of data pages stored on disk.
    # We assume stored pages have pageIDs from 0 to (dataSize-1)
    dataSize = 100 		# (You don't have to change this)

    # Generate a workload, in one of two ways
    workload = randomWorkload(dataSize, 5000, 'iLoveDatabases')
    #workload = nestedLoopsWorkload( dataSize )

    # Run some buffer manager simulations, for different buffer sizes
    for policy in ['RANDOM','LRU','MYSTERY', 'MRU', 'FIFO']:    		# Add new policies here, as needed
        for bufferSize in [5,10,25,50,75,90,100]:		# These buffer sizes don't need to be changed
            B = BufferManager( bufferSize, policy )
            B.setWorkload( workload )
            B.simulate()
            print policy, '  bufferSize=', B.size, '  missRate=', B.missRate()

if __name__ == "__main__":
    main()	
