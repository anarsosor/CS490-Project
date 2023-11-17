package HealthCare.gatewayService.filter;

import HealthCare.gatewayService.util.JwtUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    @Autowired
    private RouteValidator validator;

    @Autowired
    private JwtUtil jwtUtil;

    public AuthenticationFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(AuthenticationFilter.Config config) {
        return ((exchange, chain) -> {
            ServerHttpRequest request = null;
            if (validator.isSecured.test(exchange.getRequest())) {
                try {
                    if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                        throw new RuntimeException("missing authorization header");
                    }
                    String authHeader = exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
                    if (authHeader != null && authHeader.startsWith("Bearer ")) {
                        authHeader = authHeader.substring(7);
                    }
                    try {
                        Claims claims = jwtUtil.validateToken(authHeader);
                        request = exchange.getRequest()
                                .mutate()
                                .header("userId", claims.getId())
                                .header("username", claims.getSubject())
                                .header("isDoctor", Boolean.toString(claims.containsKey("isDoctor")))
                                .header("isPatient", Boolean.toString(claims.containsKey("isPatient")))
                                .header("isPharmacist", Boolean.toString(claims.containsKey("isPharmacist")))
                                .build();
                     } catch (MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e) {
                        throw new RuntimeException("Invalid credentials");
                    } catch (ExpiredJwtException e) {
                        throw new RuntimeException("Token has Expired");
                    }
                } catch (Exception e) {
                    ExceptionDTO exceptionDTO = new ExceptionDTO(e.getMessage());
                    String json = new Gson().toJson(exceptionDTO);

                    exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
                    exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);

                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(json.getBytes());
                    return exchange.getResponse().writeWith(Mono.just(buffer));
                }
            }
            return chain.filter(exchange.mutate().request(request).build());
        });
    }

    public static class Config {

    }
}
