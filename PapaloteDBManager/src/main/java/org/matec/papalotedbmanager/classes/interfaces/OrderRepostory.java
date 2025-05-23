package org.matec.papalotedbmanager.classes.interfaces;

import org.matec.papalotedbmanager.classes.OrderInfo;
import org.matec.papalotedbmanager.classes.Orders;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepostory extends CrudRepository<Orders,Integer> {
}
