package com.workintech.s18d1.dao;

import com.workintech.s18d1.entity.BreadType;
import com.workintech.s18d1.entity.Burger;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
        return  em.find(Burger.class, id);
    }

    @Override
    public List<Burger> findAll() {
       return em.createQuery("SELECT b FROM Burger b", Burger.class).getResultList();
    }

    @Override
    public List<Burger> findByPrice(Integer price) {
        return em.createQuery("Select b from Burger b Where b.price = :price", Burger.class)
                .setParameter("price" , price).getResultList();
    }

    @Override
    public List<Burger> findByBreadType(BreadType breadType) {
        return em.createQuery("Select b FROM Burger b WHERE b.breadType = :breadType ORDER BY b.name",
                Burger.class)
                .setParameter("breadType", breadType)
                .getResultList();
    }

    @Override
    public List<Burger> findByContent(String contents) {
        return em.createQuery("Select b FROM Burger b WHERE b.contents = :contents", Burger.class)
                .setParameter("contents" , contents)
                .getResultList();
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
