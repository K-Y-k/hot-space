package com.kyk.HotSpace.store.service;

import com.kyk.HotSpace.file.domain.StoreFile;
import com.kyk.HotSpace.file.repository.store.StoreFileRepository;
import com.kyk.HotSpace.member.domain.entity.Member;
import com.kyk.HotSpace.member.domain.entity.Role;
import com.kyk.HotSpace.member.repository.MemberRepository;
import com.kyk.HotSpace.store.domain.dto.StoreUpdateForm;
import com.kyk.HotSpace.store.domain.dto.StoreUploadForm;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService {
    private final MemberRepository memberRepository;

    private final StoreRepository storeRepository;
    private final StoreFileRepository storeFileRepository;

    @Value("${file.storeFileLocation}")
    private String storeFileLocation;


    @Override
    public Optional<Store> findById(Long id) {
        return storeRepository.findById(id);
    }

    @Override
    public Long saveStore(Long memberId, StoreUploadForm form) throws IOException {
        Member findMember = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("글 등록 실패: 로그인 상태가 아닙니다." + memberId));

        Store storeEntity = form.toEntity(findMember);
        storeRepository.save(storeEntity);
        
        findMember.changeRole(Role.CEO);


        // 이미지 파일이 있을 경우 저장
        List<MultipartFile> imageFiles = form.getImageFiles();
        if (!imageFiles.isEmpty() && !imageFiles.get(0).getOriginalFilename().isBlank()) {
            fileUpload(imageFiles, storeEntity);
        }
        
        return storeEntity.getId();
    }

    private void fileUpload(List<MultipartFile> imageFiles, Store storeEntity) throws IOException {
        // 루프를 돌려 파일을 모두 찾고 반환
        for (MultipartFile file: imageFiles) {
            String originalFileName = file.getOriginalFilename();

            // 파일에 이름을 붙일 랜덤으로 식별자 지정
            UUID uuid = UUID.randomUUID();
            String storedFileName = uuid + "_" + originalFileName;
            String savePath = storeFileLocation;

            // 실제 파일 저장 경로와 파일 이름 지정한 File 객체 생성 및 저장
            file.transferTo(new File(savePath, storedFileName));

            // 가게 파일 엔티티 저장
            StoreFile storeFileEntity = StoreFile.toStoreFileEntity(storeEntity, originalFileName, storedFileName);
            storeFileRepository.saveProfile(storeFileEntity);
        }
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

    @Override
    public List<Store> findMarkersWithinRadiusByCategory(double center_lat, double center_lng, double radiusIn, String category) {
        return storeRepository.findStoreWithinRadiusByCategory(center_lat, center_lng, radiusIn, category);
    }

}