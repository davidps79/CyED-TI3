package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import comparator.*;
import controller.MenuViewController;
import dataStructures.AvlTree;
import dataStructures.Node;

public class Database implements Serializable {
	private static final long serialVersionUID = 1L;
	private int newPersonCounter;
    private final int SEARCH_COINCIDENCES = 100;
    private AvlTree<Person> peopleByName;
    private AvlTree<Person> peopleByLastname;
    private AvlTree<Person> peopleByFullname;
    private AvlTree<Person> peopleById;
    private boolean alreadyGenerated;
    private int totalPeople;

    public Database() {
    	newPersonCounter = 0;
    	alreadyGenerated = false;
    	setup();
    }
    
	public int getNewPersonCounter() {
		return newPersonCounter;
	}

	public void addNewPersonCounter() {
		newPersonCounter++;
		totalPeople++;
	}
	
	public void setNewPersonCounter(int newPersonCounter) {
		this.newPersonCounter = newPersonCounter;
	}

    
    public void setup() {
    	peopleByName = new AvlTree<Person>("1", new NameComparator(), SEARCH_COINCIDENCES);
        peopleByLastname = new AvlTree<Person>("2", new LastnameComparator(), SEARCH_COINCIDENCES);
        peopleByFullname = new AvlTree<Person>("3", new FullnameComparator(), SEARCH_COINCIDENCES);
        peopleById = new AvlTree<Person>("4", new IdComparator(), SEARCH_COINCIDENCES);
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
    	this.totalPeople = totalPeople;
    	DataGenerator generator = new DataGenerator(totalPeople, controller, this);
    	generator.start();
    	alreadyGenerated = true;
    }

	public void generateAgain(int totalPeople, MenuViewController controller) {
		//alreadyGenerated = false;
		this.totalPeople = totalPeople;
		clearImagesFolder();
	    peopleByName = null;
	    peopleByLastname = null;
	    peopleByFullname = null;
	    peopleById = null;
	    setup();
	    generateData(totalPeople, controller);
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
		totalPeople--;
		Node<Person> node1 = person.getTree1();
		Node<Person> node2 = person.getTree2();
		Node<Person> node3 = person.getTree3();
		Node<Person> node4 = person.getTree4();
		
		TreeDeleter deleter1 = new TreeDeleter(node1, peopleByName); deleter1.start();
		TreeDeleter deleter2 = new TreeDeleter(node2, peopleByFullname); deleter2.start();
		TreeDeleter deleter3 = new TreeDeleter(node3, peopleByLastname); deleter3.start();
		TreeDeleter deleter4 = new TreeDeleter(node4, peopleById); deleter4.start();
		
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
	
	public boolean deleteFile(String sPath) {
		boolean flag = false;
		File file = new File(sPath);
		if (file.isFile() && file.exists()) {
		    file.delete();
		    flag = true;
		}
		return flag;
	}
	
	public void clearImagesFolder() {
		File dir = new File("./files/image/generated/");
		File[] files = dir.listFiles();
		boolean flag;
	    for (int i = 0; i < files.length; i++) {
	        flag = deleteFile(files[i].getAbsolutePath());
	        if (!flag) break;
	    }
	}
	
	public boolean getAlreadyGenerated() {
		return alreadyGenerated;
	}

	public void setAlreadyGenerated(boolean alreadyGenerated) {
		this.alreadyGenerated = alreadyGenerated;
	}

	public int getTotalPeople() {
		return totalPeople;
	}
}