package com.kyk.HotSpace.store.service;

import com.kyk.HotSpace.store.domain.dto.StoreUploadForm;

public interface StoreService {
    Long saveStore(Long memberId, StoreUploadForm form);
}
