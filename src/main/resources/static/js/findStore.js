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
