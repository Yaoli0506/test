package com.ksrcb.api.biz.model.configCenter;

import lombok.Data;

@Data
public class DepolyConfig {

    private String lineage;

    private String lineage_name;

    private String host_ip;

    private String appService;

    private String compile_language;

    private String middleware;

    private String middleware_version;

    private String compile_way;

    private String code_address;

    private String rollback_version;

    private String release;
}
