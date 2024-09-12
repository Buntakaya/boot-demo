package com.example.BootSample.mapper;

import com.example.BootSample.entity.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface UserInfoMapper {

    public Optional<UserInfoEntity> selectById(String id);
}
