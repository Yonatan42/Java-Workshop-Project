package com.yoni.javaworkshopprojectserver.service;

import com.yoni.javaworkshopprojectserver.EntityManagerSingleton;
import com.yoni.javaworkshopprojectserver.utils.Logger;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.*;

public abstract class BaseService {

    private static final String TAG = "BaseService";

    @EJB
    private EntityManagerSingleton entityManagerBean;
   
    protected EntityManager getEntityManager(){
        return entityManagerBean.getEntityManager();
    }

    
    protected <T> Query createSelectQuery(Class<T> entityType, BiFunction<Root<T>, CriteriaBuilder, List<Predicate>> predicateBuilder){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityType);
        Root<T> entity = criteriaQuery.from(entityType);
        criteriaQuery.select(entity);
        List<Predicate> predicates = predicateBuilder.apply(entity, builder);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        Query query = getEntityManager().createQuery(criteriaQuery);
        return query;
    }

    protected <T> T firstOrNull(List<T> list){
        if(list.isEmpty()){
            return null;
        }
        return list.get(0);
    }

    protected void withTransaction(Runnable action) {
        EntityTransaction transaction = getEntityManager().getTransaction();
        if (!transaction.isActive()) {
            transaction.begin();
        }
        action.run();
        transaction.commit();
    }

    protected <T> Runnable addValidation(T entity, Runnable action){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(entity);
        if(constraintViolations.size() > 0){
            StringBuilder constraintViolationMsg = new StringBuilder();
            for (ConstraintViolation<T> violation : constraintViolations) {
                constraintViolationMsg
                        .append('\n')
                        .append("violation: ")
                        .append(violation.getRootBeanClass().getName())
                        .append(".")
                        .append(violation.getPropertyPath())
                        .append(" ")
                        .append(violation.getMessage());
            }
            Logger.logError(TAG, "Constraint Violations: "+constraintViolationMsg);
            throw new ConstraintViolationException(constraintViolationMsg.toString(), constraintViolations);
        }else{
            return action;
        }
    }

}
