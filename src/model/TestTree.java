package model;

import java.io.FileNotFoundException;

import comparator.NameComparator;
import dataStructures.AvlTree;

public class TestTree {
    AvlTree<Person> test = new AvlTree<Person>(new NameComparator(), 10);
    
    public void start() throws FileNotFoundException {
        addPerson("MACERLO");
        addPerson("FABIO");
        addPerson("SEBASTIAN");
        addPerson("ABEL");
        addPerson("PEPITO");
        addPerson("OMAR");
        test.delete(test.getRoot());
        test.checkHealth();
    }

    public void addPerson(String name) throws FileNotFoundException {
        test.add(new Person("a", name, "a", 20, 1, 170, "Colombia"));
    }
}
