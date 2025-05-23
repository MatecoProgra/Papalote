package org.matec.papalotedbmanager.classes.interfaces;

import org.matec.papalotedbmanager.classes.OrderInfo;
import org.springframework.data.repository.CrudRepository;

public interface OrderInfoRepostory extends CrudRepository<OrderInfo, Integer> {
}
