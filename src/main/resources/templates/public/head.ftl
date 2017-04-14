
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

  <#assign shop=request.getContextPath()>

<title>公交车管理系统</title>

<!-- 下面是前端页面的css -->
      <link rel="stylesheet" href="${shop}/js/jquery-easyui-1.3.5/themes/metro/easyui.css">
      <link rel="stylesheet" href="${shop}/js/jquery-easyui-1.3.5/themes/icon.css">

      <script type="text/javascript" src="${shop}/js/jquery-easyui-1.3.5/jquery.min.js"></script>
      <script type="text/javascript" src="${shop}/js/jquery-easyui-1.3.5/jquery.easyui.min.js"></script>
      <script type="text/javascript" src="${shop}/js/jquery-easyui-1.3.5/locale/easyui-lang-zh_CN.js"></script>

      <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=fQaZ0KHrLQq5GZtdxoRtCRYl"></script>
      <script type="text/javascript" src="http://api.map.baidu.com/library/TextIconOverlay/1.2/src/TextIconOverlay_min.js"></script>
      <script type="text/javascript" src="http://api.map.baidu.com/library/MarkerClusterer/1.2/src/MarkerClusterer_min.js"></script>
      <script type="text/javascript" src="http://api.map.baidu.com/library/CurveLine/1.5/src/CurveLine.min.js"></script>
      <#--百度地图鼠标测距功能-->
      <script type="text/javascript" src="http://api.map.baidu.com/library/DistanceTool/1.2/src/DistanceTool_min.js"></script>

      <script type="text/javascript" src=${shop}/js/map.js></script>
      <script type="text/javascript" src=${shop}/js/easyui.js></script>


