package io.github.conut.msa.auth.integration.member.client;

import java.util.List;

import org.springframework.stereotype.Service;

import feign.RetryableException;
import io.github.conut.msa.auth.integration.member.dto.MemberCreateRequest;
import io.github.conut.msa.auth.integration.member.dto.MemberCreateResponse;
import io.github.conut.msa.auth.integration.member.exception.MemberServiceUnavailableException;
import io.github.conut.msa.auth.integration.member.feign.MemberFeignClient;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceClient {
    private final MemberFeignClient memberFeignClient;

    public MemberCreateResponse createMember(String uuid, String nickname, String description) {
        MemberCreateRequest memberCreateRequest = new MemberCreateRequest();
        memberCreateRequest.setUuid(uuid);
        memberCreateRequest.setNickname(nickname);
        memberCreateRequest.setDescription(description);
        return memberFeignClient.createMember(memberCreateRequest);
    }

    public List<String> getMemberRoles(String memberUuid) {
        try {
            return memberFeignClient.getMemberRoles(memberUuid);
        } catch (RetryableException exception) {
            throw new MemberServiceUnavailableException(exception);
        }
    }
}
