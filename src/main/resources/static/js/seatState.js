// 저장된 좌석 보여주기
$(document).ready(function() {
    $.ajax({
        url: '/seats/api/seatList/' + storeId,
        type: 'GET',
        dataType: 'json',
        success: function(seats) {
            console.log(JSON.stringify(seats))
            var container = document.querySelector('.seat-container');

            seats.forEach(seat => {
                var seatElement = document.createElement('div');
                seatElement.classList.add('seat');

                // 이용상태에 따른 클래스 설정
                if (seat.available === false) {
                    seatElement.classList.add('noAvailable');
                }

                // seatType에 맞는 클래스 설정
                if (seat.seatType === 'small') {
                    seatElement.classList.add('small');
                } else if (seat.seatType === 'medium') {
                    seatElement.classList.add('medium');
                } else if (seat.seatType === 'large') {
                    seatElement.classList.add('large');
                }

                // 테이블 수용 인원 설정
                seatElement.textContent = seat.tableCapacity;

                // 테이블 위치와 속성 설정
                seatElement.style.position = 'absolute';
                seatElement.style.left = `${seat.posX}px`;
                seatElement.style.top = `${seat.posY}px`;

                seatElement.setAttribute('data-seatId', seat.seatId);
                seatElement.setAttribute('data-seatType', seat.seatType);
                seatElement.setAttribute('data-posx', seat.posX);
                seatElement.setAttribute('data-posy', seat.posY);
                seatElement.setAttribute('data-capacity', seat.tableCapacity);

                // 클릭시 좌석 이용가능여부 변경 핸들러 추가
                seatElement.addEventListener('click', function() {
                    updateSeatAvailable(seat.seatId);
                });

                // 좌석을 seat-container에 추가
                container.appendChild(seatElement);
            });
        },
        error: function (status, error) {
            console.log("오류", status, error);
        }
    });
});


// 좌석 이용 업데이트
function updateSeatAvailable(seatId) {
    console.log(seatId);

    // AJAX 요청으로 서버에 배치된 좌석들의 정보를 전송
    $.ajax({
        url: '/seats/api/update/available/' + seatId,
        type: 'POST',
        contentType: 'application/json', // 요청 데이터의 형식을 JSON으로 지정
        dataType: 'json',
        success: function(updateResult) {
            console.log("이용좌석 업데이트 완료", updateResult)

            // data-seatId 속성값으로 요소를 선택
            var seatElement = document.querySelector(`[data-seatId='${seatId}']`);

            // 요소 존재 여부 검증
            if (!seatElement) {
                console.log('좌석을 찾을 수 없습니다.');
            }

            // 좌석이용가능여부 변경 사항에 따른 css 적용
            if (!updateResult.available) {
                seatElement.style.backgroundColor = 'red';
            } else {
                seatElement.style.backgroundColor = 'white';
            }
        },
        error: function (status, error) {
            console.log("오류", status, error);
        }
    });
}