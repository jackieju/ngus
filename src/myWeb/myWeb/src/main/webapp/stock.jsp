<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.ngus.myweb.util.widget.StockUtils" %>	
<%@ include file="checkSession.jsp" %>
<style>
#appStock{
}
#appStockCodeList{
border:thin ridge Blue;
width:543px;
height:300px;
overflow:scroll;
background-color:#FFFFFF;
}
#appStock table{
color:#3366FF;
font-family:Arial,Helvetica,sans-serif;
font-size:12px;
width:521px;
height:15px;
}
table .state{
text-align:center;
width:60px;
}
table .code{
text-align:center;
width:60px;
overflow:hidden;
}
table .opening-price{
text-align:right;
width:37px
}
table .live-price{
text-align:right;
width:37px;
}
table .low-price{
text-align:right;
width:37px;
}
table .high-price{
text-align:right;
width:37px;
}
table .fluctuation-price{
text-align:right;
width:37px;
}
table .fluctuation-rate{
text-align:right;
width:37px;
}
table .delete-code{
width:15px;
color:Red;
}

a:hover{
    color:red;
}
a:active{
    color:red;
}

</style>
<script type="text/javascript" src="webos/javascript/framework.js"></script>

<div id="appStock">
    <div id="appStockCodeList">
        <table style="height:15px;color:#000000;">
            <tr>
                <td style="width:70px;text-align:right;">添加股票：</td>
                <td style="width:15px;">
                    <select style="" id="market" name="market">
                        <option value="sh" selected>沪市</option>
                        <option value="sz">深市</option>
                    </select>
                </td>
                <td style="width:120px;">
                    股票代号:<input type="text" id="newStockCode" style="border:0px;width:60px;" />
                </td>
                <td>
                    <a style="color:red;" href="#" id="addStock">添加</a>
                </td>
                <td>
                    <a style="color:red;" href="#" onclick="javascript:window.location.reload();">手动刷新</a>
                </td>
            </tr>
        </table>         
        <table id="appStockCodeListTitle">
            <tr>
                <td class="state"></td>
                <td class="code">股票代号</td>
                <td class="opening-price">开盘价</td>
                <td class="live-price">最新价</td>
                <td class="low-price">最低价</td>
                <td class="high-price">最高价</td>
                <td class="fluctuation-price">涨跌额</td>
                <td class="fluctuation-rate">涨跌幅</td>
                <td class="delete-code"></td>
            </tr>
        </table>
<!--        
        <table title="031002">            
            <tr>
                <td class="state">占位</td>
                <td class="code">sz031002</td>
                <td class="opening-price">1</td>
                <td class="live-price">2</td>
                <td class="low-price">4</td>
                <td class="high-price">5</td>
                <td class="fluctuation-price">6</td>
                <td class="fluctuation-rate">7</td>
                <td class="delete-code"><a href="#" onclick="alert('delete the code!');">del</a></td>
            </tr>
        </table>
-->
    </div>
</div>
<div id="liveTrend"  style="display:none;width:543px;">
<div style="text-align:center;" >
<table style="color:#3366FF;font-family:Arial,Helvetica,sans-serif;font-size:12px;width:521px;height:15px;">
    <tr>
        <td style="width:80px;text-align:right;color:red;"><a id="liveTrendDisplay" title="点击关闭图片">点击关闭图片</a></td>
        <td style="text-align:right">[<a id="livePic">实时图</a>]</td>
        <td style="text-align:center;">K 线图</td>
        <td>[<a id="dayKPic">日线</a>]</td>
        <td>[<a id="weekKPic">周线</a>]</td>
        <td>[<a id="monthKPic">月线</a>]</td>
    </tr>
</table>
<input type="hidden" id="stockCodeForImage" value=""/>
</div>
<img id="liveTrendImage" src="" width="543" height="285"/>
</div>

<script type="text/javascript">
//初步定义如下:
//{"code":"sz031002","isExist":true/false, "resId":"text://ngus..."}
//isChoose继承了原来的数据单元，这里没有用，code是股票的代码，带有所在交易所的缩写，resId是服务器上返回的资源号，用于删除该只股票
//createItem当中首先利用股票代码去获取股票信息，然后将信息插入dom
//createItem()方法用于利用去回来的值生成新的股票信息。
$(document).ready(function(){
    var $stock={};
    //全局变量，保存所有的股票和对应的resId
    $stock.data= <%=StockUtils.getStockList((String)request.getSession().getAttribute("userid"))%>;

    //初始化函数
    $stock.init=function(){
        for(var i=0; i< $stock.data.length; i++){
            $stock.CreateItem($stock.data[i]);
        }
    }

    //用户添加新股票
    $('#addStock').click(function(){
//检查输入,这里检查是否为空
        var blankPattern= new RegExp('^\\s*$');

        if($('#newStockCode').attr('value') != null && !blankPattern.test($('#newStockCode').attr('value'))){
            var stockCode=$("select[@id=market] option[@selected]").attr('value')+$('#newStockCode').attr('value');
            $('#newStockCode').attr('value','');
            var stockData={isChoose: true,
                           code: stockCode,
                           resId:""};
            //添加股票到用户的资源树中，返回id
            $.post("/updatestock", stockData, function(response){
                //成功添加资源之后
                if(response != 0){
                    stockData.resId= response;
                    $stock.CreateItem(stockData);
                }
            });
        }
    });


//itemData包括{"code":"sz031002","name":"钢钒GFC1","isExist":true/false,}
    $stock.CreateItem= function(itemData){
        var tempURL= 'Stock?code='+ itemData.code;
        //Ajax获取股票信息，成功之后创建DOM
        $.get(tempURL,{},function(response){
            var xml= $($(response)[1]);
            var item;
            var flagPattern= new RegExp('http://www.webxml.com.cn$');
            var imgURL= 'http://www.webxml.com.cn/WebServices/ChinaStockWebService.asmx/getStockImageByCode?theStockCode=' + itemData.code;
            
            if(itemData.isExist){
                item=$('<table><tr><td class="state"></td><td class="code"></td><td class="opening-price"></td><td class="live-price"></td><td class="low-price"></td><td class="high-price"></td><td class="fluctuation-price"></td><td class="fluctuation-rate"></td><td class="delete-code"></td></tr></table>');
            }
            else{
                item=$('<table title="目前实时数据还不支持"><tr><td class="state"></td><td class="code"></td><td class="opening-price">-</td><td class="live-price">-</td><td class="low-price">-</td><td class="high-price">-</td><td class="fluctuation-price">-</td><td class="fluctuation-rate">-</td><td class="delete-code"></td></tr></table>');
            }
            
            item.openingPrice=$('<a>0</a>');

            item.delAction=$('<a style="color:red;">del</a>');
            item.liveTrendImage=$('<a style="color:red;">实时走势</a>');
            item.stockCode=$('<a>'+itemData.code+ '</a>');

            //
            $(item.find('td')[0]).append(item.liveTrendImage);

            //添加股票实时走势的查看功能
            $(item.find('td')[1]).append(item.stockCode);
            
            //添加股票的删除功能
            $(item.find('td')[8]).append(item.delAction);

            //将股票添加到dom中
            $("#appStockCodeListTitle").after(item);

            //绑定动作
            item.liveTrendImage.click(function(){
                $("#stockCodeForImage").attr('value',itemData.code);
                $("#liveTrendImage").attr('src', imgURL);
                $("#liveTrend").css("display","block");
                $("#appStock").css("display","none");
            });

            item.delAction.click(function(){
                $.post("/removestock", {resId: itemData.resId}, function(response){
                    if(response == 1){
                        item.remove();                    
                    }
                });
            });
            

            item.update= function(){
                $.get("stock?code=sz031002",{},function(response){
                    var xml= $($(response)[1]);
    //                alert($(xml.find('string')[1]).text());
    //                alert(response);
                })
            }
//            item.update();
//            window.setInterval(function(){item.update();}, 100000);
        });
    }

// 传入股票的代码
// 首先向服务器提交添加新资源的请求，通过的话传回
$("#liveTrendDisplay").click(function(){
    $("#liveTrendImage").attr('src', '');
    $("#liveTrend").css("display","none");
    $("#appStock").css("display","block");
});

$("#dayKPic").click(function(){
    var url='http://www.webxml.com.cn/WebServices/ChinaStockWebService.asmx/getStockImage_kByCode?theStockCode=' + $("#stockCodeForImage").attr('value')+ '&theType=D&random='+Math.random();
    $("#liveTrendImage").attr('src', url);
});

$("#weekKPic").click(function(){
    var url='http://www.webxml.com.cn/WebServices/ChinaStockWebService.asmx/getStockImage_kByCode?theStockCode=' + $("#stockCodeForImage").attr('value')+ '&theType=W&random='+Math.random();
    $("#liveTrendImage").attr('src', url);
});

$("#monthKPic").click(function(){
    var url='http://www.webxml.com.cn/WebServices/ChinaStockWebService.asmx/getStockImage_kByCode?theStockCode=' + $("#stockCodeForImage").attr('value')+ '&theType=M&random='+Math.random();
    $("#liveTrendImage").attr('src', url);
});

$('#livePic').click(function(){
    var url='http://www.webxml.com.cn/WebServices/ChinaStockWebService.asmx/getStockImageByCode?theStockCode=' + $("#stockCodeForImage").attr('value')+ '&random='+Math.random();
    $("#liveTrendImage").attr('src', url);
});

//初始化应用
$stock.init();

});
</script>