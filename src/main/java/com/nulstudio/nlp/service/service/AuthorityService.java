package com.nulstudio.nlp.service.service;

import com.nulstudio.nlp.domain.cache.CachedRole;
import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import com.nulstudio.nlp.service.respository.AuthorityRepositoryService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityService {

    @Resource
    private AuthorityRepositoryService authorityRepositoryService;

    @NotNull
    public CachedRole getRoleById(int id) {
        final Optional<CachedRole> optional = authorityRepositoryService.findByRoleId(id);
        if (optional.isEmpty()) {
            throw new NulException(NulExceptionConstants.ROLE_NOT_EXIST);
        }
        return optional.get();
    }
}
