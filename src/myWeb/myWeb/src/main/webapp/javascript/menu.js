document.oncontextmenu = function(ev){
	ev=ev||window.event;
	var element = Event.element(ev);		
	if(element.className == "test" || element.className == "selected" || element.className=="unselected"){
		var menu = $("contextMenu");
		menu.setAttribute("folderid", Event.findElement(ev, "LI").id);
		menu.style.display = "";
		menu.style.left = Event.pointerX(ev)+"px";
		menu.style.top = Event.pointerY(ev)+"px";
		if($("contextMenu").getAttribute("folderid")==""){
			$("deleteNode").style.display="none";
		}
		else{
			$("deleteNode").style.display="";
		}
		return false;
	}
}