package com.example.community.config;

import com.example.community.service.UserService;
import com.example.community.util.CommunityConstant;
import com.example.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter implements CommunityConstant {

    @Autowired
    private UserService userService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略静态资源的访问
        web.ignoring().antMatchers("/resources/**");
    }

    // // AuthenticationManager：认证的核心接口
    // // AuthenticationManagerBuilder：用于构建AuthenticationManager对象的工具
    // // ProviderManager：AuthenticationManager的默认实现类
    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //     // 内置的认证规则
    //     // auth.userDetailsService(userService).passwordEncoder(new Pbkdf2PasswordEncoder("12345"));
    //
    //     // 自定义认证规则
    //     // AuthenticationProvider：AuthenticationManager持有一组AuthenticationProvider，每个AuthenticationProvider负责一种认证
    //     // 委托模式：AuthenticationManager将认证委托给AuthenticationProvider
    //     auth.authenticationProvider(new AuthenticationProvider() {
    //         // Authentication：用于封装认证信息的接口，不同的实现类代表不同类型的认证信息
    //         @Override
    //         public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    //             String username = authentication.getName();
    //             String password = (String) authentication.getCredentials();
    //
    //             User user = userService.findUserByName(username);
    //             if (user == null) {
    //                 throw new UsernameNotFoundException("账号不存在！");
    //             }
    //
    //             password = CommunityUtil.md5(password + user.getSalt());
    //             if (!user.getPassword().equals(password)) {
    //                 throw new BadCredentialsException("密码错误！");
    //             }
    //
    //             /**
    //              * param1：主要信息
    //              * param2：证书
    //              * param3：权限
    //              */
    //             return new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());
    //         }
    //
    //         // 当前AuthenticationProvider支持哪种类型的认证
    //         @Override
    //         public boolean supports(Class<?> aClass) {
    //             // UsernamePasswordAuthenticationToken：Authentication的常用实现类
    //             return UsernamePasswordAuthenticationToken.class.equals(aClass);
    //         }
    //     });
    // }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 授权
        http.authorizeRequests()
                .antMatchers(
                        "/user/setting",
                        "/user/upload",
                        "/discuss/add",
                        "/comment/add/**",
                        "/letter/**",
                        "/notice/**",
                        "/like",
                        "/follow",
                        "/unfollow"
                ).hasAnyAuthority(
                        AUTHORITY_USER,
                        AUTHORITY_ADMIN,
                        AUTHORITY_MODERATOR
                )
                .antMatchers(
                        "/discuss/top",
                        "/discuss/wonderful"
                ).hasAnyAuthority(
                        AUTHORITY_MODERATOR
                )
                .antMatchers(
                        "/discuss/delete"
                )
                .hasAnyAuthority(
                        AUTHORITY_ADMIN
                )
                .anyRequest().permitAll()
                .and().csrf().disable();

        // 权限不够
        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationEntryPoint() {
                    // 没有登录
                    @Override
                    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        String xRequestedWith = httpServletRequest.getHeader("x-requested-with");
                        if ("XMLHttpRequest".equals(xRequestedWith)) {
                            httpServletResponse.setContentType("application/plain;charset=utf-8");
                            PrintWriter writer = httpServletResponse.getWriter();
                            writer.write(CommunityUtil.getJSONString(403, "你还没有登录！"));
                        } else {
                            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/login");
                        }
                    }
                })
                .accessDeniedHandler(new AccessDeniedHandler() {
                    // 权限不足
                    @Override
                    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
                        String xRequestedWith = httpServletRequest.getHeader("x-requested-with");
                        if ("XMLHttpRequest".equals(xRequestedWith)) {
                            httpServletResponse.setContentType("application/plain;charset=utf-8");
                            PrintWriter writer = httpServletResponse.getWriter();
                            writer.write(CommunityUtil.getJSONString(403, "大咩大咩！"));
                        } else {
                            httpServletResponse.sendRedirect(httpServletRequest.getContextPath() + "/denied");
                        }
                    }
                });

        // 登出相关配置
        // security底层默认会拦截/logout请求，执行退出处理
        http.logout().logoutUrl("/securityLogout");

    }


}
