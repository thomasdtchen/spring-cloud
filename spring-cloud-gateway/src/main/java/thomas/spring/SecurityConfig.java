package thomas.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@Slf4j
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(16);
        //return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
/*    @Bean
    public UserDetailsService userDetailsService(){
        AuthenticationWebFilter a;
        AbstractUserDetailsReactiveAuthenticationManager b;
        ReactiveUserDetailsServiceAutoConfiguration c;
        String encryptedPass = passwordEncoder().encode("pass");
        log.info("encryptedPass : {}", encryptedPass);
        UserDetails userDetails = User.builder().username("user").password(encryptedPass).roles("USER").build();
        return new InMemoryUserDetailsManager(userDetails);
    }*/

/*    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {
        return http
                // Demonstrate that method security works
                // Best practice to use both for defense in depth
                .authorizeExchange()
                .anyExchange().permitAll()
                .and()
                .httpBasic().and()
                .build();
    }*/

/*    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/token/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .httpBasic(Customizer.withDefaults())
                .build();
    }*/

    @Bean
    public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable());
        http.authorizeExchange(auth -> auth.pathMatchers("/login").permitAll()
                                            .anyExchange().authenticated());
        http.httpBasic(Customizer.withDefaults());
        //http.httpBasic();
        return http.build();
        //return http.authorizeHttpRequests((auth) -> auth.anyRequest().authenticated()).build();
        //.formLogin((form) -> form.loginPage("/login").permitAll());
        //return http.build();
    }
    @Bean
    public MapReactiveUserDetailsService userDetailsService() {
        String encryptedPass = passwordEncoder().encode("pass");
        log.info("encryptedPass : {}", encryptedPass);
        UserDetails user = User.builder()
                .username("user")
                //.password("{noop}user")
                .password(encryptedPass)
                .roles("USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                //.password("{noop}admin")
                .password(encryptedPass)
                .roles("ADMIN")
                .build();
        return new MapReactiveUserDetailsService(user, admin);
    }
}
