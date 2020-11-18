package com.ksrcb.api.biz.model.system;

import lombok.Data;

import javax.persistence.Id;

@Data
public class UserRole {

    @Id
    private String user_id;

    private String role_id;
}
