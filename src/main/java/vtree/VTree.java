package vtree;

import types.UserType;

import java.util.Stack;
import java.util.Vector;

public class VTree<T extends UserType>{

    Node<T> root;


    public VTree() {
        root = null;
    }


    public void Insert(T value) {
        if (root == null) {
            Node<T> newNode = new Node<>(value);
            newNode.setCount(1);
            root = newNode;
        } else {
            Node current = root;
            Node parent = root;
            boolean left = true;
            while (true) {
                if (current == null) {
                    Node<T> newNode = new Node<>(value);
                    newNode.setCount(1);
                    if (left) {
                        parent.setLeftChild(newNode);
                    } else {
                        parent.setRightChild(newNode);
                    }
                    return;
                }
                current.incrementCount();
                int cmpResult = value.compareTo((T) current.getValue());
                if (cmpResult < 0) {
                    T c = (T) current.getValue();
                    current.setValue((T) value);
                    value = c;
                }
                parent = current;
                if (current.getLeftChild() == null || current.getRightChild() != null && current.getLeftChild().getCount() < current.getRightChild().getCount()) {
                    current = current.getLeftChild();
                    left = true;
                } else {
                    current = current.getRightChild();
                    left = false;
                }
            }
        }
    }

    public Node<T> find(T value) {
        Stack<Node<T>> nodes = new Stack<>();
        nodes.push(root);
        Stack<Integer> states = new Stack<>();
        states.push(0);
        // 0 - new on this level, 1 - just visited left, 2 - just visited right
        while (true) {
            if (nodes.empty()) {
                return null;
            }
            Node<T> current = nodes.peek();
            if (current == null) {
                // go back?
                nodes.pop();
                states.pop();
                // inc last
                if (nodes.empty()) {
                    return null;
                }
//                Integer l = Integer.valueOf(states.peek().intValue() + 1);
//                states.pop();
//                states.push(l);
                continue;
            }
            int cmpResult = value.compareTo(current.getValue());
            if (cmpResult == 0) {
                return current;
            }
            if (cmpResult < 0 || states.peek() == 2) { // +-
                // go back?
                nodes.pop();
                states.pop();
                // inc last
                if (nodes.empty()) {
                    return null;
                }
//                Integer l = Integer.valueOf(states.peek().intValue() + 1);
//                states.pop();
//                states.push(l);
            } else {
                // go deeper?
                if (states.peek() == 0) {
                    // inc last
                    Integer l = Integer.valueOf(states.peek().intValue() + 1);
                    states.pop();
                    states.push(l);
                    // go left
                    states.push(0);
                    nodes.push(current.getLeftChild());
                    continue;
                }
                if (states.peek() == 1) {
                    // inc last
                    Integer l = Integer.valueOf(states.peek().intValue() + 1);
                    states.pop();
                    states.push(l);
                    // go right
                    states.push(0);
                    nodes.push(current.getRightChild());
                    continue;
                }
//                if(states.peek()==2){
//                    // go up
//                }
            }
        }
    }


    public void ForEach(DoWith func) {
        Stack<Node<T>> nodes = new Stack<>();
        nodes.push(root);
        Stack<Integer> states = new Stack<>();
        states.push(0);
        // 0 - new on this level, 1 - just visited left, 2 - just visited right
        while (true) {
            if (nodes.empty()) {
                return;
            }
            Node<T> current = nodes.peek();
            if (current == null) {
                // go back?
                nodes.pop();
                states.pop();
                // inc last
                if (nodes.empty()) {
                    return;
                }
                continue;
            }
            if (states.peek() == 2) { // +-
                func.doWith(current.getValue());
                // go back?
                nodes.pop();
                states.pop();
                // inc last
                if (nodes.empty()) {
                    return;
                }
            } else {
                // go deeper?
                if (states.peek() == 0) {
                    // inc last
                    Integer l = Integer.valueOf(states.peek().intValue() + 1);
                    states.pop();
                    states.push(l);
                    // go left
                    states.push(0);
                    nodes.push(current.getLeftChild());
                    continue;
                }
                if (states.peek() == 1) {
                    // inc last
                    Integer l = Integer.valueOf(states.peek().intValue() + 1);
                    states.pop();
                    states.push(l);
                    // go right
                    states.push(0);
                    nodes.push(current.getRightChild());
                    continue;
                }
            }
        }
    }
    public T GetByIndex (int index){
        final Vector<T>[] elementList = new Vector[]{new Vector<>()};
        final int[] curindex = {0};
        DoWith getElement = new DoWith() {
            @Override
            public void doWith(Object obj) {
                if(index== curindex[0]){
                    elementList[0].add((T) obj);
                }
                curindex[0] +=1;
            }
        };
        ForEach(getElement);
        if(elementList[0].size()>0){
            return elementList[0].get(0);
        }
        return null;
    }

    public T GetByIndexOld(int index) {
        int currentIndex = 0;
        Stack<Node<T>> nodes = new Stack<>();
        nodes.push(root);
        Stack<Integer> states = new Stack<>();
        states.push(0);
        // 0 - new on this level, 1 - just visited left, 2 - just visited right
        while (true) {
            if (nodes.empty()) {
                return null;
            }
            Node<T> current = nodes.peek();
            if (current == null) {
                // go back?
                nodes.pop();
                states.pop();
                // inc last
                if (nodes.empty()) {
                    return null;
                }
                continue;
            }
            if (states.peek() == 2) { // +-
                if(currentIndex==index){
                    return current.getValue();
                }
                currentIndex+=1;
                // go back?
                nodes.pop();
                states.pop();
                // inc last
                if (nodes.empty()) {
                    return null;
                }
            } else {
                // go deeper?
                if (states.peek() == 0) {
                    // inc last
                    Integer l = Integer.valueOf(states.peek().intValue() + 1);
                    states.pop();
                    states.push(l);
                    // go left
                    states.push(0);
                    nodes.push(current.getLeftChild());
                    continue;
                }
                if (states.peek() == 1) {
                    // inc last
                    Integer l = Integer.valueOf(states.peek().intValue() + 1);
                    states.pop();
                    states.push(l);
                    // go right
                    states.push(0);
                    nodes.push(current.getRightChild());
                    continue;
                }
            }
        }
    }
    public Stack<Node<T>> findParents(T value) {
        Stack<Node<T>> nodes = new Stack<>();
        nodes.push(root);
        Stack<Integer> states = new Stack<>();
        states.push(0);
        // 0 - new on this level, 1 - just visited left, 2 - just visited right
        while (true) {
            if (nodes.empty()) {
                return null;
            }
            Node<T> current = nodes.peek();
            if (current == null) {
                // go back?
                nodes.pop();
                states.pop();
                // inc last
                if (nodes.empty()) {
                    return null;
                }
                continue;
            }
            int cmpResult = value.compareTo(current.getValue());
            if (cmpResult == 0) {
                nodes.pop();
                if (nodes.empty()) {
                    return null;
                }
                return nodes;
            }
            if (cmpResult < 0 || states.peek() == 2) { // +-
                // go back?
                nodes.pop();
                states.pop();
                // inc last
                if (nodes.empty()) {
                    return null;
                }
            } else {
                // go deeper?
                if (states.peek() == 0) {
                    // inc last
                    Integer l = Integer.valueOf(states.peek().intValue() + 1);
                    states.pop();
                    states.push(l);
                    // go left
                    states.push(0);
                    nodes.push(current.getLeftChild());
                    continue;
                }
                if (states.peek() == 1) {
                    // inc last
                    Integer l = Integer.valueOf(states.peek().intValue() + 1);
                    states.pop();
                    states.push(l);
                    // go right
                    states.push(0);
                    nodes.push(current.getRightChild());
                    continue;
                }
//                if(states.peek()==2){
//                    // go up
//                }
            }
        }
    }

    public void RemoveByIndex(int index){
        T value = GetByIndex(index);
        if(value!=null){
            Remove(value);
        }
    }

    public T Remove(T value) {
        Stack<Node<T>> parents = findParents(value);

        if (parents == null || parents.empty()) { // root or not found
            if (root.getValue().compareTo(value) == 0) {
                if(root.getRightChild()==null && root.getLeftChild()==null){
                    T toreturn = root.getValue();
                    root = null;
                    return toreturn;
                } // if any child present, continue below
            } else {
                // no element found
                return null;
            }
        }
        Node<T> current;
        Node<T> parent = null;
        Stack<Node<T>> childs = new Stack<>();
        Stack<Integer> states = new Stack<>(); // 1 left 0 right

        if(parents==null){
            current=root;
        } else {
            parent = parents.peek();
            if (parent.getRightChild() != null && value.compareTo((T) parent.getRightChild().getValue()) == 0) {
                current = parent.getRightChild();
            } else {
                current = parent.getLeftChild();
            }
        }



        childs.push(current);

        boolean shouldReturn = false;

        T cvalue = value;
        Node<T> deleted = null;

        while (true) {
            if (childs.empty()) {
                break;
            }
            current = childs.peek();

            if (shouldReturn) {
                if(parent==null){ // remove root

                }
                if(parent!=null && parent.getRightChild()==deleted){
                    parent.setRightChild(null);
                    break;
                }
                if(parent!=null && parent.getLeftChild()==deleted){
                    parent.setLeftChild(null);
                    break;
                }
                if (childs.peek().getLeftChild() == deleted) {
                    childs.peek().setLeftChild(null);
                }
                if (childs.peek().getRightChild() == deleted) {
                    childs.peek().setRightChild(null);
                }
                T b = childs.peek().getValue();
                childs.peek().setValue(cvalue);
                childs.peek().decrementCount();
                cvalue = b;
                childs.pop();
                continue;
            }

            if (current.getLeftChild() == null && current.getRightChild() == null) {
                deleted = current;
                cvalue = current.getValue();
                shouldReturn = true;
                continue;
            }

            if (childs.peek().getLeftChild() == null || childs.peek().getRightChild() != null && ((T) childs.peek().getLeftChild().getValue()).compareTo((T) childs.peek().getRightChild().getValue()) > 0) {
                // going right
                childs.push(childs.peek().getRightChild());
            } else {
                // going left
                childs.push(childs.peek().getLeftChild());
            }

        }
        while (true) {
            if (parents == null || parents.empty()) {
                break;
            }
            parents.peek().decrementCount();
            parents.pop();
        }
        return value;
    }

    public void rebalance(){
        Vector<T> elementList = new Vector<>();
        DoWith getElement = new DoWith() {
            @Override
            public void doWith(Object obj) {
                elementList.add((T) obj);
            }
        };
        ForEach(getElement);
        root=null;
        for (T element: elementList) {
            Insert(element);
        }
    }

    public String print() {
        if(root==null){
            return "Empty tree";
        }
        return print("", root, false);
    }

    private String print(String prefix, Node n, boolean isLeft) {
        String result = "";
        if (n != null) {
            result+=prefix + (isLeft ? "├── " : "└── ") + n.getValue()+"("+n.getCount()+")"+"\n";
            result+=print(prefix + (isLeft ? "|   " : "    "), n.getLeftChild(), true);
            result+=print(prefix + (isLeft ? "|   " : "    "), n.getRightChild(), false);
        }
        return result;
    }

    public T GetRoot() {
        return root.getValue();
    }

    public void SetRoot(Node<T> _root) {
        root=_root;
    }

    public String packValue(){
        if(root==null) return "{}";
        return root.packValue();
    }
}
