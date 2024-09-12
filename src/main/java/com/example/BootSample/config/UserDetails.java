package com.example.BootSample.config;

import com.example.BootSample.entity.UserInfoEntity;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.util.Collection;

public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {
    @Serial
    private static final long serialVersionUID = 1L;

    private final String id;

    private final String password;

    @Getter
    private final String displayUserName;


    public UserDetails(UserInfoEntity user) {
        this.id = user.getId();
        this.password = user.getPassword();
        this.displayUserName = user.getUserName();
    }

    // 権限の返却
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    // パスワードの返却
    @Override
    public String getPassword() {
        return password;
    }

    // ログインIDの返却
    @Override
    public String getUsername() {
        return id;
    }

    // ユーザが期限切れかどうか
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // ユーザがロックされていないかどうか
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //　パスワードが期限切れではないかどうか
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // ユーザが有効かどうか
    @Override
    public boolean isEnabled() {
        return true;
    }
}
