package spingboot.express.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebSecurity
@Configuration
@EnableWebMvc
public class WebSecurityConfig extends WebSecurityConfigurerAdapter implements WebSecurityConfigurer<WebSecurity> {

    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private MyCorsConfigurationSource myCorsConfigurationSource;

    /**
     * 配置说明
     * httpBasic => 由http协议定义的最基础的认证方式,如果开启该配置，就会在用户请求时，弹出用户授权登陆界面
     * exceptionHandling => 异常处理机制
     * formLogin => 如果未经授权，会自动跳转到预置页面/login要求你先登录，用户名为 user 和密码在控制台已知
     * csrf => 防止用户进行csrf攻击
     * logout => 该配置会使得当前session失效，同时重定向到登陆界面
     * cors => 使用 cors 处理跨域请求
     * securityContext 安全上下文，存储认证授权的相关信息，实际上就是存储"当前用户"账号信息和相关权限
     * authorizeRequests().anyRequest().authenticated() => 就会要求所有进入应用的HTTP请求都要进行认证
     * @param http http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
        http.exceptionHandling().authenticationEntryPoint(authenticationFailureHandler);
        http.formLogin().disable();
        http.csrf().disable();
        http.logout().disable();
        http.cors().configurationSource(myCorsConfigurationSource);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        //进行请求接口的拦截
        //根据后面调用方法的不同，对于请求接口进行拦截和允许访问
//        http = http.authorizeRequests().antMatchers("/userInfo/hello").permitAll().and();
        http = http.authorizeRequests().antMatchers("/auth/signIn").permitAll().and();
        http = http.authorizeRequests().antMatchers("/refreshToken").permitAll().and();
//        http.authorizeRequests().anyRequest().authenticated();
    }

}
