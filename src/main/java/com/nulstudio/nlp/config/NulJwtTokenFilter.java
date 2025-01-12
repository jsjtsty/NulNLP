package com.nulstudio.nlp.config;

import com.nulstudio.nlp.domain.cache.CachedAccount;
import com.nulstudio.nlp.domain.cache.CachedRole;
import com.nulstudio.nlp.service.service.AccountService;
import com.nulstudio.nlp.service.service.AuthorityService;
import com.nulstudio.nlp.util.NulJwtToken;
import com.nulstudio.nlp.util.NulSpringUtil;
import com.nulstudio.nlp.util.NulValidator;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for JWT Token.
 * @author nullsty
 * @since 2.0
 */
@Component
public final class NulJwtTokenFilter extends OncePerRequestFilter {

    /**
     * HTTP Header for authentication.
     */
    private static final String HTTP_HEADER_AUTHORIZATION = "Authorization";

    /**
     * Prefix for Bearer Token.
     */
    private static final String HTTP_HEADER_TOKEN_PREFIX = "Bearer ";

    @Resource
    private AccountService accountService;

    @Resource
    private AuthorityService authorityService;


    private void checkJwtToken(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        // 1. Get authentication information from HTTP Header.
        final String requestTokenHeader = request.getHeader(HTTP_HEADER_AUTHORIZATION);

        // 2. Check and remove prefix from Bearer Token.
        if (requestTokenHeader == null || !requestTokenHeader.startsWith(HTTP_HEADER_TOKEN_PREFIX))
            return;
        final String token = requestTokenHeader.substring(HTTP_HEADER_TOKEN_PREFIX.length());

        // 3. Validate and decode JWT Token.
        final NulJwtToken jwtToken = new NulJwtToken(token);
        final NulJwtToken.NulJwtTokenProperties properties = jwtToken.validate();
        if (properties == null)
            return;

        // 4. Query user information and set context.
        final CachedAccount account = accountService.getAccountByUid(properties.uid());
        final CachedRole role = authorityService.getRoleById(account.getRoleId());
        NulValidator.validate(role);

        final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                account, null, NulSpringUtil.extractSpringAuthorities(role)
        );
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Override
    public void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response,
                                    @NotNull FilterChain filterChain) throws ServletException, IOException {
        checkJwtToken(request, response);
        filterChain.doFilter(request, response);
    }
}
