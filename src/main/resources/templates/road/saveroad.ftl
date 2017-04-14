
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<#include "../public/head.ftl">
<html>
  <head>
    <title>公交车管理系统</title>
     <style type="text/css">
		form div {
			margin:10px;
		}
	</style>
	
	<script type="text/javascript">
	
	$(function(){
		var dg=parent.$("#dg");
	
		$("input[name=name]").validatebox({
			required : true,
			missingMessage:"请输入一个名称"
		});
		
		$("#ff").form('disableValidation');
		
		$("#btn").click(function(){
			$("#ff").form("enableValidation");
			if($("#ff").form("validate"))
			{
				$("#ff").form("submit", {
						url: 'roadline_save.action',
						success: function(){
							//关闭当前窗口刷新页面
							parent.$("#win").window("close");
							dg.datagrid("reload");
							}
					});					
			}
		});
	});
		</script>
  </head>
  	
  <body>
 		<form id="ff" method="post" enctype="multipart/form-data">
	 		<div>
				<label for="name">公交线路名称:</label> <input type="text" name="name"  />
			</div>
			<div>
				<label for="price">公交线路出发时间:</label> <input type="time" name="startTime1"  />
			</div>
			<div>
				<label for="price">公交线路结束时间:</label> <input type="time" name="endTime2"  />
			</div>
			
			<div>
				<a id="btn"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>  
				<a id="btn"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">重置</a>  
			</div>
 		</form>
</body>
</html>
