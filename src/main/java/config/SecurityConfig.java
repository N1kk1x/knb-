package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // Отключение CSRF для упрощения тестирования
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/users/register").permitAll() // Разрешить доступ к эндпоинту регистрации
                        .requestMatchers("/game.html").permitAll() // Разрешить доступ к странице игры
                        .requestMatchers("/main.html").permitAll() // Разрешить доступ к странице игры
                        .anyRequest().authenticated() // Все остальные запросы требуют аутентификации
                );

        return http.build();
    }
}
