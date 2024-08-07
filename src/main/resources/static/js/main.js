var map;
var marker;
var position;
var infoWindow;
var debounceTimer;

var center_lat;
var center_lng;

var radius = 500;
var markersArray = [];


// 지도 생성
function initMap() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            // 현재 위치의 위도, 경도
            var current_lat = position.coords.latitude;
            var current_lng = position.coords.longitude;
            position = new naver.maps.LatLng(current_lat, current_lng);

            // 현재 위치의 지도 생성
            map = new naver.maps.Map('map', {
                center: position,
                zoom: 17
            });

            // 현재 중심 좌표
            var center = map.getCenter();
            center_lat = center.lat();
            center_lng = center.lng();

            map.setCursor('pointer') // 커서 손가락 모양으로 적용

            // 현재 위치의 마커 생성
            marker = new naver.maps.Marker({
                position: position,
                map: map,
                icon: {
                    url: "/img/main/marker.png",
                    scaledSize: new naver.maps.Size(40, 40),
                    origin: new naver.maps.Point(0, 0),
                    anchor: new naver.maps.Point(35, 85)
                }
            });

            // 현재 중심 반경 500m내에 등록된 가게 있는지 확인
            fetchMarkers(current_lat, current_lng)

            // 전역 infowindow 객체 초기화
            infoWindow = new naver.maps.InfoWindow({
                anchorSkew: true
            });

            // 마커 클릭 이벤트 리스너 설정
            naver.maps.Event.addListener(marker, "click", function(e) {
                searchCoordinateToAddress(e.coord);
            });

            // 맵 클릭시 해당 위치의 정보
            map.addListener('click', function(e) {
                searchCoordinateToAddress(e.coord);
            });

            // 지도 이동시 이벤트 리스너
            naver.maps.Event.addListener(map, 'center_changed', function() {
                if (debounceTimer) {
                    clearTimeout(debounceTimer);
                }

                // 한번만 동작하게 설정
                debounceTimer = setTimeout(function() {
                    var center = map.getCenter();
                    center_lat = center.lat();
                    center_lng = center.lng();

                    // 서버에 현재 중심 좌표를 전송하고 반경 500m 내의 데이터를 받아와서 마커로 표시하는 함수를 호출
                    fetchMarkers(center_lat, center_lng);
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


// 시설 종류가 선택되었을 때의 이벤트
document.getElementById('category').addEventListener('change', function(event) {
    fetchMarkers(center_lat, center_lng)
});


// 마우스 가리킬시 이벤트
document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('mouseover', function() {
        this.classList.remove('text-white');
        this.classList.add('hovered');
    });

    link.addEventListener('mouseout', function() {
        this.classList.remove('hovered');
        this.classList.add('text-white');
    });
});