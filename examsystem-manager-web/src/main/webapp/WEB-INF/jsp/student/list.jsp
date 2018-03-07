<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>学生</title>
    <meta name="keywords" content="在线考试系统,吉林大学珠海学院考试">
    <meta name="description" content="在线考试系统是一个服务于教师与学生的学习平台">

    <link rel="shortcut icon" href="favicon.ico"> <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">

    <!-- Sweet Alert -->
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">



</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!--开始 学生table -->
        <div class="ibox float-e-margins">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>学生</h5>
                    <div class="ibox-tools">
                        <a class="collapse-link">
                            <i class="fa fa-chevron-up"></i>
                        </a>
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <i class="fa fa-wrench"></i>
                        </a>
                        <ul class="dropdown-menu dropdown-user">
                            <li><a href="#">选项1</a>
                            </li>
                            <li><a href="#">选项2</a>
                            </li>
                        </ul>
                        <a class="close-link">
                            <i class="fa fa-times"></i>
                        </a>
                    </div>
                </div>
                <div class="ibox-content">
                    <div class="row row-lg">

                        <div class="col-sm-12">
                            <!-- Example Events -->
                            <div class="example-wrap">
                                <h4 class="example-title">查询</h4>
                                <div class="ibox float-e-margins ">
                                    <form role="form" class="form-inline " id="student-search-form">
                                        <input type="hidden" name="status" value="" id="status">
                                        <div class="form-group m-l-none" >
                                            <label for="studentId" class="sr-only">学号</label>
                                            <input type="text" placeholder="请输入学号" id="studentId" class="form-control" style="width: 100px">
                                        </div>
                                        <div class="form-group m-l-none" >
                                            <label for="studentName" class="sr-only">名字</label>
                                            <input type="text" placeholder="请输入名字" id="studentName" class="form-control" style="width: 100px">
                                        </div>
                                        <div class="form-group " >
                                            <div class="input-group ">
                                                <select data-placeholder="选择班级..." class="form-control chosen-select " style="width:200px;" tabindex="2" id="class_search" name="classId">
                                                    <option value="">请选择班级</option>
                                                    <c:forEach items="${classes}" var="aClass">
                                                        <option value="${aClass.id}" hassubinfo="true">${aClass.gradeName}-${aClass.majorName}-${aClass.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <button class="btn btn-primary m-t-xs btn-lg" type="button" onclick="searchStudent()"><i class="fa fa-search"></i>&nbsp;&nbsp;搜索</button>
                                    </form>
                                </div>
                                <div class=" float-e-margins ">
                                    <form role="form" class="form-inline ">
                                        <div class=" row row-lg">
                                            <div class=" col-sm-1 m-t-sm">
                                                <span class="label label-success ">状态</span>
                                            </div>
                                            <div class="col-lg-10 m-t-xs m-l-n-xl" >
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'status')" data-status="">全部</button>
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'status')" data-status="1">正常</button>
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'status')" data-status="0">暂停</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="example">
                                    <div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
                                        <a data-toggle="modal" class="btn btn-outline btn-default" href="list.jsp#modal-form-save">
                                            <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                        </a>
                                        <button type="button" class="btn btn-outline btn-default" onclick="openUpdateStudentModal()">
                                            <i class="glyphicon glyphicon-pencil" aria-hidden="true"></i>
                                        </button>
                                        <button type="button" class="btn btn-outline btn-default" onclick="btchDeleteMajor()">
                                            <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                                        </button>
                                    </div>
                                    <table id="exampleTableEvents" data-height="400" data-mobile-responsive="true">
                                    </table>
                                </div>
                            </div>
                            <!-- End Example Events -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 结束 学生table -->
    </div>

    <!-- 添加学生modal -->
    <div id="modal-form-save" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="student-add-form">
                            <p>欢迎添加学生(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">学号：</label>
                                <div class="col-sm-6">
                                    <input type="text" placeholder="学号" class="form-control" name="studentId">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">姓名：</label>
                                <div class="col-sm-6">
                                    <input type="text" placeholder="姓名" class="form-control" name="name">
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">班级名称：</label>
                                <div class=" col-sm-6">
                                    <select data-placeholder="选择班级..." class="form-control chosen-select "  style="width:270px;" tabindex="4" id="class_add"  name="classId">
                                        <c:forEach items="${classes}" var="aClass">
                                            <option value="${aClass.id}" hassubinfo="true">${aClass.gradeName}-${aClass.majorName}-${aClass.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="saveStudent()">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改学生modal -->
    <div id="modal-form-update" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="student-update-form">
                            <input type="hidden" name="id">
                            <input type="hidden" name="_method" value="put">
                            <p>欢迎修改学生(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">学号：</label>
                                <div class="col-sm-6">
                                    <input type="text" placeholder="学号" class="form-control" name="studentId">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">名字：</label>
                                <div class="col-sm-6">
                                    <input type="text" placeholder="名字" class="form-control" name="name">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">密码：</label>
                                <div class="col-sm-6">
                                    <input type="password" class="form-control" name="password"  placeholder="密码" >
                                    <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 如需修改密码，请填写该项</span>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">班级名称：</label>
                                <div class=" col-sm-6">
                                    <select data-placeholder="选择班级..." class="form-control chosen-select "  style="width:270px;" tabindex="4" id="class_update"  name="classId">
                                        <c:forEach items="${classes}" var="aClass">
                                            <option value="${aClass.id}" hassubinfo="true">${aClass.gradeName}-${aClass.majorName}-${aClass.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">状态名称：</label>
                                <div class=" col-sm-6">
                                    <div class="radio i-checks">
                                            <input type="radio" value="1" name="status" > <i></i> 正常
                                            <input type="radio"  value="0" name="status"> <i></i> 暂停
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="updateStudent()">修改</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 全局js -->
    <script src="/js/jquery.min.js?v=2.1.4"></script>
    <script src="/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="/js/plugins/layer/layer.min.js"></script>
    <script src="/js/content.js"></script>

    <!-- Chosen -->
    <script src="/js/plugins/chosen/chosen.jquery.js"></script>

    <!-- 自定义js -->
    <script src="/js/examsystem/common.js"></script>

    <!-- Sweet alert -->
    <script src="/js/plugins/sweetalert/sweetalert.min.js"></script>

    <!-- iCheck -->
    <script src="/js/plugins/iCheck/icheck.min.js"></script>
    <script>
        $(document).ready(function () {
            $('.i-checks').iCheck({
                checkboxClass: 'icheckbox_square-green',
                radioClass: 'iradio_square-green',
            });
        });
    </script>

    <!-- Bootstrap table -->
    <script src="/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script src="/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>


    <script type="text/javascript">

        String.prototype.trim = function () {
            return this.replace(/(^[\s\n\t]+|[\s\n\t]+$)/g, "").replace(/\s/g,"");
        }

        //获取选中行的id
        function getSelectionsIds(sels){
            return $.map($('#exampleTableEvents').bootstrapTable('getSelections'), function (row) {
                return row.id
            });
        }

        //清空表单
        function clearForm(){
            $(':input',"#student-add-form").not(':button,:submit,:reset,:hidden').val('').removeAttr('checked').removeAttr('selected');
            $("#class_add").val("").trigger("chosen:updated");
        }
        function clearUpdateForm(){
            $(':input',"#sysuser-update-form").not(':button,:submit,:reset,:hidden').val('').removeAttr('checked').removeAttr('selected');
            $("#student-update-form input[type=password]").val("");
            $(".i-checks").iCheck("uncheck");
        }

        function searchStudent() {
            $("#exampleTableEvents").bootstrapTable('refresh');
        }

        function btchDeleteMajor(){
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("未选中专业!");
                return ;
            }

            var ids = getSelectionsIds(sels);
            swal({
                        title: "确定删除所选专业吗",
                        text: "删除后将无法恢复，请谨慎操作！",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "是的，我要删除！",
                        cancelButtonText: "让我再考虑一下…",
                        closeOnConfirm: false,
                        closeOnCancel: false
                    },
                    function (isConfirm) {
                        if (isConfirm) {
                            var params = {"ids":ids,_method:'delete'};
                            $.ajax({
                                type: "POST",
                                url: "/v1/major",
                                data: params,
                                success: function(data){
                                    if(data.status == 201){
                                        swal(data.message, "您已经永久删除了这些专业。", "success");
                                        $("#exampleTableEvents").bootstrapTable('refresh');
                                    }
                                    else{
                                        swal(data.message, "无法删除这些专业。", "error");
                                    }
                                },
                                error:function(XMLHttpRequest, textStatus, errorThrown){
                                    var status=XMLHttpRequest.status;
                                    if(status==403){
                                        to403();
                                    }else if(status==500){
                                        to500();
                                    }
                                }
                            });

                        } else {
                            swal("已取消", "您取消了删除操作！", "error");
                        }
                    });
        }

        function saveStudent(){
            //表单校验
            if($("#student-add-form [name=studentId]").val().trim()==''){
                layer.msg("学号不能为空!");
                return ;
            }
            if($("#student-add-form [name=name]").val().trim()==''){
                layer.msg("名字不能为空!");
                return ;
            }
            if($("#student-add-form [name=classId]").val()==null){
                layer.msg("班级不能为空!");
                return ;
            }

            //ajax的post方式提交表单
            //$("#itemAddForm").serialize()将表单序列号为key-value形式的字符串
            $.ajax({
                type: "POST",
                url: "/v1/student",
                data: decodeURIComponent($("#student-add-form").serialize().replace(/\+/g,"")),
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-save").modal('hide');
                        clearForm();
                        swal(data.message, "您已经永久添加了该学生。", "success");
                        $("#exampleTableEvents").bootstrapTable('refresh');
                    }
                    else{
                        swal("添加失败",data.message, "error");
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    var status=XMLHttpRequest.status;
                    if(status==403){
                        to403();
                    }else if(status==500){
                        to500();
                    }
                }
            });
        }

        function updateStudent(){

            //表单校验
            if($("#student-update-form [name=studentId]").val().trim()==''){
                layer.msg("学号不能为空!");
                return ;
            }
            if($("#student-update-form [name=name]").val().trim()==''){
                layer.msg("名字不能为空!");
                return ;
            }
            if($("#student-update-form [name=classId]").val()==null){
                layer.msg("班级不能为空!");
                return ;
            }


            var id=$("#student-update-form [name=id]").val();

            $.ajax({
                type: "POST",
                url: "/v1/student/"+id,
                data: decodeURIComponent($("#student-update-form").serialize().replace(/\+/g,"")),
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-update").modal('hide');
                        swal(data.message, "您已经成功修改了该学生。", "success");
                        $("#exampleTableEvents").bootstrapTable('refresh');
                    }
                    else{
                        swal("修改失败",data.message, "error");
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    var status=XMLHttpRequest.status;
                    if(status==403){
                        to403();
                    }else if(status==500){
                        to500();
                    }
                }
            });
        }

        function openUpdateStudentModal(){
            clearUpdateForm();

            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("必须选择一个学生才能修改!");
                return ;
            }else if(sels.length >1){
                layer.msg("只能选择一个学生!");
                return ;
            }

            var id=sels[0].id;
            var studentId=sels[0].studentId;
            var name=sels[0].name;
            var classId=sels[0].classId;
            var status=sels[0].status;

            $("#modal-form-update").modal('show');
            $("#student-update-form [name=id]").val(id);
            $("#student-update-form [name=studentId]").val(studentId);
            $("#student-update-form [name=name]").val(name);

            $("#student-update-form input[value='"+status+"']").iCheck('check');

            $("#class_update").val(classId).trigger("chosen:updated");
        }

        function searchData(e,data){
            var value=$(e).attr("data-"+data);
            $("#student-search-form [name='"+data+"']").val(value);
            $("#exampleTableEvents").bootstrapTable('refresh');
        }

        //获取查询参数
        function queryParams(params){
            var temp={
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                studentId:$("#studentId").val().trim(),
                name:$("#studentName").val().trim(),
                classId:$("#class_search").val(),
                status:$("#status").val()
            };
            return temp;
        }

        (function () {

            //构建bootstraptable
            $('#exampleTableEvents').bootstrapTable({
                url: "/v1/student",
                search: false,
                pagination: true,
                showRefresh: true,
                showToggle: true,
                showColumns: true,
                iconSize: 'outline',
                idField:'id',
                toolbar: '#exampleTableEventsToolbar',
                queryParams:queryParams,
                icons: {
                    refresh: 'glyphicon-repeat',
                    toggle: 'glyphicon-list-alt',
                    columns: 'glyphicon-list'
                },
                columns: [
                    {
                        field: 'id',
                        visible:false
                    },
                    {
                        field: 'state',
                        checkbox:true
                    },{
                        field : 'num',
                        title : '序号',
                        formatter : ES.showNumber
                    },{
                        field: 'studentId',
                        title: '学号'
                    },{
                        field: 'name',
                        title: '名字'
                    }, {
                        field: 'createdTime',
                        title: '创建时间',
                        formatter: ES.formatDateTime //自定义方法，格式化时间
                    }, {
                        field: 'updatedTime',
                        title: '更新时间',
                        formatter: ES.formatDateTime //自定义方法，格式化时间
                    }
                ]
            });

            $("#role_add").change(function () {
                var roles=$(this).val();

                var department=false;

                if(roles!=null){
                    $.each(roles,function (index,role) {
                        if($("#sysuser-add-form option[value='"+role+"']").text()=='教师'){
                            department=true;
                        }
                    });
                }else{
                    department=true;
                }

                if(department){
                    $("#department_add").removeAttr("disabled");
                    $("#department_add").trigger("chosen:updated");
                }
                else{
                    $("#department_add").attr("disabled",true);
                    $("#department_add").trigger("chosen:updated");
                }

            });

            $("#role_update").change(function () {
                var roles=$(this).val();

                var department=false;

                if(roles!=null){
                    $.each(roles,function (index,role) {
                        if($("#sysuser-update-form option[value='"+role+"']").text()=='教师'){
                            department=true;
                        }
                    });
                }else{
                    department=true;
                }

                if(department){
                    $("#department_update").removeAttr("disabled");
                    $("#department_update").trigger("chosen:updated");
                }
                else{
                    $("#department_update").attr("disabled",true);
                    $("#department_update").trigger("chosen:updated");
                }

            });
        })();

        $("#modal-form-save").on('shown.bs.modal',function () {
            $("#class_add_chosen").width(270);
        });
        $("#modal-form-update").on('shown.bs.modal',function () {
            $("#class_update_chosen").width(270);
        });
    </script>

    <!-- Peity -->
    <script type="text/javascript">
        var config = {
            '.chosen-select': {},
            '.chosen-select-deselect': {
                allow_single_deselect: true
            },
            '.chosen-select-no-single': {
                disable_search_threshold: 10
            },
            '.chosen-select-no-results': {
                no_results_text: 'Oops, nothing found!'
            },
            '.chosen-select-width': {
                width: "95%"
            }
        }
        for (var selector in config) {
            $(selector).chosen(config[selector]);
        }
    </script>

    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
    <!--统计代码，可删除-->

</body>

</html>
