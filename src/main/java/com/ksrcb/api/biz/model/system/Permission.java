package com.ksrcb.api.biz.model.system;

import lombok.Data;

@Data
public class Permission {

   private String permission_id;

   private String func_id;

   private String state;

   private String permission;
}
