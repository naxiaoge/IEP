package com.ysd.iep.config;

import com.ysd.iep.authorize.AuthorizeConfigProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

/**
 * @author 80795
 * @date 2018/11/12 8:55
 */
@Component
public class AdminAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config
                .antMatchers("/recommend",
                        "/recommend/index",
                        "/user/getNameById",
                        "/user/getNameByIds",
                        "/user/getByRole",
                        "/depart/getIdByNames")
                .permitAll();
    }
}
