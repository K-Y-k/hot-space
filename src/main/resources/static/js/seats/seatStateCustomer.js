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
                    // 예약이 없고 사용 불가능한 경우
                    seatElement.classList.add('noAvailable');
                 } else if (seat.reservationDTO && seat.reservationDTO.approvalState === '대기') {
                    // 예약이 있고, 승인되지 않은 경우
                    seatElement.classList.add('keeping');
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

                // 좌석 클릭 이벤트
                seatElement.addEventListener('click', function(event) {
                    if (!event.target.classList.contains('noAvailable')) {
                        // 선택한 좌석 ID를 숨겨진 입력 필드에 설정
                        document.getElementById('seatIdInput').value = event.target.getAttribute('data-seatId');

                        // 예약 창 띄우기
                        $('#reservationModal').modal('show');
                    }
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