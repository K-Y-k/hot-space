var map;
var marker;
var position;

function initMap() {
    if (navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(function(position) {
            var lat = position.coords.latitude;
            var lon = position.coords.longitude;
            position = new naver.maps.LatLng(lat, lon);

            map = new naver.maps.Map('map', {
                center: position,
                zoom: 17
            });

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

            // 전역 infowindow 객체 생성
            infowindow = new naver.maps.InfoWindow();

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

                if (infowindow.getMap()) {
                    infowindow.close();
                } else {
                    infowindow.setContent(contentString);
                    infowindow.open(map, marker);
                }
            });
        }, function(error) {
            console.error('Error occurred. Error code: ' + error.code);
            //   0: 알수없는 에러, 1: 허가가 안된 상태, 2: 이용할 수 없는 위치, 3: 타임 아웃
        });
    } else {
        console.error('Geolocation는 이 브라우저에 지원하지 않습니다.');
    }
}
window.onload = initMap;


// 내 위치로 이동 클릭 이벤트
$(document).ready(function() {
    $('#moveToCurrentLocation').on('click', function() {
        initMap();
        map.fitBounds(position);
    });
});
