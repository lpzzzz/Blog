package com.lyp.service;

import com.lyp.domain.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Max;
import java.util.List;


public interface TypeService {

    /**
     * 新增分类
     * @param type
     * @return
     */
    Type saveType(Type type);

    /**
     * 根据id查询分类
     * @param id
     * @return
     */
    Type getType(Long id);

    /**
     * 根据name查询
     * @param name
     * @return
     */
    Type findByName(String  name);

    /**
     * 分页查询
     */
    Page<Type> listType(Pageable pageable);

    /**
     * 查询推荐分类
     */
    List<Type> listTypeTop(Integer size);

    /**
     * 修改分类
     */

    Type updateType(Long id,Type type);

    /**
     * 删除分类
     */

    void deleteType(Long id);

   List<Type> listType();

}
