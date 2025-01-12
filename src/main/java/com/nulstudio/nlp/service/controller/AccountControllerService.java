package com.nulstudio.nlp.service.controller;

import com.nulstudio.nlp.config.NulSecurityConfig;
import com.nulstudio.nlp.domain.cache.CachedAccount;
import com.nulstudio.nlp.domain.dto.AccountRegisterDto;
import com.nulstudio.nlp.domain.dto.AccountUpdateDto;
import com.nulstudio.nlp.domain.vo.AccountProfileVo;
import com.nulstudio.nlp.service.service.AccountService;
import com.nulstudio.nlp.service.service.AuthorityService;
import com.nulstudio.nlp.util.NulJwtToken;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountControllerService {
    @Resource
    private AccountService accountService;

    @Resource
    private AuthorityService authorityService;


    @NotNull
    public AccountProfileVo getCurrentProfile() {
        final CachedAccount account = NulSecurityConfig.getContextAccount();
        final String roleName = authorityService.getRoleById(account.getRoleId()).getName();
        return new AccountProfileVo(account.getId(), account.getUsername(), account.getName(), roleName);
    }

    @NotNull
    public AccountProfileVo getProfileById(int uid) {
        final CachedAccount account = accountService.getAccountByUid(uid);
        final String roleName = authorityService.getRoleById(account.getRoleId()).getName();
        return new AccountProfileVo(account.getId(), account.getUsername(), account.getName(), roleName);
    }

    public void updateProfile(@NotNull AccountUpdateDto accountUpdateDto) {
        final CachedAccount account = NulSecurityConfig.getContextAccount();
        if (accountUpdateDto.password() != null) {
            final String encryptedPassword = new BCryptPasswordEncoder().encode(accountUpdateDto.password());
            account.setPassword(encryptedPassword);
        }
        if (accountUpdateDto.nickName() != null) {
            account.setName(accountUpdateDto.nickName());
        }
        accountService.updateAccount(account);
    }

    @NotNull
    public NulJwtToken login(@NotNull String username, @NotNull String password) {
        final long uid = accountService.login(username, password);
        return NulJwtToken.generate(new NulJwtToken.NulJwtTokenProperties(uid));
    }

    public void register(@NotNull AccountRegisterDto accountRegisterDto) {
        accountService.register(
                accountRegisterDto.username(), accountRegisterDto.password(),
                accountRegisterDto.name(), accountRegisterDto.inviteCode()
        );
    }
}
