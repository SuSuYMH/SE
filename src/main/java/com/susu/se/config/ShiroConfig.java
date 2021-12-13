package com.susu.se.config;

import com.susu.se.utils.ShiroRealms.TestRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

//import lombok.Data;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.session.mgt.SessionManager;
//import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.apache.shiro.web.servlet.SimpleCookie;
//import org.crazycake.shiro.RedisCacheManager;
//import org.crazycake.shiro.RedisManager;
//import org.crazycake.shiro.RedisSessionDAO;
//import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.Duration;
//import java.util.LinkedHashMap;
//import java.util.Map;

/*
*整合shiro框架用到的配置类
 */
@Configuration
public class ShiroConfig {

    //创建shiroFilter,负责拦截所有请求
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(DefaultWebSecurityManager defaultWebSecurityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //给filter设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(defaultWebSecurityManager);
        //配置系统的受限资源
        Map<String, String> map = new HashMap<>();
        // map.put("/**","authc")表示系统中所有的资源都要进行认证和授权
//        map.put("/administrators","authc");//authc:请求这个资源需要认证和授权
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        //配置系统的公共资源


        //默认认证界面路径
        shiroFilterFactoryBean.setLoginUrl("/login");


        return shiroFilterFactoryBean;
    }

    //创建安全管理器
    @Bean
    public DefaultWebSecurityManager defaultWebSecurityManager(Realm realm){
        DefaultWebSecurityManager defaultWebSecurityManager = new DefaultWebSecurityManager();
        //给安全管理器设置Realm
        defaultWebSecurityManager.setRealm(realm);
        return defaultWebSecurityManager;

//        MySessionManager mySessionManager = new MySessionManager();
//        mySessionManager.setRealm(realm);
    }

    //创建自定义Realm
    @Bean
    public Realm getRealm(){
        TestRealm testRealm = new TestRealm();

        //修改凭证校验匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //设置加密算法为md5
        credentialsMatcher.setHashAlgorithmName("MD5");
        //设置散列次数
        credentialsMatcher.setHashIterations(1024);
        //设置realm的校验匹配器为这个匹配器
        testRealm.setCredentialsMatcher(credentialsMatcher);
        return testRealm;
    }


//
//    /**
//     * RedisSessionDAO shiro sessionDao层的实现 通过redis, 使用的是shiro-redis开源插件
//     * create by: leigq
//     * create time: 2019/7/3 14:30
//     *
//     * @return RedisSessionDAO
//     */
//    @Bean
//    public RedisSessionDAO redisSessionDAO() {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        redisSessionDAO.setRedisManager(redisManager());
//        redisSessionDAO.setSessionIdGenerator(sessionIdGenerator());
//        redisSessionDAO.setExpire(1800);
//        return redisSessionDAO;
//    }
//
//    /**
//     * Session ID 生成器
//     * <br/>
//     * create by: leigq
//     * <br/>
//     * create time: 2019/7/3 16:08
//     *
//     * @return JavaUuidSessionIdGenerator
//     */
//    @Bean
//    public JavaUuidSessionIdGenerator sessionIdGenerator() {
//        return new JavaUuidSessionIdGenerator();
//    }
//
//    /**
//     * 自定义sessionManager
//     * create by: leigq
//     * create time: 2019/7/3 14:31
//     *
//     * @return SessionManager
//     */
//    @Bean
//    public SessionManager sessionManager() {
//        MySessionManager mySessionManager = new MySessionManager();
//        mySessionManager.setSessionDAO(redisSessionDAO());
//        return mySessionManager;
//    }
//
//    /**
//     * 配置shiro redisManager, 使用的是shiro-redis开源插件
//     * <br/>
//     * create by: leigq
//     * <br/>
//     * create time: 2019/7/3 14:33
//     *
//     * @return RedisManager
//     */
//    private RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost(host);
//        redisManager.setPort(port);
//        redisManager.setTimeout((int) timeout.toMillis());
//        redisManager.setPassword(password);
//        return redisManager;
//    }
//
//    /**
//     * cacheManager 缓存 redis实现, 使用的是shiro-redis开源插件
//     * <br/>
//     * create by: leigq
//     * <br/>
//     * create time: 2019/7/3 14:33
//     *
//     * @return RedisCacheManager
//     */
//    @Bean
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        // 必须要设置主键名称，shiro-redis 插件用过这个缓存用户信息
//        redisCacheManager.setPrincipalIdFieldName("userId");
//        return redisCacheManager;
//    }


//
//    /**
//     * create by: leigq
//     * description: 权限管理，配置主要是Realm的管理认证
//     * create time: 2019/7/1 10:09
//     *
//     * @return SecurityManager
//     */
//    @Bean
//    public SecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(getRealm());
//        // 自定义session管理 使用redis
//        securityManager.setSessionManager(sessionManager());
//        // 自定义缓存实现 使用redis
//        securityManager.setCacheManager(cacheManager());
//        return securityManager;
//    }

    /*
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     */
    @Bean
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
