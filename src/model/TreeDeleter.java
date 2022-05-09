package model;

import dataStructures.AvlTree;
import dataStructures.Node;

public class TreeDeleter extends Thread {
    private Node<Person> node;
    private AvlTree<Person> tree;

    public TreeDeleter(Node<Person> node, AvlTree<Person> tree) {
        this.node = node;
        this.tree = tree;
    }
    
    @Override
    public void run() {
        tree.delete(node);
    }
}