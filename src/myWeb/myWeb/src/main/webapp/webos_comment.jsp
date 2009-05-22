<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ns.util.DateTime" %>	
<% 
	String userName= "Guest";
	if(request.getSession().getAttribute("username") != null)
		userName= (String)request.getSession().getAttribute("username");
%>	
<html>
	<head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>意见收集</title>
		<style type="text/css">
*{
color:#999999;
font-family:Verdana,Arial,Helvetica,sans-serif;
font-size:12px;
}
body{
text-align:center;
}
a:link, a:visited{
    color:#00648C;
    text-decoration:none;
}
a:hover, a:active{
color:#1D93B6;
text-decoration:none;
}
.title{
clear:none;
width:100%;
height:70px;
}
.app-comment{
text-align:left;
}
.app-comment-list-item{
border:1px solid #C0C0C0;
}
.app-comment-list-item-name{
color:blue;
width:75px;
}
.app-comment-list-item-date{
color:#000000;
width:100px;
}
.app-comment-list-item-content{
width:625px;
overflow:scroll;
}
.pagelink{
background:#F0F5FA none repeat scroll 0 0;
border:1px solid #072A66;
padding:1px 3px;
}
.currentpage{
background:#FFC9A5 none repeat scroll 0 0;
border:1px solid #072A66;
padding:1px 3px;
}
		</style>
        <script type="text/javascript" src="webos/javascript/framework.js"></script>
        <script type="text/javascript">
            $(document).ready(function(){ 
				var $comment={};
				var allComments;
				var number=0;
                $comment.pageSize= 20;
                $comment.pageNumber= 1;
        		$comment.init= function(){
					$.post("/weboscomment",
							{req: 'gettotlanumber'},
							function(response){
								number=response;
                                $comment.pageNumber= Math.ceil(number / $comment.pageSize);                                
					            //alert($comment.pageNumber);
                                $comment.pageListCreate($comment.pageNumber, $comment.pageNumber);
            					$.post("/weboscomment",
            							{req: 'getcommets',page:$comment.pageNumber,pageSize:$comment.pageSize},
            							function(response){
                                            allComments=eval(response);
                                            $comment.createList(allComments);
            							});
							});
				}
                $comment.getCommentByPage=function(currentPageNumber){
					$.post("/weboscomment",
							{req: 'getcommets',page:currentPageNumber,pageSize:$comment.pageSize},
							function(response){
                                allComments=eval(response);
                                $comment.createList(allComments);
							});
                };
                $comment.pageClick= function(page){
                    $comment.pageListCreate(page, $comment.pageNumber);
                    $comment.getCommentByPage(page);
                };
                $comment.pageListCreate= function(currentPage, total){
                    $('#commentPage').html('');
                    var tmp;
                    var tempPage;
                    if(currentPage > 3){
                        var firstPage=$('<span class="pagelink" title="第一页"><a href="#">>></a></span>');
                        firstPage.click(function(){
                            $comment.pageClick(1);
                        });
                        $('#commentPage').prepend(firstPage);
                    }
                    if(currentPage > 1){
                        var lastPage=$('<span class="pagelink" title="前一页"><a href="#">></a></span>');
                        lastPage.click(function(){
                            $comment.pageClick(currentPage-1);
                        });
                        $('#commentPage').prepend(lastPage);
                    }
                    if(currentPage-2 > 0){
                        tmp= currentPage-2;
                        tempPage=$('<span class="pagelink"><a href="#">'+tmp+'</a></span>');
                        tempPage.click(function(){
                            $comment.pageClick(currentPage-2);
                        });
                        $('#commentPage').prepend(tempPage);
                    }
                    if(currentPage-1 > 0){
                        tmp= currentPage-1;
                        tempPage=$('<span class="pagelink"><a href="#">'+tmp+'</a></span>');
                        tempPage.click(function(){
                            $comment.pageClick(currentPage-1);
                        });
                        $('#commentPage').prepend(tempPage);
                    }
                    tempPage=$('<span class="currentpage"><a href="#">'+currentPage+'</a></span>');
                    $('#commentPage').prepend(tempPage);
                    
                    if(currentPage+1 <= total){
                        tmp= currentPage+ 1;
                        tempPage=$('<span class="pagelink"><a href="#">'+tmp+'</a></span>');
                        tempPage.click(function(){
                            $comment.pageClick(currentPage+1);
                        });
                        $('#commentPage').prepend(tempPage);
                    }
                    if(currentPage+2 <= total){
                        tmp= currentPage+ 2;
                        tempPage=$('<span class="pagelink"><a href="#">'+tmp+'</a></span>');
                        tempPage.click(function(){
                            $comment.pageClick(currentPage+2);
                        });
                        $('#commentPage').prepend(tempPage);
                    }
                    if(currentPage+1 <= total){
                        var lastPage=$('<span class="pagelink" title="后一页"><a href="#"><</a></span>');
                        lastPage.click(function(){
                            $comment.pageClick(currentPage+1);
                        });
                        $('#commentPage').prepend(lastPage);
                    }
                    if(currentPage+3 <= total){
                        var lastPage=$('<span class="pagelink" title="最后一页"><a href="#"><<</a></span>');
                        lastPage.click(function(){
                            $comment.pageClick($comment.pageNumber);
                        });
                        $('#commentPage').prepend(lastPage);
                    }
                    var totalPageTitle=$('<span class="pagelink" title="最后一页"><a href="#">共'+total+'页</a></span>');
                    $('#commentPage').prepend(totalPageTitle);
                }
                $comment.createList= function(comments){
                    $('#commentList').html('');
                    for(var i=0; i<comments.length; i++){
	                    $comment.createItem(comments[i]);
	                }
                }
        		$comment.createItem= function(comment){
					var item = $('<table class="app-comment-list-item"><tr><td class="app-comment-list-item-name">'+comment.name+'</td><td class="app-comment-list-item-date">'+comment.date+'</td><td class="app-comment-list-item-content"><div style="overflow:auto;width:625px;color:#000080;">'+decodeURI(comment.comment)+'</div></td></tr></table>');
                	$('#commentList').prepend(item);
                	//$("#commentListHead").after(item);                
                }
                
				$("#appCommentAddAction").click(function(){
					var userName=$("#userName").attr("value");
                    var content= $("#appCommentAddContent").attr("value");
                    content= $comment.modifyInput(content);
                    if(content == null || content.length == 0){
                        alert('你的意见为空');
                    }else{
                        if(confirm("确定提交")){
                            $.post("/weboscomment",
                                    {req: 'submit',
                                     username: userName,
                                     comment: content
                                    },
                                    function(response){
                                        if(response == 1){
                                            var tmp={name: userName,
                                                     comment: content,
                                                     date: '<%=DateTime.currentTimestamp()%>'
                                                     };
                                            $comment.createItem(tmp);
                                            $("#appCommentAddContent").attr("value", "");
                                        }
                                    }); 
                        }
                    }
				});
                $comment.modifyInput= function(input){
                    if(input == null || input.length== 0)
                        return null;
                    var patern= /^\s+$/;
                    if(patern.exec(input))
                        return null;
		  input= input.replace(/&/g, '&amp;');  
                  input= input.replace(/>/g, '&gt;');
                    input= input.replace(/</g, '&lt;');
//                    input= input.replace(/&/g, '&amp;');
//                   input= input.replace(/</g, '&lt;');
                      input= encodeURI(input);
//                    alert(input);
                    return input; 
                }
                $('#appCommentResetAction').click(function(){
                    $("#appCommentAddContent").attr("value", "");
                    alert($('#commentList').html());
                });

				$comment.init();
            });
		</script>	
	</head>
	
	<body>
        <div style="width:800px;margin-left:auto;margin-right:auto;">
        <div class="title">
            <div style="height:70px;width:228px;float:left;"><img src="img/sign_logo.jpg" style="height:70px;width:228px;"/></div>
            <div style="height:35px;width:572px;margin-top:35px;float:left;background:url('img/webos_comment_background.jpg') repeat;font-size:30px;">意见收集</div>
        </div>
        <br />
		<div class="app-comment">
			<div class="app-todo-add" id="addComment">
				<div><textarea id="appCommentAddContent" rows="4"  cols="75" style="width:800px;"></textarea></div>
				<div><input id="userName" type="hidden" value="<%=userName%>"/></div>
				<div style="text-align:right;">
                    <a href="#" id="appCommentResetAction">重置</a>&nbsp;&nbsp;
                    <a href="#" id="appCommentAddAction">提交意见</a>
                </div>
			</div>
            <br />
            <div id="commentPage" style="width:800px;height:20px;">
<!--                <span class="currentpage">1</span>
                <span class="pagelink">2</span>

                <span class="pagelink">3</span>
-->            </div>
            <table class="app-comment-list-item">
                <tr>
                    <td class="app-comment-list-item-name">用户姓名</td>
                    <td class="app-comment-list-item-date">发表时间</td>
                    <td class="app-comment-list-item-content"></td>
                </tr>
            </table>
			<div id="commentList">
				<div id="commentListHead"></div>
			</div>
            <div>
                <input type="hidden" value="1" id="currentPage" />
            </div>
		</div>
        </div>
	</body>
</html>
