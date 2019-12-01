package com.syphan.practice.house.service.impl;

import com.syphan.practice.common.api.enumclass.ErrType;
import com.syphan.practice.common.api.exception.BIZException;
import com.syphan.practice.common.service.base.BaseServiceImpl;
import com.syphan.practice.house.api.BoardingHouseService;
import com.syphan.practice.house.api.dto.BoardingHouseCreateDto;
import com.syphan.practice.house.api.model.BoardingHouse;
import com.syphan.practice.house.dao.BoardingHouseRepository;
import com.syphan.practice.registration.api.UserService;
import com.syphan.practice.registration.api.model.User;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@org.apache.dubbo.config.annotation.Service
public class BoardingHouseServiceImpl extends BaseServiceImpl<BoardingHouse, Integer> implements BoardingHouseService {

    private BoardingHouseRepository repository;

    @Autowired
    public BoardingHouseServiceImpl(BoardingHouseRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Reference
    private UserService userService;

    @Override
    public BoardingHouse create(int userId, BoardingHouseCreateDto data) throws BIZException {
        User user = userService.getById(userId);
        if (user == null) throw BIZException.buildBIZException(ErrType.NOT_FOUND,
                "user.not.found", String.format("%s%s%s", "User with id [ ", userId, " ] not fount"));
        BoardingHouse house = new BoardingHouse();
        house.setUserId(userId);
        house.setUsername(user.getFullName() != null ? user.getFullName() : user.getUsername());
        house.setUserPhone(user.getPhoneNum());
        house.setHouseName(data.getName());
        house.setAddress(data.getAddress());
        house.setRoomNum(data.getRoomNum());
        return repository.save(house);
    }
}
