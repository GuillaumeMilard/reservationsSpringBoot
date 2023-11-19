package be.iccbxl.pid.reservationsSpringBoot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/").permitAll(); // Autoriser l'accès à la racine ("/") sans authentification à tout le monde
                     auth.requestMatchers("/admin").hasRole("ADMIN"); // Autoriser l'accès à "/admin" pour les users ayant le rôle "ADMIN"
                    auth.requestMatchers("/user").hasRole("USER"); // Autoriser l'accès à "/user" pour les users ayant le rôle "USER"
                    auth.anyRequest().authenticated(); // Pour toutes les autres requêtes, l'authentification est requise
                })
                .formLogin(Customizer.withDefaults()) // Configure le formulaire de connexion avec les paramètres par défaut
                .rememberMe(Customizer.withDefaults()) // Configure la fonction "remember me" avec les paramètres par défaut.
                .build(); // Construire la configuration de sécurité

    } /* ce code configure Spring Security pour autoriser l'accès à différentes parties de l'application en fonction des rôles des utilisateurs
     * et utilise la configuration par défaut pour le formulaire de connexion et la fonction "remember me".
     */

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder bCryptPasswordEncoder) throws Exception {
        AuthenticationManagerBuilder authMngrBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authMngrBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);

        return authMngrBuilder.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
