package com.syphan.practice.registration.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.syphan.practice.common.api.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
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
@Table(name = "Role")
public class Role extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private String code;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "permission_id", referencedColumnName = "id"))
    private Set<Permission> permissions = new HashSet<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    @Transient
    private Set<User> users = new HashSet<>();
}
