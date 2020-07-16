var page="1";
var pageSize="10";
var table;
$(function () {
    sessionStorage.setItem("filePath","");
    datas={total:8,
        data:[
            {"fileId":1,"fileName":"文件1.txt","fileState":1,"type":"文档","userId":"1","makeTime":"2020-07-15 12:12:11","path":"https://sss-123.obs.cn-north-4.myhuaweicloud.com/文件1.txt","size":"86KB"},
            {"fileId":1,"fileName":"login.jpg","fileState":1,"type":"图片","userId":"1","makeTime":"2020-07-15 12:12:11","path":"https://sss-1.obs.cn-north-4.myhuaweicloud.com/login.jpg","size":"86KB"},
            {"fileId":1,"fileName":"文件1.txt","fileState":1,"type":"音乐","userId":"1","makeTime":"2020-07-15 12:12:11","path":"https://sss-123.obs.cn-north-4.myhuaweicloud.com/文件1.txt","size":"86KB"},
            {"fileId":1,"fileName":"文件1.txt","fileState":1,"type":"视频","userId":"1","makeTime":"2020-07-15 12:12:11","path":"https://sss-123.obs.cn-north-4.myhuaweicloud.com/文件1.txt","size":"86KB"},
            {"fileId":1,"fileName":"文件1.txt","fileState":1,"type":"文件夹","userId":"1","makeTime":"2020-07-15 12:12:11","path":"https://sss-123.obs.cn-north-4.myhuaweicloud.com/文件1.txt","size":"86KB"},
            {"fileId":1,"fileName":"文件1.txt","fileState":1,"type":"其他","userId":"1","makeTime":"2020-07-15 12:12:11","path":"https://sss-123.obs.cn-north-4.myhuaweicloud.com/文件1.txt","size":"86KB"},
            {"fileId":1,"fileName":"文件1.txt","fileState":1,"type":"文档","userId":"1","makeTime":"2020-07-15 12:12:11","path":"https://sss-123.obs.cn-north-4.myhuaweicloud.com/文件1.txt","size":"86KB"},
            {"fileId":1,"fileName":"文件1.txt","fileState":1,"type":"文档","userId":"1","makeTime":"2020-07-15 12:12:11","path":"https://sss-123.obs.cn-north-4.myhuaweicloud.com/文件1.txt","size":"86KB"}
        ]
    }
    //获取表格数据
    getAllFilesList();

})
/**
 * 后台返回数据填充表格 demo
 */
function getAllFilesList() {
    var obsFile={};
    obsFile.id=sessionStorage.getItem("id");

    renderTable(datas);
    renderpage(datas);
    /*$.ajax({
        url: rootPath + "/files/slectFileList",
        method: "post",
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(obsFile),
        success: function (res) {
            renderTable(res);
            renderpage(res);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("失败" + XMLHttpRequest.status + ":" + textStatus + ":" + errorThrown);
        }
    })*/
}

/**
 * 表格渲染
 */
function renderTable(data) {
    layui.use('table', function () {
        table = layui.table;
        table.render({
            elem: '#allFilesTable'
            ,url: '../../admin/data/common.json'
            , page: false
            , height: 350
            ,toolbar: '#toolbar' //开启头部工具栏，并为其绑定左侧模板
            ,defaultToolbar: ['exports', 'print']
            , parseData: function (res) {
                return {
                    "code": 0,
                    "msg": "",
                    "data": data.data
                }
            }
            , cols: [[
                {type:'checkbox',width:30},
                {field: 'type',width:70,title: '图标', templet:function (data) {
                        if(data.type=="图片"){
                            return '<i class="layui-icon layui-icon-picture" style="font-size: 30px;"></i>';
                        }else if(data.type=="文档"){
                            return '<i class="layui-icon layui-icon-list" style="font-size: 30px;"></i>';
                        }else if(data.type=="音乐"){
                            return '<i class="layui-icon layui-icon-headset" style="font-size: 30px;"></i>';
                        }else if(data.type=="视频"){
                            return '<i class="layui-icon layui-icon-video" style="font-size: 30px;"></i>';
                        }else if(data.type=="文件夹"){
                            return '<i class="layui-icon layui-icon-tabs" style="font-size: 30px;"></i>';
                        }else if(data.type=="其他"){
                            return '<i class="layui-icon layui-icon-file" style="font-size: 30px;"></i>';
                        }
                    }}
                , {field: 'fileName',width:280, title: '文件名', templet: function (data) {
                        return "<a href='javascript:void(0)' style='color: #1aa5fb'"+" onclick=openFile('" + data.path + "','"+data.type+"')>" + data.fileName + "</a>";
                    }}
                , {field: 'size',width:300, title: '文件大小'}
                , {field: 'makeTime',width:250, title: '上传时间'}
            ]]
        });

        //监听行工具事件
        table.on('tool(usersDemo)', function (obj) {
            var data = obj.data;
            if (obj.event === 'delete'){
                layer.confirm('真的删除该文件/文件夹吗？', function(index){
                    obj.del();
                    deleteFile(data.fileId,data.path);
                    layer.close(index);
                });
            }
        });
    });
}
/**
 * 分页
 */
function renderpage(date) {
    layui.use('laypage', function () {
        var laypage = layui.laypage;

        //执行一个laypage实例
        laypage.render({
            elem: 'allFilesTable_page' //注意，这里的 test1 是 ID，不用加 # 号
            , layout: ['count', 'prev', 'page', 'next', 'limit', 'skip']
            , count: date.total //数据总数，从服务端得到
            , limits: [10,20, 30]
            , curr: page
            , limit: pageSize
            , jump: function (obj, first) {
                //首次不执行
                if (!first) {
                    page = obj.curr;
                    pageSize = obj.limit;
                    getAllFilesList();
                }
            }
        });

    });
}
/**

 * 返回选择的数据

 */

var callbackData =function () {
    var checkStatus=table.checkStatus("allFilesTable");
    if(checkStatus.data.length!=1){
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
            layer.alert('只能选择一个文件！');
        })
    }else{
        if(checkStatus.data[0].type!="文件夹"){
            layui.use('layer', function() { //独立版的layer无需执行这一句
                var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
                layer.alert('只能移动到文件夹中！');
            })
        }else{
            var data = {

                fileId: checkStatus.data[0].fileId,

                fpath: checkStatus.data[0].fileName

            };
        }
    }
    return data;

};