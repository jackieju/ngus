<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/myWeb.tld" prefix="myWeb"%>
<html>
<head>
<title>Dojo Tree Widget Test (ajax, drag'n'drop and more)</title>

<script type="text/javascript">
	var djConfig = {isDebug: true, debugAtAllCosts: true };
</script>
<script type="text/javascript" src="javascript/dojo.js"></script>

<script type="text/javascript">
	dojo.require("dojo.lang.*");
	dojo.require("dojo.fx.html");	// so can use toggle="fade"
	dojo.require("dojo.widget.Tree");
	//dojo.require("dojo.widget.FloatingPane")
	//dojo.require("dojo.widget.ContentPane");
	//dojo.require("dojo.widget.Checkbox");
	//dojo.require("dojo.widget.TabContainer");
	//dojo.require("dojo.widget.Button");
	dojo.require("dojo.widget.TreeRPCController");
	dojo.require("dojo.widget.TreeSelector");
	dojo.require("dojo.widget.TreeNode");
	dojo.require("dojo.widget.TreeContextMenu");

	//dojo.require("dojo.widget.SplitContainer");
	//dojo.require("dojo.widget.ColorPalette");
	//dojo.require("dojo.widget.LayoutContainer");
	//dojo.require("dojo.widget.ResizeHandle");
	dojo.hostenv.writeIncludes();

	function restoreIconSrc() {
		// icon was changed during the action => no need to move it back
		//alert("Restore "+this.icon.src.substr(-18))
		if (this.icon.src.substr(-18) != 'static/loading.jpg') { // check if icon.src is loading icon
			return;
		}
		this.icon.src = this.oldIconSrc;
	}


	/* process up or down operation */
	function moveClicked(selectedNode, controllerId, icon, direction) {
		if (selectedNode.actionIsDisabled(selectedNode.actions.MOVE)) {
			return false;
		}

		this.icon = icon;
		this.oldIconSrc = icon.src;
		
		//this.controller = dojo.widget.manager.getWidgetById(controllerId);

		if (!selectedNode) {
			alert('No node selected');
			return false;
		}
		
		/*if (direction == 'up') {
			if (!selectedNode.getPreviousSibling()) return;
			var res = controller.move(selectedNode, selectedNode.parent, selectedNode.getParentIndex()-1);
		} else if (direction == 'down') {
			if (!selectedNode.getNextSibling()) return;
			var res = controller.move(selectedNode, selectedNode.parent, selectedNode.getParentIndex()+1);
		}*/


	}

	/* process create operation */
	function createFolderClicked(selectedNode, controllerId, icon) {		
		if (!selectedNode || selectedNode.actionIsDisabled(selectedNode.actions.ADDCHILD)) {
			return false;
		}
		alert(selectedNode.widgetId+" CreateFolder");
		this.icon = icon;
		this.oldIconSrc = icon.src;

		//this.controller = dojo.widget.manager.getWidgetById(controllerId);
		
		if (!selectedNode || !selectedNode.isFolder) {
			alert('Select folder please');
			return false;
		}
		window.location.href="addFolder.jsp?parentID="+selectedNode.widgetId;
		
		//this.icon.src = 'static/loading.jpg';

		// I send some data to server and recieve feedback with right node
		//var res = controller.createChild(selectedNode, 0, { suggestedTitle: "New node" }, dojo.lang.hitch(this, restoreIconSrc));

		// local checks failed
		//if (res == false) {
		//	restoreIconSrc.apply(this);
		//}
	}

	function createBookMarkClicked(selectedNode, controllerId, icon) {
		if (!selectedNode || selectedNode.actionIsDisabled(selectedNode.actions.ADDCHILD)) {
			return false;
		}
		alert(selectedNode.widgetId+" CreateBookMark");
		this.icon = icon;
		this.oldIconSrc = icon.src;
		
		window.location.href="addBookMark.jsp?parentID="+selectedNode.widgetId;
		//this.controller = dojo.widget.manager.getWidgetById(controllerId);
		
		if (!selectedNode || !selectedNode.isFolder) {
			alert('Select folder please');
			return false;
		}

		//this.icon.src = 'static/loading.jpg';

		// I send some data to server and recieve feedback with right node
		//var res = controller.createChild(selectedNode, 0, { suggestedTitle: "New node" }, dojo.lang.hitch(this, restoreIconSrc));

		// local checks failed
		//if (res == false) {
		//	restoreIconSrc.apply(this);
		//}
	}
	
	function removeClicked(selectedNode, controllerId, icon) {

		if (!selectedNode) {
			alert('No node selected');
			return false;
		}
		alert(selectedNode.widgetId+" Remove");
		if (selectedNode.actionIsDisabled(selectedNode.actions.REMOVE)) {
			return false;
		}

		this.icon = icon;
		this.oldIconSrc = icon.src;
		window.location.href="DeleteServlet?id="+selectedNode.widgetId;
		//this.controller = dojo.widget.manager.getWidgetById(controllerId);


		//this.icon.src = 'static/loading.jpg';

		/*var res = controller.removeNode(selectedNode, dojo.lang.hitch(this, restoreIconSrc));

		// local checks failed
		if (res == false) {
			restoreIconSrc.apply(this);
		}*/


	}
	function EditClicked(selectedNode, controllerId, icon) {

		if (!selectedNode) {
			alert('No node selected');
			return false;
		}
		alert(selectedNode.widgetId+" Edit");
		if (selectedNode.actionIsDisabled(selectedNode.actions.REMOVE)) {
			return false;
		}

		this.icon = icon;
		this.oldIconSrc = icon.src;
		if(selectedNode.isFolder==true){
			window.location.href="editFolder.jsp?id="+selectedNode.widgetId;
		}
		else{
			window.location.href="editBookMark.jsp?id="+selectedNode.widgetId;
		}
		//this.controller = dojo.widget.manager.getWidgetById(controllerId);


		//this.icon.src = 'static/loading.jpg';

		/*var res = controller.removeNode(selectedNode, dojo.lang.hitch(this, restoreIconSrc));

		// local checks failed
		if (res == false) {
			restoreIconSrc.apply(this);
		}*/


	}
	var reporter = function(reporter) {
		this.name = eventName;
		this.go = function(message) {
			var rep = [ reporter + " -- event: "+this.name ];
			for(i in message) rep.push(i+": "+message[i]);
			dojo.debug(rep.join(', '));
		}
	}

	dojo.addOnLoad(function(){


		/* Add debug print for all controller events */
		/*var controller = dojo.widget.manager.getWidgetById('treeController');
		for(eventName in controller.eventNames) {
			dojo.event.topic.subscribe(
				controller.eventNames[eventName],
				new reporter('controller'),
				'go'
			);
		}*/

		/* Add debug print for all firstTree events */
		/*var firstTree = dojo.widget.manager.getWidgetById('firstTree');


		for(eventName in firstTree.eventNames) {
			dojo.event.topic.subscribe(
				firstTree.eventNames[eventName],
				new reporter('firstTree'),
				'go'
			);
		}*/

		/* Add debug print for all secondTree events */
		/*var secondTree = dojo.widget.manager.getWidgetById('ListTree');
		for(eventName in secondTree.eventNames) {
			dojo.event.topic.subscribe(
				secondTree.eventNames[eventName],
				new reporter('ListTree'),
				'go'
			);
		}*/

		//dojo.widget.manager.getWidgetById('1.1').edit({title: '123'});


	});


</script>

</head>
<body>


<!--  <div dojoType="TreeRPCController" RPCUrl="local" widgetId="treeController" DNDController="create"></div> -->

<div dojoType="TreeSelector" widgetId="treeSelector"></div>


<div dojoType="TreeContextMenu" toggle="explode" contextMenuForWindow="false" widgetId="treeContextMenu">
	<div dojoType="TreeMenuItem" treeActions="addChild" iconSrc="static/createsmall.gif" widgetId="treeContextMenuCreateFolder" caption="Create Folder"></div>
	<div dojoType="TreeMenuItem" treeActions="addChild" iconSrc="static/createsmall.gif" widgetId="treeContextMenuCreateBookMark" caption="Create BookMark"></div>
	<div dojoType="TreeMenuItem" treeActions="edit" iconSrc="static/edit.gif" widgetId="treeContextMenuEdit" caption="Edit"></div>
	<div dojoType="TreeMenuItem" treeActions="remove" iconSrc="static/removesmall.gif" caption="Remove" widgetId="treeContextMenuRemove"></div>
	
	<!--  <div dojoType="TreeMenuItem" treeActions="move" iconSrc="static/downsmall.png" caption="Up" widgetId="treeContextMenuUp"></div>
	<div dojoType="TreeMenuItem" treeActions="move" iconSrc="static/upsmall.png" caption="Down" widgetId="treeContextMenuDown"></div>-->
</div>
<script>

/* setup menu actrions */
dojo.addOnLoad(function() {

	dojo.event.topic.subscribe('treeContextMenuCreateFolder/engage',
		function (menuItem) { createFolderClicked( menuItem.getTreeNode(), 'treeController',  menuItem.getTreeNode().expandIcon); }
	);

	dojo.event.topic.subscribe('treeContextMenuCreateBookMark/engage',
		function (menuItem) { createBookMarkClicked( menuItem.getTreeNode(), 'treeController',  menuItem.getTreeNode().expandIcon); }
	);
	
	dojo.event.topic.subscribe('treeContextMenuEdit/engage',
		function (menuItem) { EditClicked( menuItem.getTreeNode(), 'treeController', menuItem.getTreeNode().expandIcon);}
	);
	dojo.event.topic.subscribe('treeContextMenuRemove/engage',
		function (menuItem) { removeClicked( menuItem.getTreeNode(), 'treeController',  menuItem.getTreeNode().expandIcon); }
	);

/*	dojo.event.topic.subscribe('treeContextMenuUp/engage',
		function (menuItem) { moveClicked( menuItem.getTreeNode(), 'treeController',  menuItem.getTreeNode().expandIcon, 'up'); }
	);

	dojo.event.topic.subscribe('treeContextMenuDown/engage',
		function (menuItem) { moveClicked( menuItem.getTreeNode(), 'treeController',  menuItem.getTreeNode().expandIcon, 'down'); }
	);*/


});


</script>



<style>
#toolsDiv img {
	vertical-align: middle;
}
.treeTable tr {
	vertical-align: top;
}
</style>
<!--
	A sample toolbar
-->

<hr/>



<!--
	Every node must have widgetId to get recognized by server (ajax)
	!!! wipe toggle from widget.Tree is buggy in FF (try open a lot of nodes)
-->
<table class="treeTable" cellpadding="10">
<tr>
<td style="border:1px dashed black;">
<h4>ListTree</h4>

<div dojoType="Tree" menu="treeContextMenu" DNDMode="between" selector="treeSelector" widgetId="ListTree" DNDAcceptTypes="ListTree">
<myWeb:displayAll />
</div>
</td>
</tr>
</table>



</body>
</html>
