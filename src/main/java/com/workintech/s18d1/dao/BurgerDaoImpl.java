package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import com.workintech.s18d1.exceptions.BurgerException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;

import java.util.List;
@Slf4j
@Repository
public class BurgerDaoImpl implements BurgerDao{

    private EntityManager em;

    @Autowired
    public BurgerDaoImpl(EntityManager em){
        this.em =em;
    }

    @Transactional
    @Override
    public Burger save(Burger burger) {
         em.persist(burger);
         return burger;
    }

    @Override
    public Burger findById(Long id) {
        Burger burger = em.find(Burger.class, id);
        if(burger  == null) throw new BurgerException("Burger couldnt be found" + id, HttpStatus.NOT_FOUND);
        return  em.find(Burger.class, id);
    }

    @Override
    public List<Burger> findAll() {
       return em.createQuery("SELECT b FROM Burger b", Burger.class).getResultList();
    }

    @Override
    public List<Burger> findByPrice(Integer price) {
        TypedQuery<Burger> query = em.createQuery("SELECT b FROM Burger b WHERE b.price > :price ORDER BY b.price DESC", Burger.class);
        query.setParameter("price", price);
        return query.getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        TypedQuery<Burger> query = em.createQuery("SELECT b FROM Burger b WHERE b.breadType = :breadType ORDER BY b.name DESC", Burger.class);
        query.setParameter("breadType", breadType);
        return query.getResultList();
    }

    @Override
    public List<Burger> findByContent(String content) {
        TypedQuery<Burger> query = em.createQuery("SELECT b FROM Burger b WHERE b.contents LIKE CONCAT('%',:content,'%') ORDER BY b.name DESC", Burger.class);
        query.setParameter("content", content);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Burger update(Burger burger) {
        return em.merge(burger);
    }
    @Transactional
    @Override
    public Burger remove(Long id) {
        Burger burger = em.find(Burger.class, id);
        em.remove(burger);
        return burger;
    }
}
