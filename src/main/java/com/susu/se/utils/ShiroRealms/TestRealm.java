package com.susu.se.utils.ShiroRealms;

import com.susu.se.model.users.User;
import com.susu.se.service.UserService;
import com.susu.se.utils.ApplicationContextUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.util.ObjectUtils;


//自定义Realm,需要调用业务方法去做实现,业务对象是交给工厂管理的，而Realm没有交给工厂管理，所以没有办法直接注入service
// 对象，所以要通过某种方法能去工厂中拿到已经创建好的service，写一个工厂工具类
public class TestRealm extends AuthorizingRealm{
    //授权，这个方法返回角色以及角色的授权信息
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }


    //认证，这个方法返回认证信息
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //获取身份信息
        String principal =(String) authenticationToken.getPrincipal();
        //在工厂中获取Service对象
        UserService userService = (UserService) ApplicationContextUtil.getBean("userService");
        //用获取到的Service对象和名字来获取与数据库对应的user对象
        User user = userService.findByName(principal);

        if(!ObjectUtils.isEmpty(user)){
            //构造一个认证信息进行返回
            return new SimpleAuthenticationInfo(user.getName(), user.getPassword(), ByteSource.Util.bytes(user.getSalt()), this.getName());
        }

        return null;
    }
}
