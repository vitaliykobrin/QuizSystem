package com.testing_system.dao;

import com.testing_system.entity.Answer;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@SuppressWarnings("unchecked")
public class AnswerDao implements EntityDao<Answer, Integer> {

    @Autowired
    private SessionFactory sessionFactory;
    private Class<Answer> clazz = Answer.class;

    @Override
    public List<Answer> getAll(String orderParameter) throws HibernateException {
        return (List<Answer>) sessionFactory.getCurrentSession()
                .createCriteria(clazz)
                .addOrder(Order.asc(orderParameter))
                .list();
    }

    @Override
    public Answer getById(Integer id) throws HibernateException {
        return (Answer) sessionFactory.getCurrentSession()
                .get(clazz, id);
    }

    @Override
    public Answer get(String propertyName, Object value) throws HibernateException {
        List<Answer> list = (List<Answer>) sessionFactory.getCurrentSession()
                .createCriteria(clazz)
                .add(Restrictions.eq(propertyName, value))
                .list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Integer save(Answer entity) throws HibernateException {
        return (Integer) sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void update(Answer entity) throws HibernateException {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    public void delete(Answer entity) throws HibernateException {
        sessionFactory.getCurrentSession().delete(entity);
    }
}
