package com.ksrcb.api.biz.model.system;

import lombok.Data;

import javax.persistence.Id;

@Data
public class Role {

    @Id
    private String role_id;

    private String description;

    private String role_type;

    private String remark;

    private String state;

}
