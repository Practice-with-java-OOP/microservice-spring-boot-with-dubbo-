package com.syphan.practice.registration.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syphan.practice.common.api.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "permission")
public class Permission extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private String code;

    @Transient
    @JsonIgnore
    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();
}
