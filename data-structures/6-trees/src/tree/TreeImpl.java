package tree;

import java.util.Stack;

public class TreeImpl<E extends Comparable<? super E>> implements Tree<E> {

    private Node<E> root;
    private int maxLevel;

    public TreeImpl() {
        this(0);
    }

    public TreeImpl(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    @Override
    public boolean add(E value) {
        if (isEmpty()) {
            root = new Node<>(value);
            return true;
        }

        NodeAndPrevious nodeAndPrevious = doFind(value);
        if (nodeAndPrevious.current != null) {
            return false; //Found duplicate
        }

        Node<E> newValue = new Node<>(value);
        Node<E> parent = nodeAndPrevious.previous;

        int level = parent.getLevel() + 1;
        if (level > maxLevel) {
            return false;
        }

        if (parent.shouldBeLeft(value)) {
            parent.setLeftChild(newValue);
        } else {
            parent.setRightChild(newValue);
        }

        return true;
    }

    @Override
    public boolean remove(E value) {
        NodeAndPrevious nodeAndPrevious = doFind(value);
        Node<E> removedNode = nodeAndPrevious.current;
        Node<E> parent = nodeAndPrevious.previous;

        if (removedNode == null) {
            return false;
        }

        if (removedNode.isLeaf()) {
            removeLeaf(removedNode, parent);
        }
        else if (hasOnlySingleChildNode(removedNode)) {
            removeNodeWithSingleChild(removedNode, parent);
        }
        else {
            removeCommonNode(removedNode, parent);
        }

        return true;
    }

    private void removeCommonNode(Node<E> removedNode, Node<E> parent) {
        Node<E> successor = getSuccessor(removedNode);
        if (removedNode == root) {
            root = successor;
        }
        else if (parent.getLeftChild() == removedNode) {
            parent.setLeftChild(successor);
        }
        else {
            parent.setRightChild(successor);
        }

        successor.setLeftChild(removedNode.getLeftChild());
    }

    private Node<E> getSuccessor(Node<E> removedNode) {
        Node<E> successor = removedNode;
        Node<E> successorParent = null;
        Node<E> current = removedNode.getRightChild();

        while (current != null) {
            successorParent = successor;
            successor = current;
            current = current.getLeftChild();
        }

        if (successor != removedNode.getRightChild()) {
            successorParent.setLeftChild(successor.getRightChild());
            successor.setRightChild(removedNode.getRightChild());
        }

        return successor;
    }

    private void removeNodeWithSingleChild(Node<E> removedNode, Node<E> parent) {
        Node<E> childNode = removedNode.getLeftChild() != null
                ? removedNode.getLeftChild()
                : removedNode.getRightChild();

        if (removedNode == root) {
            root = childNode;
        }
        else if (parent.getLeftChild() == removedNode) {
            parent.setLeftChild(childNode);
        }
        else {
            parent.setRightChild(childNode);
        }
    }

    private boolean hasOnlySingleChildNode(Node<E> node) {
        return node.getLeftChild() != null ^ node.getRightChild() != null;
    }

    private void removeLeaf(Node<E> removedNode, Node<E> parent) {
        if (removedNode == root) {
            root = null;
        }
        else if (parent.getLeftChild() == removedNode) {
            parent.setLeftChild(null);
        }
        else {
            parent.setRightChild(null);
        }
    }

    @Override
    public boolean find(E value) {
        NodeAndPrevious nodeAndPrevious = doFind(value);
        return nodeAndPrevious.current != null;
    }

    private NodeAndPrevious doFind(E value) {
        Node<E> previous = null;
        Node<E> current = root;

        current.setLevel(1);

        while (current != null) {
            if (previous != null) {
                current.setLevel(previous.getLevel() + 1);
            }
            if (current.getValue().equals(value)) {
                return new NodeAndPrevious(current, previous);
            }

            previous = current;
            if (current.shouldBeLeft(value)) {
                current = current.getLeftChild();
            } else {
                current = current.getRightChild();
            }
        }

        return new NodeAndPrevious(null, previous);
    }

    @Override
    public boolean isEmpty() {
        return root == null;
    }

    @Override
    public void display() {
        Stack<Node> globalStack = new Stack<>();
        globalStack.push(root);
        int nBlanks = 64;

        boolean isRowEmpty = false;
        System.out.println();

        while (!isRowEmpty) {
            Stack<Node> localStack = new Stack<>();

            isRowEmpty = true;
            for (int i = 0; i < nBlanks; i++) {
                System.out.print(" ");
            }

            while (!globalStack.isEmpty()) {
                Node tempNode = globalStack.pop();
                if (tempNode != null) {
                    System.out.print(tempNode.getValue());
                    localStack.push(tempNode.getLeftChild());
                    localStack.push(tempNode.getRightChild());
                    if (tempNode.getLeftChild() != null || tempNode.getRightChild() != null) {
                        isRowEmpty = false;
                    }
                } else {
                    System.out.print("--");
                    localStack.push(null);
                    localStack.push(null);
                }

                for (int j = 0; j < nBlanks * 2 - 2; j++) {
                    System.out.print(" ");
                }
            }

            System.out.println();

            while (!localStack.isEmpty()) {
                globalStack.push(localStack.pop());
            }

            nBlanks /= 2;
        }
        System.out.println();
    }

    @Override
    public void traverse(TraverseMode traverseMode) {
        switch (traverseMode) {
            case IN_ORDER:
                inOrder(root);
                break;
            case PRE_ORDER:
                preOrder(root);
                break;
            case POST_ORDER:
                postOrder(root);
                break;
            default:
                throw new IllegalArgumentException("Unknown traverse mode " + traverseMode);
        }
    }

    private void postOrder(Node<E> node) {
        if (node == null) {
            return;
        }

        inOrder(node.getLeftChild());
        inOrder(node.getRightChild());
        System.out.println(node);
    }

    private void preOrder(Node<E> node) {
        if (node == null) {
            return;
        }

        System.out.println(node);
        inOrder(node.getLeftChild());
        inOrder(node.getRightChild());
    }

    private void inOrder(Node<E> node) {
        if (node == null) {
            return;
        }

        inOrder(node.getLeftChild());
        System.out.println(node);
        inOrder(node.getRightChild());
    }

    @Override
    public boolean isBalanced() {
        return isBalanced(root);
    }

    private boolean isBalanced(Node node) {
        return (node == null) ||
                isBalanced(node.getLeftChild()) &&
                        isBalanced(node.getRightChild()) &&
                        Math.abs(height(node.getLeftChild()) - height(node.getRightChild())) <= 1;
    }

    private int height(Node node) {
        return node == null ? 0 : 1 + Math.max(height(node.getLeftChild()), height(node.getRightChild()));
    }

    private class NodeAndPrevious {
        Node<E> current;
        Node<E> previous;

        public NodeAndPrevious(Node<E> current, Node<E> previous) {
            this.current = current;
            this.previous = previous;
        }
    }
}
