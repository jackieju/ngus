<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ngus.myweb.util.widget.StockUtils" %>	
<%@ include file="checkSession.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>test</title>
        <script type="text/javascript" src="webos/javascript/framework.js"></script>
        <script type="text/javascript" src="webos/javascript/framework.json.js"></script>
        <style>
            body{
                font-family:Arial,Verdana,宋体,Helvetica,sans-serif;
                font-size:12px;
            }
            td{
                padding:0;
            }
            a:link a:visited{
                color:#00648C;
                text-decoration:none;
            }
        	.stock-list{
			    width: 250px;
			}
			.stock-editor{
			    display: none;
			}
			.stock-list-item{
			    width: 100%;
			    line-height: 20px;
				padding: 2px 0px;
			}
			.stock-list-item-state{
			    width: 50px;
				vertical-align: top;
			}
			.stock-list-item-code{
			}
			.stock-list-item-action{
				width: 50px;
				vertical-align: top;
			}
			.stock-panel{
			}
       	</style>
        
        <script type="text/javascript">
            $(document).ready(function(){ 
         // Your code goes here
                var $stockList= {};
                $stockList.autoList={};
                data= <%=StockUtils.getStockList((String)request.getSession().getAttribute("userid"))%>;
                $stockList.init = function(dataList){
                    var tableHead= $('<table class="stock-list-item"><tr><td class="stock-list-item-state"></td><td class="stock-list-item-code">股票代码</td><td class="stock-list-item-action"></td></tr></table>');
                    var tableBody= $stockList.createList(dataList);
                    var tableEnd = $('<table class="stock-list-item"><tr><td class="stock-list-item-state"></td><td class="stock-list-item-code"></td><td class="stock-list-item-action"></td></tr></table>');
                    var submitTable=$('<table class="stock-list-item"><tr><td class="stock-list-item-state"></td><td class="stock-list-item-code"></td><td class="stock-list-item-action"></td></tr></table>');
                    tableEnd.newCode =$('<input type="text" value="" />');
                    tableEnd.addStockButton= $('<a href="# style="color:blue;">添加 </a>');
                    submitTable.submit= $('<br style="line-height:0;">');
                    $(tableEnd.find("td")[1]).append(tableEnd.newCode);    
                    $(tableEnd.find("td")[2]).append(tableEnd.addStockButton);
                    $(submitTable.find("td")[2]).append(submitTable.submit);

                    tableEnd.addStockButton.click(function(){
                        //var newCode={"isChoose":false,"code":"000000","resId":""};

                        //
                        //
                        data[data.length] = {
                                                isChoose: true,
                                                code: tableEnd.newCode.val(),
                                                resId: ""
                                            };
                        
                        //alert(newCode[data.length-1].resId);
                        //alert(newCode.isChoose);
                        //alert($stockList.updateItem(data[data.length-1]));
                        //alert(tableEnd.newCode.val());

                        $stockList.updateItem(data[data.length-1])
                    });

                    $("#stockListHeadDiv").append(tableHead);
                    $("#stockListBodyDiv").append(tableBody);
                    $("#stockListEndDiv").append(tableEnd);
                    $("#submitTableDiv").append(submitTable);
                    //$stockList.autoList= $("#autoList");
                    $("#appStockListViewAction").click(function(){
                        $("#stockListEditor").css({
                            display : "none"
                        });
                        $("#stockPanel").css({
                            display : "block"
                        });
                        var res=$stockList.getAllCodes(data);
                        //var iframe =$('<iframe scrolling="no" frameborder="0" style="height: 100%; width: 100%;" src="http://gmodules.com/ig/ifr?url=http://quotesandlines.googlepages.com/cnn-news-customized-rss-feeds.xml&up_entries=4&up_summaries=100&up_subject=CNN.com&up_feedname1=Top&up_feed1=http%3A%2F%2Frss.cnn.com%2Frss%2Fcnn_topstories.rss&up_feedname2=World&up_feed2=http%3A%2F%2Frss.cnn.com%2Frss%2Fcnn_world.rss&up_feedname3=Business&up_feed3=http%3A%2F%2Frss.cnn.com%2Frss%2Fmoney_topstories.rss&up_feedname4=Sports&up_feed4=http%3A%2F%2Frss.cnn.com%2Frss%2Fsi_topstories.rss&up_feedname5=Markets&up_feed5=http%3A%2F%2Frss.cnn.com%2Frss%2Fmoney_markets.rss&up_feedname6=Entertainment&up_feed6=http%3A%2F%2Frss.cnn.com%2Frss%2Fcnn_showbiz.rss&up_feedname7=Technology&up_feed7=http%3A%2F%2Frss.cnn.com%2Frss%2Fcnn_tech.rss&up_selectedTab=&synd=open&w=320&h=270&title=__UP_subject__&border=%23ffffff%7C3px%2C1px+solid+%23999999" vspace="0" marginwidth="0" marginheight="0" hspace="0"></iframe>');
                        $("#stockTable")[0].src="http://gmodules.com/ig/ifr?url=http://ligangjiang.googlepages.com/ChinaStock.xml&up_StockList="+res+"&up_DisplayPrice=1&up_DisplayChange=1&up_DisplayPercentage=1&up_DisplayVolume=1&up_DisplayAmount=0&up_RefreshInterval=15&synd=open&lang=ALL&country=ALL";

                    });
                    $("#appStockPanelEditAction").click(function(){
                        $("#stockPanel").css({
                            display : "none"
                        });
                        $("#stockListEditor").css({
                            display : "block"
                        });
                    });
                }
                $stockList.createList= function(listData){
                    var tableBody= $('<div id="autoList"></div>');
                    
                    for(var i=0; i< listData.length; i++){
                        tableBody.append($stockList.createItem(listData[i]));
                    }
                    return tableBody;
                    //$("body").append(tableEnd);
                }
                //delete a item from auto list
                $stockList.removeItem= function(item){
                    $stockList.autoList= $("#autoList");
                    //alert($stockList.autoList.children().length);
                    for(var i = 0; i < $stockList.autoList.children().length; i++){
		              if($stockList.autoList.children()[i] == item[0]){
				        $($stockList.autoList.children()[i]).remove();
				        break;
			         }
		           }
                }
                $stockList.updateItem= function(itemData){
                    $.post("/updatestock", {isChoose: itemData.isChoose,
                                          code: itemData.code,
                                          resId: itemData.resId
                                          }, 
                                          function(response){
                                                //alert(response);
                                                if(response != 0){
                                                    itemData.resId= response;
                                                    $("#autoList").append($stockList.createItem(itemData));                      
                                                }
                            });
                }
                $stockList.createItem= function(itemData){
                    var item= $('<table class="stock-list-item"><tr><td class="stock-list-item-state"></td><td class="stock-list-item-code"></td><td class="stock-list-item-action"></td></tr></table>');
                    item.state= $('<input type="checkbox" title="choose or not"' + (itemData.isChoose ? ' checked="checked"' : '') + ' />');
                    item.content= $('<a href="#" title="">' + itemData.code + '</a>');
                    item.remove= $('<a href="#" style="color:red;">删除<a/>');

                    $(item.find("td")[0]).append(item.state);
                    $(item.find("td")[1]).append(item.content);
                    $(item.find("td")[2]).append(item.remove);
            
                    item.state.click(function(){
                        item.state[0].checked=false;
                        //$stockList.getAllCodes();
                        // update 
                    });

                    //
                    item.remove.click(function(){
                        //remove resource
                        //$stockList.removeItem(item);ɾdomڵ϶Ӧstock
                        $.post("/removestock",{resId: itemData.resId}, function(response){
                            if(response == 1){
                                $stockList.removeItem(item);
                                for(var i=0; i< data.length; i++){
                                    if(data[i].resId == itemData.resId){
                                        data.splice(i, 1);
                                        break;
                                    }
                                }
                            }
                        });
                        
                        //alert("delete");
                        //$.post()
                    });
                    return item;
                }
                $stockList.getAllCodes= function(data){
                     var list= '';
                     for(var i = 0; i< data.length-1; i++){			         
                        list+=data[i].code+ "%7C";
                    }
                    var i= data.length -1;
                    if(i >= 0){
                        list+=data[i].code;
                    }
                    return list;
                }                
                
                $stockList.init(data);
                var res=$stockList.getAllCodes(data);
                //var iframe =$('<iframe scrolling="no" frameborder="0" style="height: 100%; width: 100%;" src="http://gmodules.com/ig/ifr?url=http://quotesandlines.googlepages.com/cnn-news-customized-rss-feeds.xml&up_entries=4&up_summaries=100&up_subject=CNN.com&up_feedname1=Top&up_feed1=http%3A%2F%2Frss.cnn.com%2Frss%2Fcnn_topstories.rss&up_feedname2=World&up_feed2=http%3A%2F%2Frss.cnn.com%2Frss%2Fcnn_world.rss&up_feedname3=Business&up_feed3=http%3A%2F%2Frss.cnn.com%2Frss%2Fmoney_topstories.rss&up_feedname4=Sports&up_feed4=http%3A%2F%2Frss.cnn.com%2Frss%2Fsi_topstories.rss&up_feedname5=Markets&up_feed5=http%3A%2F%2Frss.cnn.com%2Frss%2Fmoney_markets.rss&up_feedname6=Entertainment&up_feed6=http%3A%2F%2Frss.cnn.com%2Frss%2Fcnn_showbiz.rss&up_feedname7=Technology&up_feed7=http%3A%2F%2Frss.cnn.com%2Frss%2Fcnn_tech.rss&up_selectedTab=&synd=open&w=320&h=270&title=__UP_subject__&border=%23ffffff%7C3px%2C1px+solid+%23999999" vspace="0" marginwidth="0" marginheight="0" hspace="0"></iframe>');
                $("#stockTable")[0].src="http://gmodules.com/ig/ifr?url=http://ligangjiang.googlepages.com/ChinaStock.xml&up_StockList="+res+"&up_DisplayPrice=1&up_DisplayChange=1&up_DisplayPercentage=1&up_DisplayVolume=1&up_DisplayAmount=0&up_RefreshInterval=15&synd=open&lang=ALL&country=ALL";
            });
        </script>
    </head>

    <body>
        <center>
        <div id="stockListEditor" class="stock-editor">
            <div id="stockListHeadDiv" class="stock-list">
            </div>
            <div id="stockListBodyDiv" class="stock-list">
            </div>
            <div id="stockListEndDiv" class="stock-list">
            </div>
            <div id="submitTableDiv" class="stock-list">
            </div>
	       	<a href="#" id="appStockListViewAction">查看股票行情</a>
        </div>
        <div id="stockPanel" class="stock-panel" >
            <div id="stockPanelContent" class="stock-panel-content">
                <iframe id="stockTable" frameborder="0" width="100%" onload=""></iframe>
            </div>
            <div style="width:100%;text-align:center;">
                <a href="#" id="appStockPanelEditAction">编辑自选股票</a>
            </div>
        </div>
        </center>
    </body>
</html>