/*
 * Inverter.h
 *
 *  Created on: Sep 24, 2009
 *      Author: mbarrenecheajr
 */

#ifndef INVERTER_H_
#define INVERTER_H_

#include <map>
#include <set>
#include <vector>
#include <fstream>
#include <iostream>
#include <string>

using namespace std;

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

#endif /* INVERTER_H_ */
