package model;

import dataStructures.AvlTree;

public class TreeDeleter extends Thread {
    private Person person;
    private AvlTree<Person> tree;

    public TreeDeleter(Person person, AvlTree<Person> tree) {
        this.person = person;
        this.tree = tree;
    }
    
    @Override
    public void run() {
        tree.delete(person.getNode());
        System.out.println("ELIMINANDOOOOOOOO " + this.toString());
    }
}