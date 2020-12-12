package dao;

import entity.Entity;
import java.util.List;

public interface Dao<T extends Entity> {
    void add(T t) throws DaoExcept;

    T getById(int id) throws DaoExcept;

    List<T> getList(Criteria criteria) throws DaoExcept;

    void delete(T t) throws DaoExcept;
}