var contextPath = "";
var menuUrl = {};
var locationCode = '0';
var currentUrl = 'equipmentLedgerMid.html';
var nodeId='1';

$(function() {
    contextPath = getRootPath();
    //监听横条菜单
    $("li").click(function(){
        $(".layui-this").removeClass("layui-this");
        $(this).addClass("layui-this")
    });

    //监听左侧 设备树的点击事件
    layui.extend({dtree:'{/}layui/dtree/dtree'}).use(['dtree','layer','jquery'],function () {
        var dtree=layui.dtree,layer=layui.layer,$=layui.jquery;

        dtree.on("node('test1')" ,function(obj){
            locationCode = obj.param.recordData.locationCode;
            nodeId=obj.param.nodeId;
            //刷新当前页面
            jump(currentUrl)
        });
    })

})

/**
 * 获取根路径
 * @returns {string}
 */
function getRootPath(){
    var curPageUrl = window.document.location.href;
    var rootPath = curPageUrl.split("//")[0] + "//" + curPageUrl.split("//")[1].split("/")[0] + "/"
        + curPageUrl.split("//")[1].split("/")[1];
    return rootPath;
}

/**
 * iframe内的页面跳转
 * @param url
 */
function jump(url) {
    currentUrl = url;
    $('iframe').attr('src', url + "?" + nodeId+"?"+locationCode);

}
/**
 * iframe外的页面跳转
 * @param url
 */
function turnTo(url) {
    window.location.href = url;
}

/**
 * 初始化菜单栏
 * @param menuList
 */
function initMenu(menuList) {
    var html = "<li class=\"layui-nav-item\" style='margin-left: 30px; font-size: 15pt' onclick=\"turnTo('main.html')\"><i class='layui-icon layui-icon-home'></i>回到首页</li>";
    //组装菜单
    $.each(menuList,function(key,value){  //遍历键值对
        var id = value.id;
        var name = value.name;
        var url = value.url;

        if (key == 0){
            html += "<li class='layui-nav-item layui-this' style='margin-left: 30px; font-size: 15pt' onclick='jump(\""+ url +"\")'>" + name + "</li>";
        }else {
            html += "<li class='layui-nav-item' style='margin-left: 30px; font-size: 15pt' onclick='jump(\""+ url +"\")'>" + name + "</li>";
        }

        menuUrl.id = id;
        menuUrl.url = url;
    })
    //加载面包屑的 菜单显示
    $(".layui-nav").html("");
    $(".layui-nav").html(html);

}