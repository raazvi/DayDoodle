package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.DiaryIdeaDto;
import com.daydoodle.daydoodle.common.DiaryPageDto;
import com.daydoodle.daydoodle.entities.DiaryIdea;
import com.daydoodle.daydoodle.entities.DiaryPage;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class DiaryIdeaBean {

    private static final Logger log= Logger.getLogger(DiaryIdeaBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds all diary ideas in the database.
     */
    public List<DiaryIdeaDto> findAllDiaryIdeas(){
        log.info("\n Entered findAllDiaryIdeas method. \n");

        try{
            TypedQuery<DiaryIdea> typedQuery=entityManager.createQuery("SELECT diaryIdea FROM DiaryIdea diaryidea", DiaryIdea.class);
            List<DiaryIdea> ideas=typedQuery.getResultList();

            log.info("\n Exited findAllDiaryIdeas method, successfully found "+ ideas.size() +" diary pages. \n");
            return copyDiaryIdeasToDto(ideas);

        }catch(Exception ex){
            log.info("\n Error in findAllDiaryIdeas method! "+ex.getMessage()+" \n");
            throw new EJBException(ex);
        }
    }
    private List<DiaryIdeaDto> copyDiaryIdeasToDto(List<DiaryIdea> ideas) {
        log.info("\n Entered copyDiaryIdeasToDto method with list size of: "+ideas.size()+" \n");
        List<DiaryIdeaDto> listToReturn =new ArrayList<>();

        for(DiaryIdea di: ideas){
            DiaryIdeaDto diaryIdeaDto = new DiaryIdeaDto(di.getId(),di.getPrompt());
            listToReturn.add(diaryIdeaDto);
        }

        log.info("\n Exited copyDiaryIdeasToDto method. \n");

        return listToReturn;
    }

}
