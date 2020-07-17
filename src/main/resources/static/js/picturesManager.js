var page="1";
var pageSize="10";
var table;
$(function () {
    //获取表格数据
    getAllFilesList();

})
/**
 * 后台返回数据填充表格 demo
 */
function getAllFilesList() {
    var obsFile={};
    obsFile.userId=sessionStorage.getItem("id");
    obsFile.type="图片";
    $.ajax({
        url: sessionStorage.getItem("rootPath") + "/files/slectFilesList",
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
    })
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
                            return '<i class="layui-icon layui-icon-picture" style="font-size: 30px;"></i>';
                    }}
                , {field: 'fileName',width:280, title: '文件名',sort:true, templet: function (data) {
                        return "<a href='javascript:void(0)' style='color: #1aa5fb'"+" onclick=openFile('" + data.path + "','"+data.type+"')>" + data.fileName + "</a>";
                    }}
                , {field: 'size',width:280, title: '文件大小',sort:true}
                , {field: 'makeTime',width:250, title: '上传时间',sort:true}
                , {fixed: 'right', width:200,title: '操作',templet:function (data) {
                        var btns = "";
                            btns += ' <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</a>';
                            btns += ' <a class="layui-btn layui-btn-xs layui-btn-primary" lay-event="preview"><i class="layui-icon layui-icon-refresh"></i>重命名</a>';
                        return btns;
                    }}
            ]]
        });

        //监听行工具事件
        table.on('tool(allFilesDemo)', function (obj) {
            var data = obj.data;
            if (obj.event === 'delete'){
                layer.confirm('真的删除该文件吗？', function(index){
                    deleteFile(data.fileId,data.path,data.type);
                    obj.del();
                    layer.close(index);
                });
            }else if(obj.event === 'preview'){
                var data = obj.data;
                layer.prompt({title: '输入新名称',  formType: 0,value:data.fileName}, function(fileName, index){
                    var obsFile={};
                    obsFile.userId=sessionStorage.getItem("id");
                    obsFile.fileName=data.fileName;
                    obsFile.fileId=data.fileId;
                    obsFile.path=data.path.substring(0,data.path.length-data.fileName.length)+fileName;
                    $.ajax({
                        url: sessionStorage.getItem("rootPath") + "/files/rename",
                        data:JSON.stringify(obsFile),
                        dataType:'json',
                        contentType: 'application/json;charset=utf-8',
                        type:'post',
                        success:function(res){
                            layer.msg("重命名成功！",{icon:1,time:1000},function () {
                                getAllFilesList();
                                layer.close(index);
                            });
                        },
                        error: function (XMLHttpRequest, textStatus, errorThrown) {
                            layer.msg("重命名失败！" ,{icon:2,time:1000});
                            console.log("失败" + XMLHttpRequest.status + ":" + textStatus + ":" + errorThrown);
                        }
                    })
                });
            }
        });
    });
}
/**
 * 删除文件
 */
function deleteFile(id,path,type) {
    var obsFile = {};
    obsFile.fileId=id;
    obsFile.userId=sessionStorage.getItem("id");
    obsFile.path=path;
    obsFile.type=type;
    $.ajax({
        url: sessionStorage.getItem("rootPath") + "/files/deleteFile",
        data:JSON.stringify(obsFile),
        dataType:'json',
        contentType: 'application/json;charset=utf-8',
        type:'post',
        success:function(res){
            layer.msg("删除文件/文件夹成功！",{icon:1,time:1000});
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("删除文件/文件夹失败！" ,{icon:2,time:1000});
            console.log("失败" + XMLHttpRequest.status + ":" + textStatus + ":" + errorThrown);
        }
    })
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
 * 下载文件
 */
function ObsDownload() {
    var checkStatus = table.checkStatus("allFilesTable");
    if (checkStatus.data.length > 0) {
        //遍历下载
        for (var i = 0; i < checkStatus.data.length; i++) {
            var res = "https://sss-"+sessionStorage.getItem("id")+".obs.cn-north-4.myhuaweicloud.com/"+checkStatus.data[i].path+'?response-content-disposition=attachment';
            window.open(res,"_blank");
        }
    } else {
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
            layer.alert('请至少选择一张图片');
        })
    }
}
/**
 * 打开文件
 */
function openFile(path,type) {
    var obsFile={};
    obsFile.userId=sessionStorage.getItem("id");
    obsFile.path=path;
    $.ajax({
        url: sessionStorage.getItem("rootPath") + "/files/preview",
        method: "post",
        dataType: "text",
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(obsFile),
        success: function (res) {
            window.open(res,"_blank");
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("失败" + XMLHttpRequest.status + ":" + textStatus + ":" + errorThrown);
        }
    })
}
