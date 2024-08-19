// 좌석 끌어오기 이벤트
$(document).ready(function() {
    $(".seat").draggable({
        helper: "clone",                 // 드래그할 때 복사본을 생성
        stop: function(event, ui) {
            // 가져온 좌석 타입
            var originalId = $(this).attr("id");

            // 드래그가 끝난 후 위치 값을 업데이트
            var posX = ui.offset.left - $(".seat-container").offset().left;
            var posY = ui.offset.top - $(".seat-container").offset().top;

            // 복사된 좌석을 드롭 영역에 추가
            var newSeat = $(ui.helper).clone(false);
            newSeat.css({
                position: "absolute",
                left: posX,
                top: posY
            }).removeClass("ui-draggable-dragging") // 드래그 클래스 제거
              .addClass("cloned-seat");             // 복제 클래스 구분을 위해 추가

            // 복사된 좌석 타입, 인원수, 포지션 속성 설정
            newSeat.attr("data-seatType", originalId);
            newSeat.attr("data-capacity", 0);
            newSeat.attr("data-posx", posX);
            newSeat.attr("data-posy", posY);

            // seat-container에 추가
            $(".seat-container").append(newSeat);

            // 추가된 좌석도 드래그 속성 유지
            newSeat.draggable({
                stop: function(event, ui) {
                    var dragPosX = ui.position.left;
                    var dragPosY = ui.position.top;

                    $(this).attr("data-posx", dragPosX);
                    $(this).attr("data-posy", dragPosY);

                    removeSeat(this, dragPosX, dragPosY);

                    console.log("(Clone Drag) Seat Type: " + $(this).attr("data-seatType") + " Position X: " + dragPosX + " Position Y: " + dragPosY);
                }
            });

            // 벗어났는지 검증 로직
            removeSeat(newSeat, posX, posY);

            console.log("(Clone) Seat Type: " + originalId + " Position X: " + posX + " Position Y: " + posY);
        }
    });


    // 복제된 좌석 클릭 이벤트
    $(document).on('click', '.cloned-seat', function() {
        var seatType = $(this).attr('data-seatType');
        var tableCapacity = prompt("인원 수를 지정해주세요(숫자만 작성)");

        if (tableCapacity !== null || tableCapacity.trim() != '') {
            $(this).attr("data-capacity", tableCapacity);
            $(this).text(tableCapacity);

            console.log("Capacity: " + tableCapacity);
        }
    });
});


// 좌석 배치 저장 함수
function saveSeatArrangement() {
    var seats = [];
    let isValid = true;

    // 배열에 드래그한 좌석들 모두 넣기
    document.querySelectorAll('.cloned-seat').forEach(seat => {
        // 테이블 유효성 확인
        var tableCapacity = seat.getAttribute('data-capacity');

        console.log("tableCapacity: ", tableCapacity)

        if (tableCapacity === '0') { // getAttribute는 문자열로 받음
            alert("모든 좌석의 인원 수를 지정해주세요.");
            isValid = false;
            return false;
        }

        seats.push({
            seatType: seat.getAttribute('data-seatType'),
            posX: seat.getAttribute('data-posx'),
            posY: seat.getAttribute('data-posy'),
            tableCapacity: seat.getAttribute('data-capacity'),
        });
    });

    // 테이블 유효성 검사
    if (!isValid) {
        return;
    }

    console.log(seats);

    // AJAX 요청으로 서버에 배치된 좌석들의 정보를 전송
    $.ajax({
        url: '/seats/api/upload/' + storeId,
        type: 'POST',
        contentType: 'application/json', // 요청 데이터의 형식을 JSON으로 지정
        data: JSON.stringify(seats),     // 좌석 정보를 JSON 문자열로 변환하여 전송
        dataType: 'json',
        success: function(uploadResult) {
            alert('저장되었습니다.');
            window.location.href = '/stores/storeList';
        },
        error: function (status, error) {
            console.log("오류", status, error);
        }
    });
}


// 좌석 벗어나면 삭제 함수
function removeSeat(seat, posX, posY) {
    var container = $(".seat-container");
    var containerOffset = container.offset();
    var containerWidth = container.width();
    var containerHeight = container.height();


    // 드래그한 좌석이 seat-container 밖으로 벗어나면 해당 좌석 삭제
    if (posX < 0 || posY < 0 || posX > containerWidth || posY > containerHeight) {
        seat.remove();
    }
}


// 저장된 좌석 보여주기
$(document).ready(function() {
    $.ajax({
        url: '/seats/api/seatList/' + storeId,
        type: 'GET',
        dataType: 'json',
        success: function(seats) {
            console.log(JSON.stringify(seats))

            seats.forEach(seat => {
                var seatElement = document.createElement('div');
                // 저장시 포함되도록 clone 클래스로 설정
                seatElement.classList.add('cloned-seat');
                seatElement.classList.add('seat');

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

                seatElement.setAttribute('data-seatType', seat.seatType);
                seatElement.setAttribute('data-posx', seat.posX);
                seatElement.setAttribute('data-posy', seat.posY);
                seatElement.setAttribute('data-capacity', seat.tableCapacity);

                // 드래그 가능하도록 설정
                $(seatElement).draggable({
                    stop: function(event, ui) {
                        var dragPosX = ui.position.left;
                        var dragPosY = ui.position.top;

                        $(this).attr("data-posx", dragPosX);
                        $(this).attr("data-posy", dragPosY);

                        removeSeat(this, dragPosX, dragPosY);

                        console.log("Seat Type: " + $(this).attr("data-seatType") + " Position X: " + dragPosX + " Position Y: " + dragPosY);
                    }
                });

                // 좌석을 seat-container에 추가
                var container = document.querySelector('.seat-container');
                container.appendChild(seatElement);
            });
        },
        error: function (status, error) {
            console.log("오류", status, error);
        }
    });
});