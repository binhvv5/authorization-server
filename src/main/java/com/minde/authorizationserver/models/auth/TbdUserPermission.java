package com.minde.authorizationserver.models.auth;

import com.minde.authorizationserver.models.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Table(name = "TBD_USER_PERMISSION ")
@Data
@Entity
@EqualsAndHashCode(callSuper=false)
public class TbdUserPermission extends AbstractEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "auth_id")
    private Long authId;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "can_create")
    private Boolean canCreate;

    @Column(name = "can_read")
    private Boolean canRead;

    @Column(name = "can_update")
    private Boolean canUpdate;

    @Column(name = "can_delete")
    private Boolean canDelete;
}
