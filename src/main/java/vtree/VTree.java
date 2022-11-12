package vtree;

import types.UserType;

import java.util.Stack;
import java.util.Vector;

public class VTree {

    Node root;


    public VTree() {
        root = null;
    }


    public void Insert(UserType value) {
        if (root == null) {
            Node newNode = new Node(value);
            newNode.setCount(1);
            root = newNode;
        } else {
            Node current = root;
            Node parent = root;
            boolean left = true;
            while (true) {
                if (current == null) {
                    Node newNode = new Node(value);
                    newNode.setCount(1);
                    if (left) {
                        parent.setLeftChild(newNode);
                    } else {
                        parent.setRightChild(newNode);
                    }
                    return;
                }
                current.incrementCount();
                int cmpResult = value.compareTo(current.getValue());
                if (cmpResult < 0) {
                    UserType c = current.getValue();
                    current.setValue(value);
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

    public Node find(UserType value) {
        Stack<Node> nodes = new Stack<>();
        nodes.push(root);
        Stack<Integer> states = new Stack<>();
        states.push(0);
        // 0 - new on this level, 1 - just visited left, 2 - just visited right
        while (true) {
            if (nodes.empty()) {
                return null;
            }
            Node current = nodes.peek();
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
        Stack<Node> nodes = new Stack<>();
        nodes.push(root);
        Stack<Integer> states = new Stack<>();
        states.push(0);
        // 0 - new on this level, 1 - just visited left, 2 - just visited right
        while (true) {
            if (nodes.empty()) {
                return;
            }
            Node current = nodes.peek();
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
    public UserType GetByIndex (int index){
        final Vector<UserType>[] elementList = new Vector[]{new Vector<>()};
        final int[] curindex = {0};
        DoWith getElement = new DoWith() {
            @Override
            public void doWith(Object obj) {
                if(index== curindex[0]){
                    elementList[0].add((UserType) obj);
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

    public UserType GetByIndexOld(int index) {
        int currentIndex = 0;
        Stack<Node> nodes = new Stack<>();
        nodes.push(root);
        Stack<Integer> states = new Stack<>();
        states.push(0);
        // 0 - new on this level, 1 - just visited left, 2 - just visited right
        while (true) {
            if (nodes.empty()) {
                return null;
            }
            Node current = nodes.peek();
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
    public Stack<Node> findParents(UserType value) {
        Stack<Node> nodes = new Stack<>();
        nodes.push(root);
        Stack<Integer> states = new Stack<>();
        states.push(0);
        // 0 - new on this level, 1 - just visited left, 2 - just visited right
        while (true) {
            if (nodes.empty()) {
                return null;
            }
            Node current = nodes.peek();
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
        UserType value = GetByIndex(index);
        if(value!=null){
            Remove(value);
        }
    }

    public UserType Remove(UserType value) {
        Stack<Node> parents = findParents(value);

        if (parents == null || parents.empty()) { // root or not found
            if (root.getValue().compareTo(value) == 0) {
                if(root.getRightChild()==null && root.getLeftChild()==null){
                    UserType toreturn = root.getValue();
                    root = null;
                    return toreturn;
                } // if any child present, continue below
            } else {
                // no element found
                return null;
            }
        }
        Node current;
        Node parent = null;
        Stack<Node> childs = new Stack<>();
        Stack<Integer> states = new Stack<>(); // 1 left 0 right

        if(parents==null){
            current=root;
        } else {
            parent = parents.peek();
            if (parent.getRightChild() != null && value.compareTo( parent.getRightChild().getValue()) == 0) {
                current = parent.getRightChild();
            } else {
                current = parent.getLeftChild();
            }
        }



        childs.push(current);

        boolean shouldReturn = false;

        UserType cvalue = value;
        Node deleted = null;

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
                UserType b = childs.peek().getValue();
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

            if (childs.peek().getLeftChild() == null || childs.peek().getRightChild() != null && (childs.peek().getLeftChild().getValue()).compareTo(childs.peek().getRightChild().getValue()) > 0) {
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
        Vector<UserType> elementList = new Vector<>();
        DoWith getElement = new DoWith() {
            @Override
            public void doWith(Object obj) {
                elementList.add((UserType) obj);
            }
        };
        ForEach(getElement);
        root=null;
        for (UserType element: elementList) {
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

    public UserType GetRoot() {
        return root.getValue();
    }

    public void SetRoot(Node _root) {
        root=_root;
    }

    public String packValue(){
        if(root==null) return "{}";
        return root.packValue();
    }
}
