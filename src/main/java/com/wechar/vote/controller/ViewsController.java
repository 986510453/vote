package com.wechar.vote.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.logging.Logger;

@Controller
public class ViewsController {
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    static Logger logger = Logger.getLogger("ViewsWebController");
    //main page
    @RequestMapping("/")
    public String welcome(ModelMap map) {
        // 加入一个属性，用来在模板中读取
        logger.info("/");
        return "index";
    }
}
