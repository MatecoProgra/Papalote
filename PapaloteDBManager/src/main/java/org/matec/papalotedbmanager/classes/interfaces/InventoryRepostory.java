package org.matec.papalotedbmanager.classes.interfaces;

import org.matec.papalotedbmanager.classes.Inventory;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepostory extends CrudRepository<Inventory, Integer> {
}
