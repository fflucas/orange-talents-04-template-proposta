package br.com.zupacademy.fabio.proposta.shared;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class TransactionExecutor {

    @PersistenceContext
    private EntityManager manager;
    @Autowired
    public TransactionExecutor(EntityManager manager) {
        this.manager = manager;
    }

    @Transactional
    public <T> T commitAndSave(T object) {
        manager.persist(object);
        return object;
    }

    @Transactional
    public <T> T find(Class<T> object, Object id){
        return manager.find(object, id);
    }

    @Transactional
    public List<Long> findPropostaElegivelSemNumeroCartao(){
        return manager.createQuery("SELECT id FROM Proposta WHERE cardNumber IS NULL AND status = 'ELEGIVEL'")
                .getResultList();
    }

    @Transactional
    public <T> T mergeAndCommit(T object) {
        return manager.merge(object);
    }

    @Transactional
    public <T> void delete(T object){
        manager.remove(object);
    }
}
