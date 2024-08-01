// 클릭한 위치의 주소 등의 정보를 정보창에 출력
function searchCoordinateToAddress(latlng) {
    console.log("latlng=", latlng);
    var click_lat = latlng.lat();
    var click_lng = latlng.lng();

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
            '<div class="info_inner">',
            '   <h3>체인점 이름</h3>',
            '   <img src="./img/marker.png" width="55" height="55" class="thumb"/> <br>',
            '   <p>',
            '     ' + addrType + ' ' + address,
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
            // 인포윈도우가 열려 있을 경우 -> 닫음
            infoWindow.close();
        } else {
            // 인포윈도우가 닫혀 있을 경우 -> 표시
            infoWindow.setContent(contentString);
            infoWindow.open(map, latlng);
        }
    });
}


function getInfoWindowContent(latlng) {
    var items = response.v2.results;
    var item = items[0];
    var address = makeAddress(item);
    var addrType = item.name === 'roadaddr' ? '[도로명 주소]' : '';

    var contentString = [
        '<div class="info_inner">',
        '   <h3>체인점 이름</h3>',
        '   <img src="./img/marker.png" width="55" height="55" class="thumb"/> <br>',
        '   <p>',
        '     ' + addrType + ' ' + address,
        '       <h6>연락처</h6>',
        '       <div class="button-container">',
        '           <button class="btn btn-secondary">테이블 확인</button>',
        '           <button class="btn btn-secondary">예약하기</button>',
        '       </div>',
        '       <a href="http://www.seoul.go.kr" target="_blank">www.seoul.go.kr/</a>',
        '   </p>',
        '</div>'
    ].join('');

    return contentString;
}