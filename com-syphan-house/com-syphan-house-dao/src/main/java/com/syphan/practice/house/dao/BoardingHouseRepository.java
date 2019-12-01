package com.syphan.practice.house.dao;

import com.syphan.practice.common.dao.JpaQueryRepository;
import com.syphan.practice.house.api.model.BoardingHouse;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardingHouseRepository extends JpaQueryRepository<BoardingHouse, Integer> {
}
