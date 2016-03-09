package com.testingSystem.dao;

import com.testingSystem.entity.Answer;
import com.testingSystem.entity.Question;
import com.testingSystem.util.HibernateSessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@SuppressWarnings("unchecked")
public class QuestionDao implements EntityDao<Question, Long> {

    @Autowired
    private SessionFactory sessionFactory;
    private Class<Question> clazz = Question.class;

    @Override
    public List<Question> getAll(String orderParameter) throws HibernateException {
        return (List<Question>) sessionFactory.getCurrentSession()
                .createCriteria(clazz)
                .addOrder(Order.asc(orderParameter))
                .list();
    }

    @Override
    public Question getById(Long id) throws HibernateException {
        return (Question) sessionFactory.getCurrentSession()
                .get(clazz, id);
    }

    @Override
    public Question get(String propertyName, Object value) throws HibernateException {
        List<Question> list = (List<Question>) sessionFactory.getCurrentSession()
                .createCriteria(clazz)
                .add(Restrictions.eq(propertyName, value))
                .list();
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public Long save(Question entity) throws HibernateException {
        return (Long) sessionFactory.getCurrentSession().save(entity);
    }

    @Override
    public void update(Question entity) throws HibernateException {
        sessionFactory.getCurrentSession().update(entity);
    }

    @Override
    public void delete(Question entity) throws HibernateException {
        sessionFactory.getCurrentSession().delete(entity);
    }

    public void setAnswers(Long questionId, List<Answer> answers) throws HibernateException {
        Question question = (Question) sessionFactory.getCurrentSession().get(Question.class, questionId);
        question.setAnswers(answers);
        sessionFactory.getCurrentSession().update(question);
    }

    public void setText(Long questionId, String text) throws HibernateException {
        Question question = (Question) sessionFactory.getCurrentSession().get(Question.class, questionId);
        question.setQuestionText(text);
        sessionFactory.getCurrentSession().update(question);
    }
}
