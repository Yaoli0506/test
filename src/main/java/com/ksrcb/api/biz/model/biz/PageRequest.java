package com.ksrcb.api.biz.model.biz;

import lombok.Data;

@Data
public class PageRequest {
    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
}
