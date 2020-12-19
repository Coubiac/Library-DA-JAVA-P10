package com.library.appliweb.service;

import com.library.appliweb.beans.BookBean;

import com.library.appliweb.beans.ReservationBean;
import com.library.appliweb.beans.UserBean;
import com.library.appliweb.proxies.BooksProxy;
import com.library.appliweb.proxies.ReservationProxy;
import com.library.appliweb.proxies.UserProxy;
import com.library.appliweb.requests.ReservationEntity;
import com.library.appliweb.requests.ReservationPostRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private BooksProxy booksProxy;

    @Autowired
    private UserProxy userProxy;

    @Autowired
    private ReservationProxy reservationProxy;


    /**
     * Methode permettant d'obtenir toutes les réservations liées à un livre.
     * @param bookBean
     * @return
     */
    public List<ReservationBean> getBookReservations(BookBean bookBean){
        PagedResources<ReservationEntity> resources = reservationProxy.findAllByBookId(bookBean.getId());
        Collection<ReservationEntity> reservationEntities = resources.getContent();

        if(reservationEntities.isEmpty()){return null;}
        List<ReservationBean> reservationBeans = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationEntities
             ) {
            ReservationBean reservationBean = transformRequest(reservationEntity);
            reservationBeans.add(reservationBean);
        }
        return reservationBeans;
    }

    public String getPositionInReservationList(UserBean userBean, BookBean bookBean) {

        List<ReservationBean> reservationBeans = getBookReservations(bookBean);
        if(reservationBeans == null){
            return "";
        }
        String userId = userBean.getUserId();
        Collections.sort(reservationBeans);
        Integer position = 1;
        Integer totalReservation = reservationBeans.size();
        for (ReservationBean reservationBean:reservationBeans){
            String ownerId = reservationBean.getUserBean().getUserId();

            if(userId.equals(ownerId)){
                return position + "/" + totalReservation;
            }
            position++;
        }
        return "";
    }





    /**
     * Méthode utilitaire permettant de créer un objet ReservationBean à partir d'un objet ReservationEntity
     * @see ReservationBean
     * @see ReservationEntity
     * @param reservationEntity
     * @return
     */
    private ReservationBean transformRequest(ReservationEntity reservationEntity){
        ReservationBean reservationBean = new ReservationBean();

        reservationBean.setBookBean(booksProxy.recupererUnLivre(reservationEntity.getBookId()).getContent());
        reservationBean.getBookBean().setId(reservationEntity.getBookId());
        reservationBean.setUserBean(userProxy.getUserById(reservationEntity.getUserId()).getContent());
        reservationBean.setDateReservation(reservationEntity.getDateReservation());
        reservationBean.setActive(reservationEntity.isActive());
        reservationBean.setDateActive(reservationEntity.getDateActive());
        reservationBean.setId(reservationEntity.getId());

        return reservationBean;
    }

    public List<ReservationBean> getReservationsByUserId(String userId) {

        PagedResources<ReservationEntity> resources = reservationProxy.findAllByUserId(userId);

        Collection<ReservationEntity> reservationEntities = resources.getContent();

        if(reservationEntities.isEmpty()){return null;}
        List<ReservationBean> reservationBeans = new ArrayList<>();
        for (ReservationEntity reservationEntity : reservationEntities
        ) {
            ReservationBean reservationBean = transformRequest(reservationEntity);
            reservationBeans.add(reservationBean);
        }


        return reservationBeans;
    }


    public void addReservation(int bookId, String userId) {
        ReservationPostRequest reservationPostRequest =
                new ReservationPostRequest(bookId, userId);

        reservationProxy.create(reservationPostRequest);

    }
}
