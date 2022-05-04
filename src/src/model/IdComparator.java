package model;

import java.util.Comparator;

public class IdComparator implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        return o2.getId().compareTo(o1.getId());
    }
}