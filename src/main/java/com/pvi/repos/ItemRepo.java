package com.pvi.repos;

import com.pvi.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> {

    @Query(value = "select * from item where order_id IS NULL", nativeQuery = true)
    List<Item> getNullItem();

    @Query(value = "select * from item where order_id IS NULL AND computer_id=?1", nativeQuery = true)
    List<Item> getAvailableComp(Long computerID);
}
