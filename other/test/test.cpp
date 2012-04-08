#include <iostream>
#include <cstdlib>

struct node_t {

  int value;
  node_t * nextNode;

};

node_t * removeDups (node_t * node);
void deleteNodes (node_t * node);
void printNodes (node_t * node);

using namespace std;

int main (int argc, char ** argv){

  node_t * node = new node_t;
  for (int i = 0; i < 5; i++){

    node->value = i;
    cout << "(" << node->value << ", ";

    node->nextNode = new node_t;
    node = node->nextNode;

    cout << node->value << ")" << endl;
  }

  printNodes(node);
  removeDups(node);
  // printNodes(node);

  deleteNodes(node);
  return 0;

}

node_t * removeDups (node_t * node){


  return NULL;

}

void printNodes (node_t * node){

  while (node != NULL){
    cout << "[" << node->value << "] -> ";
    node = node->nextNode;
  }

  cout << endl;
}

void deleteNodes (node_t * node){
  delete node;
}
