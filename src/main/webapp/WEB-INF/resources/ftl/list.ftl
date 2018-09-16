<#setting classic_compatible=true>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>seckill goods List</title>
    <style type="text/css">
        .STYLE1 {
            font-family: Arial, Helvetica, sans-serif;
            font-weight: bold;
            font-size: 36px;
            color: #FF0000;
        }
        .STYLE13 {font-size: 24}
        .STYLE15 {font-family: Arial, Helvetica, sans-serif; font-size: 24px; }
    </style>
</head>

<body>
<table width="1800" height="600" border="0" cellpadding="0" cellspacing="0">
    <tr>
        <td width="500" height="200">&nbsp;</td>
        <td width="800" height="200" align="center"><div align="center"><span class="STYLE1">seckill goods List </span></div></td>
        <td width="500" height="200">&nbsp;</td>
    </tr>
    <tr>
        <td width="500" height="200">&nbsp;</td>
        <td width="800" height="200">
            <table width="800" height="200" border="1" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="150" height="65" align="center"><span class="STYLE15">商品名称</span></td>
                    <td width="250" height="65" align="center"><span class="STYLE15">秒杀开始时间</span></td>
                    <td width="250" height="65" align="center"><span class="STYLE15">秒杀结束时间</span></td>
                    <td width="150" height="65" align="center"><span class="STYLE15">详情</span></td>
                </tr>
            <#list goodsList as goods>
                <tr>
                    <td width="150" height="65" align="center"><span class="STYLE15">${goods.goodsName}</span></td>
                    <td width="250" height="65" align="center"><span class="STYLE15">${goods.startTime?string('yyyy-MM-dd hh:mm:ss')}</span></td>
                    <td width="250" height="65" align="center"><span class="STYLE15">${goods.endTime?string('yyyy-MM-dd hh:mm:ss')}</span></td>
                    <td width="150" height="65" align="center"><span class="STYLE15"><a href="/seckill/detail?id=${goods.id}">链接</span></td>
                </tr>
            </#list>
            </table>
        </td>
        <td width="500" height="200">&nbsp;</td>
    </tr>
    <tr>
        <td width="500" height="200">&nbsp;</td>
        <td width="800" height="200">&nbsp;</td>
        <td width="500" height="200">&nbsp;</td>
    </tr>
</table>
</body>
</html>