package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.DiaryPageDto;
import com.daydoodle.daydoodle.entities.DiaryPage;
import com.daydoodle.daydoodle.entities.User;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@Stateless
public class DiaryPageBean {

    private static final Logger log= Logger.getLogger(DiaryPageBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds all diary pages in the database.
     */
    public List<DiaryPageDto> findAllDiaryPages(){
        log.info("\n Entered findAllDiaryPages method. \n");

        try{
            TypedQuery<DiaryPage> typedQuery=entityManager.createQuery("SELECT diaryPage FROM DiaryPage diaryPage", DiaryPage.class);
            List<DiaryPage> pages=typedQuery.getResultList();

            log.info("\n Exited findAllDiaryPages method, successfully found "+ pages.size() +" diary pages. \n");
            return copyDiaryPagesToDto(pages);

        }catch(Exception ex){
            log.info("\n Error in findAllDiaryPages method! "+ex.getMessage()+" \n");
            throw new EJBException(ex);
        }
    }

    private List<DiaryPageDto> copyDiaryPagesToDto(List<DiaryPage> pages) {
        log.info("\n Entered copyDiaryPagesToDto method with list size of: "+pages.size()+" \n");
        List<DiaryPageDto> listToReturn =new ArrayList<>();

        for(DiaryPage d: pages){
            DiaryPageDto diaryDtoTemp = new DiaryPageDto(d.getEntryId(),d.getAuthor(),d.getEntryDate(),d.getTitle(),d.getEntryText(),d.getMood(),d.isFavourite());
            listToReturn.add(diaryDtoTemp);
        }

        listToReturn.sort(Comparator.comparing(DiaryPageDto::getEntryDate).reversed());
        log.info("\n Exited copyDiaryPagesToDto method. \n");

        return listToReturn;
    }

    /**
     * Finds all the pages written by a specific user.
     */
    public List<DiaryPageDto> findPagesByUser(String username, List<DiaryPageDto> allPages){
        log.info("\n Entered findPagesByUser method, finding diary pages for user:  "+username+" \n");
        List<DiaryPageDto> listToReturn =new ArrayList<>();

        for(DiaryPageDto d: allPages){
            if(d.getAuthor().getUsername().equals(username)){
                listToReturn.add(d);
            }
        }

        log.info("\n Exited findPagesByUser method, found: "+ listToReturn.size() +" pages. \n");
        return listToReturn;

    }

    /**
     * Finds a specific page identified by its id.
     */
    public DiaryPageDto findPageById(Long pageId, List<DiaryPageDto> allPages) {
        log.info("\n Entered findPageById method, finding diary page with the id:  "+pageId+" \n");
        DiaryPageDto page= allPages.stream().filter(d -> Objects.equals(d.getEntryId(), pageId)).findFirst().orElse(new DiaryPageDto());

        log.info("\n Exited findPageById method. \n");
        return page;
    }

    /**
     * Creates a new diary page.
     */
    public void createDiaryPage(String title, String entryText, String authorUsername, String mood, LocalDate date) {

        log.info("\n Entered createDiaryPage method. \n");

        DiaryPage newDiaryPage=new DiaryPage();
        newDiaryPage.setTitle(title);
        newDiaryPage.setEntryText(entryText);
        newDiaryPage.setEntryDate(date);
        newDiaryPage.setMood(mood);

        User author=entityManager.find(User.class,authorUsername);
        author.getDiaryPages().add(newDiaryPage);
        newDiaryPage.setAuthor(author);

        entityManager.persist(newDiaryPage);

        log.info("\n Exited createDiaryPage method. \n");

    }

    /**
     * Returns all favourite pages of a user identified by its username
     */
    public List<DiaryPageDto> findAllFavouriteDiaryPagesByUser(String username,List<DiaryPageDto> allDiaryPages) {
        log.info("\n Entered findAllFavouriteDiaryPagesByUser method. \n");

        List<DiaryPageDto> listToReturn =new ArrayList<>();
        for(DiaryPageDto d: allDiaryPages){
            if(d.getAuthor().getUsername().equals(username) && d.isFavourite()){
                listToReturn.add(d);
            }
        }

        log.info("\n Exited findAllFavouriteDiaryPagesByUser method. \n");
        return listToReturn;
    }


}
