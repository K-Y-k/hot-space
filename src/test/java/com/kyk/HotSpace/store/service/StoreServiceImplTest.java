package com.kyk.HotSpace.store.service;

import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.domain.entity.Role;
import com.kyk.HotSpace.member.repository.SpringDataJpaMemberRepository;
import com.kyk.HotSpace.store.domain.dto.StoreUpdateForm;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.store.repository.StoreRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
@SpringBootTest
public class StoreServiceImplTest {
    @Autowired
    StoreRepository storeRepository;

    @Autowired
    SpringDataJpaMemberRepository memberRepository;

    private Member savedMember;

    @BeforeEach
    void setUp() {
        // 공통으로 사용하는 Member 객체를 생성하고 저장
        Member member = new Member("memberA", "ID123", "pass123", Role.CUSTOMER);
        savedMember = memberRepository.saveMember(member);
    }

    @AfterEach
    void afterEach() {
        storeRepository.clear();
        memberRepository.clear();
    }

    @Test
    @DisplayName("가게 저장 및 조회")
    void save_store() {
        // given
        Store store = new Store(1L, "식당", "가게1", "유성구 xx로", "0000-0000-0000", null, 15.33, 17.44, savedMember, null, null);

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
        Store store = new Store(1L, "식당", "가게1", "유성구 xx로", "0000-0000-0000", null, 36.3691624, 127.3460812, savedMember, null, null);
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
        Store store1 = new Store(1L, "식당", "가게1", "유성구 xx로", "0000-0000-0000", null, 36.3691624, 127.3460812, savedMember, null, null);
        storeRepository.save(store1);

        Store store2 = new Store(2L, "pc방", "가게2", "유성구 xx로", "0000-0000-0000", null, 36.3691624, 127.3460812, savedMember, null, null);
        Store savedStore2 = storeRepository.save(store2);

        // when
        List<Store> storeWithinRadius = storeRepository.findStoreWithinRadiusByCategory(36.3694581, 127.3420904, 500.0, "pc방");
        Store findStore = storeWithinRadius.get(0);

        // then
        assertThat(savedStore2.getId()).isEqualTo(findStore.getId());
    }


    @Test
    @DisplayName("내가 등록한 가게 리스트")
    void storeList() {
        // given
        Store store1 = new Store(1L, "식당", "가게1", "유성구 xx로", "0000-0000-0000", null, 36.3691624, 127.3460812, savedMember, null, null);
        Store store2 = new Store(2L, "식당", "가게1", "유성구 xx로", "0000-0000-0000", null, 36.3691624, 127.3460812, savedMember, null, null);
        Store store3 = new Store(3L, "식당", "가게1", "유성구 xx로", "0000-0000-0000", null, 36.3691624, 127.3460812, savedMember, null, null);
        storeRepository.save(store1);
        storeRepository.save(store2);
        storeRepository.save(store3);

        // when
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "id"));
        Page<Store> pageResult = storeRepository.findStoresByMemberId(savedMember.getId(), pageable);

        // then
        assertEquals(10, pageResult.getSize());          // 한 페이지의 최대 데이터 개수
        assertEquals(3, pageResult.getContent().size()); // 한 페이지의 최대 데이터 개수 기준으로 포함된 총 데이터 크기
        assertEquals(3, pageResult.getTotalElements());  // 전체 총 데이터 크기
        assertEquals(1, pageResult.getTotalPages());     // 현재 총 페이지 수
        assertEquals(0, pageResult.getNumber());         // 현재 페이지 번호
    }


    @Test
    @DisplayName("가게 수정")
    void storeUpdate() {
        // given
        Store store1 = new Store(null, "식당", "가게1", "유성구 xx로", "0000-0000-0000", null, 36.3691624, 127.3460812, savedMember, null, null);
        Store savedStore = storeRepository.save(store1);

        StoreUpdateForm storeUpdateForm = new StoreUpdateForm("수정카테고리", "수정가게명", "수정주소", "수정번호");

        // when
        store1.changeStore(storeUpdateForm);

        // then
        assertThat(savedStore.getCategory()).isEqualTo(storeUpdateForm.getCategory());
        assertThat(savedStore.getName()).isEqualTo(storeUpdateForm.getName());
    }
}
