package io.github.conut.msa.auth.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import feign.RetryableException;
import io.github.conut.msa.auth.member.client.MemberClient;
import io.github.conut.msa.auth.member.dto.MemberCreateRequest;
import io.github.conut.msa.auth.member.dto.MemberCreateResponse;
import io.github.conut.msa.auth.member.exception.MemberServiceUnavailableException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberClient memberClient;

    public MemberCreateResponse createMember(String nickname, String description) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest();
        memberCreateRequest.setNickname(nickname);
        memberCreateRequest.setDescription(description);
        return memberClient.createMember(memberCreateRequest);
    }

    public List<String> getMemberRoles(String memberUuid) {
        try {
            return memberClient.getMemberRoles(memberUuid);
        } catch (RetryableException exception) {
            throw new MemberServiceUnavailableException(exception);
        }
    }
}
