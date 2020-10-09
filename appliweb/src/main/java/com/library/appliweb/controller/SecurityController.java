package com.library.appliweb.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.library.appliweb.beans.LoginFormBean;
import com.library.appliweb.configuration.ApplicationPropertiesConfiguration;
import com.library.appliweb.configuration.Credentials;
import com.library.appliweb.proxies.UserProxy;
import com.library.appliweb.service.SecurityService;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller
public class SecurityController {
    @Autowired
    ApplicationPropertiesConfiguration appProperties;
    @Autowired
    UserProxy userProxy;
    @Autowired
    SecurityService securityService;

    @GetMapping(value = "/login")
    public String showLoginForm(Model model, String error, String logout) {
        if (error != null) {
            model.addAttribute("error", "Votre nom d'utilisateur ou mot de passe est invalide.");
        }

        if (logout != null) {
            model.addAttribute("message", "Vous avez été déconnecté.");
        }
        return "security/login";
    }

    @PostMapping(value = "/login")
    public ModelAndView login(@ModelAttribute("userForm") LoginFormBean userForm, HttpServletRequest request) throws FeignException {

        try {
            ResponseEntity<Void> responseEntity = userProxy.getToken(userForm);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                System.out.println("reponse reçue !");
                securityService.setAuthInSession(responseEntity.getHeaders());
                RedirectView redirect = new RedirectView();
                if (request.getHeader("referer") != null) {
                    redirect.setUrl(request.getHeader("referer"));
                }
                redirect.setUrl("/");
                return new ModelAndView(redirect);
            }
            else return new ModelAndView("security/login");
        }
        catch (FeignException e){
            ModelAndView modelAndView = new ModelAndView("security/login");
            modelAndView.addObject("error", "Votre nom d'utilisateur ou mot de passe est invalide.");
            return modelAndView;
        }


    }
}
