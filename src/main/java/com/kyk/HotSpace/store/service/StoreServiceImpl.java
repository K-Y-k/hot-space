package com.kyk.HotSpace.store.service;

import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.domain.entity.Role;
import com.kyk.HotSpace.member.repository.MemberRepository;
import com.kyk.HotSpace.store.domain.dto.StoreDTO;
import com.kyk.HotSpace.store.domain.dto.StoreUpdateForm;
import com.kyk.HotSpace.store.domain.dto.StoreUploadForm;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService {
    private final MemberRepository memberRepository;

    private final StoreRepository storeRepository;


    @Override
    public Long saveStore(Long memberId, StoreUploadForm form) {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("글 등록 실패: 로그인 상태가 아닙니다." + memberId));

        Store storeEntity = form.toEntity(findMember);
        storeRepository.save(storeEntity);
        
        findMember.changeRole(Role.CEO);
        
        return storeEntity.getId();
    }

    @Override
    public void changeStore(Long memberId, StoreUpdateForm form) {

    }

    @Override
    public void deleteStore(Long memberId) {

    }

    @Override
    public List<Store> findMarkersWithinRadius(double lat, double lng, double radius) {
        return storeRepository.findStoreWithinRadius(lat, lng, radius);
    }

}