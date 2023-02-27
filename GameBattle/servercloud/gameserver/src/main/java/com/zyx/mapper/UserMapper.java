package com.zyx.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zyx.pojo.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 张宇森
 * @version 1.0
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
