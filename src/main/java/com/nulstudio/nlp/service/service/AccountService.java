package com.nulstudio.nlp.service.service;

import com.nulstudio.nlp.domain.cache.CachedAccount;
import com.nulstudio.nlp.domain.cache.CachedRole;
import com.nulstudio.nlp.exception.NulException;
import com.nulstudio.nlp.exception.NulExceptionConstants;
import com.nulstudio.nlp.service.respository.AccountRepositoryService;
import com.nulstudio.nlp.util.NulSpringUtil;
import com.nulstudio.nlp.util.NulValidator;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements UserDetailsService {
    @Resource
    private AccountRepositoryService accountRepositoryService;

    @Resource
    private AuthorityService authorityService;

    @Resource
    private InviteService inviteService;

    public long login(@NotNull String username, @NotNull String password) {
        final long uid = accountRepositoryService.getUidByUsername(username)
                .orElseThrow(() -> new NulException(NulExceptionConstants.USER_NOT_EXIST))
                .longValue();
        final CachedAccount account = accountRepositoryService.getAccountByUid(uid)
                .orElseThrow(() -> new NulException(NulExceptionConstants.USER_NOT_EXIST));
        final CachedRole role = authorityService.getRoleById(account.getRoleId());
        NulValidator.validate(role);
        final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, account.getPassword())) {
            throw new NulException(NulExceptionConstants.WRONG_PASSWORD);
        }
        return account.getId();
    }

    @Transactional
    public long register(@NotNull String username, @NotNull String password,
                        @NotNull String name, @NotNull String inviteCode) {
        if (accountRepositoryService.getUidByUsername(username).isPresent()) {
            throw new NulException(NulExceptionConstants.USER_ALREADY_EXIST);
        }

        final int roleId = inviteService.registerInvite(inviteCode);
        final String encryptedPassword = new BCryptPasswordEncoder().encode(password);

        CachedAccount account = new CachedAccount();
        account.setUsername(username);
        account.setPassword(encryptedPassword);
        account.setName(name);
        account.setRoleId(roleId);
        account = accountRepositoryService.saveAccount(account);

        return account.getId();
    }

    @NotNull
    public CachedAccount getAccountByUid(long uid) {
        return accountRepositoryService.getAccountByUid(uid)
                .orElseThrow(() -> new NulException(NulExceptionConstants.USER_NOT_EXIST));
    }

    public void updateAccount(@NotNull CachedAccount account) {
        accountRepositoryService.saveAccount(account);
    }

    public long getUidByUserName(@NotNull String userName) {
        return accountRepositoryService.getUidByUsername(userName)
                .orElseThrow(() -> new NulException(NulExceptionConstants.USER_NOT_EXIST))
                .longValue();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final long uid = getUidByUserName(username);
        final CachedAccount account = this.getAccountByUid(uid);
        final CachedRole role = authorityService.getRoleById(account.getRoleId());
        return new User(account.getUsername(), account.getPassword(), NulSpringUtil.extractSpringAuthorities(role));
    }
}
