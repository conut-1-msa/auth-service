package io.github.conut.msa.auth.invite.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.conut.msa.auth.invite.service.InviteService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth/invite")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminInviteController {
    private final InviteService inviteService;

    @PostMapping
    public ResponseEntity<?> createInviteCode() {
        return ResponseEntity.status(HttpStatus.CREATED).body(inviteService.createInviteCode());
    }

    @GetMapping("/list")
    public ResponseEntity<?> getInviteCodeList() {
        return ResponseEntity.ok(inviteService.getInviteCodeList());
    }
}
