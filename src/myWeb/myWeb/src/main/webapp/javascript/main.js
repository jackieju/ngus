function removeTest(){
	treeObj.childNode[0].removeChild(0);
}

function addTest(){
	var url = "addbookmark.jsp";
    var pars= null;
    if(selectedNode) 		
    	pars = "parentId="+selectedNode.element.id;
    else   
        pars = "parentId=";
	var myAjax = new Ajax.Request(
		url, 
		{
			method: 'post',
			parameters: pars, 
			onComplete: loadComplete.bind(this)
		});
	function loadComplete(originalRequest){
		//var tmp = originalRequest.responseText;
		Element.update("bk_display", originalRequest.responseText);
	}		
}
function addFolderTest(){
	var url = "addfolder.jsp";		
	var pars = "parentId="+selectedNode.element.id;
	var myAjax = new Ajax.Request(
		url, 
		{
			method: 'post',
			parameters: pars, 
			onComplete: loadComplete.bind(this)
		});
	function loadComplete(originalRequest){
		//var tmp = originalRequest.responseText;
		Element.update("bk_display", originalRequest.responseText);
	}		
}
function tag_mouseover(tmp){
	tmp.className = "selected";
}
function tag_mouseout(tmp){
	var tags = $F("tags").match(/[^ ]+/g);
	tags = (tags==null)? new Array() : tags;
	if(tags.indexOf(tmp.innerHTML)==-1){
		tmp.className = "unselected";
	}
}
function tag_click(tmp){
	var tags = $F("tags").match(/[^ ]+/g);
	tags = (tags==null)? new Array() : tags;
	if(tags.indexOf(tmp.innerHTML)==-1){
		tmpclassName = "selected";
		tags.push(tmp.innerHTML);
	}
	else{
		tmp.className = "unselected";
		tags = tags.without(tmp.innerHTML);
	}
	var test = tags.join(" ");
	$("tags").value = test==""? test : test+" ";
	focusTo($("tags"));
}
var range=0; //ie
function focusTo(obj, selectFrom) {
		
	if (typeof selectFrom == 'undefined') selectFrom = obj.value.length;
	if(obj.createTextRange){ //ie + opera		
		range = obj.createTextRange();
		range.moveEnd("character", obj.value.length);
		range.moveStart("character",selectFrom);
		//obj.select()
		//range.select()
		setTimeout('range.select()', 10)
	} else if (obj.setSelectionRange){ //ff
		obj.select()
		obj.setSelectionRange(selectFrom,obj.value.length)
	} else { //safari :(
		obj.blur()
	}		
	//range = 0;		
}
function addbookmark(){
	var url = "addBookMark.do";		
	//alert("add");
	var pars = Form.serialize("addbookmark");
	//alert(pars);
	//alert(pars);
	var myAjax = new Ajax.Request(
		url, 
		{
			method: 'post',
			parameters: pars,
			onLoading: loading.bind(this),
			onComplete: loadComplete.bind(this)
		});
	function loading(){
		Element.update("add", "<img src='img/loading.gif' width='13px'/> Loading....");
	}
	function loadComplete(originalRequest){
		//var tmp = originalRequest.responseText;
		Element.update("add", originalRequest.responseText);
	}		
}	
function addfolder(){
	var url = "addFolder.do";		
	var pars = Form.serialize("addfolder");
	//alert(pars);
	var myAjax = new Ajax.Request(
		url, 
		{
			method: 'post',
			parameters: pars,
			onLoading: loading.bind(this),
			onComplete: loadComplete.bind(this)
		});
	function loading(){
		Element.update("bk_display", "<img src='img/loading.gif' width='13px'/> Loading....");
	}
	function loadComplete(originalRequest){		
		Element.update("bk_display", "");
		selectedNode.loadChild();
	}		
}
function editbookmark(id){
	var url = "editbookmark.jsp";		
	var pars = "id="+id;
	var	flag = 0;
	var myAjax = new Ajax.Request(
		url, 
		{
			method: 'post',
			parameters: pars, 
			onLoading: loading.bind(this),
			onComplete: loadComplete.bind(this)
		});
	function loading(){
	if (flag == 0)
		Element.update("bk_display", "<img src='img/loading.gif' width='13px'/> Loading....");
	}
	function loadComplete(originalRequest){		
		Element.update("bk_display", originalRequest.responseText);
		flag = 1;
	}	
}
function edit(){
	var url = "editBookMark.do";		
	var pars = Form.serialize("editbookmark");	
	var myAjax = new Ajax.Request(
		url, 
		{
			method: 'post',
			parameters: pars, 
			onLoading: loading.bind(this),
			onComplete: loadComplete.bind(this)
		});
	function loading(){
		Element.update("bk_display", "<img src='img/loading.gif' width='13px'/> Loading....");
	}
	function loadComplete(originalRequest){		
		//Element.update("bk_display", "");
		//selectedNode.loadChild();
		Element.update("bk_display", originalRequest.responseText);
	}
}
function deletebookmark(id){
	var url = "delete.do";		
	var pars = "id="+id
	//alert(pars);
	var myAjax = new Ajax.Request(
		url, 
		{
			method: 'post',
			parameters: pars, 
			onLoading: loading.bind(this),
			onComplete: loadComplete.bind(this)
		});
	function loading(){
		Element.update("bk_display", "<img src='img/loading.gif' width='13px'/> Loading....");
	}
	function loadComplete(originalRequest){
		//Element.update("bk_display", "");
		//selectedNode.loadChild();
		Element.update("bk_display", originalRequest.responseText);
	}
}
function deleteFolder(id){
	if(id==null||id==""){
		return false;
	}
	var url = "delete.do";		
	var pars = "id="+escape(id);
	//alert(pars);
	var myAjax = new Ajax.Request(
		url, 
		{
			method: 'post',
			parameters: pars, 
			onLoading: loading.bind(this),
			onComplete: loadComplete.bind(this)
		});
	function loading(){
		treeObj.childNode[0].element.getElementsByTagName("UL")[0].remove(true);
		Element.update("bk_display", "<img src='img/loading.gif' width='13px'/> Loading....");
	}
	function loadComplete(originalRequest){
		//selectedNode.loadChild();		
		treeObj.childNode[0].loadChild();
		Element.update("bk_display", originalRequest.responseText);
	}
}