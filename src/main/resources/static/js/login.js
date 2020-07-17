/**
 * 获取根路径
 * @returns
 */
function getRootPath(){
    var curPageUrl = window.document.location.href;
    rootPath = curPageUrl.split("//")[0] + "//" + curPageUrl.split("//")[1].split("/")[0];
    sessionStorage.setItem("rootPath",rootPath);
    console.log("rootPath"+rootPath)
}

$(function () {
    layui.use(['pearHash','form', 'element','jquery'], function() {
        var form = layui.form;
        var element = layui.element;
        var $ = layui.jquery;
        var pearHash = layui.pearHash;

        $("body").on("click",".login",function(){
            var userId="";
            var password="";

            if ($("#userId").val() == null || $("#userId").val() == ''){
                layer.tips('请填写用户名', '#userId', {
                    tips: [1, '#23cc99'],
                    time: 2000
                });
            }else{
                userId=$("#userId").val();
            }

            if ($("#password").val() == null || $("#password").val() == ''){
                layer.tips('请填写密码', '#password', {
                    tips: [1, '#23cc99'],
                    time: 2000
                });
            }else{
                password = pearHash.md5($("#password").val());
            }
            var user={};
            user.userId=userId;
            user.password=password;
            if(userId!="" && password!=""){
                getRootPath();
                $.ajax({
                    url:sessionStorage.getItem("rootPath")+"/person/login",
                    method: "post",
                    dataType: "json",
                    contentType: 'application/json;charset=utf-8',
                    data: JSON.stringify(user),
                    success: function (data) {
                        if (data.staus == true){
                            if(data.state==0){
                                var $ = layui.jquery, layer = layui.layer;
                                layer.msg('您的账户已被禁用，如有疑问请联系管理员！', {time: 3000, icon:4});
                            }else{
                                sessionStorage.setItem("id",data.id);//存储用户信息到session
                                sessionStorage.setItem("userId",data.userId);//存储用户信息到session
                                sessionStorage.setItem("password",data.password);//存储用户信息到session
                                sessionStorage.setItem("userName",data.userName);//存储用户信息到session
                                sessionStorage.setItem("email",data.email);//存储用户信息到session
                                sessionStorage.setItem("phoneNum",data.phoneNum);//存储用户信息到session
                                if(data.role==1)
                                    window.location.href="index.html";
                                else
                                    window.location.href = "com_index.html";
                            }
                        }else {
                            //弹出错误提示
                            var $ = layui.jquery, layer = layui.layer;
                            layer.msg('用户名或密码错误，请重新输入！', {time: 3000, icon:2});
                        }
                    },
                    error: function (XMLHttpRequest, textStatus, errorThrown) {
                        console.log("失败" + XMLHttpRequest.status + ":" + textStatus + ":" + errorThrown);
                    }
                })
            }
        })
    })
    }
)