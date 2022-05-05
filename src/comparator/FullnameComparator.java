package comparator;

import java.util.Comparator;

import model.Person;

public class FullnameComparator implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        String s1= o2.getName()+o2.getLastName();
        String s2= o1.getName()+o1.getLastName();
        return s1.compareTo(s2);
    }
}