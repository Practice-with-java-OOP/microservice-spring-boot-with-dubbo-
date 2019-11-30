package com.syphan.practice.common.api;

import com.syphan.practice.common.api.wrapper.PageWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends BaseEntity, ID extends Serializable> {
    public T save(T entity);

    public T getById(ID id);

    public List<T> getAllById(Iterable<ID> ids);

    public PageWrapper<T> list(Pageable pageable);

    public List<T> listAll(Sort sort);
}
