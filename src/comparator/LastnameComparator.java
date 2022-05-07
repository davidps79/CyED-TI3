package comparator;

import java.util.Comparator;

import model.Person;

public class LastnameComparator implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        return o2.getLastname().compareTo(o1.getLastname());
    }
}