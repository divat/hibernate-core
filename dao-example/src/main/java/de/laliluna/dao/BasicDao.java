package de.laliluna.dao;


import java.util.List;

public interface BasicDao<T> {
    T findById(Integer id);

    void update(T entity);

    void reattach(T entity);

    void save(T entity);

    void delete(T entity);

    List findAll();
}
