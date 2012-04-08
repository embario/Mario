/*
 * Inverter.cpp
 *
 *  Created on: Sep 24, 2009
 *      Author: Mario Barrenechea
 *      UMass Honors Computer Science
 *      CMPSCI 377: Operating Systems
 */

#ifndef INVERTER_CC
#define INVERTER_CC

#include <map>
#include <set>
#include <vector>
#include <fstream>
#include <iostream>
#include <string>

using namespace std;

/** Class for Parsing through files **/
class Inverter {

public:

	Inverter(char * fname);
	~Inverter();

	bool isFile(string fname);
	void parseFile(string fname, int fileIndex);
	void showWords();
	void showFiles();

	//Getters
	inline char * getFileName(){ return this->m_fileName_;}
	inline int getNumOfFiles(){ return this->m_numOfFiles_;}
	inline map <string, set<int> > * getWords(){ return this->m_words_;}
	inline vector <string> * getFiles(){ return this->m_files_;}

private:

	char * m_fileName_;
	int m_numOfFiles_;
	map <string, set<int> > * m_words_;
	vector <string> * m_files_;

};


Inverter::Inverter(char * fname) {

	this->m_fileName_ = fname;
	this->m_words_ = new map<string, set<int> >;
	this->m_files_ = new vector<string>;
}

Inverter::~Inverter() {

	delete this->m_words_;
	delete this->m_files_;
}

/* isFile():
 *
 * This function accepts a string representing file in
 * current directory and returns true if the file
 * is able to read from or false if otherwise. */

bool Inverter::isFile(string fname){

	ifstream stream (fname.c_str(), ifstream::in);

	if(stream.bad() || stream.fail())
		return false;

	else if(stream.good() == true)
		return true;
	else
		return false;
}


/**parseFile();
 *
 * Principal function for loading the map
 * object with words from existing files
 */
void Inverter::parseFile(string fname, int fileIndex){

	//Add the existing filename onto the vector
	vector <string> * files = this->getFiles();
	files->push_back(fname);

	map <string, set<int> > * words = this->getWords();
	map <string, set<int> >::iterator itr;

	char buffer [256];
	char delimiters [] = " 1234567890!@#$%^&*()-_=+\"\\|,<.>/?;:[]{}";
	ifstream stream (fname.c_str(), ifstream::in);

	//While we haven't finished parsing the file for tokens...
	while(stream >> buffer){

		char * token = strtok(buffer, delimiters);

		//Break down tokens to find words
		while(token != NULL){

			string word = token;
			token = strtok(NULL, delimiters);
			itr = words->find(word);

			//If the word already exists...
			if(itr != words->end())
				(*itr).second.insert(fileIndex);
			else{

				set <int> indices;
				indices.insert(fileIndex);
				words->insert(make_pair(word, indices));
			}

		}//end while loop
	}//end parsing file loop
	stream.close();

}

/**
 * showWords();
 *
 * print out all of the words belonging in
 * files that were successfully parsed
 * by the Inverter object.
 */
void Inverter::showWords(){

	map <string, set<int> > * words = this->getWords();
	map <string, set<int> > ::iterator itr;
	set <int> :: iterator setItr;

	for(itr = words->begin(); itr != words->end(); itr++){

		cout << (*itr).first << ":";

		for(setItr = (*itr).second.begin(); setItr != (*itr).second.end(); setItr++)
			cout << " " << (*setItr);

		cout << endl;
	}

}

/**
 * showFiles();
 * Print out all of the files that were successfully
 * parsed by the Inverter object.
 */
void Inverter::showFiles(){

	vector <string> * files = this->getFiles();
	vector <string> :: iterator itr;

	for(itr = files->begin(); itr != files->end(); itr++)
		printf("%s\n", (*itr).c_str());
}


/**
 * Main Method
 */
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

#endif
