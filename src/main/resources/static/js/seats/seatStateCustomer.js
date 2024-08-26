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

                // 좌석을 seat-container에 추가
                container.appendChild(seatElement);
            });
        },
        error: function (status, error) {
            console.log("오류", status, error);
        }
    });
});