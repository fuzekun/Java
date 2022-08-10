package Lamba;

import java.util.ArrayList;
import java.util.List;

public class FilterEmployeeByAge implements MyPredicate<Empoyee>{
	@Override
	public boolean test(Empoyee t) {
		return t.getAge() >= 35;
		
	}
}
