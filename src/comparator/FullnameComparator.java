package comparator;

import java.io.Serializable;
import java.util.Comparator;

import model.Person;

public class FullnameComparator implements Comparator<Person>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
    public int compare(Person o1, Person o2) {
        String s1= o2.getName()+o2.getLastname();
        String s2= o1.getName()+o1.getLastname();
        return s1.compareToIgnoreCase(s2);
    }
}