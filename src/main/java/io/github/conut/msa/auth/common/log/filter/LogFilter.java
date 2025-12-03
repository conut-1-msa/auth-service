package io.github.conut.msa.auth.common.log.filter;

import java.io.IOException;

import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(1)
public class LogFilter extends OncePerRequestFilter {
    private static final String TRACE_ID_HEADER = "X-Trace-Id";
    private static final String TRACE_ID_KEY = "traceId";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String traceId = request.getHeader(TRACE_ID_HEADER);
        MDC.put(TRACE_ID_KEY, traceId);

        try {
            log.info("Request: {} {}",
                request.getMethod(),
                request.getRequestURI()
            );
            filterChain.doFilter(request, response);
        } finally {
            log.info("Response: {}",
                response.getStatus()
            );
            MDC.remove(TRACE_ID_KEY);
        }
    }
}
