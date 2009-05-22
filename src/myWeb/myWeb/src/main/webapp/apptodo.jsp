<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ngus.myweb.util.widget.TodoUtils" %>	

<style type="text/css">
	.app-todo {
		overflow-y: auto;
		padding: 5px;
	}
	.app-todo-add {
        	height:40px;
		color: Red;
	}
	.app-todo-list {
		width: 100%;
	}
	.app-todo-list-item {
		width: 100%;
		line-height: 20px;
		padding: 2px 0px;
	}
	.app-todo-list-item-state {
		width: 25px;
		vertical-align: top;
	}
	.app-todo-list-item-content {
	}
	.app-todo-list-item-content-done {
	}
	.app-todo-list-item-content a.app-todo-list-item-content-done {
		color: #000000;
		text-decoration: line-through;
		overflow: scroll;
	}
	.app-todo-list-item-action {
		width: 25px;
		vertical-align: top;
	}
	.app-todo-list-item .app-todo-list-item-action a {
		color: #FF0000;
		font-weight: bold;
	}
</style>
<div class="app-todo">
	<div class="app-todo-list" id="appTodoList">
	</div>
    <div class="app-todo-add" id="appTodoAdd">
        <table>
        <tr>
            <td style="width:25px;">
            </td>
            <td>
    	        <input id="newItemContent" type="text" value="" style="width:100%;" title="待做事项的内容"/>
    	        <div id="newItemContentDiv" style="width:100%;display:none;"></div>
            </td>
            <td style="width:10px;">
            </td>
        </tr>
        <tr>
            <td>
            </td>
            <td>	
                <a href="#" id="appTodoAddAction">+ 添加新事项</a>
            </td>		    
            <td></td>
        </tr>
        </table>
    </div>
	<div class="app-todo-list" id="appTodoListDone"></div>
</div>
<script type="text/javascript">
	var $todo = $app.$todo = {};
	$todo.data = [];
	$todo.id = $app.id + "Todo";
	$todo.addId = $todo.id + "Add";
	$todo.addActionId = $todo.id + "AddAction";
	$todo.listId = $todo.id + "List";
	$todo.list = {};
    $todo.listDone= {};
	$todo.selectAll = function(){
		var data = [];
		for(var i = 0; i < $todo.list.children().length; i++){
			var item = $($todo.list.children()[i]);
			data[data.length] = {
				isDone : $(item.find("td")[0]).children()[0].checked,
				content : $(item.find("td")[1]).children()[0].innerHTML
			};
			if(!$webos.isDebug){
				data[data.length - 1].resourceId = item[0].resourceId;
			}
		}
		return data;
	};
	$todo.init = function(){
		$todo.list = $("#" + $todo.listId);
        $todo.listDone= $('#appTodoListDone');
		$todo.data = <%=TodoUtils.getTodoList((String)request.getSession().getAttribute("userid")) %>;
		//init list
		//alert("get :" + $todo.data.length);
		$todo.createList($todo.data);
		$("#" + $todo.addActionId).click(function(){
			$todo.insertItem();
		});
	};
	$todo.createList = function(listData){
		for(var i = 0; i < listData.length; i++){
            if(listData[i].isDone == false){
    			$todo.list.append($todo.createItem(listData[i]));
            }
            else{
                $todo.listDone.append($todo.createItem(listData[i]));
            }
		}
	};

	$todo.createItem = function(itemData){
		var item = $('<table class="app-todo-list-item"><tr><td class="app-todo-list-item-state"></td><td class="app-todo-list-item-content"></td><td class="app-todo-list-item-action"></td></tr></table>');
		item.isEditing = false;
		item.state = $('<input type="checkbox" title="完成/未完成"' + (itemData.isDone ? ' checked="checked"' : '') + ' />');
		item.content = $('<a href="#" title="编辑内容请点击">' + itemData.content + '</a>');
		item.edit = $('<input type="text" value="text" title="Press enter to save" style="display:none;" />');
		item.action = $('<a href="#" style="display:block;" title="删除">X</a>');
		$(item.find("td")[0]).append(item.state);
		$(item.find("td")[1]).append(item.content);
		$(item.find("td")[1]).append(item.edit);
		$(item.find("td")[2]).append(item.action);
	    item.data=itemData;
        item[0].resourceId = itemData.resourceId;

		$todo.formatItemContent(item);
		item.mouseover(function(){
			$todo.displayItemActions(item);
			//style
		});
		item.mouseout(function(){
//			$todo.hideItemActions(item);
            $todo.displayItemActions(item);
		});
		item.state.click(function(){
            if(item.data.isDone){
                item.data.isDone=false;
                $todo.list.append($todo.createItem(item.data));
            }    
            else {
                item.data.isDone=true;
                $todo.listDone.append($todo.createItem(item.data));
            }
                
			$todo.formatItemContent(item);
			$todo.updateItem(item);
            item.remove();
            
		});
		item.content.click(function(){
			$todo.editItem(item);
		});
		item.edit.keydown(function(e){
			var key = e.charCode || e.keyCode || 0;
			if(key == 13){
				$todo.cancelItemEditing(item);
				if(item.edit.val() == ""){
					$todo.removeItem(item);
				}
				else{
//                  alert(item.edit.attr('value'));
                    item.data.content=item.edit.attr('value');
					$todo.updateItem(item);
				}
			}
		});
		item.edit.blur(function(e){
			$todo.cancelItemEditing(item);
			if(item.edit.val() == ""){
				$todo.removeItem(item);
			}
			else{
//                alert(item.edit.attr('value'));
                item.data.content=item.edit.attr('value');
				$todo.updateItem(item);
			}
		});
		item.action.click(function(){
			$todo.removeItem(item);
		});
		//alert("create end");
		return item;
	};
	$todo.updateItem = function(item){
		var itemData = $todo.getDataByItem(item);
		var itemJson = JSON.stringify(itemData);
		$.post($app.urls.todoUpdateItem, itemData, function(response){
			if(!$webos.isDebug){
				item[0].resourceId = response;
			}
		});
	};
	$todo.removeItem = function(item){
		$.post($app.urls.todoDeleteItem, {resourceId : item[0].resourceId}, function(response){
			if(response == 1){
				item.remove();
			}
		});
	};
	$todo.insertItem = function(){
        var blankPattern= new RegExp('^\\s*$');
        if($('#newItemContent').attr('value') == null || blankPattern.test($('#newItemContent').attr('value')))
            {        $('#newItemContent').attr('value','');}
else{
        $('#newItemContent').css('display', 'none');
		var itemData = {
			isDone : false,
			content : $('#newItemContent').attr('value')
		};
		var itemJson = JSON.stringify(itemData);
		$.post($app.urls.todoInsertItem, itemData, function(response){
				itemData.resourceId = response;
			$todo.list.append($todo.createItem(itemData));
		});
        $('#newItemContent').attr('value','');
        $('#newItemContent').css('display', 'block');
}
	};
	$todo.getDataByItem = function(item){
		var itemData = {
			isDone : item.state[0].checked,
			content : item.content[0].innerHTML
		};
		if($webos.isDebug){
			itemData.itemId = item[0].itemId;
		}
		else{
			itemData.resourceId = item[0].resourceId;
		}
		return itemData;
	};
	$todo.formatItemContent = function(item){
		if(item.state[0].checked){
			item.content.addClass("app-todo-list-item-content-done");
		}
		else{
			item.content.removeClass("app-todo-list-item-content-done");
		}
	};
	$todo.cancelItemEditing = function(item){
		item.content.text(item.edit.val());
		item.edit.css({
			display : "none"
		});
		item.content.css({
			display : "block"
		});
		item.isEditing = false;
	};
	$todo.editItem = function(item){
		item.edit.val(item.content.text());
		item.edit.css({
			width : (item.find("td")[1].clientWidth - 15) + "px"
		});
		item.content.css({
			display : "none"
		});
		item.edit.css({
			display : "block"
		});
		item.edit[0].focus();
		item.isEditing = true;
	};
	$todo.displayItemActions = function(item){
		item.action.css({
			display : "block"
		});
	};
	$todo.hideItemActions = function(item){
		item.action.css({
			display : "none"
		});
	};
</script>
