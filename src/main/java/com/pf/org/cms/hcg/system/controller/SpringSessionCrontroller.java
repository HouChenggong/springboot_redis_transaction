package com.pf.org.cms.hcg.system.controller;

import com.pf.org.cms.hcg.system.bean.UserDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



@Controller
public class SpringSessionCrontroller {

    private static final Logger log = LoggerFactory.getLogger(SpringSessionCrontroller.class);

    @RequestMapping(value = "/putsession",method = RequestMethod.GET)
    @ResponseBody
    public String putSession(HttpServletRequest request) {
        HttpSession session = request.getSession();
        log.info(session.getClass().toString());
        log.info(session.getId());
        String name = "fly";
        session.setAttribute("user", name);
        return "hello，" + name;

    }
}

