package com.kyk.HotSpace.store.service;

import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.repository.MemberRepository;
import com.kyk.HotSpace.store.domain.dto.StoreUploadForm;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {
    private final MemberRepository memberRepository;

    private final StoreRepository storeRepository;


    @Override
    public Long saveStore(Long memberId, StoreUploadForm form) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("글 등록 실패: 로그인 상태가 아닙니다." + memberId));

        Store storeEntity = form.toEntity(findMember);
        storeRepository.save(storeEntity);

        return storeEntity.getId();
    }


}
