package com.lyp.service.impl;

import com.lyp.dao.TypeRepository;
import com.lyp.domain.Type;
import com.lyp.exception.NotFoundException;
import com.lyp.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional // 将他们放到一个事务中
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    /**
     * 通过id查询 Type
     * @param id
     * @return
     */
    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    /**
     * 根据name查询
     * @param name String
     * @return Type
     */
    @Override
    public Type findByName(String name) {
        return typeRepository.findByName(name);
    }

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC,"blogs.size");
        Pageable pageable = new PageRequest(0,size,sort);
        return typeRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findById(id).orElse(null);
        if (t == null) {
            throw new NotFoundException("不存在该类型!");
        }
        BeanUtils.copyProperties(type,t); // 将type中的值复制到t中
        return typeRepository.save(t);
    }

    @Override
    public void deleteType(Long id) {
        typeRepository.deleteById(id);
    }

    /**
     * 查询分类列表
     * @return
     */
    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }


}
