var page="1";
var pageSize="10";

$(function () {
    //获取表格数据
    getUserList();
    layui.use('form', function () {
        var form = layui.form; //只有执行了这一步，部分表单元素才会自动修饰成功
        //监听开关操作
        form.on('switch(state)', function(obj){
            if(obj.elem.checked==false){
                changeState(parseInt(this.value),0);
            }else{
                changeState(parseInt(this.value),1);
            }
        });
    })

})
/**
 * 修改用户状态
 */
function changeState(id,state) {
    var user={};
    user.id=id;
    user.state=state;
    $.ajax({
        url: sessionStorage.getItem("rootPath") + "/users/changeState",
        data:JSON.stringify(user),
        dataType:'json',
        contentType: 'application/json;charset=utf-8',
        type:'post',
        success:function(res){
            layer.msg("修改状态成功！",{icon:1,time:1000});
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("修改状态失败！" ,{icon:2,time:1000});
            console.log("失败" + XMLHttpRequest.status + ":" + textStatus + ":" + errorThrown);
        }
    })
}
/**
 * 后台返回数据填充表格 demo
 */
function getUserList() {
    var user={};
    if ($("#userName").val() != "" && $("#userName").val() != null){
        user.userName=$("#userName").val();
    }
    if ($("#role").val() != "" && $("#role").val() != null){
        user.role=$("#role").val();
    }
    if ($("#state").val() != "" && $("#state").val() != null){
        user.state=$("#state").val();
    }
    $.ajax({
        url: sessionStorage.getItem("rootPath")+ "/users/selectUserList",
        method: "post",
        dataType: "json",
        contentType: 'application/json;charset=utf-8',
        data: JSON.stringify(user),
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
        var table = layui.table;
        table.render({
            elem: '#usersTable'
            ,url: '../../admin/data/common.json'
            , page: false
            , height: 400
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
                {field: 'userId', title: '账号', sort: true}
                , {field: 'userName', title: '真实姓名'}
                , {field: 'role', title: '角色', sort: true,templet:function (data) {
                        if(data.role==1 ){
                            return "管理员";
                        }else if(data.role==0){
                            return "普通用户";
                        }
                    }}
                , {field: 'email', title: '邮箱'}
                , {field: 'phoneNum', title: '电话'}
                , {field: 'state', title: '状态', sort: true,templet:function (data) {
                        if(data.state==1){
                            return  '<input type="checkbox" value="'+data.id+'" checked="" lay-filter="state" lay-skin="switch" lay-filter="switchTest" lay-text="使用|禁用">';
                        }else if(data.state==0){
                            return '<input type="checkbox" value="'+data.id+'" lay-filter="state" lay-skin="switch" lay-text="使用|禁用">';
                        }
                    }}
                , {fixed: 'right', title: '操作',templet:function (data) {
                        var btns = "";
                            btns += ' <a class="layui-btn layui-btn-xs layui-btn-danger" lay-event="delete"><i class="layui-icon layui-icon-delete"></i>删除</a>';
                        return btns;
                    }}
            ]]
        });

        //监听行工具事件
        table.on('tool(usersDemo)', function (obj) {
            var data = obj.data;
            if (obj.event === 'delete'){
                layer.confirm('真的删除该用户吗？', function(index){
                    obj.del();
                    deleteUser(data.id);
                    layer.close(index);
                });
            }
        });
    });
}
/**
 * 删除用户
 */
function deleteUser(id) {
    $.ajax({
        url:sessionStorage.getItem("rootPath") + "/users/deleteUser",
        data:JSON.stringify(id),
        dataType:'json',
        contentType: 'application/json;charset=utf-8',
        type:'post',
        success:function(res){
            layer.msg("删除用户成功！",{icon:1,time:1000});
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            layer.msg("删除用户失败！" ,{icon:2,time:1000});
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
            elem: 'usersTable_page' //注意，这里的 test1 是 ID，不用加 # 号
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
                    getUserList();
                }
            }
        });

    });
}

/**
 * 弹窗新增
 */
function showAddWin() {
    layui.use('layer', function () { //独立版的layer无需执行这一句
        var $ = layui.jquery, layer = layui.layer; //独立版的layer无需执行这一句
        layer.open({
            type: 2,
            title: '新增用户',
            skin: 'layui-layer-molv', //样式类名
            shade: 0.8,
            shadeClose:false,
            area: ['50%', '70%'],
            content: 'addUser.html'
        });
    });
}
/**
 * 批量导入
 */
function addUsers() {
    layer.msg('玩命开发中...');
}
