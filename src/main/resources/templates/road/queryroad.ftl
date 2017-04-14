
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<#include "../public/head.ftl">
<html>
  <head>
    <title>公交车管理系统</title>
      <link rel="stylesheet" type="text/css" href="${shop}/css/main.css">
	<script type="text/javascript">
		$(function(){
		$('#dg').datagrid({
				url : 'roadline_queryJoinStartStationAndEndStation.action',
				striped:true,
				nowrap:true,
				fitColumns:true,
		/*rowStyler : function(index, row) {
					if (index%2==0) {
						 return 'background-color:#6293BB;color:#ff0000;';
					}else{
						 return 'background-color:#6293BB;color:#00ff00;';
					}
				},*/
				idField:"id",
				toolbar: [{
					iconCls: 'icon-add',
					text:"添加公交线路",
					handler: function()
						{
								$("#win").window({    
								    width:450,    
								    height:500, 
								    title:'添加公交线路',   
								    content:'<iframe frameborder="0" width="100%" height="100%" src="send_road_saveroad.action"/>'  
								});  
						}
					},'-',{
                    iconCls: 'icon-add',
                    text:"编辑公交线路信息",
                    handler: function()
                    {
                        var rows = $('#dg').datagrid('getSelections');
                        if(rows.length!=1) {
                            showInfoMessage("只能编辑一个公交线路");
                        }else{
                            var row=rows[0];
                            $('#dlg').dialog('open').dialog('setTitle','编辑公交线路');
                            $('#fm').form('load',row);
                            url = 'roadline_update.action?id='+row.id;
                        }
                    }
                },'-',{
					iconCls: 'icon-edit',
					text:"编辑公交线路站点",
					handler: function()
					{
						var rows=$("#dg").datagrid("getSelections");
						if(rows.length!=1){
							$.messager.show({
								title : '消息提示',
								msg : '只能编辑一个公交线路',
								timeout : 2000,
								showType : 'slide'
							});

						} else {
							window.open("send_road_updateroad.action?id="+rows[0].id);
						}
					}
				}, '-', {
					iconCls : 'icon-remove',
					text : "删除公交线路",
					handler : function() 
					{
						var rows=$("#dg").datagrid("getSelections");
						if(rows.length==0){
							$.messager.show({
								title : '消息提示',
								msg : '必须选择一个记录',
								timeout : 2000,
								showType : 'slide'
							});

						} else {
								$.messager.confirm('确认对话框', '您想要删除吗？', function(r){
								/*if (r){
									var ids=""; 
									for(var i=0;i<rows.length;i++){
										ids+=rows[i].id+',';
										}
									ids=ids.substring(0,ids.lastIndexOf(','));
									//发送ajax请求
									$.post("product_deletebyIds.action",{ids:ids},function(result){
										if(result=="true"){
											//让所有选择的选项不选中
											$('#dg').datagrid('uncheckAll'); 
											$('#dg').datagrid('reload');    // 重新载入当前页面数据  
										}else{
											$.messager.show({
											title : '消息提示',
											msg : '删除失败请检查操作',
											timeout : 2000,
											showType : 'slide'
											});
										}
									},"text");
									}*/
									});
								}
					}
				}, '-',{
					iconCls: 'icon-remove',
					text:"删除公交线路道路信息",
					handler: function()
					{
						var rows=$("#dg").datagrid("getSelections");
						if(rows.length!=1){
							showInfoMessage("只能删除一个公交线路");
						} else {
						$.messager.confirm('确认对话框', '您想要删除吗？', function(r){
							if(r)
							deleteRoadlineStringById(rows[0].id);
							});
						}
					}
				}, '-', {
					text : "<input id='search' />"
				} ],

				queryParams : {
					name : ''
				},
				pagination : true,
				frozenColumns : [ [ {
					field : 'fdsf',
					checkbox : true
				}, {
					field : 'id',
					title : '编号',
					width : 100
				} ] ],
				columns : [ [ {
					field : 'name',
					title : '公交线路名称',
					width : 100
				}, 
				{
					field : 'startTime',
					title : '公交出发时间',
					width : 100
				},{
					field : 'endTime',
					title : '公交结束时间',
					width : 150,
					formatter : function(value, row, index) {
						return "<span>"+value+"</span>";
					}
				},{
					field : 'roadstationByStartid',
					title : '起点站点名称',
					width : 150,
					formatter : function(value, row, index) {
						return "<span>"+value.name+"</span>";
					}
				} ,{
					field : 'roadstationByEndid',
					title : '结束站点名称',
					width : 150,
					formatter : function(value, row, index) {
						return "<span>"+value.name+"</span>";
					}
				}] ]
			});

			$('#search').searchbox({
				searcher : function(value, name) {
					$('#dg').datagrid('load', {
						name : value
					});
				},
				prompt : 'Please Input Value'
			});

		});


        function saveRoadline(){
            $('#fm').form('submit', {
                url: url,
                onSubmit: function () {
                    return $(this).form('validate');
                },
                success: function (result) {
                    var result = eval('(' + result + ')');
                    if (result.errorMsg) {
                        $.messager.show({
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    } else {
                        $('#dlg').dialog('close');		// close the dialog
                        $('#dg').datagrid('reload');	// reload the user data
                    }
                }
            });
        }

	</script>
  </head>


<body>

 	<table id="dg" ></table>
 	<div id="win" data-options="collapsible:false,minimizable:false,maximizable:false,modal:true"></div>


    <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
         closed="true" buttons="#dlg-buttons">
        <div class="ftitle">公交线路信息</div>
        <form id="fm" method="post">
            <div class="fitem">
                <label>公交线路名称</label>
                <input name="name" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>开车时间</label>
                <input name="startTime"  class="easyui-timespinner"  required="true">
            </div>
            <div class="fitem">
                <label>结束时间</label>
                <input name="endTime" class="easyui-timespinner" required="true" >
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRoadline()">保存</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
    </div>

</body>  
</html>
