package com.yoni.javaworkshopprojectserver.service;

import com.yoni.javaworkshopprojectserver.EntityManagerSingleton;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public abstract class BaseService {

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

    public void withTransaction(Runnable action){
        getEntityManager().getTransaction().begin();
        action.run();
        getEntityManager().getTransaction().commit();
    }

}
