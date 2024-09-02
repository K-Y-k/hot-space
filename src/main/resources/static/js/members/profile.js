// 선택한 파일의 이미지를 가져와서 보여주기
function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();
        reader.readAsDataURL(input.files[0]);

        reader.onload = function (e) {
            var tempImage=new Image();
        	tempImage.src=reader.result;

        	console.log(tempImage);

        	tempImage.onload=function(){
        		var canvas=document.createElement('canvas');
        		var canvasContext=canvas.getContext("2d");

            	var img = new Image();
	        	img.src = e.target.result;

        		canvas.width=img.width*0.5;
        		canvas.height=img.height*0.5;

        		canvasContext.drawImage(this,0,0,canvas.width,canvas.height);

        		var dataURI=canvas.toDataURL("image/png");

        		document.querySelector("#thumbnail").src=dataURI;
        	}
        };
    }
}