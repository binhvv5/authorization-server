package com.minde.authorizationserver.models.auth;

import com.minde.authorizationserver.models.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Table(name = "TBD_USER_AUTHORITIES")
@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class TbdUserAuthorities  extends AbstractEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "auth_id")
    private Long authId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "auth_name")
    private String authName;
//
//    @OneToMany
//    @JoinColumn(name = "auth_id")
//    private List<TbdUserPermission> permissions;
}
