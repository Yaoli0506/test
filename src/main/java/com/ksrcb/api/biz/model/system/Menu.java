package com.ksrcb.api.biz.model.system;

import lombok.Data;

@Data
public class Menu {

    private String menuId;

    private String parentId;

    private String menuName;

    private String state;

    private String orderId;

    private String description;

    private String menuType;

}
