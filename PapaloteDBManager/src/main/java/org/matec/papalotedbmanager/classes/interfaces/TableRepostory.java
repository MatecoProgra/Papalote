package org.matec.papalotedbmanager.classes.interfaces;

import org.matec.papalotedbmanager.classes.TableEntity;
import org.springframework.data.repository.CrudRepository;

public interface TableRepostory extends CrudRepository<TableEntity,Integer> {
}
