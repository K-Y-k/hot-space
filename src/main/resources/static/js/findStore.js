// 서버에서 현재 지도 중심 반경 500m 내의 등록된 가게 데이터를 받아와서 마커로 표시하는 함수
function fetchMarkers(center_lat, center_lng) {
    console.log('Current center:', center_lat, center_lng);

    // 카테고리 가져오기
    var category = document.getElementById('category').value;

    $.ajax({
        url: '/stores/api/checkStore',
        type: 'GET',
        dataType: "json",
        data: {center_lat, center_lng, radius, category},
        success: function(result) {
            // 기존 마커를 모두 제거
            clearMarkers();

            console.log(JSON.stringify(result))

            // 새로운 마커를 추가
            result.forEach(function(markerData) {
                var findStoreMarker = new naver.maps.Marker({
                    position: new naver.maps.LatLng(markerData.latitude, markerData.longitude),
                    map: map
                });

                markersArray.push(findStoreMarker);

                // 생성한 마커 클릭 이벤트 핸들러: 클릭한 가게의 정보 출력
                naver.maps.Event.addListener(findStoreMarker, 'click', function() {
                    $.ajax({
                        url: '/stores/api/storeDetails',
                        type: 'GET',
                        dataType: "json",
                        data: { storeId: markerData.id },
                        success: function(storeDetailResult) {
                            console.log(JSON.stringify(storeDetailResult))

                            var contentString = `
                                <div class="info_inner">
                                    <h3>${storeDetailResult.name}</h3>
                                    <img src="./img/marker.png" width="55" height="55" class="thumb"/> <br>
                                    <p>
                                        <h6>주소: ${storeDetailResult.address}</h6>
                                        <h6>연락처: ${storeDetailResult.number}</h6>
                                        <div class="button-container">
                                            <button class="btn btn-secondary">테이블 확인</button>
                                            <button class="btn btn-secondary">예약하기</button>
                                        </div>
                                        <a href="${storeDetailResult.siteUrl}" target="_blank">${storeDetailResult.siteUrl}</a>
                                    </p>
                                </div>
                            `;

                            if (infoWindow.getMap()) {
                                infoWindow.close();
                            } else {
                                infoWindow.setContent(contentString);
                                infoWindow.open(map, findStoreMarker);
                            }
                        },
                        error: function (status, error) {
                            console.log("오류", status, error);
                        }
                    });
                });
            });
        },
        error: function (status, error) {
            console.log("오류", status, error);
        }
    });
}

// 기존 마커를 모두 제거하는 함수
function clearMarkers() {
    // 넣어둔 마커 객체를 모두 조회하며 초기화
    markersArray.forEach(function(marker) {
        marker.setMap(null);
    });

    // 마커를 담는 배열도 초기화
    markersArray = [];
}
