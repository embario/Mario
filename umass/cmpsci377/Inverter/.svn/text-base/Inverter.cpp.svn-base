/*
 * Inverter.cpp
 *
 *  Created on: Sep 24, 2009
 *      Author: mbarrenecheajr
 */

#include "Inverter.h"

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

void Inverter::showWords(){

	map <string, set<int> > * words = this->getWords();
	map <string, set<int> > ::iterator itr;
	set <int> :: iterator setItr;
	string phrase, setitr;

	for(itr = words->begin(); itr != words->end(); itr++){

		cout << (*itr).first;

		for(setItr = (*itr).second.begin(); setItr != (*itr).second.end(); setItr++)
			cout << " " << (*setItr);

		cout << endl;
	}

}


void Inverter::showFiles(){

	vector <string> * files = this->getFiles();
	vector <string> :: iterator itr;

	for(itr = files->begin(); itr != files->end(); itr++)
		printf("%s\n", (*itr).c_str());
}


