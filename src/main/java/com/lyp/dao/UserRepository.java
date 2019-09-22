package com.lyp.dao;


import com.lyp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface UserRepository extends JpaRepository<User , Long> ,JpaSpecificationExecutor<User> {

    /**
     * 根据用户名或密码查询数据库
     * @param username
     * @param password
     * @return
     */
    User findByUsernameAndPassword(String username , String password);
}
