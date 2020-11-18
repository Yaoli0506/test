package com.ksrcb.api.biz.model.system;

import lombok.Data;

import javax.persistence.Id;

@Data
public class User {
    @Id
    private String user_id;

    private String username;

    private String password;

    private String salt;

    private String user_type;

    private String state;

    private String mobile;

    private String email;

    private String create_time;

    private String update_time;

    private String asso_role;

    private String name;

    private String department;

    private String systemMgmr;

    private String belong_system;

    private boolean active;

}
