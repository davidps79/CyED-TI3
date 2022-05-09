package model;

import java.time.LocalDate;
import dataStructures.Node;

import java.io.FileNotFoundException;
import java.io.Serializable;

public class Person implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name;
    private String lastname;
    private int age;
    private LocalDate birthDate;
    private Gender gender;
    private int height;
    private String nationality;
    private String id;
    private String pictureLocation;
	private Node<Person> nodeTree1;
	private Node<Person> nodeTree2;
	private Node<Person> nodeTree3;
	private Node<Person> nodeTree4;

    public Person(String id, String name, String lastname, int age, int gender, int height, String nationality) throws FileNotFoundException {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.nationality = nationality;        
        this.age = age;
        this.height = height;
        this.pictureLocation = null;
        
        //Calcular fecha de nacimiento
        int birthYear = 2022-age;
        int birthMonth = randomNumberBetween(1, 12);
        int rangeEnd;
        if (birthMonth == 2) rangeEnd = 28;
        else if (birthMonth == 1 || birthMonth == 3 ||
        birthMonth == 5 || birthMonth ==7 || birthMonth == 8
        || birthMonth == 10 || birthMonth == 12) rangeEnd = 31;
        else rangeEnd = 30;
        int birthDay = randomNumberBetween(1, rangeEnd);
        this.birthDate = LocalDate.of(birthYear, birthMonth, birthDay);

        // Género
        if (gender == 1) this.gender = Gender.FEMALE;
        else this.gender = Gender.MALE;
    }
    
    public Person(String id, String name, String lastname, int age, String gender, int height, 
    String nationality, LocalDate birthDate, String pictureLocation) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.gender = Gender.FEMALE;
        if (gender.equals("Masculino")) this.gender = Gender.MALE;
        this.height = height;
        this.nationality = nationality;        
        this.age = age;
        this.pictureLocation = null;
        this.birthDate = birthDate;
        this.pictureLocation = pictureLocation;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
    
    public int randomNumberBetween(int min, int max) {
        return (int) (Math.random() * (max + 1 - min)) + min;
    }

    
    public String toString2() {
        return id + ": " + name + " " + lastname + " " + gender.toString().toLowerCase() + " " + age + " years - Birth: " + birthDate.toString() +" Height: " + height + " cm" + "; Country: " + nationality;
    }

    @Override
    public String toString() {
        return name;
    }

	public String getFullname() {
		return name + " " + lastname;
	}

	public String getPictureLocation() {
		return pictureLocation;
	}

	public void setPictureLocation(String pictureLocation) {
		this.pictureLocation = pictureLocation;
	}
	
	public Node<Person> getTree1() {
		return nodeTree1;
	}
	
	public Node<Person> getTree2() {
		return nodeTree2;
	}
	
	public Node<Person> getTree3() {
		return nodeTree3;
	}
	
	public Node<Person> getTree4() {
		return nodeTree4;
	}
	
	public void setTree1(Node<Person> nodeTree1) {
		this.nodeTree1 = nodeTree1;
	}
	
	public void setTree2(Node<Person> nodeTree2) {
		this.nodeTree2 = nodeTree2;
	}
	
	public void setTree3(Node<Person> nodeTree3) {
		this.nodeTree3 = nodeTree3;
	}
	
	public void setTree4(Node<Person> nodeTree4) {
		this.nodeTree4 = nodeTree4;
	}
}