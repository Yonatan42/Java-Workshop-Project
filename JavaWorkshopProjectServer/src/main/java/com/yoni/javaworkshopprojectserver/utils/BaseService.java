package com.yoni.javaworkshopprojectserver.utils;

import com.yoni.javaworkshopprojectserver.EntityManagerSingleton;
import java.util.List;
import java.util.function.BiFunction;
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
   
    private EntityManager getEntityManager(){
        return entityManagerBean.getEntityManager();
    }

    
    // todo, perhaps generalize this and get a Class and make it work fo all types
    pro <T> Query createSelectQuery(Class<T> entityType, BiFunction<Root<T>, CriteriaBuilder, List<Predicate>> predicateBuilder){
        CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityType);
        Root<T> entity = criteriaQuery.from(entityType);
        criteriaQuery.select(entity);
        List<Predicate> predicates = predicateBuilder.apply(entity, builder);
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        Query query = getEntityManager().createQuery(criteriaQuery);
        return query;
    }
    


}
