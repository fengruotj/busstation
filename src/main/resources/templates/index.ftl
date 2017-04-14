
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<#include "public/head.ftl">
<html>
  <head>
    <title>公交车管理系统</title>
      <style type="text/css">
		#menu {
			width: 200px;
			height: 500px;
		}
		
		#menu ul {
			list-style: none;
			padding: 0px;
			margin: 0px;
			
		}
		
		#menu ul li {
			border-bottom: 1px solid #fff;
		}
		
		#menu ul li a{
			display: block;
			text-decoration: none;
			height: 20px;
			color: #fff;
			padding: 5px;
			background-color: #008792;
		}
			#menu ul li a:hover{
				background-color: #00a6ac;
			}
	</style>

	<script type="text/javascript">
		$(function(){
			//alert(${request.getContextPath()});
			$("a[title]").click(function(){
				var text=$(this).text();
				var href=$(this).attr("title");
				if($("#tt").tabs("exists",text)){
					$("#tt").tabs("select",text);
				}else{
					$("#tt").tabs("add",{
						title:text,
						//content:正文内容
						closable:true,
						content:'<iframe title ='+text+' frameborder="0" width="100%" height="100%" src='+ href +'/>'
					});
				}
			});
		
		var map = new BMap.Map("container");
		addControlMap(map,"武汉",12);

//		//添加浏览器GPS定位功能
//		GPSlocation(map);

		ajax("roadline_findAllRoadlineByJson.action",{},"post",function(data){getRoadLineListMap(map,data);});
		
		});
		
	</script>
	
  </head>
  
 <body class="easyui-layout">   
    <div data-options="region:'north',title:'欢迎来到公交管理系统',split:true" style="height:60px;">
		<span>欢迎来到公交线路管理系统</span>${BasePath}
	</div>
    <!-- 此处显示系统菜单 -->
	<div data-options="region:'west',title:'系统菜单',split:true" data-options="fit:true" style="width:250px;" >
	    <!-- 此处显示系统菜单 -->
		<div id="menu" class="easyui-accordion"  data-options="fit:true">
			<div title="基本操作" >
				<ul>
					<li><a title="send_road_queryroad.action">公交线路管理</a></li>
					<li><a title="send_section_querysection.action">公交线路段管理</a></li>
				</ul>
			</div>
			<div title="换乘操作" >
				<ul>
					<li><a title="send_road_bussearch.action">公交换乘算法</a></li>
					<li><a title="send_bussearch_search.action">公交线路基于最短路径的算法</a></li>
				</ul>
			</div>
		</div>
	</div>

	<div data-options="region:'center',title:'基本视图'" style="padding:1px;background:#fff;">
	
		<div id="tt" class="easyui-tabs" data-options="fit:true">
		<div title="主页" >
			<div id="container"
				style="position: absolute;  width: 100%; height: 100%; overflow:hidden;">
			</div>
		</div>
		</div>

	</div>
	
</body>  

</html>
