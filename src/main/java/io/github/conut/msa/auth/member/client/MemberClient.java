package io.github.conut.msa.auth.member.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.github.conut.msa.auth.member.dto.MemberCreateRequest;
import io.github.conut.msa.auth.member.dto.MemberCreateResponse;

@FeignClient(name = "member-service", url = "http://localhost:8081")
public interface MemberClient {
    @PostMapping("/member")
    MemberCreateResponse createMember(@RequestBody MemberCreateRequest memberCreateRequest);
}
