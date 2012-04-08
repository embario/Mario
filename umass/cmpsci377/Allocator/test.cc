/*
 * test.cc
 *
 *  Created on: Nov 12, 2009
 *      Author: mbarrenecheajr
 */

/***************************************/
#include <cstdlib>
#include <ctime>
#include <set>
#include <fcntl.h>
#include <iostream>

using namespace std;


//Method Headers
bool randomBool(double percent);
int randIndex();
size_t randomSize();
//global vars
set <void *> ptrs;
/****************************************/


int main(){

	srand(time(0));
	//creates 10 randomly sized objects and puts them into the set
	for (int i = 0; i< 50; i++){
		void *currentPtr = malloc(randomSize());
		ptrs.insert(currentPtr);

	}
	cout << "allocated the ten objects" << endl;
	for (int i = 0; i< 100; i++){
		cout << "iteration " << i ;
		bool shouldDelete = randomBool(.25);	//25% of the time delete
		if (shouldDelete){		//delete a random ptr from the set
			cout << " deleting! " << endl;
			int setSize = ptrs.size();
			int randElement = rand()% setSize;
			set<void*>::iterator it;
			void* ptrToDelete = NULL;
			int i = 0;
			for (it = ptrs.begin(); it !=ptrs.end(); it++ ){
				if (i == randElement){
					ptrToDelete = *it;
					ptrs.erase(it);
				}
				i++;
			}
			if (ptrToDelete != NULL)
				free( ptrToDelete);
		}
		else{				//add a pointer of random size to the set
			cout <<" mallocing!" << endl;
			void *currentPtr = malloc(randomSize());
			ptrs.insert(currentPtr);
		}
	}
	cout << "deleting the rest of the set" << endl;
	int count = 0;
	while (!ptrs.empty()){
		cout << (count++) << endl;
		free(*(ptrs.begin()));
		ptrs.erase(ptrs.begin());
	}


}


//if percent is .75 this will return true 75% of the time.
bool randomBool(double percent){
	double val = (double)rand()/(double)RAND_MAX;
	return percent >= val;
}

//returns 0-9 for the size class
int randIndex(){
	int val = rand();
	return val % 10;

}

//returns a random number which happens to be within a size class
size_t randomSize(){
	int exponent = randIndex() + 3;
	int maxSize = 1;
	int minSize;
	for (int i = 0; i< exponent; i++){
		maxSize *= 2;
		if (i == exponent -2)
			minSize = maxSize;
	}
	int difference = maxSize - minSize;
	int random = rand()%difference;
	return random + minSize;


}
