package model;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

import comparator.FullnameComparator;

public class AVLTest {

    AvlTree test;
    FullnameComparator fullComparator;

	public void setupStage1() throws FileNotFoundException{
        test=new AvlTree(fullComparator, 10);
        
    }
    
    public void setupStage2() throws FileNotFoundException{
        test=new AvlTree(fullComparator, 10);
        Person person= new Person("id",  "Pedro", "lastname", 10, 0, 120, "nationality");
        test.add(person);
    }
    
    public void setupStage3() throws FileNotFoundException{
        test=new AvlTree(fullComparator, 10);
        Person person= new Person("id",  "Pedro", "lastname", 10, 0, 120, "nationality");
        test.add(person);
        test.delete(test.searchNode("id"));
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