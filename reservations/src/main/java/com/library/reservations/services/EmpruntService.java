package com.library.reservations.services;

import com.library.reservations.beans.EmpruntBean;
import com.library.reservations.beans.ExemplaireBean;
import com.library.reservations.configuration.ApplicationPropertiesConfiguration;


import com.library.reservations.proxies.BooksProxy;
import com.library.reservations.proxies.EmpruntsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;


import org.springframework.stereotype.Service;


import java.util.Calendar;
import java.util.Collection;
import java.util.Objects;


@Service
public class EmpruntService {
    @Autowired
    ApplicationPropertiesConfiguration appProperties;

    @Autowired
    EmpruntsProxy empruntsProxy;

    @Autowired
    private BooksProxy booksProxy;


    public Boolean isExemplaireDispo(int barcode) {
        Collection<EmpruntBean> emprunts = empruntsProxy.findByExemplaireBarcode(barcode).getContent();

        if (emprunts.isEmpty()) return true;
        Boolean returnValue = true;
        for (EmpruntBean emprunt : emprunts) {
            if (emprunt.getDateRetour() == null) {
                returnValue = false;
            }

        }
        return returnValue;
    }

    public Integer findExemplairesDispo(int bookId) {
        Collection<ExemplaireBean> exemplaireBeans = booksProxy.recupererExemplairesLivre(bookId).getContent();
        int exemplairesDispo = 0;
        for (ExemplaireBean exemplaireBean : exemplaireBeans) {

            if (isExemplaireDispo(exemplaireBean.getBarcode())) {

                exemplairesDispo++;
            }
        }

        return exemplairesDispo;
    }

    public int findExemplaires(int bookId){
        Collection<ExemplaireBean> exemplaireBeans = booksProxy.recupererExemplairesLivre(bookId).getContent();


                return exemplaireBeans.size();
    }

    public PagedModel<EmpruntBean> getEmpruntsByUserId(String userId)
    {
        PagedModel<EmpruntBean> emprunts = empruntsProxy.findByUserId(userId);

        for (EmpruntBean emprunt:emprunts){
            ExemplaireBean theExemplaire = booksProxy.recupererExemplaire(emprunt.getExemplaireBarcode()).getContent();
            emprunt.setBookId(theExemplaire.getBook().getId());
            emprunt.setBookTitle(theExemplaire.getBook().getTitle());

            Calendar c = Calendar.getInstance();
            c.setTime(emprunt.getDateEmprunt());
            if(emprunt.getIsExtended()){
                c.add(Calendar.DATE, appProperties.getDureeEmprunt() + appProperties.getDureeProlongation());
            }
            else {
                c.add(Calendar.DATE, appProperties.getDureeEmprunt());
            }
            emprunt.setDateRetourPrevu(c.getTime());
        }
        return emprunts;
    }

    public boolean estDejaEmprunte(String userId, int bookId){
        PagedModel<EmpruntBean> emprunts = this.getEmpruntsByUserId(userId);
        for (EmpruntBean empruntBean: emprunts
             ) {
            if(empruntBean.getBookId() == bookId){
                return true;
            }
        }

        return false;
    }







}
