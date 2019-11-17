package com.yanglei.service;

import java.util.List;

public interface PermissionService {

    List<String> queryPermissionKeyByUerName(Integer userId);
}
