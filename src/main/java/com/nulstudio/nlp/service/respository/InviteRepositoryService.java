package com.nulstudio.nlp.service.respository;

import com.nulstudio.nlp.domain.cache.CachedInvite;
import com.nulstudio.nlp.entity.NulInvite;
import com.nulstudio.nlp.repository.InviteRepository;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InviteRepositoryService {
    @Resource
    private InviteRepository inviteRepository;

    @Cacheable(value = "invite", key = "#inviteCode")
    @NotNull
    public Optional<CachedInvite> findInvite(@NotNull String inviteCode) {
        final Optional<NulInvite> optional = inviteRepository.findByInviteCode(inviteCode);
        return optional.map(CachedInvite::new);
    }

    @CachePut(value = "invite", key = "#invite.inviteCode")
    @NotNull
    public CachedInvite saveInvite(@NotNull CachedInvite invite) {
        final NulInvite result = inviteRepository.save(invite.restore());
        return new CachedInvite(result);
    }

    @NotNull
    public List<NulInvite> findAll() {
        return inviteRepository.findAll();
    }
}
