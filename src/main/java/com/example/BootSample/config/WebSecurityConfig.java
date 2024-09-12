package com.example.BootSample.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * ログイン認証のコンフィグレーションクラス
 */
//Spring Securityの有効化
@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    //パスワードの暗号化
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //リクエストごとの認可と認証の設定
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                        //静的リソースへのアクセス許可
                        .requestMatchers("/css/**").permitAll()
                        .requestMatchers("/js/**").permitAll()
                        .requestMatchers("/img/**").permitAll()
                        // /login と / へのアクセスも許可
                        //permitAllで全許可
                        .requestMatchers("/login").permitAll()
                        .requestMatchers("/").permitAll()
                        //その他のリクエストは認証が必要
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        //ログインページの指定
                        .loginPage("/login")
                        //ログイン処理を行うURLの指定
                        .loginProcessingUrl("/login")
                        //リクエストパラメータ
                        .usernameParameter("id")
                        .passwordParameter("password")
                        //ログイン認証に成功したときの遷移先
                        .defaultSuccessUrl("/top")
                        .permitAll()
                )
                .logout(logout -> logout
                        //ログアウトURLの指定
                        .logoutUrl("/logout").permitAll()
                        //ログアウトが成功したときの遷移先
                        .logoutSuccessUrl("/login")
                );

        return http.build();
    }
}
