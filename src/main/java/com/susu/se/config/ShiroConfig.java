package com.susu.se.config;

import com.susu.se.utils.ShiroRealms.TestRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

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
        map.put("/administrators","authc");//authc:请求这个资源需要认证和授权
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

}
