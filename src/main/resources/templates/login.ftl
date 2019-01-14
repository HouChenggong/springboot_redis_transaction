<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <#include "/common/common.ftl"/>
</head>
<body>
<div>
    <span>hello! please login!</span>
</div>
<div>

    <span>用户名:</span><input type="text" id="loginName" />
    <span>密码:</span><input type="text" id="password" />

</div>
<div>
    <input type="button" onclick="submitLogin()" value="登陆">
</div>
</body>

</html>
<#--<script type="text/javascript" src="/js/md5.js"></script>-->
<script type="text/javascript">
    function submitLogin() {
        var loginName = $.trim($("#loginName").val());
        var password = $.trim($("#password").val());
        $.ajax({
            type: 'POST',
            url: "http://localhost:8080/authenticate",
            data: {"loginName":loginName,"password":password},
            dataType:"json",
            success: function(rep){
                if(rep.status == 0){
                    alert(rep.message);
                    //window.location.reload();
//                    loadPage(0,baseUrl);
                }else{
                    alert(rep.message);
                }
            },
            error:function(rep){
                alert("获取信息失败!");
            }
        });
    }
</script>
