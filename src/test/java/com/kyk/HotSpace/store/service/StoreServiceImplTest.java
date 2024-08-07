package com.kyk.HotSpace.store.service;

import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.domain.entity.Role;
import com.kyk.HotSpace.member.repository.SpringDataJpaMemberRepository;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.store.repository.StoreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
public class StoreServiceImplTest {
    @Autowired
    StoreRepository storeRepository;

    @Autowired
    SpringDataJpaMemberRepository memberRepository;

    @AfterEach
    void afterEach() {
        storeRepository.clear();
        memberRepository.clear();
    }

    @Test
    @DisplayName("가게 저장 및 조회")
    void save_store() {
        // given
        Member member = new Member("memberA", "ID123", "pass123", Role.CUSTOMER);
        Member savedMember = memberRepository.saveMember(member);

        Store store = new Store(1L, "식당", "가게1", "유성구 xx로", "0000-0000-0000", null, 15.33, 17.44, savedMember);

        // when
        Store savedStore = storeRepository.save(store);
        Store findStore = storeRepository.findById(savedStore.getId()).orElseThrow(() ->
                new IllegalArgumentException("가게 가져오기 실패: 가게를 찾지 못했습니다." + savedStore.getId()));

        // then
        assertThat(savedStore.getId()).isEqualTo(findStore.getId());
    }


    @Test
    @DisplayName("반경 500m 등록된 가게 찾기")
    void find_store_withinRadius() {
        // given
        Member member = new Member("memberA", "ID123", "pass123", Role.CUSTOMER);
        Member savedMember = memberRepository.saveMember(member);

        Store store = new Store(1L, "식당", "가게1", "유성구 xx로", "0000-0000-0000", null, 36.3691624, 127.3460812, savedMember);
        Store savedStore = storeRepository.save(store);

        // when
        List<Store> storeWithinRadius = storeRepository.findStoreWithinRadius(36.3694581, 127.3420904, 500.0);
        Store findStore = storeWithinRadius.get(0);

        // then
        assertThat(savedStore.getId()).isEqualTo(findStore.getId());
    }

    @Test
    @DisplayName("가게 종류에 맞는 반경 500m 등록된 가게 찾기")
    void find_store_withinRadius_byCategory() {
        // given
        Member member = new Member("memberA", "ID123", "pass123", Role.CUSTOMER);
        Member savedMember = memberRepository.saveMember(member);

        Store store1 = new Store(1L, "식당", "가게1", "유성구 xx로", "0000-0000-0000", null, 36.3691624, 127.3460812, savedMember);
        storeRepository.save(store1);

        Store store2 = new Store(2L, "pc방", "가게2", "유성구 xx로", "0000-0000-0000", null, 36.3691624, 127.3460812, savedMember);
        Store savedStore2 = storeRepository.save(store2);

        // when
        List<Store> storeWithinRadius = storeRepository.findStoreWithinRadiusByCategory(36.3694581, 127.3420904, 500.0, "pc방");
        Store findStore = storeWithinRadius.get(0);

        // then
        assertThat(savedStore2.getId()).isEqualTo(findStore.getId());
    }
}
