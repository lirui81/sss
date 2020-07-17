var page="1";
var pageSize="10";
var table;
var layer;
var fileId,fpath;
$(function () {
    sessionStorage.setItem("filePath","");
    //获取表格数据
    getAllFilesList();

})
/**
 * 后台返回数据填充表格 demo
 */
function getAllFilesList() {
    layui.use('layer', function () { //独立版的layer无需执行这一句
        var $ = layui.jquery;
        layer = layui.layer; //独立版的layer无需执行这一句
    })
    var obsFile={};
    obsFile.userId=sessionStorage.getItem("id");
    if(sessionStorage.getItem("filePath")!=""){
        obsFile.path=sessionStorage.getItem("filePath")+"/";
    }
    $.ajax({
        url: sessionStorage.getItem("rootPath") + "/files/slectFileList",
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
 * 模糊查询
 */
function getFilesList() {
    var obsFile={};
    obsFile.userId=sessionStorage.getItem("id");
    obsFile.fileName=$("#filename").val();

    $.ajax({
        url: sessionStorage.getItem("rootPath") + "/files/selectFile",
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
                , {field: 'fileName',width:280, title: '文件名',sort:true, templet: function (data) {
                        return "<a href='javascript:void(0)' style='color: #1aa5fb'"+" onclick=openFile('" + data.path + "','"+data.type+"')>" + data.fileName + "</a>";
                    }}
                , {field: 'size',width:280, title: '文件大小',sort:true, templet: function (data) {
                        if(data.type=="文件夹"){
                            return "--";
                        }else {
                            return data.size;
                        }
                    }}
                , {field: 'makeTime',width:250, title: '上传时间',sort:true, templet: function (data) {
                        if(data.type=="文件夹"){
                            return "--";
                        }else {
                            return data.makeTime;
                        }
                    }}
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
                layer.confirm('真的删除该文件/文件夹吗？', function(index){
                    deleteFile(data.fileId,data.path,data.type);
                    obj.del();
                });
            }else if(obj.event === 'preview'){
                var data = obj.data;
                if(data.type=="文件夹"){
                    layer.msg("文件夹暂时不能重命名哦！")
                }else{
                    layer.prompt({title: '输入新名称', formType: 0,value:data.fileName},function(fileName, index){
                        var obsFile={};
                        obsFile.userId=sessionStorage.getItem("id");
                        obsFile.fileName=data.path;
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
            if(res==0){
                layer.msg("该文件夹内含有文件，请先删除文件后重试！",{icon:0,time:1000});
            }else{
                layer.msg("删除文件/文件夹成功！",{icon:1,time:1000});
            }
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
 * 新建文件夹
 */
function addFloder() {
    layui.use('layer', function () { //独立版的layer无需执行这一句
        var $ = layui.jquery; layer = layui.layer; //独立版的layer无需执行这一句
        layer.prompt({title: '输入文件夹名称', formType: 2}, function(fileName, index){
            var obsFile={};
            obsFile.userId=sessionStorage.getItem("id");
            obsFile.fileName=fileName;
            if(sessionStorage.getItem("filePath")==""){
                obsFile.path=sessionStorage.getItem("filePath")+fileName;
            }else{
                obsFile.path=sessionStorage.getItem("filePath")+"/"+fileName;
            }
            $.ajax({
                url: sessionStorage.getItem("rootPath") + "/files/addFloder",
                data:JSON.stringify(obsFile),
                dataType:'json',
                contentType: 'application/json;charset=utf-8',
                type:'post',
                success:function(res){
                    layer.msg("创建文件夹成功！",{icon:1,time:1000},function () {
                        getAllFilesList();
                        layer.close(index);
                    });
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    layer.msg("创建失败！" ,{icon:2,time:1000});
                    console.log("失败" + XMLHttpRequest.status + ":" + textStatus + ":" + errorThrown);
                }
            })
        });
    });
}
/**
 * 下载文件
 */
function ObsDownload() {
    var checkStatus=table.checkStatus("allFilesTable");
    if(checkStatus.data.length>0){
        //遍历下载
        for(var i=0;i<checkStatus.data.length;i++){
            if(checkStatus.data[i].type!="文件夹"){
                var res = "https://sss-"+sessionStorage.getItem("id")+".obs.cn-north-4.myhuaweicloud.com/"+checkStatus.data[i].path+'?response-content-disposition=attachment';
                window.open(res,"_blank");
            }else{
                layer.msg("文件夹暂时不支持下载！")
            }
        }
    }else {
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
            layer.alert('请至少选择一个文件');
        })
    }
}
/**
 * 移动
 */
function ObsMove() {
    layer.msg('玩命开发中...');
}
/**
 * 复制
 */
function ObsCopy() {
    layer.msg('玩命开发中...');

    /*var checkStatus=table.checkStatus("allFilesTable");
    if(checkStatus.data.length>0){
        //弹窗选文件
        layui.use('layer', function () { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
            layer.open({
                type: 2,
                title: '选择目的文件夹',
                skin: 'layui-layer-molv', //样式类名
                shade: 0.8,
                shadeClose:false,
                area: ['50%', '70%'],
                content: 'chooseFilesManager.html',
                btn: ['确定','关闭'],
                yes: function(layero, index){
                    var res = window["layui-layer-iframe" + index].callbackData();
                    if (res != null || res != '') {
                        if (res == 'false') {
                            return;
                        } else {
                            console.log(res.fileId+res.fpath);
                            fileId=res.fileId;
                            fpath=res.fpath;
                            //遍历复制文件
                            for (var i;i<checkStatus.data.length;i++){
                                copyFiles(checkStatus.data[i].fileName);
                            }
                            layui.use('layer', function() { //独立版的layer无需执行这一句
                                var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
                                layer.msg("复制文件成功！",{icon:1,time:1000},function () {});
                            })
                        }
                    }
                }
            });
        });
    }else {
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
            layer.alert('请至少选择一个文件/文件夹');
        })
    }*/
}

/*function copyFiles(fileId,fileName) {
    var obsFile={};
    obsFile.userId=sessionStorage.getItem("id");
    obsFile.path=fpath;
    obsFile.fileName=fileName;
    $.ajax({
        method: "post",
        url:sessionStorage.getItem("rootPath")() +  "/files/copy",
        dataType:"json",
        contentType: 'application/json;charset=utf-8',
        data:JSON.stringify(obsFile),
        async: false,
        timeout : 50000, //超时时间：50秒
        success: function (data) {

        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            console.log("失败" + XMLHttpRequest.status + ":" + textStatus + ":" + errorThrown);
        }
    })
}*/


/**
 * 打开文件
 */
function openFile(path,type) {
    if(type=="图片" || type=="视频" || type=="音乐"){
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
    }else if(type=="文件夹")
    {
        var obsFile={};
        obsFile.userId=sessionStorage.getItem("id");
        obsFile.path=path+"/";
        $.ajax({
            url: sessionStorage.getItem("rootPath") + "/files/slectFileList",
            method: "post",
            dataType: "json",
            contentType: 'application/json;charset=utf-8',
            data: JSON.stringify(obsFile),
            success: function (res) {
                sessionStorage.setItem("filePath",path);
                renderTable(res);
                renderpage(res);
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                console.log("失败" + XMLHttpRequest.status + ":" + textStatus + ":" + errorThrown);
            }
        })
    }else{
        layui.use('layer', function() { //独立版的layer无需执行这一句
            var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
            layer.alert('该文件暂时不支持预览！');
        })
    }
}
