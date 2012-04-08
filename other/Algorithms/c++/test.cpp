#include <vector>
#include <iostream>

using namespace std;

int main(){

  int * array = new int [50];
  cout << sizeof(array) << endl;
  cout << sizeof(int) << endl;
  cout << sizeof(int *) << endl;
  cout << (sizeof(array)/sizeof(int*)) << endl;
  return 0;
  delete array;

}
