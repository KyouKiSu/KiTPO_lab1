package vtree;

import types.UserType;

public class Node<T extends UserType> {
    final static String className = "Node";
    private T value;
    private Node<T> leftChild;
    private Node<T> rightChild;
    private int cnt;
    public Node(T _value){
        this.value = _value;
        this.leftChild = null;
        this.rightChild = null;
        this.cnt = 0;
    }
    public String packValue() {
        String packed = "{"+
                    "\"className\""+":\""+className+"\","+
                    "\"cnt\""+":"+cnt;
        if (value!=null) packed +=","+"\"value\""+":"+value.packValue();
        if (leftChild!=null) packed += ","+"\"leftChild\""+":"+ leftChild.packValue();
        if (rightChild!=null) packed += ","+"\"rightChild\""+":"+ rightChild.packValue();

        packed+="}";
        return packed;
    }

    public T getValue() {
        return this.value;
    }

    public void setCount(int i){
        this.cnt = i;
    }

    public int getCount(){
        return this.cnt;
    }

    public void incrementCount(){
        this.cnt +=1;
    }

    public void decrementCount(){
        this.cnt -=1;
    }

    public void setValue(final T value) {
        this.value = value;
    }

    public Node getLeftChild() {
        return this.leftChild;
    }

    public void setLeftChild(final Node leftChild) {
        this.leftChild = leftChild;
    }

    public Node getRightChild() {
        return this.rightChild;
    }

    public void setRightChild(final Node rightChild) {
        this.rightChild = rightChild;
    }
}
