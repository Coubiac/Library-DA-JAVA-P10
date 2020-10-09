package com.library.appliweb.controller;

import com.library.appliweb.beans.EmpruntBean;
import com.library.appliweb.configuration.ApplicationPropertiesConfiguration;
import com.library.appliweb.configuration.Credentials;
import com.library.appliweb.proxies.EmpruntsProxy;
import com.library.appliweb.service.EmpruntService;
import com.library.appliweb.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmpruntController {

    @Autowired
    ApplicationPropertiesConfiguration appProperties;

    @Autowired
    private EmpruntsProxy empruntsProxy;

    @Autowired EmpruntService empruntService;

    @Autowired
    private Credentials credentials;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/emprunts")
    public String voirMesEmprunts(Model model, @RequestParam(required = false) Integer page){
        if (!securityService.isAuthenticated()) return "security/login";
        int size = appProperties.getLivresParPage();
        if (page == null) page = 1;
        PagedResources<EmpruntBean> emprunts = empruntService.getEmpruntsByUserId(credentials.getUserId(), page - 1,size);
        Long totalPages = emprunts.getMetadata().getTotalPages();
        List<Integer> pageNumbers = new ArrayList<>();
        int thePage = 1;
        while (thePage < totalPages) {
            pageNumbers.add(thePage);
            ++thePage;
        }

        model.addAttribute("pageNumbers", pageNumbers);
        model.addAttribute("currentPage", page);
        model.addAttribute("emprunts", emprunts);

        return "mesEmprunts";
    }


    @GetMapping("/emprunts/{empruntId}")
    public String voirUnEmprunt(int empruntId){


        return null;
    }

    @GetMapping("/emprunts/prolonge")
    public RedirectView prolongerUnEmprunt(@RequestParam int empruntId){

        empruntService.prolonge(empruntId);
        return new RedirectView("/emprunts");
    }
}
