<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html>
<head>
    <title>秒杀系统</title>
</head>
<body>
<h1>iPhoneXsMAX !!! 1元秒杀！！！</h1>

<form id="msform" action="${pageContext.request.contextPath}/doseckill" enctype="application/x-www-form-urlencoded">
    <input type="hidden" id="prodid" name="prodid" value="0101">
    <input type="button" id="miaosha_btn" name="seckill_btn" value="秒杀点我"/>
</form>

</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/script/jquery/jquery-3.1.0.js"></script>
<script type="text/javascript">
    /*窗口加载事件*/
    $(function () {
        /*点击秒杀按钮触发事件*/
        $("#miaosha_btn").click(function () {
            // 获取表单的url地址
            var url = $("#msform").attr("action");
            // 发起异步请求
            $.post(url, $("#msform").serialize(), function (data) {
                // 返回的结果进行判断,如果返回值为false就是秒杀失败
                if (data == "false") {
                    alert("秒杀失败");
                    $("#miaosha_btn").attr("disabled", true);
                }
            });
        })
    })
</script>
</html>