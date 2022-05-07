package comparator;

import java.util.Comparator;

import model.Person;

public class IdComparator implements Comparator<Person> {
    @Override
    public int compare(Person o1, Person o2) {
        return o2.getId().compareTo(o1.getId());
    }
}