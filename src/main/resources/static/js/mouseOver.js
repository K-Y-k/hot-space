// 네비 마우스 가리킬시 이벤트
document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('mouseover', function() {
        this.classList.remove('text-white');
        this.classList.add('hovered');
    });

    link.addEventListener('mouseout', function() {
        this.classList.remove('hovered');
        this.classList.add('text-white');
    });
});