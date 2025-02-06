package com.nulstudio.nlp.service;

import com.nulstudio.nlp.common.NulConstants;
import com.nulstudio.nlp.domain.dto.AccountLoginDto;
import com.nulstudio.nlp.domain.dto.AccountRegisterDto;
import com.nulstudio.nlp.domain.vo.InviteVo;
import com.nulstudio.nlp.entity.NulAccount;
import com.nulstudio.nlp.entity.NulInvite;
import com.nulstudio.nlp.entity.NulRole;
import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import com.nulstudio.nlp.repository.AccountRepository;
import com.nulstudio.nlp.repository.InviteRepository;
import com.nulstudio.nlp.repository.RoleRepository;
import com.nulstudio.nlp.util.NulSpringUtil;
import com.nulstudio.nlp.util.NulValidator;
import jakarta.annotation.Resource;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Random;

@Service
public class AccountService implements UserDetailsService {
    @Resource
    private AccountRepository accountRepository;

    @Resource
    private RoleRepository roleRepository;

    @Resource
    private InviteRepository inviteRepository;

    private final Random random = new Random();

    public @NotNull ObjectId login(@NotNull AccountLoginDto accountLoginDto) {
        final NulAccount account = accountRepository.findByUsername(accountLoginDto.username())
                .orElseThrow(() -> new NulException(NulExceptionConstants.USER_NOT_EXIST));
        final NulRole role = roleRepository.findById(account.getRoleId())
                .orElseThrow(() -> new NulException(NulExceptionConstants.ROLE_NOT_EXIST));
        NulValidator.validate(role);
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(accountLoginDto.password(), account.getPassword())) {
            throw new NulException(NulExceptionConstants.WRONG_PASSWORD);
        }
        return account.getId();
    }

    @Transactional
    public @NotNull ObjectId register(@NotNull AccountRegisterDto accountRegisterDto) {
        if (accountRepository.existsByUsername(accountRegisterDto.username())) {
            throw new NulException(NulExceptionConstants.USER_ALREADY_EXIST);
        }
        final NulInvite invite = inviteRepository.findByInviteCode(accountRegisterDto.inviteCode())
                .orElseThrow(() -> new NulException(NulExceptionConstants.INVALID_INVITE_CODE));
        NulValidator.validate(invite);

        final NulRole role = roleRepository.findById(invite.getRoleId())
                .orElseThrow(() -> new NulException(NulExceptionConstants.ROLE_NOT_EXIST));
        NulValidator.validate(role);

        invite.setRemaining(invite.getRemaining() - 1);
        inviteRepository.save(invite);

        NulAccount account = new NulAccount();
        account.setUsername(accountRegisterDto.username());
        account.setPassword(accountRegisterDto.password());
        account.setName(accountRegisterDto.name());
        account.setRoleId(invite.getRoleId());
        account = accountRepository.save(account);

        return account.getId();
    }

    public @NotNull NulAccount findAccountById(@NotNull ObjectId id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NulException(NulExceptionConstants.USER_NOT_EXIST));
    }

    public @NotNull NulRole findRoleById(@NotNull ObjectId id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new NulException(NulExceptionConstants.ROLE_NOT_EXIST));
    }

    @Transactional
    public @NotNull InviteVo generateInvite(
            @NotNull ObjectId roleId, int remaining, @NotNull Instant expireTime, int status
    ) {
        final NulRole role = findRoleById(roleId);
        NulValidator.validate(role);

        final NulInvite invite = new NulInvite();
        invite.setRemaining(remaining);
        invite.setExpireTime(expireTime);
        invite.setRoleId(roleId);
        invite.setStatus(status);

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
        } while (inviteRepository.existsByInviteCode(inviteCode));

        invite.setInviteCode(inviteCode);
        inviteRepository.save(invite);
        return new InviteVo(invite);
    }

    @Override
    public @NotNull UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final NulAccount account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new NulException(NulExceptionConstants.USER_NOT_EXIST));
        final NulRole role = roleRepository.findById(account.getRoleId())
                .orElseThrow(() -> new NulException(NulExceptionConstants.ROLE_NOT_EXIST));
        return new User(account.getUsername(), account.getPassword(), NulSpringUtil.extractSpringAuthorities(role));
    }
}
