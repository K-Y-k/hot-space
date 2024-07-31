var map;
var marker;
var position;
var infoWindow;
var debounceTimer;
var radius = 500;

var click_lat;
var click_lng;

// 지도 생성
function initMap() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            // 현재 위치의 위도, 경도
            var lat = position.coords.latitude;
            var lon = position.coords.longitude;
            position = new naver.maps.LatLng(lat, lon);

            // 현재 위치의 지도 생성
            map = new naver.maps.Map('map', {
                center: position,
                zoom: 17
            });

            map.setCursor('pointer') // 커서 손가락 모양으로 적용

            // 현재 위치의 마커 생성
            marker = new naver.maps.Marker({
                position: position,
                map: map,
                icon: {
                    url: "/img/marker.png",
                    scaledSize: new naver.maps.Size(40, 40),
                    origin: new naver.maps.Point(0, 0),
                    anchor: new naver.maps.Point(35, 85)
                }
            });


            // 전역 infowindow 객체 초기화
            infoWindow = new naver.maps.InfoWindow({
                anchorSkew: true
            });


            // 마커 클릭 이벤트 리스너 설정
            naver.maps.Event.addListener(marker, "click", function(e) {
                var contentString = [
                    '<div class="info_inner">',
                    '   <h3>체인점 이름</h3>',
                    '   <img src="./img/marker.png" width="55" height="55" class="thumb"/> <br>',
                    '   <p>',
                    '       <h6>주소</h6>',
                    '       <h6>연락처</h6>',
                    '       <div class="button-container">',
                    '           <button class="btn btn-secondary">테이블 확인</button>',
                    '           <button class="btn btn-secondary">예약하기</button>',
                    '       </div>',
                    '       <a href="http://www.seoul.go.kr" target="_blank">www.seoul.go.kr/</a>',
                    '   </p>',
                    '</div>'
                ].join('');

                if (infoWindow.getMap()) {
                    infoWindow.close();
                } else {
                    infoWindow.setContent(contentString);
                    infoWindow.open(map, marker);
                }
            });

            // 맵 클릭시 해당 위치의 정보
            map.addListener('click', function(e) {
                searchCoordinateToAddress(e.coord);
            });

            // 지도 이동 이벤트 리스너
            naver.maps.Event.addListener(map, 'center_changed', function() {
                if (debounceTimer) {
                    clearTimeout(debounceTimer);
                }

                debounceTimer = setTimeout(function() {
                    var center = map.getCenter();
                    var lat = center.lat();
                    var lng = center.lng();
                    console.log('Current center:', lat, lng);

                    // 서버에 현재 중심 좌표를 전송하고 반경 500m 내의 데이터를 받아와서 마커로 표시하는 함수를 호출
                    fetchMarkers(lat, lng);
                }, 300); // 300ms 디바운스 시간 설정
            });
        }, function(error) {
            console.error('Error occurred. Error code: ' + error.code);
            //   0: 알수없는 에러, 1: 허가가 안된 상태, 2: 이용할 수 없는 위치, 3: 타임 아웃
        });
    } else {
        console.error('Geolocation는 이 브라우저에 지원하지 않습니다.');
    }
}

naver.maps.onJSContentLoaded = initMap;


// 내 위치로 이동 클릭 이벤트
$(document).ready(function() {
    $('#moveToCurrentLocation').on('click', function() {
        initMap();
        map.fitBounds(position);
    });
});

// 서버에서 반경 500m 내의 데이터를 받아와서 마커로 표시하는 함수
function fetchMarkers(lat, lng) {
    console.log('받아와짐 : ' + lat + lng)

    $.ajax({
//        url: '/stores/api/checkStore?lat=${lat}&lng=${lng}&radius=500',
        url: '/stores/api/checkStore',
        type: 'GET',
        dataType: "json",
        data: {lat, lng, radius},
        success: function(result) {
            // 기존 마커를 모두 제거
            clearMarkers();

            console.log(JSON.stringify(result))

            // 새로운 마커를 추가
            result.forEach(function(markerData) {
                var newMarker = new naver.maps.Marker({
                    position: new naver.maps.LatLng(markerData.lat, markerData.lng),
                    map: map
                });

                markersArray.push(newMarker);
            });
        },
        error: function (status, error) {
            console.log("오류", status, error);
        }
    });
}

// 기존 마커를 모두 제거하는 함수
function clearMarkers() {
    markersArray.forEach(function(marker) {
        marker.setMap(null);
    });

    markersArray = [];
}

