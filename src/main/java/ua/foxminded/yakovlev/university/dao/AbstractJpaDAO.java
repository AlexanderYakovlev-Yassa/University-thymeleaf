package ua.foxminded.yakovlev.university.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractJpaDAO<E extends Serializable, ID> {
 
   private Class<E> entityClass;
 
   @PersistenceContext
   EntityManager entityManager;
 
   public final void setEntityClass(Class<E> entityClassToSet){
      this.entityClass = entityClassToSet;
   }
 
   public E findById(ID id){
      return entityManager.find(entityClass, id);
   }
   
   @SuppressWarnings("unchecked")
   public List<E> findAll(){
      return entityManager.createQuery("from " + entityClass.getName())
       .getResultList();
   }
 
   public void save(E entity){
      entityManager.persist(entity);
   }
 
   public E update(E entity){
      return entityManager.merge(entity);
   }
 
   public void delete(E entity){
      entityManager.remove(entity);
   }
   
   public void deleteById(ID entityId){
      E entity = findById(entityId);
      delete(entity);
   }
}