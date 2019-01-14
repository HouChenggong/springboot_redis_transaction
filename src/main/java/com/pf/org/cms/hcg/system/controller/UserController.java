package com.pf.org.cms.hcg.system.controller;


        import com.pf.org.cms.configuration.RedisTest;
        import com.pf.org.cms.hcg.system.bean.PermissionDO;
        import com.pf.org.cms.hcg.system.bean.UserDO;
        import com.pf.org.cms.hcg.system.service.PermissionService;
        import com.pf.org.cms.hcg.system.service.UserNewService;
        import com.pf.org.cms.manage.RedisManager;
        import io.swagger.annotations.*;
        import org.apache.shiro.SecurityUtils;
        import org.apache.shiro.authc.AuthenticationException;
        import org.apache.shiro.authc.IncorrectCredentialsException;
        import org.apache.shiro.authc.UsernamePasswordToken;

        import org.apache.shiro.subject.Subject;
        import org.slf4j.Logger;
        import org.slf4j.LoggerFactory;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.beans.factory.annotation.Value;
        import org.springframework.stereotype.Controller;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RequestMethod;
        import org.springframework.web.bind.annotation.RequestParam;
        import org.springframework.web.bind.annotation.ResponseBody;

        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import javax.servlet.http.HttpSession;
        import java.io.PrintWriter;
        import java.io.UnsupportedEncodingException;
        import java.net.URLEncoder;
        import java.util.List;
        import java.util.Map;

/**
 * @author xiyou
 * @create 2017-11-21 17:08
 * @desc 系统管理控制层
 **/
@Api("用户管理的控制层")
@Controller
public class UserController {
    @Autowired
    UserNewService userNewService;

    @Autowired
    PermissionService permissionService;
    @Autowired
    RedisManager redisManager ;

    @Value("${spring.profiles.active}")
    private String profileActive;


    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "用户登陆验证" , notes = "swagger测试接口",tags = "user",httpMethod = "GET")
    //用户用户名和密码MD5加密后的验证
    @RequestMapping(value = "/authenticate", method = RequestMethod.GET)
    @ResponseBody
    public String authenticate(
            @RequestParam("loginName") String loginName,
            @RequestParam("password") String password,
                               HttpServletRequest request,
            HttpSession session, HttpServletResponse response) {

        //把前端输入的username和password封装为token
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        // 认证身份
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            System.out.println(subject.getPreviousPrincipals());
            System.out.println(subject.getPrincipals());
            System.out.println(subject.getPrincipal());
            session.setAttribute("user", subject.getPrincipals().toString());
            log.info("登陆成功");
            return "success";
        } catch (Exception e) {
            log.info("登陆失败");
            return "false";
        }


    }


    @ApiOperation(value = "Redis测试接口" , notes = "swagger测试接口" )
    @RequestMapping(value = "/demo", method = RequestMethod.GET)
    @ResponseBody
    public String testRedis(
            @RequestParam("key") String key) {
        System.out.println("入参key为:"+key);
        String s = "查询结果为:"+redisManager.getStr(key);
        return s;
    }


    @ApiOperation(value = "用户用户密码权限验证的接口" , notes = "swagger测试接口" )
    @RequestMapping(value = "/system/authenticate",method = RequestMethod.POST)
    @ResponseBody
    public String testDemo(Map<String, Object> map,HttpServletRequest request) {
        System.out.println("入参key为:"+map.toString());
         UserDO demos = userNewService.getUserByLoginName("wanwan");
        map.put("data", demos);
        System.out.println(demos.toString());
        System.out.println(profileActive);
        HttpSession session =request.getSession();
        session.setAttribute("user",demos);
        log.debug("debug---log-------------" + demos.toString());
        log.info("info---log-------------" + demos.toString());
        log.warn("warn---log-------------" + demos.toString());
        log.error("error---log-------------" + demos.toString());
        return "success";
    }

}
