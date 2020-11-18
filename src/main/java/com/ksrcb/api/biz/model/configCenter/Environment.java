package com.ksrcb.api.biz.model.configCenter;

import lombok.Data;

@Data
public class Environment {

    private String system_no;

    private String env_no;

    private String env_name;

    private String host_ip;

    private String host_name;
}
