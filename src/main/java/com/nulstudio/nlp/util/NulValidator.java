package com.nulstudio.nlp.util;

import com.nulstudio.nlp.domain.cache.CachedInvite;
import com.nulstudio.nlp.domain.cache.CachedRole;
import com.nulstudio.nlp.entity.NulInvite;
import com.nulstudio.nlp.entity.NulRole;
import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import jakarta.annotation.Nullable;

import java.sql.Timestamp;

public final class NulValidator {
    public static void validate(@Nullable CachedRole role) {
        if (role == null)
            throw new NulException(NulExceptionConstants.ROLE_NOT_EXIST);
        if (role.getStatus() != NulRole.Status.Normal)
            throw new NulException(NulExceptionConstants.ROLE_BLOCKED);
    }

    public static void validate(@Nullable CachedInvite invite) {
        if (invite == null)
            throw new NulException(NulExceptionConstants.INVALID_INVITE_CODE);
        if (invite.getStatus() == NulInvite.Status.Blocked)
            throw new NulException(NulExceptionConstants.INVITE_CODE_BLOCKED);
        if (invite.getRemaining() <= 0)
            throw new NulException(NulExceptionConstants.INVITE_CODE_USED_UP);
        if (new Timestamp(System.currentTimeMillis()).after(invite.getExpireTime()))
            throw new NulException(NulExceptionConstants.EXPIRED_INVITE_CODE);
    }
}
