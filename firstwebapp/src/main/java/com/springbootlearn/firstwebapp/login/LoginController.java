package com.springbootlearn.firstwebapp.login;


import ch.qos.logback.core.pattern.util.AlmostAsIsEscapeUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("name")
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;


    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

//    @RequestMapping("login")
//    public String goToLoginPage(@RequestParam String name, ModelMap model) {
//        model.put("name", name);
//        logger.debug("name found is : {}", name);
//        return "login";
//    }

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String goToLoginPage() {
        return "login";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String goToHomePage(@RequestParam String name, @RequestParam String password, ModelMap map) {


        //Authentication  [doaa,dummy5]
        if (authenticationService.authenticateAUser(name, password)) {
            map.put("name", name);
            return "welcomePage";
        }

        map.put("errorMessage", "Invalid Credentials");
        return "login";

    }


}