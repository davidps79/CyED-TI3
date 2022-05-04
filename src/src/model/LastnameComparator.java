package model;

import java.util.Comparator;

public class LastnameComparator implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        return o2.getLastName().compareTo(o1.getLastName());
    }
}