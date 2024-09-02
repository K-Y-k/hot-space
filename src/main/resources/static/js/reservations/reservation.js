// 참석인원 조정 함수
function adjustQuantity(amount) {
    var input = document.getElementById('guests');
    var currentValue = parseInt(input.value, 10);
    var newValue = currentValue + amount;

    if (newValue < 1) newValue = 1; // 최소 인원 수를 1로 설정하여 음수 방지

    input.value = newValue;
}