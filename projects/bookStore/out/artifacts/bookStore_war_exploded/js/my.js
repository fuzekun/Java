var interval;

function startSecond() {

	interval = window.setInterval("changeSecond2()", 1000);//定时器

};

function changeSecond() {
	var second = document.getElementById("second");//得到现在网页second的内容

	var svalue = second.innerHTML;//相当于tmp,为了保存

	svalue = svalue - 1;

	if (svalue == 0) {
		window.clearInterval(interval);
		location.href = "index.jsp";
		return;
	}

	second.innerHTML = svalue;//让网页上的内容减少
}

function  changeSecond2() {
	var second = document.getElementById("second");

	second--;

	if(second == 0){
		window.clearInterval(interval);
		location.href = "index.jsp";
		return ;
	}

	second.innerHTML = second;
}


//获取XMLHttpRequest对象
function getXMLHttpRequest() {
	var xmlhttp;
	if (window.XMLHttpRequest) {// code for all new browsers
		xmlhttp = new XMLHttpRequest();
	} else if (window.ActiveXObject) {// code for IE5 and IE6
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}

	return xmlhttp;

}