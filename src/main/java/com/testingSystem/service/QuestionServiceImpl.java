package com.testingSystem.service;

import com.testingSystem.dao.QuestionDao;
import com.testingSystem.entity.Answer;
import com.testingSystem.entity.Question;
import com.testingSystem.util.HibernateSessionUtil;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService {

    @Autowired private QuestionDao questionDao;
    @Autowired private HibernateSessionUtil sessionUtil;

    @Override
    public List<Question> getAll(String orderParameter) throws HibernateException {
        List<Question> questions = questionDao.getAll(orderParameter);
        return questions;
    }

    @Override
    public Question getById(Long id) throws HibernateException {
        Question question = questionDao.getById(id);
        return question;
    }

    @Override
    public Question get(String propertyName, Object value) throws HibernateException {
        Question question = questionDao.get(propertyName, value);
        return question;
    }

    @Override
    public Long save(Question entity) throws HibernateException {
        Long generatedId = questionDao.save(entity);
        return generatedId;
    }

    @Override
    public void update(Question entity) throws HibernateException {
        questionDao.update(entity);
    }

    @Override
    public void delete(Question entity) throws HibernateException {
        questionDao.delete(entity);
    }

    @Override
    public void setAnswers(Long questionId, List<Answer> answers) throws HibernateException {
        questionDao.setAnswers(questionId, answers);
    }

    @Override
    public void setText(Long questionId, String text) throws HibernateException {
        questionDao.setText(questionId, text);
    }
}