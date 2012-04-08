#include <iostream>


using namespace std;

struct BSTNode{

  int value;
  BSTNode * parent;
  BSTNode * left;
  BSTNode * right;

};

/*
Attempts to sort the BSTNode Tree structure using MergeSort.
 */
BSTNode * mergeSort (BSTNode * root);
BSTNode * merge (BSTNode * left, BSTNode * right){

int main (int argc, char ** argv){

  BSTNode * node = new BSTNode;
  node->value = 5;
  node->parent = new BSTNode;
  node->parent->value = 10;
  return 0;

}

BSTNode * mergeSort (BSTNode * root){

  if(root == NULL)
    return NULL;

  BSTNode * left = mergeSort(root->left);
  BSTNode * right = mergeSort(root->right);
  return merge (left, right);
}

BSTNode * merge (BSTNode * left, BSTNode * right){

  if (left == NULL && right == NULL)
    return NULL;

  

  


}
