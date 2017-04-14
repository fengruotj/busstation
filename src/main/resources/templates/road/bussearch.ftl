
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<#include "../public/head.ftl">
<html>
  <head>
    <title>公交车管理系统</title>
    <script type="text/javascript">
    $(function(){
		var map = new BMap.Map("container");
		addControlMap(map,"武汉",12);
		
		var opts = {
				width : 300,     // 信息窗口宽度
				height: 100,     // 信息窗口高度
				title : "站台信息" , // 信息窗口标题
				enableMessage:true//设置允许信息窗发送短息
			   };
			   
			$("input[id=startaddress]").validatebox({
				required : true,
				missingMessage:"请输入一个起始地址"
			});  
			
			$("input[id=endaddress]").validatebox({
				required : true,
				missingMessage:"请输入一个结束地址"
			});  
			
			$("#search").click(function(){
				remove_overlay(map);
				deletePoint(map);
				var startaddress=$("#startaddress").val();
				var endaddress=$("#endaddress").val();
				ajax("roadline_bussearch.action",{startaddress:startaddress,endaddress:endaddress},"post",onsuccess);
			});
			
			function onsuccess(data){
			 	alert(data.result);
				     	switch(data.type){
					     	case 1:
					     		var point = new BMap.Point(114.372772,30.651134);
					     		getRoadLineListMap(map,data);		//将放的可行的直达路径显示到地图上
					     		addlabelonMap(map,point,data.label,"red"); //将label标签显示到地图上
					     		break;
					     	case 2:
					     		getRoadLineListMap(map,data);		//将放的可行的直达路径显示到地图上
					     		var point = new BMap.Point(114.372772,30.651134);
					     		addlabelonMap(map,point,data.label,"red"); //将label标签显示到地图上
					     		
					     		for(var a=0;a<data.changestation.length;a++){
					     			var point = new BMap.Point(data.changestation[a].longitude,data.changestation[a].latitude);
					     			addlabelonMap(map,point,"换乘点","blue"); //将label标签显示到地图上
					     		}
					     		break;
				     	}
			};
			
		});
    </script>
  </head>
<body>
	输入起始地址: <input type="text" id="startaddress">
	输入结束地址: <input type="text" id="endaddress">    
	 <a id="search"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">搜索</a>  
 	<div id="container"
			style="position: absolute;  width: 100%; height: 90%; overflow:hidden;">
	</div>
</body>
</html>
