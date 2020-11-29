package org.example.mapper;

import org.apache.ibatis.annotations.SelectProvider;
import org.example.model.Privilege;
import org.example.provider.PrivilegeProvider;

public interface PrivilegeMapper {
    @SelectProvider(type = PrivilegeProvider.class, method = "selectById")
    Privilege selectById(Long id);
}
