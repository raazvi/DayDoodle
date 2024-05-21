package com.daydoodle.daydoodle.ejb;

import com.daydoodle.daydoodle.common.ActivityDto;
import com.daydoodle.daydoodle.common.PictureDto;
import com.daydoodle.daydoodle.entities.Activity;
import com.daydoodle.daydoodle.entities.Picture;
import jakarta.ejb.EJBException;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Stateless
public class PictureBean {

    private static final Logger log= Logger.getLogger(PictureBean.class.getName());

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Returns a list of all the activities in the database.
     */
    public List<PictureDto> findAllPictures(){
        log.info("\n Entered findAllPictures method \n");

        try{
            TypedQuery<Picture> typedQuery=entityManager.createQuery("SELECT picture FROM Picture picture", Picture.class);
            List<Picture> activities=typedQuery.getResultList();

            log.info("\n Exited findAllPictures method, successfully found "+ activities.size() +" pictures \n");
            return copyPicturesToDto(activities);

        }catch(Exception ex){
            log.info("\n Error in findAllPictures method! "+ex.getMessage()+" \n");
            throw new EJBException(ex);
        }

    }
    private List<PictureDto> copyPicturesToDto(List<Picture> pictures) {
        log.info("\n Entered copyPicturesToDto method with list size of: "+ pictures.size()+" \n");
        List<PictureDto> listToReturn =new ArrayList<>();

        for(Picture p: pictures){

            PictureDto pictureDtoTemp=new PictureDto(p.getId(),p.getImageData(),p.getImageName(),p.getImageFormat());
            listToReturn.add(pictureDtoTemp);
        }

        log.info("\n Exited copyPicturesToDto method. \n");

        return listToReturn;
    }

}
