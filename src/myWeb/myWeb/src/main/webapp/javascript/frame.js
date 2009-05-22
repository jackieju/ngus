document.onmousemove = mouseMove;
document.onmousedown = mouseDown;
document.onmouseup   = mouseUp;
var dragable = false;
var originalOffset;
var originalTreeWidth;
var originalBkWidth;
function mouseDown(ev){
	ev = ev||window.event;
	var element = Event.element(ev);
	if(element.id== "split_bar"){
		dragable = true;
		element.style.backgroundColor = "#ECE9D8";
		originalOffset = Position.cumulativeOffset(element);
		//alert($("tree").paddingLeft);
		originalTreeWidth = $("tree").offsetWidth - 2;
		originalBkWidth = $("bk_display").offsetWidth - 2;
	}
}
function mouseMove(ev){
	ev = ev || window.event;
	var element = Event.element(ev);
	if(dragable){
		var offsetX = Event.pointerX(ev) - originalOffset[0];
		var offsetY = Event.pointerY(ev) - originalOffset[1];
		if(originalTreeWidth+offsetX > 600 || originalTreeWidth+offsetX<150){
			return;
		}
		$("tree").style.width = originalTreeWidth+offsetX+"px";
		$("bk_display").style.width = originalBkWidth-offsetX+"px";
	}
}
function mouseUp(ev){
	dragable = false;
	$("split_bar").style.backgroundColor = "transparent";
}