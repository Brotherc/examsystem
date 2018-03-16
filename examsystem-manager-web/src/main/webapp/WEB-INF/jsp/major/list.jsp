<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>专业</title>
    <meta name="keywords" content="在线考试系统,吉林大学珠海学院考试">
    <meta name="description" content="在线考试系统是一个服务于教师与学生的学习平台">

    <link rel="shortcut icon" href="favicon.ico"> <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">

    <!-- Sweet Alert -->
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">



</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!--开始 专业table -->
        <div class="ibox float-e-margins">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>专业</h5>
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
                                    <form role="form" class="form-inline ">
                                        <div class="form-group m-l-none" >
                                            <label for="name" class="sr-only">名字</label>
                                            <input type="text" placeholder="请输入名字" id="name" class="form-control">
                                        </div>
                                        <div class="form-group " >
                                            <div class="input-group ">
                                                <select data-placeholder="选择系..." class="form-control chosen-select " style="width:200px;" tabindex="2" id="department" name="departmentId">
                                                    <option value="">请选择系</option>
                                                    <c:forEach items="${departments}" var="department">
                                                        <option value="${department.id}" hassubinfo="true">${department.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <button class="btn btn-primary m-t-xs btn-lg" type="button" onclick="searchMajor()"><i class="fa fa-search"></i>&nbsp;&nbsp;搜索</button>
                                    </form>
                                </div>
                                <div class="example">
                                    <div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
                                        <a data-toggle="modal" class="btn btn-outline btn-default" href="list.jsp#modal-form-save">
                                            <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                        </a>
                                        <button type="button" class="btn btn-outline btn-default" onclick="openUpdateMajorModal()">
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
        <!-- 结束 专业table -->
    </div>

    <!-- 添加专业modal -->
    <div id="modal-form-save" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="major-add-form">
                            <p>欢迎添加专业(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">名称：</label>
                                <div class="col-sm-6">
                                    <input type="text" placeholder="专业名称" maxlength="32" class="form-control" name="name">
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">系名称：</label>
                                <div class=" col-sm-6">
                                    <select data-placeholder="选择系..." class="form-control chosen-select " style="width:50px;" tabindex="2" id="major_add"  name="departmentId">
                                        <option value="">请选择系</option>
                                        <c:forEach items="${departments}" var="department">
                                            <option value="${department.id}" hassubinfo="true">${department.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="saveMajor()">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改专业modal -->
    <div id="modal-form-update" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="major-update-form">
                            <input type="hidden" name="id">
                            <input type="hidden" name="_method" value="put">
                            <p>欢迎修改专业(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">名称：</label>
                                <div class="col-sm-6">
                                    <input type="text" placeholder="专业名称" maxlength="32" class="form-control" name="name">
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">系名称：</label>
                                <div class=" col-sm-6">
                                    <select data-placeholder="选择系..." class="form-control chosen-select " style="width:50px;" tabindex="2" id="major_update"  name="departmentId">
                                        <option value="">请选择系</option>
                                        <c:forEach items="${departments}" var="department">
                                            <option value="${department.id}" hassubinfo="true">${department.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="updateMajor()">修改</button>
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
            $(':input',"#major-add-form").not(':button,:submit,:reset,:hidden').val('').removeAttr('checked').removeAttr('selected');
            $("#major_add").val("").trigger("chosen:updated");
        }

        function searchMajor() {
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

        function saveMajor(){
            //表单校验
            if($("#major-add-form [name=name]").val().trim()==''){
                layer.msg("名字不能为空!");
                return ;
            }
            if($("#major-add-form [name=departmentId]").val().trim()==''){
                layer.msg("系名字不能为空!");
                return ;
            }
            //ajax的post方式提交表单
            //$("#itemAddForm").serialize()将表单序列号为key-value形式的字符串
            $.ajax({
                type: "POST",
                url: "/v1/major",
                data: decodeURIComponent($("#major-add-form").serialize().replace(/\+/g,"")),
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-save").modal('hide');
                        clearForm();
                        swal(data.message, "您已经永久添加了该专业。", "success");
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

        function updateMajor(){
            //表单校验
            if($("#major-update-form [name=name]").val().trim()==''){
                layer.msg("名字不能为空!");
                return ;
            }
            if($("#major-update-form [name=departmentId]").val().trim()==''){
                layer.msg("系名字不能为空!");
                return ;
            }

            var id=$("#major-update-form [name=id]").val();

            $.ajax({
                type: "POST",
                url: "/v1/major/"+id,
                data: decodeURIComponent($("#major-update-form").serialize().replace(/\+/g,"")),
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-update").modal('hide');
                        swal(data.message, "您已经成功修改了该专业。", "success");
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

        function openUpdateMajorModal(){
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("必须选择一个专业才能修改!");
                return ;
            }else if(sels.length >1){
                layer.msg("只能选择一个专业!");
                return ;
            }
            var name=sels[0].name;
            var id=sels[0].id;
            var departmentId=sels[0].departmentId;

            $("#modal-form-update").modal('show');
            $("#major-update-form [name=name]").val(name);
            $("#major-update-form [name=id]").val(id);
            $("#major_update").val(departmentId).trigger("chosen:updated");
        }

        //获取查询参数
        function queryParams(params){
            var temp={
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                name:$("#name").val().trim(),
                departmentId:$("#department").val()
            };
            return temp;
        }

        (function () {
            //构建bootstraptable
            $('#exampleTableEvents').bootstrapTable({
                url: "/v1/major",
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
                        field: 'name',
                        title: '名字'
                    },{
                        field: 'departmentId',
                        visible:false
                    },{
                        field: 'departmentName',
                        title: '系名字'
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

        })();

        $("#modal-form-save").on('shown.bs.modal',function () {
            $("#major_add_chosen").width(270);
        });
        $("#modal-form-update").on('shown.bs.modal',function () {
            $("#major_update_chosen").width(270);
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
