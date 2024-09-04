// 입력한 주소
function searchAddress() {
    var address = document.getElementById('address').value;

    naver.maps.Service.geocode({ address: address }, function(status, response) {
        if (status !== naver.maps.Service.Status.OK) {
            return alert('주소 검색에 실패했습니다.');
        }

        var result = response.v2.addresses[0]; // 검색된 첫 번째 주소 선택
        var coord = new naver.maps.LatLng(result.y, result.x);

        map.setCenter(coord);      // 지도 중심을 검색된 좌표로 이동
        map.setZoom(17);

        marker.setMap(null);       // 이전 현재 위치를 생성했던 마커를 제거
    });
}

// 엔터 키를 눌렀을 때 searchAddress() 함수 호출
function handleEnter(event) {
    if (event.key === "Enter") {
        searchAddress();
    }
}


// 주소 가공해서 가져오기 함수
function makeAddress(item) {
    if (!item) {
        return;
    }

    var name = item.name,
        region = item.region,
        land = item.land,
        isRoadAddress = name === 'roadaddr';

    var sido = '', sigugun = '', dongmyun = '', ri = '', rest = '';

    if (hasArea(region.area1)) {
        sido = region.area1.name;
    }

    if (hasArea(region.area2)) {
        sigugun = region.area2.name;
    }

    if (hasArea(region.area3)) {
        dongmyun = region.area3.name;
    }

    if (hasArea(region.area4)) {
        ri = region.area4.name;
    }

    if (land) {
        if (hasData(land.number1)) {
            if (hasData(land.type) && land.type === '2') {
                rest += '산';
            }

            rest += land.number1;

            if (hasData(land.number2)) {
                rest += ('-' + land.number2);
            }
        }

        if (isRoadAddress === true) {
            if (checkLastString(dongmyun, '면')) {
                ri = land.name;
            } else {
                dongmyun = land.name;
                ri = '';
            }

            if (hasAddition(land.addition0)) {
                rest += ' ' + land.addition0.value;
            }
        }
    }

    return [sido, sigugun, dongmyun, ri, rest].join(' ');
}

function hasArea(area) {
    return !!(area && area.name && area.name !== '');
}

function hasData(data) {
    return !!(data && data !== '');
}

function checkLastString (word, lastString) {
    return new RegExp(lastString + '$').test(word);
}

function hasAddition (addition) {
    return !!(addition && addition.value);
}
