<#assign basePath=request.contextPath />
<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <script src="//cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="//cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <title>秒杀详情页</title>
</head>

<body>
<input type="hidden" id="basePath" value="${basePath}" />
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h1>${seckillGoods.goodsName}</h1>
        </div>
        <div class="panel-body">
            <h2 class="text-danger">
                <!-- 显示time图标 -->
                <span class="glyphicon glyphicon-time"></span>
                <!-- 展示倒计时 -->
                <span class="glyphicon" id="seckillArea"></span>
            </h2>
        </div>
    </div>
</div>

<!--以下应该放cdn上-->
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<script src="//cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.min.js"></script>
<!-- jQery countDonw倒计时插件  -->
<script src="//cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.min.js"></script>
<script src="${basePath}/resources/js/seckill.js?v=20180905"  type="text/javascript"></script>
<script language="javaScript" type="text/javascript">
    seckill.detail.init(${seckillGoods.id?c},${seckillGoods.startTimeMills?c},${seckillGoods.endTimeMills?c},${seckillGoods.userKilled?string('true','false')},${seckillGoods.currentTimeMills?c});
</script>
</body>

</html>