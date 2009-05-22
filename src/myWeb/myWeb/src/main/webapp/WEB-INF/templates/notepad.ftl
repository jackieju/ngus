<#if ret_type!="innerHtml">
<#include "header.ftl">
</#if>
		<link href="css/notepad.css" rel="stylesheet" type="text/css" media="all" />
		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
		<script type="text/javascript" src="javascript/prototype.js"></script>
				
		<script type="text/javascript">
		


// get tags

var myAjax = new Ajax.Request(
                        "/ModelServlet",
                        {
                                method: 'post',
                                parameters: "req=listTags", 
                                onLoading: loading.bind(this),
                                onComplete: loadComplete.bind(this),
                                onFailure: error.bind(this)
                        });	
                       				
		function error(){
			//alert("error");
		}
		function loading(originalRequest){
		//alert("loading");
		}
		function loadComplete(originalRequest){
		//alert('loadComplete');		
		//alert(originalRequest.responseXML);
		//alert(originalRequest.responseText);
		var tagsNode = document.getElementById('allTags');
		var xmlDoc = originalRequest.responseXML;
		var vs=xmlDoc.getElementsByTagName('tag');
		//alert(vs.length);
		for ( i =0; i< vs.length; i++)
		{
		var nnode = document.createElement('div');
		nnode.setAttribute('class', "button_down");
		nnode.setAttribute('onclick', "hit_button(this)");
		var ntnode=document.createTextNode(vs[i].getAttribute('name'));
		nnode.appendChild(ntnode);
		
		//document.createTextNode('<div  class="button_down" onclick="hit_button(this)" >'+ vs[i].getAttribute('name') +'</div>'); 
		tagsNode.insertBefore(nnode, tagsNode.lastChild);
		}
		
		}
		
			/* ------------------
			function to handle user operation
			---------------------*/
			
	function hit(resId) { 
	var d = document.getElementById("content."+resId);
		if (d.style.visibility == "visible")
			{ d.style.visibility = "hidden"; }
		else{ d.style.visibility = "visible"; }
		
		hit2(d); 
	} 
	function hit2(t) { 
	if (t.style.display != "none") 
		t.style.display = "none"; 
	else{ 
		all = t.childNodes;
		for( i=0;i<all.length;i++) 
		{ 
		if (all[i].tt==1) 
			if (all[i].style.display!="none") 
				all[i].style.display="none"; 
		} 
		t.style.display=""; 
		} 
	} 
	function dbhit(resId){	
	var d = document.getElementById('content.'+resId);	
	if (d.style.height != "500px")
	{	
		d.style.height="500px";
		document.getElementById("textArea."+resId).style.height="490px";
	}
	else{
	
		d.style.height="100px";
		document.getElementById("textArea."+resId).style.height="90px";
	}
	}
	
	function hit_button(b)
	{
	
	
	if (b.className=="button_down")
		b.className='button_up';
	else{
	
		b.className='button_down';
	}

	
	}
			
			
			
			
			
			
			
			/* ---------------------------------
			function for data operation
			-----------------------------------*/
/* update existing note */
	function post(resId){
var ta_id = 'textArea.'+resId;
var ta_node =document.getElementById(ta_id);
var tag_node = document.getElementById('tag.'+resId);

//alert(ta_node.value);
var xml = '<?xml version="1.0" encoding="utf-8"?><ro res_id="'+resId+'" shareLevel="0" title="todo" tags="test" type="1" storageType="1" storagePath="/tree/notepad" resourceType="doc">'+ ta_node.value + '<list name="rdo" ><rdo modelName="notepad" test="1" /></list></ro>';	
var p = encodeURI("req=updateRes&resobj="+xml);
var myAjax = new Ajax.Request(
                        "/ModelServlet",
                        {
                                method: 'post',
                                parameters: p, 
                                onLoading: loading.bind(this),
                                onComplete: loadComplete1.bind(this),
                                onFailure:function err(){alert("ajax error");} 
                        });

}
						
                function loading1(originalRequest){
                //alert("loading1");
		
                }
                function loadComplete1(originalRequest){
                //alert('loadComplete');
        //alert(originalRequest.responseXML);
alert("Update Succeeded");

}

function del(resId){

var p = encodeURI("req=delRes&resId="+resId);
var myAjax = new Ajax.Request(
                        "/ModelServlet",
                        {
asynchronous: false,
                                method: 'post',
                                parameters: p,
                                onLoading: loading.bind(this),
                                onComplete: function lc(p){
				}.bind(this), 
                                onFailure:function err(){alert("ajax error");}
                        });
alert("delete succeeded");
var line = document.getElementById(resId);
line.parentNode.removeChild(line);



}
function appendNewLine(){
var ta_node =document.getElementById('textArea.newline');
//alert(ta_node.value);
var xml = '<?xml version="1.0" encoding="utf-8"?><ro shareLevel="0" title="todo" tags="test" type="1" storageType="1" storagePath="/tree/notepad" resourceType="doc">'+ ta_node.value + '<list name="rdo" ><rdo modelName="notepad" test="1" /></list></ro>';
var p = encodeURI("req=updateRes&resobj="+xml);
var myAjax = new Ajax.Request(
                        "/ModelServlet",
                        {
                                method: 'post',
                                parameters: p,
                                onLoading: function loading(){},
                                onComplete: lc_append.bind(this),
                                onFailure:function err(){alert("Ajax error");}
                        });
}
function lc_append(originalRequest){
var xmlDoc = originalRequest.responseXML;
var new_id = xmlDoc.getElementsByTagName("ro")[0].getAttribute("res_id");
var time = xmlDoc.getElementsByTagName("ro")[0].getAttribute("createTime");
var v = xmlDoc.getElementsByTagName("ro")[0].firstChild.nodeValue;
var div = document.getElementById('clone');
var clone = div.cloneNode(true);
div.id=new_id;
div.style.display="block";
var title = document.getElementById('title.clone');
title.id = 'title.'+new_id;
title.onDbClick="dbhit('"+new_id+"')";
title.setAttribute('onClick',"hit('"+new_id+"')");
if (v.length <= 30)
	v = v+ "...";
else
	v= v.substring(0,30)+"...";
title.appendChild(document.createTextNode(time+' '+v));
var ta = document.getElementById('textArea.clone');
ta.id = 'textArea.'+new_id;
ta.setAttribute('onDblClick',"dbhit('"+new_id+"')");
ta.setAttribute('onclick', "hit('"+new_id+"')");
ta.value=document.getElementById('textArea.newline').value;
var content = document.getElementById('content.clone');
content.id = 'content.'+new_id;
var update = document.getElementById('update.clone');
update.id ='update.'+new_id;
update.setAttribute('onclick', "post('"+new_id+"')");
var deletea = document.getElementById('delete.clone');
deletea.id ='delete.'+new_id;
deletea.setAttribute('onclick', "del('"+new_id+"')");



var newline =  document.getElementById("newline");
var parent = newline.parentNode;
var insertion = div.parentNode.removeChild(div);
parent.insertBefore(insertion, newline);
parent.appendChild(clone);
alert("publish new note OK");

}


		</script><!-- body of one piece of results||starts||-->
					
					<div class="result_display">
	<div id="allTags" class="tags" style="display:inline;{font-family:宋体;font-size:8pt;width:100%;display:margin:2px;padding:1px;inline;border-width:1px;border-style:dotted;}" >
	TAGS:
	
		<div  class="button_down" onclick="hit_button(this)" >
	All	
		</div>
	
		
	</div>
			
		<#list res_list as res>		
	<div class="oneline" id="${res.getResId()}" >
		<div class="title" style="{cursor:hand;font-family:宋体;font-size:10pt;background:#9999cc;margin:0px;padding:1;width:100%;height:22px;visibility:visible;display:block;}" onDblClick="dbhit('${res.getResId()}')" onclick="hit('${res.getResId()}')">	
${res.getCreateTime()}
<#assign x = res.getValue()>
<#if (x?length >= 30)>
		
			${res.getValue()?substring(0,30)+'...'}
<#else>
			${res.getValue()}
</#if>
		<!--<img src="todo5.gif" height="15px" alt="already done at 2008-02-02"/> -->
		
		</div>
		
		<div id="${"content."+res.getResId()}" class="content" style="{border-style:dotted;border-width:1px;visibility:hidden;display:none;}">
		<textArea id="${"textArea."+res.getResId()}" style="{font-family:宋体;font-size:10pt;}" class="textArea" >
			${res.getValue()}
		</textArea>
		<div style="{font-family:宋体;font-size:10pt;background:#ffcccc;width:100%;height:20px;}">
		tag: <input id="tag.${res.getResId()}" value='<#list res.getTags() as tag>
		${tag}
		</#list>' />
		<input type="submit" value="update" onclick="post('${res.getResId()}')" style="{width:49px;font-size:9pt;}"/>
		<input type="submit" value="delete" onclick="del('${res.getResId()}')" style="{width:49px;font-size:9pt;}"/>

		</div>			
		</div>
<div style="{height:10px}" ></div>		
	</div>
</#list>
				
 <div class="oneline" id="newline" >
                <div class="title" id="title.newline" style="{cursor:hand;font-family:宋体;font-size:10pt;background:#9999cc;margin:0px;padding:1;width:100%;height:22px;visibility:visible;display:block;}" onDblClick="dbhit('newline')" onclick="hit('newline')">
                (空)
                <!--<img src="todo5.gif" height="15px" alt="already done at 2008-02-02"/> -->

                </div>

                <div id="content.newline" class="content" style="{border-style:dotted;border-width:1px;visibility:hidden;display:none;}">
                <textArea id="textArea.newline" style="{font-family:宋体;font-size:10pt;}" class="textArea" >
                        
                </textArea>
                <div  style="{font-family:宋体;font-size:10pt;background:#ffcccc;width:100%;height:20px;}">
                tag:<input id="tag.newline" value=''/> 
                <input type="submit" value="添加" onclick="appendNewLine()" style="{width:49px;font-size:9pt;}"/>
              

                </div>
                </div>

</div>
						<div class="clear"></div>
						<div class="patchbox"></div>
					</div>
					
					<!-- body of one piece of results||ends||-->

<!-- for clone -->
<div class="oneline" id="clone" style="{display:none;}">
                <div class="title" id="title.clone" style="{cursor:hand;font-family:宋体;font-size:10pt;background:#9999cc;margin:0px;padding:1;width:100%;height:22px;visibility:visible;display:block;}" onDblClick="dbhit('dummy')" onclick="hit('dummy')">

                <!--<img src="todo5.gif" height="15px" alt="already done at 2008-02-02"/> -->

                </div>

                <div id="content.clone" class="content" style="{border-style:dotted;border-width:1px;visibility:hidden;display:none;}">
                <textArea id="textArea.clone" style="{font-family:宋体;font-size:10pt;}" class="textArea" >
                </textArea>
                <div  style="{font-family:宋体;font-size:10pt;background:#ffcccc;width:100%;height:20px;}">
                tag:<input id="tag.clone" value=''/>

                <input type="submit" value="update" id="update.clone" onclick="post('dummy')" style="{width:49px;font-size:9pt;}"/>
                <input type="submit" value="delete" id="delete.clone" onclick="del('dummy')" style="{width:49px;font-size:9pt;}"/>

                </div>
                </div>
<div style="{height:10px}" ></div>
        </div>
													
										
<#if ret_type!="innerHtml">
<#include "footer.ftl">
</#if>

				

