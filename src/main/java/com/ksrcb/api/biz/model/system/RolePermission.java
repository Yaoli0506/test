package com.ksrcb.api.biz.model.system;

import lombok.Data;

@Data
public class RolePermission {

    private String role_id;

    private String permission_id;

    private String operator;
}
