package model;

import java.io.FileNotFoundException;

import comparator.NameComparator;

public class TestTree {
    AvlTree<Person> test = new AvlTree<>(new NameComparator());
    
    public void start() throws FileNotFoundException {
        addPerson("MARCELO");
        addPerson("SEBASTIAN");
        addPerson("FABIO");
        addPerson("ABEL");
        addPerson("BERNARDO");        
        test.checkHealth();
    }

    public void addPerson(String name) throws FileNotFoundException{
        test.add(new Person("a", name, "a", 20, 1, 170, "Colombia"));
    }
}
