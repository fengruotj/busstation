
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<#include "../public/head.ftl">
<html>
  <head>
    <title>公交车管理系统</title>
      <link rel="stylesheet" type="text/css" href="${shop}/css/main.css">
    <script type="text/javascript">
    	$(function(){
		$('#dg').datagrid({
				url : 'roadsection_queryRoadSection.action',
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
					iconCls: 'icon-edit',
					text:"编辑公交线路端信息",
					handler: function()
					{
						var rows=$("#dg").datagrid("getSelections");
						if(rows.length!=1){
                            showInfoMessage("只能编辑一个公交路段");
						} else {
                            var row=rows[0];
                            $('#dlg').dialog('open').dialog('setTitle','编辑公交线路');
                            $('#fm').form('load',row);
                            url = 'roadsection_update.action?id='+row.id;
						}
					}
				},'-',{
                    iconCls: 'icon-edit',
                    text:"编辑公交线路路线长度",
                    handler: function()
                    {
                        var rows=$("#dg").datagrid("getSelections");
                        if(rows.length!=1){
                            showInfoMessage("只能编辑一个公交路段");
                        } else {
                            window.open("send_section_updatesection.action?id="+rows[0].id+"&startstation="+rows[0].statrtstation+"&endstation="+rows[0].endstation);
                        }
                    }
                },'-', {
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
					title : '公交线路段名称',
					width : 100
				}, 
				{
					field : 'statrtstation',
					title : '线路端起点名称',
					width : 100
				},{
					field : 'endstation',
					title : '线路端终点名称',
					width : 150,
					formatter : function(value, row, index) {
						return "<span>"+value+"</span>";
					}
				},{
					field : 'elapsedtime',
					title : '线路端经过时间',
					width : 150,
					formatter : function(value, row, index) {
						return "<span>"+value+"分钟"+"</span>";
					}
				},{
                        field : 'distance',
                        title : '线路端距离',
                        width : 150,
                        formatter : function(value, row, index) {
                            return "<span>"+value+"米"+"</span>";
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

        function saveRoadSection(){
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



    <div id="dlg" class="easyui-dialog" style="width:600px;height:280px;padding:10px 20px"
         closed="true" buttons="#dlg-buttons">
        <div class="ftitle">公交线路路段信息</div>
        <form id="fm" method="post">
            <div class="fitem">
                <label>公交线路路段名称:</label>
                <input name="name" style="width: 300px" class="easyui-validatebox" required="true">
            </div>
            <div class="fitem">
                <label>线路路段经过时间:</label>
                <input name="elapsedtime"  class="easyui-validatebox"  required="true">&nbsp 分钟
            </div>
            <div class="fitem">
                <label>公交线路路段距离</label>
                <input name="distance" class="easyui-validatebox" required="true" > &nbsp 米
            </div>
        </form>
    </div>
    <div id="dlg-buttons">
        <a href="#" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRoadSection()">保存</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
    </div>
</body>
</html>
