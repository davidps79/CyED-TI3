package model;

import java.util.ArrayList;

import comparator.*;
import controller.MenuViewController;
import dataStructures.AvlTree;
import dataStructures.Node;

public class Database {
    private final int SEARCH_COINCIDENCES = 100;
    private AvlTree<Person> peopleByName;
    private AvlTree<Person> peopleByLastname;
    private AvlTree<Person> peopleByFullname;
    private AvlTree<Person> peopleById;
    
    public Database() {
    	peopleByName = new AvlTree<Person>(new NameComparator(), SEARCH_COINCIDENCES);
        peopleByLastname = new AvlTree<Person>(new LastnameComparator(), SEARCH_COINCIDENCES);
        peopleByFullname = new AvlTree<Person>(new FullnameComparator(), SEARCH_COINCIDENCES);
        peopleById = new AvlTree<Person>(new IdComparator(), SEARCH_COINCIDENCES);
    }
    
    public ArrayList<Person> getCoincidences(String filter, String toSearch) {
    	ArrayList<Person> arr = new ArrayList<Person>();
    	
    	switch(filter) {
    	case "Nombre":
        	arr = peopleByName.searchCoincidence(filter, toSearch);
    		break;
    	case "Apellido":
        	arr = peopleByLastname.searchCoincidence(filter, toSearch);
    		break;
    	case "Código":
        	arr = peopleById.searchCoincidence(filter, toSearch);
    		break;
    	case "Nombre completo":
        	arr = peopleByFullname.searchCoincidence(filter, toSearch);
    		break;
    	}

    	
        return arr;
    }
    
    public void generateData(int totalPeople, MenuViewController controller) {
    	DataGenerator generator = new DataGenerator(totalPeople, controller, this);
    	generator.start();
    }

	public AvlTree<Person> getPeopleByFullname() {
		return peopleByFullname;
	}
	
	public AvlTree<Person> getPeopleByName() {
		return peopleByName;
	}
	
	public AvlTree<Person> getPeopleById() {
		return peopleById;
	}
	
	public AvlTree<Person> getPeopleByLastname() {
		return peopleByLastname;
	}

	public void deletePerson(Person person) throws InterruptedException {
		TreeDeleter deleter1 = new TreeDeleter(person, peopleByName); deleter1.start();
		TreeDeleter deleter2 = new TreeDeleter(person, peopleByFullname); deleter2.start();
		TreeDeleter deleter3 = new TreeDeleter(person, peopleByLastname); deleter3.start();
		TreeDeleter deleter4 = new TreeDeleter(person, peopleById); deleter4.start();
		
		deleter1.join();
		deleter2.join();
		deleter3.join();
		deleter4.join();
	}

	public void addPerson(Person person) throws InterruptedException {
        TreeAdder add1 = new TreeAdder(person, peopleByName); add1.start();
        TreeAdder add2 = new TreeAdder(person, peopleByLastname); add2.start();
        TreeAdder add3 = new TreeAdder(person, peopleByFullname); add3.start();
        TreeAdder add4 = new TreeAdder(person, peopleById); add4.start();

        add1.join();
        add2.join();
        add3.join();
        add4.join();
	}
}