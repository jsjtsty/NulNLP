package com.nulstudio.nlp.service.respository;

import com.nulstudio.nlp.domain.cache.CachedRole;
import com.nulstudio.nlp.entity.NulRole;
import com.nulstudio.nlp.repository.RoleRepository;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorityRepositoryService {

    @Resource
    private RoleRepository roleRepository;

    //@Cacheable(value = "role", key = "#id", unless = "#result.isEmpty()")
    @Transactional
    public Optional<CachedRole> findByRoleId(int id) {
        final Optional<NulRole> optional = roleRepository.findById(id);
        return optional.map(CachedRole::new);
    }

}
