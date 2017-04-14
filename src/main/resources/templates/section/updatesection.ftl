
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<#include "../public/head.ftl">
<html>
  <head>
    <title>公交车管理系统</title>

      <script type="text/javascript">
          $(function() {
              var map = new BMap.Map("container");
              addControlMap(map, "武汉", 12);
              ajax("roadstation_findByName.action",{stationName:'${RequestParameters.startstation}'},"post",function(data,status){
                  addMakreOnMap(data.longitude,data.latitude,data.name,map);
              });
              ajax("roadstation_findByName.action",{stationName:'${RequestParameters.endstation}'},"post",function(data){
                  addMakreOnMap(data.longitude,data.latitude,data.name,map);
              });

              var myDis = new BMapLib.DistanceTool(map);//鼠标测量距离功能

              $("#addstation").click(function () {
                  showInfoMessage("您已经打开了鼠标测量距离功能");
                  myDis.open();
              });

              $("#endstation").click(function () {
                  showInfoMessage("您已经关闭了鼠标测量距离功能");
                  myDis.close();
              });

              $("#saveDistance").click(function () {
                  var distance=$("#distance").val();
                  $.post("roadsection_savedistance.action",{id:'${RequestParameters.id}',distance:distance},function(data,status){
                      if (data.errorMsg) {
                          $.messager.show({
                              title: 'Error',
                              msg: data.errorMsg
                          });
                      }else{
                        alert("修改成功");
                      }
                  });
              });
          });
      </script>
  </head>

  <body>
  <#--${RequestParameters.startstation}  &nbsp ${RequestParameters.endstation}-->
  开启鼠标测量距离功能：<a id="addstation"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">开启</a>
  <a id="endstation"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-remove'">关闭</a>
      <div>距离:</div>
      <input id="distance" class="easyui-numberbox" required="true" data-options="prompt:'请输入距离',required:true" style="width:20%;height:25px">
      <a id="saveDistance"  href="#" class="easyui-linkbutton" data-options="iconCls:'icon-add'">保存</a>

  <div id="container"
       style="position: absolute;  width: 100%; height: 90%; overflow:hidden;">
  </div>
</body>
</html>
