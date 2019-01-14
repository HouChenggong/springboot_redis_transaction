package com.pf.org.cms.common;

import com.pf.org.cms.configuration.ShiroConfiguration;

import com.pf.org.cms.hcg.system.bean.PermissionDO;
import com.pf.org.cms.hcg.system.bean.UserDO;
import com.pf.org.cms.hcg.system.service.PermissionService;
import com.pf.org.cms.hcg.system.service.UserNewService;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: pf
 * @Date: 2017/12/12 19:29
 * @Description: 认证和授权具体实现
 * 因为shiro并不知道你登陆认证的具体逻辑和授权的具体逻辑，所以需要用户自己实现，继承AuthorizingRealm，
 * 实现doGetAuthorizationInfo（授权）和doGetAuthenticationInfo（登陆认证）两个抽象方法
 */
public class MyRealm extends AuthorizingRealm {
    @Autowired
    private UserNewService userNewService;

    @Autowired
    private PermissionService permissionService;
    private static final Logger log = LoggerFactory.getLogger(MyRealm.class);

    /**
     * 为当前subject授权
     *
     * @param principalCollection
     * @return AuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("------------------授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        String loginName = (String) principalCollection.getPrimaryPrincipal();
        log.info("==========shiro 授权" + loginName);
        UserDO userDO = userNewService.getUserByLoginName(loginName);
        List<PermissionDO> permission = permissionService.findByUser(userDO);
        for (PermissionDO p : permission) {
            info.addStringPermission(p.getPermissionCode());
            log.info("===p.getPermissionCode()" + p.getPermissionCode());
        }
        return info;
    }

    /**
     * 认证登陆subject身份
     *
     * @param token
     * @return AuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("===============执行了认证方法");
        //认证只做了【查询数据库 用户是否存在】
        UserDO userDO = null;
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String loginName = usernamePasswordToken.getUsername();
        log.info("====UserRealm:token.getUsername()：" + loginName);
        userDO = userNewService.getUserByLoginName(loginName);
        if (userDO == null) {
            throw new UnknownAccountException("用户不存在！");
        }
        Object principle = loginName;
        String credentials = userDO.getPassword();
        String realmName = getName();
        ByteSource salt = ByteSource.Util.bytes(userDO.getSalt());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(principle, credentials, salt, realmName);
        return info;

    }

    public static void main(String[] args) {    //====生成MD5加密后的： loginName+ salt.
        String salt = Long.toString(System.currentTimeMillis());
        System.out.println("保存在数据库中的盐"+salt);
        ByteSource byteSource = ByteSource.Util.bytes(salt);
        String hashAlgorithName = "MD5";
        String password = "wanwan";
        int hashIterations2 = 5;//加密次数
        Object obj = new SimpleHash(hashAlgorithName, password, byteSource, hashIterations2);
        System.out.println("保存在数据库中的密码："+obj.toString());


    }

}
