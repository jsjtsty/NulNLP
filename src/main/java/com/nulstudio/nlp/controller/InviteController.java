package com.nulstudio.nlp.controller;

import com.nulstudio.nlp.common.NulResult;
import com.nulstudio.nlp.domain.vo.InviteVo;
import com.nulstudio.nlp.service.controller.InviteControllerService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
@RequestMapping("/account/invite")
public class InviteController {
    @Resource
    private InviteControllerService inviteControllerService;


    @PreAuthorize("hasAuthority('admin:invite')")
    @PostMapping
    public NulResult<InviteVo> generate(
            @RequestParam int roleId,
            @RequestParam(defaultValue = "1") int remaining,
            @RequestParam(required = false) @Nullable Timestamp expireTime,
            @RequestParam(defaultValue = "false") boolean blocked
    ) {
        return NulResult.response(inviteControllerService.generate(roleId, remaining, expireTime, blocked));
    }
}
