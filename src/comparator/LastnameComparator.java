package comparator;

import java.io.Serializable;
import java.util.Comparator;

import model.Person;

public class LastnameComparator implements Comparator<Person>, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
    public int compare(Person o1, Person o2) {
        return o2.getLastname().compareToIgnoreCase(o1.getLastname());
    }
}