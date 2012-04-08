/*
 * Driver.cpp
 *
 *  Created on: Sep 24, 2009
 *      Author: Mario Barrenechea
 *      UMass Honors Computer Science
 *      CMPSCI 377: Operating Systems */

#include <iostream>
#include "Inverter.h"
#include <pthread.h>
#include <string>

using namespace std;

int main(int argc, char ** argv){

	//Argument Check
	if(argc < 2 || argc > 2)
		return 1;

	Inverter * inverter = new Inverter(argv[1]);

	//Does the file (argument) exist?
	if(inverter->isFile(argv[1]) == false){
		delete inverter;
		return 1;
	}

	//Get our Input Stream
	ifstream stream (inverter->getFileName(), ifstream::in);
	string buffer;
	int fileIndex = 0;

	//Main Loop to determine if tokens are files within
	//the argument file, and if they are, parse them.
	while(stream >> buffer){

		if(inverter->isFile(buffer) == true)
			inverter->parseFile(buffer, fileIndex++);
		else
			continue;
	}

	stream.close();
	inverter->showWords();
	delete inverter;
	return 0;

}

