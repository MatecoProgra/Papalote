package org.matec.papalotedbmanager.classes.interfaces;

import org.matec.papalotedbmanager.classes.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepostory extends CrudRepository<Product, Integer> {
}
