package org.matec.papalotedbmanager.classes.interfaces;

import org.matec.papalotedbmanager.classes.ProductCategory;
import org.springframework.data.repository.CrudRepository;

public interface ProductCatRepostory extends CrudRepository<ProductCategory, Integer> {
}
