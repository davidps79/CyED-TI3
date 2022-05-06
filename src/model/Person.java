package model;

import java.time.LocalDate;
import java.io.FileNotFoundException;
import java.io.Serializable;

public class Person implements Serializable {
	private String name;
    private String lastname;
    private int age;
    private LocalDate birthDate;
    private Gender gender;
    private int height;
    private String nationality;
    private String id;
    
    public Person(String id, String name, String lastname, int age, int gender, int height, String nationality) throws FileNotFoundException {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.height=height;
        this.nationality = nationality;        
        this.age = age;
        this.height = height;
        
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
}