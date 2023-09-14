package thomas.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;



@Slf4j
@Component
public class GlobLogFilter implements GlobalFilter, Ordered {


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("Request into the system");
        log.info("uri: {}", exchange.getRequest().getURI());
        String username = exchange.getRequest().getQueryParams().getFirst("username");
        log.info("username: {}", username);
/*        if(username == null){
            log.error("username is null");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }*/
        return chain.filter(exchange);
    }


    @Override
    public int getOrder() {
        return 0;
    }
}
