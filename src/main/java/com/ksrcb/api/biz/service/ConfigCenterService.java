package com.ksrcb.api.biz.service;

import com.ksrcb.api.biz.model.configCenter.Environment;

import java.util.List;
import java.util.Map;

public interface ConfigCenterService {

    /**
     * 查询系统配置
     * @return
     */
    List<Map<String, Object>> qryLineage();


    /**
     * 查询所属系统环境
     * @return
     */
    List<Map<String, Object>> qryEnv(String system_no);

}
