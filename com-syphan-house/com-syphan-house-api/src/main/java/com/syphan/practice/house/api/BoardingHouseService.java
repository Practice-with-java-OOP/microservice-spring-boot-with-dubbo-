package com.syphan.practice.house.api;

import com.syphan.practice.common.api.BaseService;
import com.syphan.practice.common.api.exception.BIZException;
import com.syphan.practice.house.api.dto.BoardingHouseCreateDto;
import com.syphan.practice.house.api.model.BoardingHouse;

public interface BoardingHouseService extends BaseService<BoardingHouse, Integer> {

    BoardingHouse create(int userId, BoardingHouseCreateDto data) throws BIZException;
}
