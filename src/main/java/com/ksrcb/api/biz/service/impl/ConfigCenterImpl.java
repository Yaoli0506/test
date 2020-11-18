package com.ksrcb.api.biz.service.impl;

import com.ksrcb.api.biz.mapper.ConfigCenterMapper;
import com.ksrcb.api.biz.service.ConfigCenterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ConfigCenterImpl implements ConfigCenterService {

    @Autowired
    private ConfigCenterMapper configCenterMapper;

    /**
     * 查询系统配置
     * @return
     */
    @Override
    public List<Map<String, Object>> qryLineage() {
        List<Map<String, Object>> lineageList = configCenterMapper.qryLinage();
        return lineageList;
    }

    @Override
    public List<Map<String, Object>> qryEnv(String system_no) {
        List<Map<String, Object>> envList = configCenterMapper.qryEnv(system_no);
        return envList;
    }
}
