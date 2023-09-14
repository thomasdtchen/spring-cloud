package thomas.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {
/*    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Gateway AuthenticationFilter uri: {}", exchange.getRequest().getURI());
        Mono<Authentication> authentication = ReactiveSecurityContextHolder.getContext().map(ctx -> ctx.getAuthentication());
        // UsernamePasswordAuthenticationFilter f;
        Authentication  auth = authentication.block();
        //authentication.subscribe(auth -> {
        log.info("auth: {}", auth);
        String username;
        if(auth != null) {
            if (auth.getPrincipal() instanceof UserDetails) {
                username = ((UserDetails) auth.getPrincipal()).getUsername();
            } else {
                username = auth.getName();
            }
            log.info("username: {}", username);
            String authorities = StringUtils.collectionToCommaDelimitedString(AuthorityUtils.authorityListToSet(auth.getAuthorities()));
            log.info("authorities: {}", authorities);
            String jwtToken = JwtUtils.generateToken(username, authorities);
            log.info("jwtToken: {}", jwtToken);
            ServerHttpRequest tokenRequest = exchange.getRequest().mutate().header("MY-TOKEN", jwtToken).build();
            ServerWebExchange build = exchange.mutate().request(tokenRequest).build();
            return chain.filter(build);
        }
        //});
        return chain.filter(exchange);
    }*/

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
/*        var attributes = exchange.getSession().block().getAttributes();
        return chain.filter(exchange);*/
        final List<String> abc = new ArrayList<>();
        return ReactiveSecurityContextHolder.getContext()
                .doOnNext((sc) -> {
                    abc.add("abc");
                    Authentication auth = sc.getAuthentication();
                    log.info("auth: {}", auth);
                    String username;
                    if(auth != null) {
                        if (auth.getPrincipal() instanceof UserDetails) {
                            username = ((UserDetails) auth.getPrincipal()).getUsername();
                        } else {
                            username = auth.getName();
                        }
                        log.info("username: {}", username);
                        String authorities = StringUtils.collectionToCommaDelimitedString(AuthorityUtils.authorityListToSet(auth.getAuthorities()));
                        log.info("authorities: {}", authorities);
                        String jwtToken = JwtUtils.generateToken(username, authorities);
                        log.info("jwtToken: {}", jwtToken);
                        ServerHttpRequest tokenRequest = exchange.getRequest().mutate().header("MY-TOKEN", jwtToken).build();
                        ServerWebExchange build = exchange.mutate().request(tokenRequest).build();
                        chain.filter(build);
                    }
                    //custom logic on sc.getAuthentication().getName()
                })
                .then(chain.filter(exchange));
    }
/*    @Override
    public Mono<Void> filter(ServerWebExchange exchange, @NotNull WebFilterChain chain) {
        return exchange.getSession().flatMap((session) -> {
            SecurityContext context = session.getAttribute("SPRING_SECURITY_CONTEXT_ATTR_NAME");
*//*            log.debug((context != null)
                    ? LogMessage.format("Found SecurityContext '%s' in WebSession: '%s'", context, session)
                    : LogMessage.format("No SecurityContext found in WebSession: '%s'", session));*//*
            if (context == null) {
                //return filterHelper(exchange, chain);
                return chain.filter(exchange);
            } else {
                // access the principal object here
                return chain.filter(exchange);
            }
        });
    }*/

    @Override
    public int getOrder() {
        return -2;
    }


}
