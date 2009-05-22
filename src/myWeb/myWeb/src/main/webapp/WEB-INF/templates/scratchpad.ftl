<#if ret_type!="innerHtml">
<#include "header.ftl">
</#if>

		<link href="css/style.css" rel="stylesheet" type="text/css" media="all" />
		<script type="text/javascript" src="javascript/prototype.js"></script>
				
		<script type="text/javascript">

function updateScratchPad(){
//    document.getElementById('scratchPad').style.height= document.body.scrollHeight - 100;
    var node = document.getElementById("scratchPad");
    var resid = document.getElementById("res_id").value; 
    var xml = '<?xml version="1.0" encoding="utf-8"?><ro res_id="'+ resid+'" shareLevel="0" title="scratchpad" tags="" type="1" storageType="1" storagePath="/tree/scratchpad" resourceType="doc">'+ node.value +'<list name="rdo" ><rdo modelName="scratchPad" test="1"/></list></ro>';
    var hash = {'req':'updateRes','resobj':xml};  
    var myAjax = new Ajax.Request(
                        "/ModelServlet",
                        {
                                method: 'post',
                                parameters: hash, 
                                onLoading: loading.bind(this),
                                onComplete: loadComplete.bind(this),
                                onFailure: error.bind(this)
                        });
}
		function error(){
			alert("ajax error");
		}
		function loading(originalRequest){
		//alert("loading");
		}
		function loadComplete(originalRequest){
		//alert('loadComplete');		
	//alert(originalRequest.responseXML);	
		}
        function framewidth(){
            //alert(document.getElementById('scratchPadContainer').style["height"]);
            alert(window.parent.document.getElementById('webosWidgetScratchPadCenter').clientHeight);
           
        }
		</script>
<style>
body{
    margin:0;
}
</style>
<!-- body of one piece of results||starts||-->
<#if res_list?size == 0><#assign v="">
<#assign id="">
<#else>
<#assign v= res_list[0].getValue()>
<#assign id=res_list[0].getResId()>
</#if>
    <br style="line-height:0;">
    <div id="scratchPadContainer" class="result_display" style="padding-left:10px;padding-right:10px;height:100%;">
<!--        <div style="height:22px;background-image:url('/webos/img/app_scratchpad_title.jpg');"></div>
-->		<div class="1pic_display" style="width:100%;height:100%;">
            <textArea id="scratchPad" cols="50" style="width:100%;height:100%;background-color:#FFFFCC;" onchange="updateScratchPad();">${v}</textArea>
            <input type="hidden" id="res_id" value="${id}"/>
    	</div>
<!--		<div class="clear"></div>
		<div class="patchbox">hello</div>
-->	</div>
    
<!-- body of one piece of results||ends||-->
<#if ret_type!="innerHtml">
<#include "footer.ftl">
</#if>
