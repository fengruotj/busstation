
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
		//var dg=parent.$("#dg");
		function getQueryString(name) {
		var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
		var r = window.location.search.substr(1).match(reg);
		if (r != null) return unescape(r[2]); return null;
		}
		$("#idroad").val(getQueryString("id"));
		$("#longitude").val(getQueryString("longitude"));
		$("#latitude").val(getQueryString("latitude"));

		$("input[name=name]").validatebox({
			required : true,
			missingMessage:"请输入一个名称"
		});
		
		$("input[name=name]").validatebox({
			required : true,
			missingMessage:"请输入一个备注"
		});
		
		$("input[name=name]").validatebox({
			required : true,
			missingMessage:"请输入一个停留时间"
		});
		
		$("#ff").form('disableValidation');
		
		$("#btn").click(function(){
			$("#ff").form("enableValidation");
			if($("#ff").form("validate"))
			{
				$("#ff").form("submit", {
						url: 'roadstation_save.action',
						success: function(){
							//关闭当前窗口刷新页面
							parent.$("#win").window("close");
							parent.location.reload();
							//dg.datagrid("reload");
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
			<label for="name">站点名称:</label> <input type="text" name="name"  />
		</div>
		<div>
			<label for="price">站点备注:</label> <input type="text" name="demo"  />
		</div>
		<div>
			<label for="price">站点停留时间:</label> <input type="text" name="staytime"  />
		</div>
		<div>
			<input type="hidden" id="longitude" name="longitude"  />
		</div>
		<div>
			<input type="hidden" id="latitude" name="latitude"  />
		</div>
		<div>
			<input type="hidden" id="idroad" name="idroad"  />
		</div>
		
	<div>
		<a id="btn"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a>  
		<a id="btn"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">重置</a>  
	</div>
	</form>
</body>
</html>
