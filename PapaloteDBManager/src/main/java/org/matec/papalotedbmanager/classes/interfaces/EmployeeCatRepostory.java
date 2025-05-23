package org.matec.papalotedbmanager.classes.interfaces;

import org.matec.papalotedbmanager.classes.EmployeeCategory;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeCatRepostory extends CrudRepository<EmployeeCategory, Integer> {
}
