package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.CalendarDto;
import com.daydoodle.daydoodle.common.FunFactDto;
import com.daydoodle.daydoodle.entities.Calendar;
import com.daydoodle.daydoodle.entities.FunFact;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@Stateless
public class FunFactBean {

    private static final Logger log = Logger.getLogger(FunFactBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds all fun facts in the database.
     */
    public List<FunFactDto> findAllFunFacts() {
        log.info("\n Entered findAllFunFacts method \n");
        try {
            TypedQuery<FunFact> typedQuery = entityManager.createQuery("SELECT f FROM FunFact f", FunFact.class);
            List<FunFact> facts = typedQuery.getResultList();
            log.info("\n Exited findAllFunFacts method \n");
            return copyFunFactsToDto(facts);
        } catch (Exception ex) {
            log.info("\n Error in findAllFunFacts method! " + ex.getMessage() + " \n");
            throw new EJBException(ex);
        }
    }

    private List<FunFactDto> copyFunFactsToDto(List<FunFact> facts) {
        log.info("\n Entered copyFunFactsToDto \n");
        List<FunFactDto> dtos = new ArrayList<>();
        for (FunFact fact : facts) {
            FunFactDto dto = new FunFactDto(fact.getId(),fact.getFact());
            dtos.add(dto);
        }
        log.info("\n Exited copyFunFactsToDto \n");
        return dtos;


    }

    public FunFactDto getRandomFunFact() {
        List<FunFactDto> facts = findAllFunFacts();
        Random random = new Random();
        FunFactDto dto = facts.get(random.nextInt(facts.size()));
        return dto;
    }
}
