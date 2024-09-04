package com.kyk.HotSpace.store.controller;

import com.kyk.HotSpace.member.domain.LoginSessionConst;
import com.kyk.HotSpace.member.domain.dto.MemberDTO;
import com.kyk.HotSpace.store.domain.dto.StoreDTO;
import com.kyk.HotSpace.store.domain.dto.StoreUpdateForm;
import com.kyk.HotSpace.store.domain.dto.StoreUploadForm;
import com.kyk.HotSpace.store.domain.entity.Store;
import com.kyk.HotSpace.store.service.StoreServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/stores")
@RequiredArgsConstructor
public class StoreController {
    private final StoreServiceImpl storeService;

    @GetMapping("/upload")
    public String uploadForm(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                             @ModelAttribute("uploadForm") StoreUploadForm storeUploadForm,
                             Model model) {
        // 세션에 회원 데이터가 없으면 홈 화면으로 이동
        if(loginMember == null) {
            log.info("로그인 상태가 아님");

            model.addAttribute("message", "회원만 등록할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        return "stores/store_upload";
    }

    @PostMapping("/upload")
    public String storeUpload(@SessionAttribute(LoginSessionConst.LOGIN_MEMBER) MemberDTO loginMember,
                              @Valid @ModelAttribute("uploadForm") StoreUploadForm form, BindingResult bindingResult,
                              Model model) throws IOException {
        log.info("가게 이름 = {}", form.getName());
        log.info("가게 연락처 = {}", form.getNumber());
        log.info("가게 url = {}", form.getSiteUrl());
        log.info("가게 좌표 = {}, {}", form.getLatitude(), form.getLongitude());
        log.info("가게 주소 = {}", form.getAddress());
        log.info("가게 이미지 파일 = {}", form.getImageFiles().get(0).getOriginalFilename());

        if (bindingResult.hasErrors()) {
            return "stores/store_upload";
        }

        storeService.saveStore(loginMember.getId(), form);

        model.addAttribute("message", "등록 되었습니다!");
        model.addAttribute("redirectUrl", "/stores/list");
        return "messages";
    }

    @GetMapping("{storeId}/update")
    public String updateForm(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                             @ModelAttribute("updateForm")StoreUpdateForm form,
                             @PathVariable Long storeId, Model model) {
        // 세션 회원 검증
        if(loginMember == null) {
            log.info("로그인 상태가 아님");

            model.addAttribute("message", "회원만 이용할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        StoreDTO findStore = storeService.findById(storeId);

        // 본인 가게인지 검증
        if (!Objects.equals(findStore.getCEO_Id(), loginMember.getId())) {
            model.addAttribute("message", "본인 가게가 아닙니다.");
            model.addAttribute("redirectUrl", "/");
            return "messages";
        }
        
        form.setCategory(findStore.getCategory());
        form.setName(findStore.getName());
        form.setNumber(findStore.getNumber());
        form.setLatitude(findStore.getLatitude());
        form.setLongitude(findStore.getLongitude());
        form.setSiteUrl(findStore.getSiteUrl());

        log.info("원본 가게 카테고리 = {}", form.getAddress());
        log.info("원본 가게 이름 = {}", form.getName());
        log.info("원본 가게 전화번호 = {}", form.getNumber());
        log.info("원본 가게 위도 = {}", form.getLatitude());
        log.info("원본 가게 경도 = {}", form.getLongitude());
        log.info("원본 가게 url = {}", form.getSiteUrl());

        model.addAttribute("storeId", storeId);

        return "stores/store_update";
    }

    @PostMapping("{storeId}/update")
    public String storeUpdate(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                              @Valid @ModelAttribute("updateForm")StoreUpdateForm form,
                              BindingResult bindingResult,
                              @PathVariable Long storeId, Model model) throws IOException {
        // 세션 회원 검증
        if(loginMember == null) {
            log.info("로그인 상태가 아님");

            model.addAttribute("message", "회원만 이용할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        if (bindingResult.hasErrors()) {
            return "stores/store_update";
        }

        log.info("수정 가게 이름 = {}", form.getName());
        log.info("수정 가게 연락처 = {}", form.getNumber());
        log.info("수정 가게 url = {}", form.getSiteUrl());
        log.info("수정 가게 좌표 = {}, {}", form.getLatitude(), form.getLongitude());
        log.info("수정 가게 주소 = {}", form.getAddress());
        log.info("수정 가게 이미지 파일 = {}", form.getImageFiles().get(0).getOriginalFilename());

        storeService.changeStore(storeId, form);

        model.addAttribute("message", "수정 되었습니다.");
        model.addAttribute("redirectUrl", "/stores/list");
        return "messages";
    }

    @GetMapping("/{storeId}/delete")
    public String storeDelete(@PathVariable Long storeId, Model model) {
        // 가게, 가게 이미지 DB/파일 삭제
        storeService.deleteStore(storeId);

        model.addAttribute("message", "삭제 되었습니다!");
        model.addAttribute("redirectUrl", "/stores/list");
        return "messages";
    }


    @GetMapping("/list")
    public String storeList(@SessionAttribute(name = LoginSessionConst.LOGIN_MEMBER, required = false) MemberDTO loginMember,
                            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {
        // 세션에 회원 데이터가 없으면 홈 화면으로 이동
        if(loginMember == null) {
            log.info("로그인 상태가 아님");

            model.addAttribute("message", "회원만 이용할 수 있습니다. 로그인 먼저 해주세요!");
            model.addAttribute("redirectUrl", "/members/login");
            return "messages";
        }

        
        // 회원이 등록한 가게 페이징
        Page<Store> stores = storeService.findStoresByMemberId(loginMember.getId(), pageable);

        // 페이지 번호 설정
        int nowPage = pageable.getPageNumber() + 1;                  // 페이지에 넘어온 페이지를 가져옴 == boards.getPageable().getPageNumber()
                                                                     // pageable의 index는 0부터 시작이기에 1을 더해준 것이다.

        int startPage = Math.max(1, nowPage - 2);                    // 마이너스가  나오지 않게 Math.max로 최대 1로 조정
        int endPage = Math.min(nowPage + 2, stores.getTotalPages()); // 총 페이지보다 넘지 않게 Math.min으로 조정

        // 가게, 페이지 설정 모델 등록
        model.addAttribute("stores", stores);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        // 이전, 다음페이지
        model.addAttribute("previous", pageable.previousOrFirst().getPageNumber());
        model.addAttribute("next", pageable.next().getPageNumber());

        // 이전, 다음 페이지의 존재 여부
        model.addAttribute("hasPrev", stores.hasPrevious());
        model.addAttribute("hasNext", stores.hasNext());

        return "stores/store_list";
    }
}
