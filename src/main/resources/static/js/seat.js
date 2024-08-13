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

            // 새로운 ID와 포지션 속성 설정
            newSeat.attr("data-seatType", originalId);
            newSeat.attr("data-posx", posX);
            newSeat.attr("data-posy", posY);

            // seat-container에 추가
            $(".seat-container").append(newSeat);

            // 추가된 좌석도 draggable 속성 유지
            newSeat.draggable({
                stop: function(event, ui) {
                    var dragPosX = ui.position.left;
                    var dragPosY = ui.position.top;

                    $(this).attr("data-posx", dragPosX);
                    $(this).attr("data-posy", dragPosY);

                    removeSeat(this, posX, posY);

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

        if (tableCapacity !== null) {
            $(this).attr("data-capacity", tableCapacity);
            $(this).text(tableCapacity);

            console.log("Seat Type: " + seatType + " | Capacity: " + tableCapacity);
        }
    });
});


// 좌석 배치 저장
function saveSeatArrangement() {
    // 배열에 드래그한 좌석들 모두 넣기
    const seats = [];
    document.querySelectorAll('.cloned-seat').forEach(seat => {
        seats.push({
            seatType: seat.getAttribute('data-seatType'),
            posX: seat.getAttribute('data-posx'),
            posY: seat.getAttribute('data-posy'),
            tableCapacity: seat.getAttribute('data-capacity'),
        });
    });

    console.log(seats);

    // AJAX 요청으로 서버에 배치된 좌석들의 정보를 전송
    $.ajax({
        url: '/seats/api/upload',
        type: 'POST',
        contentType: 'application/json', // 요청 데이터의 형식을 JSON으로 지정
        data: JSON.stringify(seats),     // 좌석 정보를 JSON 문자열로 변환하여 전송
        dataType: 'json',
        success: function(uploadResult) {
            console.log('Seat arrangement saved:', uploadResult);
        },
        error: function(xhr, status, error) {
            console.error('Error saving seat arrangement:', error);
        }
    });
}


// 좌석 벗어나면 삭제
function removeSeat(seat, posX, posY) {
    var container = $(".seat-container");
    var containerOffset = container.offset();
    var containerWidth = container.width();
    var containerHeight = container.height();

    // 좌석이 seat-container 밖으로 벗어났는지 확인
    if (posX < 0 || posY < 0 || posX > containerWidth || posY > containerHeight) {
        // 벗어나면 해당 좌석 삭제
        seat.remove();
    }
}