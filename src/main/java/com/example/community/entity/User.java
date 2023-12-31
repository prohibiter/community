package com.example.community.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@Component
public class User /*implements UserDetails*/ {
    private int id;
    private String username;
    private String password;
    private String salt;
    private String email;
    private int type;
    private int status;
    private String activationCode;
    private String headerUrl;
    private Date createTime;

    // // 用户所具有的权限
    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
    //     List<GrantedAuthority> list = new ArrayList<>();
    //     list.add(new GrantedAuthority() {
    //         @Override
    //         public String getAuthority() {
    //             switch (type) {
    //                 case 1:
    //                     return "ADMIN";
    //                 default:
    //                     return "USER";
    //             }
    //         }
    //     });
    //     return list;
    // }

    // // true：账号未过期
    // @Override
    // public boolean isAccountNonExpired() {
    //     return true;
    // }
    //
    // // true：账号未锁定
    // @Override
    // public boolean isAccountNonLocked() {
    //     return true;
    // }
    //
    // // true：凭证未过期
    // @Override
    // public boolean isCredentialsNonExpired() {
    //     return true;
    // }
    //
    // // true：账号可用
    // @Override
    // public boolean isEnabled() {
    //     return true;
    // }
}
