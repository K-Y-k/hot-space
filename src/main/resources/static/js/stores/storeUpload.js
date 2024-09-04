var map;
var marker;
var select_marker;
var position;
var infoWindow;

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

            map.setCursor('pointer');

            marker = new naver.maps.Marker({
                position: position,
                map: map,
                icon: {
                    url: "/img/main/marker.png",
                    scaledSize: new naver.maps.Size(30, 40),
                    origin: new naver.maps.Point(0, 0),
                    anchor: new naver.maps.Point(20, 40)
                }
            });

            infoWindow = new naver.maps.InfoWindow({
                anchorSkew: true
            });

            naver.maps.Event.addListener(map, 'click', function(e) {
                searchCoordinateToAddress(e.coord);
            });
        }, function(error) {
            console.error('Error occurred. Error code: ' + error.code);
        });
    } else {
        console.error('Geolocation은 이 브라우저에 지원하지 않습니다.');
    }
}
naver.maps.onJSContentLoaded = initMap;

function searchCoordinateToAddress(latlng) {
    click_lat = latlng.lat();
    click_lng = latlng.lng();

    console.log("latlng=", latlng);
    console.log("latitude=", click_lat);
    console.log("longitude=", click_lng);

    // naver.maps.Service.reverseGeocode 메서드를 호출하여 좌표를 주소로 변환
    naver.maps.Service.reverseGeocode({
        coords: latlng,  // 변환할 좌표 latlng를 설정
        orders: [        // 주소의 우선순위를 지정
            naver.maps.Service.OrderType.ROAD_ADDR // ROAD_ADDR (도로명 주소)
        ].join(',')
    }, function(status, response) {
        if (status === naver.maps.Service.Status.ERROR) {
            return alert('오류 발생');
        }

        // 결과를 items 배열에 저장
        var items = response.v2.results

        // 변환된 각 주소를 반복 처리하여 htmlAddresses 배열에 추가
        item = items[0];
        var address = makeAddress(item);
        addrType = item.name === 'roadaddr' ? '[도로명 주소]' : '';

        var contentString = [
            '<div class="info_inner" style="text-align: center;">',
            '   <h3>선택한 위치</h3>',
            '   <p>' + address + '</p>',
            '</div>'
        ].join('');

        document.getElementById("latitude").value = click_lat;
        document.getElementById("longitude").value = click_lng;
        document.getElementById("store-address").value = address;

        if (infoWindow.getMap()) {
            // 기존 마커 지움
            select_marker.setMap(null);

            infoWindow.close();
        } else {
            // 선택한 위치의 마커 재생성
            position = new naver.maps.LatLng(click_lat, click_lng);

            select_marker = new naver.maps.Marker({
                position: position,
                map: map
            });

            infoWindow.setContent(contentString);
            infoWindow.open(map, latlng);
        }
    });
}

// 내 위치로 이동 클릭 이벤트
$(document).ready(function() {
    $('#moveToCurrentLocation').on('click', function() {
        initMap();
        map.fitBounds(position);
    });
});