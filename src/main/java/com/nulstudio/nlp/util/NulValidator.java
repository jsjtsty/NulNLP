package com.nulstudio.nlp.util;

import com.nulstudio.nlp.common.NulConstants;
import com.nulstudio.nlp.entity.NulInvite;
import com.nulstudio.nlp.entity.NulRole;
import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import jakarta.annotation.Nullable;

import java.time.Instant;

public final class NulValidator {
    public static void validate(@Nullable NulRole role) {
        if (role == null)
            throw new NulException(NulExceptionConstants.ROLE_NOT_EXIST);
        if (role.getStatus() != NulConstants.ROLE_STATUS_NORMAL)
            throw new NulException(NulExceptionConstants.ROLE_BLOCKED);
    }

    public static void validate(@Nullable NulInvite invite) {
        if (invite == null)
            throw new NulException(NulExceptionConstants.INVALID_INVITE_CODE);
        if (invite.getStatus() == NulConstants.INVITE_STATUS_BLOCKED)
            throw new NulException(NulExceptionConstants.INVITE_CODE_BLOCKED);
        if (invite.getRemaining() <= 0)
            throw new NulException(NulExceptionConstants.INVITE_CODE_USED_UP);
        if (Instant.now().isAfter(invite.getExpireTime()))
            throw new NulException(NulExceptionConstants.EXPIRED_INVITE_CODE);
    }
}
