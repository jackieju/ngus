var treeObj;
var selectedNode;
function mouseDown(ev){
	return false;
}
function jsTree(ele){	
	this.element = ele;	
	//this.element.rel = this;
	this.childNode = new Array();
	this.initTree();
}
jsTree.prototype = {
	toggle : function(){
		Element.toggle(this.element);
	},
	addChild : function(){
		
	},
	removeChild : function(node){
		if(typeof(node)=="number"){
			node = this.childNode[node];
		}
		node.deletialize();	
		this.childNode = this.childNode.without(node);
		return this.childNode.length;
	},
	initTree : function()
	{			
		if(this.element.nodeType == 1/*Node.ELEMENT_NODE no use in ie*/){						
			var lists = this.element.childNodes;				
			for(var i = 0; i<lists.length; i++){
				if(lists[i].tagName=="LI"){					
					this.childNode.push(new treeNode(lists[i]));
				}
			}
		}
	},
	deletialize : function(){
		//this.element.rel = null;
		Element.remove(this.element);
	},
	length : function(){
		return this.childNode.length;
	}
}


function treeNode(ele){
	this.element = ele;
	/*four bit mask
		bit 1.. isFolder 	or 	bookmark
		bit 2.. open 		or	close
		bit 3.. isLast 		or 	not_last
		bit 4.. mouseover	or	mouseout
	*/
	//this.element.rel = this;
	this.parentNode=null;
	this.property = 0x0;
	this.childLoaded = 0; //0 not loaded, 1 loading, 2 loaded
	this.initTree();
	//this.subNode = null;
}
treeNode.prototype = {
	isFolder_mask : 0x8
	,
	open_mask : 0x4
	,
	isLast_mask : 0x2
	,
	mouseover_mask : 0x1
	,
	setProperty : function(mask){
		this.property = this.property ^ mask;		
	}
	,
	isFolder : function(){
		return this.property & this.isFolder_mask == this.isFolder_mask;
	}
	,
	isOpen : function(){
		return this.property & this.open_mask == this.open_mask;
	}
	,	
	toggle : function(ev){
		ev=ev||window.event;
		/*	We clicked the <SPAN> element not the <LI> 
			this statement can prevent toogling when we click the child of this element
		*/
		if(this.childLoaded == 1){
			return;
		}
		if((ev.srcElement || ev.target).parentNode != this.element){
			return false;
		}
		if((this.property & this.isFolder_mask) != this.isFolder_mask){
			return false;
		}
		if(this.childLoaded == 0){
			//alert(this.childLoaded);
			this.loadChild();
			return;
		}
		this.setProperty(this.open_mask);
		if(this.subNode){
			this.subNode.toggle(this.isOpen());
		}
		this.render();
	},
	
	/**
		url pars!!!!!!
	*/
	loadChild : function(){
		this.childLoaded = 1;
		//alert("loadChild: " + this.childLoaded);
		var url = "loadfolder.jsp";
		var pars = "id="+this.element.id;
		var myAjax = new Ajax.Request(
			url, 
			{
				method: 'post',
				parameters: pars, 
				onLoading: loading.bind(this),
				onComplete: loadComplete.bind(this),
				onFailure: error.bind(this)
			});
		function error(){
			this.childLoaded = 0;
		}
		function loading(originalRequest){
			new Insertion.Bottom(this.element, "<ul id='tmp'><li><img src='img/loading.gif' width='13px'/> Loading....</li></ul>");
		}
		function loadComplete(originalRequest){		
			Element.remove("tmp");
			var tmp = originalRequest.responseText;			
			new Insertion.Bottom(this.element, tmp);
			if(this.subNode){
				this.subNode.deletialize();
			}
			if(this.element.getElementsByTagName("UL")[0])
				this.subNode = new jsTree(this.element.getElementsByTagName("UL")[0]);
			this.childLoaded = 2;
			this.setProperty(this.open_mask);
			this.render();
		}
	},
	render : function(){		
		this.element.className = "style"+this.property;
	},
	mouseover : function(ev){
		ev=ev||window.event;
		/*We clicked the <SPAN> element not the <LI> */
		if((ev.srcElement || ev.target).parentNode != this.element){
			return false;
		}
		this.setProperty(this.mouseover_mask);
		this.render();		
	},
	mouseout : function(ev){
		ev=ev||window.event;
		/*We clicked the <SPAN> element not the <LI> */
		if((ev.srcElement || ev.target).parentNode != this.element){
			return false;
		}
		this.setProperty(this.mouseover_mask);
		this.render();
	},
	addChild : function(node){
		if(!this.subNode){
			this.subNode = new jsTree(document.createElement("UL"));
			this.element.appendChild(this.subNode.element);
		}
		this.subNode.addChild(node);
	},
	removeChild : function(node){
		if(this.subNode.removeChild(node)==0){
			this.subNode.deletialize();
			delete this.subNode;
			//this.subNode = null;
		}
	},
	select_node : function(ev, load){
		ev=ev||window.event;
		var tmp = ev.srcElement || ev.target;
		if(selectedNode!=undefined){
			selectedNode.unselect_node();
		}
		tmp.className = "selected";		
		selectedNode = this;
		if(ev.button!=2)
			this.loadBookMark();
	},
	loadBookMark : function(id){
		var url = "loadbookmark.jsp";		
		var pars = "id="+(id||this.element.id);		
		var myAjax = new Ajax.Request(
			url, 
			{
				method: 'post',
				parameters: pars, 
				onLoading: loading.bind(this),
				onComplete: loadComplete.bind(this)
			});
		function loading(originalRequest){
			Element.update("bk_display", "<img src='img/loading.gif' width='13px'/> Loading....");
		}
		function loadComplete(originalRequest){
			//var tmp = originalRequest.responseText;
			//alert(originalRequest.responseText);
			Element.update("bk_display", originalRequest.responseText);
		}
	}
	,
	unselect_node : function(){
		this.element.getElementsByTagName("SPAN")[1].className = "test";
	},
	initTree : function(){
		var last = true;
		var tmp = this.element;
		while(tmp.nextSibling!=null){
			if(tmp.nextSibling.tagName=="LI"){
				last = false;
				break;
			}
			tmp = tmp.nextSibling;
			
		}
		if(last)
			this.setProperty(this.isLast_mask);
			
		/*var ul = this.element.getElementsByTagName("UL");
		if(ul.length!=0){
			this.setProperty(this.isFolder_mask);
			if(ul[0].style.display!="none"){
				this.setProperty(this.open_mask);
			}
		}*/
		this.setProperty(this.isFolder_mask);
		if(this.element.getAttribute("open")=="true"){
			this.childLoaded = true;
			this.setProperty(this.open_mask);
		}
		this.render();
		var children = this.element.childNodes;
		for(var j=0; j<children.length; j++){											
			if(children[j].tagName=="UL"){
				this.subNode = new jsTree(children[j]);
			}
			else if(children[j].tagName=="A"){
				///children[j].className="content";				
				var outer = document.createElement("SPAN");
				outer.className = "content";
				var p = children[j].parentNode;
				outer.appendChild(children[j].cloneNode(true));
				p.replaceChild(outer, children[j]);
				var inner_content = children[j].getElementsByTagName("SPAN")[0];
				inner_content.onmousedown = this.select_node.bindAsEventListener(this);
			}
		}
		this.element.onmouseover = this.mouseover.bindAsEventListener(this);
		this.element.onmouseout = this.mouseout.bindAsEventListener(this);
		this.element.onclick = this.toggle.bindAsEventListener(this);		
	},
	deletialize : function(){
		//this.element.rel = null;
		Element.remove(this.element);
	}
}