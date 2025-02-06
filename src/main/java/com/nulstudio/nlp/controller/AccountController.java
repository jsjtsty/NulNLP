package com.nulstudio.nlp.controller;

import com.nulstudio.nlp.common.NulConstants;
import com.nulstudio.nlp.common.NulResult;
import com.nulstudio.nlp.domain.dto.AccountLoginDto;
import com.nulstudio.nlp.domain.dto.AccountRegisterDto;
import com.nulstudio.nlp.domain.dto.AccountUpdateDto;
import com.nulstudio.nlp.domain.vo.AccountProfileVo;
import com.nulstudio.nlp.domain.vo.InviteVo;
import com.nulstudio.nlp.domain.vo.LoginVo;
import com.nulstudio.nlp.service.AccountService;
import com.nulstudio.nlp.util.NulJwtToken;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
public class AccountController {

    @Resource
    private AccountService accountService;

    @PostMapping("/sessions")
    public NulResult<LoginVo> login(
            @RequestBody AccountLoginDto accountLoginDto
    ) {
        final NulJwtToken token = NulJwtToken.generate(new NulJwtToken.NulJwtTokenProperties(
                accountService.login(accountLoginDto)
        ));
        return NulResult.response(new LoginVo(token.token()));
    }

    @DeleteMapping("/sessions")
    public NulResult<Void> logout(@NotNull HttpServletRequest request) {
        // TODO
        throw new RuntimeException();
    }

    @PostMapping("/account")
    public NulResult<LoginVo> register(@RequestBody AccountRegisterDto accountRegisterDto) {
        final NulJwtToken token = NulJwtToken.generate(new NulJwtToken.NulJwtTokenProperties(
                accountService.register(accountRegisterDto)
        ));
        return NulResult.response(new LoginVo(token.token()));
    }

    @PreAuthorize("hasAuthority('admin:invite')")
    @PostMapping("/account/invite")
    public @NotNull NulResult<InviteVo> generateInvite(
            @RequestParam ObjectId roleId,
            @RequestParam(defaultValue = "1") int remaining,
            @RequestParam(required = false) @Nullable Instant expireTime,
            @RequestParam(defaultValue = "0") int status
    ) {
        if (expireTime == null) {
            expireTime = Instant.now().plusMillis(NulConstants.INVITE_CODE_DEFAULT_EXPIRATION_TIME);
        }
        return NulResult.response(accountService.generateInvite(roleId, remaining, expireTime, status));
    }

    @GetMapping
    public NulResult<AccountProfileVo> getProfile() {
        // TODO
        throw new RuntimeException();
    }

    @GetMapping("/{id}")
    public NulResult<AccountProfileVo> getProfileById(@PathVariable int id) {
        // TODO
        throw new RuntimeException();
    }

    @PutMapping
    public NulResult<Void> updateProfile(@RequestBody AccountUpdateDto accountUpdateDto) {
        // TODO
        throw new RuntimeException();
    }
}
