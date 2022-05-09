package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import comparator.FullnameComparator;
import comparator.IdComparator;
import dataStructures.AvlTree;

public class AvlTest {

    AvlTree<Person> test;
    FullnameComparator fullComparator=new FullnameComparator();
    AvlTree<Person> test1;
    IdComparator idComparator=new IdComparator();

	public void setupStage1() throws FileNotFoundException{
        test=new AvlTree<Person>("3",fullComparator, 10);
        test1=new AvlTree<Person>("4",idComparator, 10);
        Person person= new Person("id5",  "Pedro", "lastname", 10, 0, 120, "nationality");
        test.add(person);
        test1.add(person);
        Person person1= new Person("id6",  "Ramiro", "lastname", 10, 0, 120, "nationality");
        test.add(person1);
        test1.add(person1);
        Person person2= new Person("id4",  "Omar", "lastname", 10, 0, 120, "nationality");
        test.add(person2);
        test1.add(person2);
        Person person3= new Person("id2",  "Martha", "lastname", 10, 0, 120, "nationality");
        test.add(person3);
        test1.add(person3);
        Person person4= new Person("id3",  "Nadia", "lastname", 10, 0, 120, "nationality");
        test.add(person4);
        test1.add(person4);
    }
    
    public void setupStage2() throws FileNotFoundException{
    	  test=new AvlTree<Person>("3",fullComparator, 10);
        Person person= new Person("id",  "Pedro", "lastname", 10, 0, 120, "nationality");
        test.add(person);
    }
    
    public void setupStage3() throws FileNotFoundException{
    	  test=new AvlTree<Person>("3",fullComparator, 10);
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
    
    @Test
    void testDelete1() throws FileNotFoundException{
        setupStage1();
        test1.delete(test1.searchNode("id3"));
        assertEquals("Pedro", ((Person)test1.getRoot().getItem()).getName());
        assertEquals("Omar", ((Person)test1.getRoot().getLeft().getItem()).getName());
        assertEquals("Martha", ((Person)test1.getRoot().getLeft().getLeft().getItem()).getName());
        assertEquals("Ramiro", ((Person)test1.getRoot().getRight().getItem()).getName());
    }


}