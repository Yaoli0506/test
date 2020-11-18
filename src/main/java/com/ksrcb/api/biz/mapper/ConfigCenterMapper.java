package com.ksrcb.api.biz.mapper;

import com.ksrcb.api.biz.model.configCenter.DepolyConfig;
import com.ksrcb.api.biz.model.configCenter.Environment;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

public interface ConfigCenterMapper extends Mapper<DepolyConfig> {

    /**
     * 查询系统配置
     * @return
     */
    List<Map<String, Object>> qryLinage();

    /**
     * 查询系统所属环境
     * @return
     */
    List<Map<String, Object>> qryEnv(String system_no);


}
