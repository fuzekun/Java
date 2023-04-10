package com.springboot.dto.bookStore;

import com.springboot.entity.bookStore.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.context.annotation.Bean;

import javax.xml.namespace.QName;
import java.util.List;

/**
 * @author: Zekun Fu
 * @date: 2023/4/10 17:23
 * @Description:
 */


@Mapper
public interface BookStoreUserMapper {
    public List<User>findAllUser();
}
