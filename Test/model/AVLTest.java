package model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.FileNotFoundException;
import comparator.FullnameComparator;
import dataStructures.AvlTree;

public class AVLTest {

    AvlTree<Person> test;
    FullnameComparator fullComparator=new FullnameComparator();

	public void setupStage1() throws FileNotFoundException{
        test = new AvlTree<>(fullComparator, 10);
        Person person= new Person("id",  "Pedro", "lastname", 10, 0, 120, "nationality");
        test.add(person);
        Person person1= new Person("id",  "Ramiro", "lastname", 10, 0, 120, "nationality");
        test.add(person1);
        Person person2= new Person("id",  "Omar", "lastname", 10, 0, 120, "nationality");
        test.add(person2);
        Person person3= new Person("id",  "Martha", "lastname", 10, 0, 120, "nationality");
        test.add(person3);
        Person person4= new Person("id",  "Nadia", "lastname", 10, 0, 120, "nationality");
        test.add(person4);
    }
    
    public void setupStage2() throws FileNotFoundException{
        test = new AvlTree<>(fullComparator, 10);
        Person person= new Person("id",  "Pedro", "lastname", 10, 0, 120, "nationality");
        test.add(person);
    }
    
    public void setupStage3() throws FileNotFoundException{
        test = new AvlTree<>(fullComparator, 10);
        Person person= new Person("id",  "Pedro", "lastname", 10, 0, 120, "nationality");
        test.add(person);
        test.delete(test.searchNode("id"));
    }
    
    @Test
    void testBalance() throws FileNotFoundException{
        setupStage1();
        assertEquals("Pedro", ((Person)test.getRoot().getItem()).getName());
        assertEquals("Nadia", ((Person)test.getRoot().getLeft().getItem()).getName());
        assertEquals("Martha", ((Person)test.getRoot().getLeft().getLeft().getItem()).getName());
        assertEquals("Omar", ((Person)test.getRoot().getLeft().getRight().getItem()).getName());
        assertEquals("Ramiro", ((Person)test.getRoot().getRight().getItem()).getName());
    }
    
    @Test
    void testSearch() throws FileNotFoundException{
        setupStage2();
        assertEquals("Pedro",test.searchId("id"));
    }

    @Test
    void testAdd() throws FileNotFoundException{
        setupStage2();
        assertEquals(false,test.isEmpty());
    }
    
    @Test
    void testDelete() throws FileNotFoundException{
        setupStage3();
        assertEquals(true,test.isEmpty());
    }


}
