package com.lyp.dao;

import com.lyp.domain.Type;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type,Long> ,JpaSpecificationExecutor<Type>{

    /**
     * 根据那么查询分类
     */
    Type findByName(String name);


    /**
     * 按照分类所对应的个数的大小有大到小进行排序 取钱6个
     */
    @Query("select t from Type t")
    List<Type> findTop(Pageable pageable );
}
