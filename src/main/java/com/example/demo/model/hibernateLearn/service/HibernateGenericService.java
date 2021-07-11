package com.example.demo.model.hibernateLearn.service;

import java.util.List;

/**
 * Basic interface with CRUD methods
 *
 * @param <T> - entity
 */
public interface HibernateGenericService<T> {

    int save(T t);

    List<T> findAll();

    List<T> findAll(int offset, int limit);

    T findById(Long id);

    int update(Long id);

    int deleteById();

}
