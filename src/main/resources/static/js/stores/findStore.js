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
            // 기존 마커, InfoWindow 모두 제거
            clearMarkersAndInfo();

            console.log(JSON.stringify(result))

            // 새로운 마커를 추가
            result.forEach(function(markerData) {
                var findStoreMarker = new naver.maps.Marker({
                    position: new naver.maps.LatLng(markerData.latitude, markerData.longitude),
                    map: map
                });

                // 가게 이름 InfoWindow 생성
                var markerInfoWindow = new naver.maps.InfoWindow({
                    content: '<div style="text-align: center; padding: 5px; font-size: 12px;">' + markerData.name + '</div>',
                    backgroundColor: "white",
                    borderColor: "black",
                    borderWidth: 1,
                    anchorSize: new naver.maps.Size(20, 20),
                    anchorSkew: true,
                    pixelOffset: new naver.maps.Point(0, -10)
                });

                // 마커에 커서를 올리면 가게 이름 InfoWindow 출력
                naver.maps.Event.addListener(findStoreMarker, 'mouseover', function() {
                    markerInfoWindow.open(map, findStoreMarker);
                });

                // 마커와 InfoWindow를 관리하는 배열에 저장
                markersArray.push({ marker: findStoreMarker, infoWindow: markerInfoWindow });

                // 생성한 마커 클릭 이벤트 핸들러: 클릭한 가게의 정보 출력
                naver.maps.Event.addListener(findStoreMarker, 'click', function() {
                    $.ajax({
                        url: '/stores/api/storeDetails',
                        type: 'GET',
                        dataType: "json",
                        data: { storeId: markerData.id },
                        success: function(storeDetailResult) {
                            console.log(JSON.stringify(storeDetailResult))

                            // 슬라이더 HTML 생성
                            var slides = storeDetailResult.imageFileName.map(fileName => `
                                <div class="swiper-slide">
                                    <img src="/storeFileImageUpload/${fileName}" class="thumb"/>
                                </div>
                            `).join('');

                            var contentString = `
                                <br>
                                <button class="close-btn" style="float: right;">X</button>
                                <br>

                                <div class="info_inner">
                                    <h3>${storeDetailResult.name}</h3>

                                    <br>

                                    <div class="swiper-container">
                                        <div class="swiper-wrapper">
                                            ${slides}
                                        </div>

                                        <div class="swiper-button-next"></div>
                                        <div class="swiper-button-prev"></div>
                                        <div class="swiper-pagination"></div>
                                    </div>

                                    <br>
                                    <br>
                                    <br>

                                    <h6>${storeDetailResult.address}</h6>
                                    <h6>${storeDetailResult.number}</h6>
                                    <a href="https://${storeDetailResult.siteUrl}" target="_blank">운영 사이트</a>

                                    <br>
                                    <br>

                                    <div class="button-container">
                                        <button class="btn btn-primary" style="width: 50%; height: 20%;" onclick="location.href='/seats/${storeDetailResult.id}/state/view'">테이블 확인/예약</button>
                                    </div>
                                </div>
                            `;

                            // <aside> 태그에 정보 출력
                            document.getElementById('info').innerHTML = contentString;

                            // Swiper 초기화
                            var swiper = new Swiper('.swiper-container', {
                                slidesPerView: 1,
                                spaceBetween: 10,
                                pagination: {
                                    el: '.swiper-pagination',
                                    clickable: true,
                                },
                                navigation: {
                                    nextEl: '.swiper-button-next',
                                    prevEl: '.swiper-button-prev',
                                },
                            });

                            // 닫기 버튼 클릭 이벤트 핸들러
                            document.querySelector('.close-btn').addEventListener('click', function() {
                                document.getElementById('info').innerHTML = '';
                            });
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
function clearMarkersAndInfo() {
    // 넣어둔 마커, InfoWindow 객체를 모두 조회하며 초기화
    markersArray.forEach(function(item) {
        item.marker.setMap(null); // 마커 제거
        item.infoWindow.close();  // InfoWindow 닫기
    });

    // 마커를 담는 배열도 초기화
    markersArray = [];
}
