public class BSTNode {

    private int value_;
    private BSTNode parent_ = null;
    private BSTNode left_ = null;
    private BSTNode right_ = null;

    public BSTNode (int val){
        this.value_ = val;
    }

    //Getters
    public BSTNode getParent(){ return this.parent_;}
    public BSTNode getLeftChild(){ return this.left_;}
    public BSTNode getRightChild(){ return this.right_;}
    public int getValue(){ return this.value_;}

    //Setters
    public void setParent(BSTNode n){ this.parent_ = n;}
    public void setLeftChild(BSTNode n){ this.left_ = n;}
    public void setRightChild(BSTNode n){ this.right_ = n;}
    public void setValue(int i){ this.value_ = i;}

}