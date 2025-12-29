package io.github.conut.msa.auth.integration.member.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import io.github.conut.msa.auth.integration.member.dto.MemberCreateRequest;
import io.github.conut.msa.auth.integration.member.dto.MemberCreateResponse;

@FeignClient(name = "member-service", url = "${services.member.url:}")
public interface MemberClient {
    @PostMapping("/internal/member")
    MemberCreateResponse createMember(@RequestBody MemberCreateRequest memberCreateRequest);

    @GetMapping("/internal/member/roles")
    List<String> getMemberRoles(@RequestHeader("X-User-UUID") String memberUuid);
}
