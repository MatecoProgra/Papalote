package org.matec.papalotedbmanager.classes.interfaces;

import org.matec.papalotedbmanager.classes.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepostory extends CrudRepository<Employee,Integer> {
}
