
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<#include "../public/head.ftl">
<html>
  <head>
    <title>公交车管理系统</title>
    
    <script type="text/javascript">
	$(function(){
		var map = new BMap.Map("container");
		addControlMap(map,"武汉",12);

		//添加道路搜索功能
		var local=addLocalserarch(map);
		var polyline;
		//得到道路线路通过线路id
		
		polyline=getRoadLineListByIdMap(map,${RequestParameters.id});

					$("#addstation").click(function(){
						removelistener();
						showInfoMessage("现在你可以添加站点");
					    map.addEventListener("click",showInfo);
					});
					
					function showInfo(e){
					var url='<iframe frameborder="0" width="100%" height="100%" src="send_station_savestation.action?id=${RequestParameters.id}&longitude='+e.point.lng+'&latitude='+e.point.lat+'"/>';
						$("#win").window({    
								    width:450,    
								    height:500, 
								    title:'添加站点信息',   
								    content:url
								});  
					}
					
					$("#endstation").click(function(){
						$.messager.show({
								title : '消息提示',
								msg : '结束添加站点',
								timeout : 2000,
								showType : 'slide'
							});
					   map.removeEventListener("click", showInfo);
					});
					
					$("#localbtn").click(function(){
						var search=$("#localsearch").val();
						local.search(search);
					});
					
					var m1=true;
					$("#addrodsection").click(function(){
						if(m1){
							m1=false;
							showmessageInfo("现在你可以添加道路段信息","slide");
							removelistener();
					    	map.addEventListener("click",showupaddroadInfo);
						}else{
							showmessageInfo("您已经打开了监听事件","slide");
						}
					});
					
					$("#endrodsection").click(function(){
						 m1=true;
						$.messager.show({
								title : '消息提示',
								msg : '结束添加路段信息',
								timeout : 2000,
								showType : 'slide'
							});
					   map.removeEventListener("click", showupaddroadInfo);
					});

        			var points;
					if(polyline!=null)
                        points=polyline.getPath();
					else{
                        points=[];
					}
					//添加道路信息
					function showupaddroadInfo(e){
							var param=${RequestParameters.id};
							var data={'id':param,'longitude':e.point.lng,'latitude':e.point.lat};
							ajaxwithoutjson("roadline_addRoadlineString.action",data,"post",function(){
							     	points.push(new BMap.Point(e.point.lng,e.point.lat));
							     	polyline.setPath(points);//polyline.setPath()后map进行了绘制故不用讲其放入map图层里面
							     	//polyline=addPolylineMap(map,polyline.getPath,"blue");
							});		
					}
					
			$("#startupdate").click(function(){
					removelistener();
					showInfoMessage("您已经打开了编辑功能");
					polyline.enableEditing();
			});
			
			$("#endupdate").click(function(){
					showInfoMessage("关闭批量编辑功能");
					polyline.disableEditing();
					savelotupdate();
			});	
			
			function savelotupdate(){
					//alert(polyline.getPath());
					var linestring="";
					$.each(polyline.getPath(),function(id,item){
						if(id==0){
							linestring+="LINESTRING("+item.lng+" "+item.lat;
						} 
						else{
							linestring+=","+item.lng+" "+item.lat;
						}
					});
					linestring+=")";
					ajaxwithoutjson("roadline_updatelotRoadlineString.action",{temp:linestring,id:${RequestParameters.id}},"post",function(){
						showInfoMessage("保存成功");
					});
			};	
			
			function removelistener(){
				if(polyline!=null)
				polyline.disableEditing();
				map.removeEventListener("click", showupaddroadInfo);
				map.removeEventListener("click", showInfo);
			}	
		});
	</script>
  </head>
  
  <body>
  线路id是:${RequestParameters.id}
 添加新的站点：<a id="addstation"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
  <a id="endstation"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">结束</a>  
  输入地址: <input type="text" id="localsearch">  
  <a id="localbtn"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">搜索</a>  
   添加道路路段信息：<a id="addrodsection"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>
  <a id="endrodsection"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">结束</a> <br/>
  批量编辑公交线路信息：<a id="startupdate"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">编辑</a>
  <a id="endupdate"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">结束</a>  
		<div id="container"
			style="position: absolute;  width: 100%; height: 86%; overflow:hidden;">
		</div>
<div id="win" data-options="collapsible:false,minimizable:false,maximizable:false,modal:true"></div>
</body>
</html>
