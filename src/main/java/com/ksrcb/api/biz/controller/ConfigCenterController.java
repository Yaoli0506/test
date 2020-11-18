package com.ksrcb.api.biz.controller;

import com.ksrcb.api.biz.service.ConfigCenterService;
import com.ksrcb.api.common.bean.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/configCenter")
public class ConfigCenterController {

    @Autowired
    ConfigCenterService configCenterService;

    /**
     * 查询系统配置
     * @return
     */
    @GetMapping("/depolyConfig/qryLineage")
    public ResponseBean qryLineage(){
        List<Map<String, Object>>  lineageList =  configCenterService.qryLineage();
        return ResponseBean.success(lineageList);
    }

    /**
     * 查询所属系统环境
     * @return
     */
    @GetMapping("/depolyConfig/qryEnv")
    public ResponseBean qryEnv(String system_no){
        List<Map<String, Object>> environmentList =  configCenterService.qryEnv(system_no);
        return ResponseBean.success(environmentList);
    }

}
