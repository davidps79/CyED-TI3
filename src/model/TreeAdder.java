package model;

public class TreeAdder extends Thread {
    private Person person;
    private AvlTree<Person> tree;

    public TreeAdder(Person person, AvlTree<Person> tree) {
        this.person = person;
        this.tree = tree;
    }
    
    @Override
    public void run() {
        tree.add(person);
    }
}
