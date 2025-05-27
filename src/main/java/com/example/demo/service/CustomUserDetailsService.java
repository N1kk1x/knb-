package com.example.demo.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service  // Регистрация бина Spring
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Пример: возвращаем пользователя с логином admin и паролем password (без шифрования)
        if ("admin".equals(username)) {
            return org.springframework.security.core.userdetails.User
                    .withUsername("admin")
                    .password("{noop}password")  // {noop} означает, что пароль без шифрования
                    .roles("ADMIN")
                    .build();
        }
        // Если пользователь не найден, бросаем исключение
        throw new UsernameNotFoundException("User not found");
    }
}
