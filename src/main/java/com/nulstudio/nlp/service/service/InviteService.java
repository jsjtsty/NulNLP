package com.nulstudio.nlp.service.service;

import com.nulstudio.nlp.common.NulConstants;
import com.nulstudio.nlp.domain.cache.CachedInvite;
import com.nulstudio.nlp.domain.cache.CachedRole;
import com.nulstudio.nlp.entity.NulInvite;
import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import com.nulstudio.nlp.service.respository.InviteRepositoryService;
import com.nulstudio.nlp.util.NulValidator;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Random;

@Service
public class InviteService {
    @Resource
    private InviteRepositoryService inviteRepositoryService;

    @Resource
    private AuthorityService authorityService;

    private final Random random = new Random();

    @Transactional
    public int registerInvite(@NotNull String inviteCode) {
        final CachedInvite invite = inviteRepositoryService.findInvite(inviteCode)
                .orElseThrow(() -> new NulException(NulExceptionConstants.INVALID_INVITE_CODE));
        invite.validate();

        final CachedRole role = authorityService.getRoleById(invite.getRole());
        NulValidator.validate(role);

        invite.setRemaining(invite.getRemaining() - 1);
        inviteRepositoryService.saveInvite(invite);

        return role.getId();
    }

    @Transactional
    @NotNull
    public CachedInvite generate(@NotNull CachedRole role, int remaining, @NotNull Timestamp expireTime, boolean blocked) {
        NulValidator.validate(role);

        CachedInvite invite = new CachedInvite();
        invite.setRemaining(remaining);
        invite.setExpireTime(expireTime);
        invite.setRole(role.getId());
        if (blocked)
            invite.setStatus(NulInvite.Status.Blocked);

        String inviteCode;
        final StringBuilder builder = new StringBuilder(NulConstants.INVITE_CODE_LENGTH);
        do {
            builder.setLength(0);
            for (int i = 0; i < NulConstants.INVITE_CODE_LENGTH; ++i) {
                builder.append(
                        switch (random.nextInt(3)) {
                            case 0 -> (char) ('a' + random.nextInt(26));
                            case 1 -> (char) ('A' + random.nextInt(26));
                            case 2 -> (char) ('0' + random.nextInt(10));
                            default -> throw new IllegalStateException();
                        }
                );
            }
            inviteCode = builder.toString();
        } while (inviteRepositoryService.findInvite(inviteCode).isPresent());

        invite.setInviteCode(inviteCode);
        invite = inviteRepositoryService.saveInvite(invite);
        return invite;
    }

    @NotNull
    public List<NulInvite> findAll() {
        return inviteRepositoryService.findAll();
    }
}
