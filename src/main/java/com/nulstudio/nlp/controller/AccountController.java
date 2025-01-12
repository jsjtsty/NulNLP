package com.nulstudio.nlp.controller;

import com.nulstudio.nlp.common.NulConstants;
import com.nulstudio.nlp.common.NulResult;
import com.nulstudio.nlp.domain.dto.AccountRegisterDto;
import com.nulstudio.nlp.domain.dto.AccountUpdateDto;
import com.nulstudio.nlp.domain.vo.AccountProfileVo;
import com.nulstudio.nlp.service.controller.AccountControllerService;
import com.nulstudio.nlp.util.NulJwtToken;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Resource
    private AccountControllerService accountControllerService;

    @GetMapping("/login")
    public ResponseEntity<NulResult<Void>> login(
            @RequestParam @NotNull String userName,
            @RequestParam @NotNull String password
    ) {
        final NulJwtToken token = accountControllerService.login(userName, password);
        final HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, NulConstants.BEARER_TOKEN_PREFIX + token.token());
        return ResponseEntity.ok().headers(headers).body(NulResult.response());
    }

    @DeleteMapping("/login")
    public NulResult<Void> logout(@NotNull HttpServletRequest request) {
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
        throw new RuntimeException();
    }

    @GetMapping
    public NulResult<AccountProfileVo> getProfile() {
        return NulResult.response(accountControllerService.getCurrentProfile());
    }

    @GetMapping("/{id}")
    public NulResult<AccountProfileVo> getProfileById(@PathVariable int id) {
        return NulResult.response(accountControllerService.getProfileById(id));
    }

    @PostMapping
    public NulResult<Void> createProfile(@RequestBody @NotNull AccountRegisterDto accountRegisterDto) {
        accountControllerService.register(accountRegisterDto);
        return NulResult.response();
    }

    @PutMapping
    public NulResult<Void> updateProfile(@RequestBody @NotNull AccountUpdateDto accountUpdateDto) {
        accountControllerService.updateProfile(accountUpdateDto);
        return NulResult.response();
    }
}
