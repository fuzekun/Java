package com.example.demo.Controller;

import com.example.demo.Service.JedisService;
import com.example.demo.Utils.RedisKeyUntil;
import com.example.demo.model.ViewObject;
import io.swagger.annotations.SwaggerDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admins")
public class SatisfiedController {

    @Autowired
    private JedisService jedisService;

    @RequestMapping(value = "/about")
    public String about(){
        return "about";
    }

    @RequestMapping(value = "/loginht")
    public String login(){
        return "login";
    }

    @RequestMapping(value = "/mdf")
    public String mdf(){
        return "modifyUser";
    }

    @RequestMapping(value = "/link")
    public String link(){return "link";}

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(){
        return "admin/index";
    }

    @RequestMapping(value = "article", method = RequestMethod.GET)
    public String article(){
        return "admin/article";
    }

    @RequestMapping(value = "user", method = RequestMethod.GET)
    public String user(){
        return "admin/user";
    }

    @RequestMapping(value = "comment", method = RequestMethod.GET)
    public String comment(){
        return "admin/comment";
    }

}
