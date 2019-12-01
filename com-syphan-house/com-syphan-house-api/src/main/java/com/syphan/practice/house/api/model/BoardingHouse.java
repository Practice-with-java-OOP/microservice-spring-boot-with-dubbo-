package com.syphan.practice.house.api.model;

import com.syphan.practice.common.api.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "boarding_house")
public class BoardingHouse extends BaseEntity {

    @NotNull
    @Column(name = "user_id")
    private Integer userId;

    @NotBlank
    @Column(name = "user_name")
    private String username;

    @NotBlank
    @Column(name = "phone_number")
    private String userPhone;

    @NotBlank
    @Column(name = "house_name")
    private String houseName;

    @NotBlank
    @Column(name = "address")
    private String address;

    @NotNull
    @Column(name = "room_number")
    private int roomNum;
}
