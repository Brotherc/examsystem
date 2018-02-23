<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>考试</title>
    <meta name="keywords" content="在线考试系统,吉林大学珠海学院考试">
    <meta name="description" content="在线考试系统是一个服务于教师与学生的学习平台">

    <link rel="shortcut icon" href="favicon.ico"> <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/css/plugins/spinner/bootstrap-spinner.css" rel="stylesheet">
    <link href="/css/plugins/datetimepicker/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">

    <!-- Sweet Alert -->
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!--开始 考试table -->
        <div class="ibox float-e-margins">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>考试</h5>
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
                                <div class=" float-e-margins ">
                                    <form role="form" class="form-inline " id="exam-search-form">
                                        <input type="hidden" name="term" id="term" >
                                        <input type="hidden" name="status" id="status">
                                        <div class="form-group " >
                                            <div class="input-group ">
                                                <select data-placeholder="选择课程..." class="form-control chosen-select " style="width:200px;" tabindex="2" id="course" name="courseId">
                                                    <option value="">请选择课程</option>
                                                    <c:forEach items="${courses}" var="course">
                                                        <option value="${course.id}" hassubinfo="true">${course.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <div class="form-group " >
                                            <div class=" col-sm-6">
                                                <select data-placeholder="选择学年..." class="form-control chosen-select " style="width:200px;" tabindex="2" id="schoolYear"  name="schoolYearId">
                                                    <option value="">请选择学年</option>
                                                    <c:forEach items="${schoolYears}" var="schoolYear">
                                                        <option value="${schoolYear.id}" hassubinfo="true">${schoolYear.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <button class="btn btn-primary m-t-xs btn-lg" type="button" onclick="searchMajor()"><i class="fa fa-search"></i>&nbsp;&nbsp;搜索</button>
                                    </form>
                                </div>
                                <div class=" float-e-margins ">
                                    <form role="form" class="form-inline ">
                                        <div class=" row row-lg">
                                            <div class=" col-sm-1 m-t-sm">
                                                <span class="label label-success ">学期</span>
                                            </div>
                                            <div class="col-lg-10 m-t-xs m-l-n-xl" >
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'term')" data-term="">全部</button>
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'term')" data-term="1">上</button>
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'term')" data-term="0">下</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="ibox float-e-margins ">
                                    <form role="form" class="form-inline ">
                                        <div class=" row row-lg">
                                            <div class=" col-sm-1 m-t-sm">
                                                <span class="label label-success ">状态</span>
                                            </div>
                                            <div class=" col-lg-10 m-t-xs m-l-n-xl">
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'status')" data-status="">全部</button>
                                                <c:forEach items="${statuses}" var="status" >
                                                    <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'status')" data-status="${status.code}">${status.name}</button>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="example">
                                    <div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
                                        <a data-toggle="modal" class="btn btn-outline btn-default" href="list.jsp#modal-form-save">
                                            <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                        </a>
                                        <button type="button" class="btn btn-outline btn-default" onclick="openUpdateExamModal()">
                                            <i class="glyphicon glyphicon-pencil" aria-hidden="true"></i>
                                        </button>
                                        <button type="button" class="btn btn-outline btn-default" onclick="btchDeleteExam()">
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
        <!-- 结束 考试table -->
    </div>

    <!-- 添加考试modal -->
    <div id="modal-form-save" class="modal fade" aria-hidden="true" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body" >
                    <div class="row"  >
                        <form class="form-horizontal" id="exam-add-form" >
                            <p>欢迎添加考试(⊙o⊙)</p>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">课程名称：</label>
                                <div class=" col-sm-6">
                                    <select data-placeholder="选择课程..." class="form-control chosen-select " style="width:270px;" tabindex="2" id="course_add"   name="courseId">
                                        <c:forEach items="${courses}" var="course" >
                                            <option value="${course.id}" hassubinfo="true">${course.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label">开考时间：</label>
                                <div class="col-sm-6 " >
                                    <div class="input-group date"  >
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control"  name="startTime">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label">结束时间：</label>
                                <div class="col-sm-6 " >
                                    <div class="input-group date" >
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control"  name="endTime">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">试卷：</label>
                                <div class=" col-sm-6">
                                    <select data-placeholder="选择试卷..." class="form-control chosen-select " style="width:50px;" tabindex="2" id="testPaper_add"  name="testPaperId">
                                        <c:forEach items="${testPapers}" var="testPaper">
                                            <option value="${testPaper.id}" hassubinfo="true">${testPaper.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">学年：</label>
                                <div class=" col-sm-6">
                                    <select data-placeholder="选择学年..." class="form-control chosen-select " style="width:270px;" tabindex="2" id="schoolYear_add"  name="schoolYearId">
                                        <option value="">请选择学年</option>
                                        <c:forEach items="${schoolYears}" var="schoolYear">
                                            <option value="${schoolYear.id}" hassubinfo="true">${schoolYear.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">学期：</label>
                                <div class=" col-sm-6">
                                    <div class="radio i-checks">
                                        <input type="radio" value="1" name="term" > <i></i> 上
                                        <input type="radio"  value="0" name="term"> <i></i> 下
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">场次：</label>
                                <div class="col-sm-6 " >
                                    <div class="input-group spinner" data-trigger="spinner" >
                                        <input type="text" class="form-control" value="1" data-max="20" data-min="1" data-step="1" name="partNum" id="partNum_add">
                                        <span class="input-group-addon">
                                                <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
                                                <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
                                        </span>
                                    </div>
                                    <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 如果场次大于1，则时间间隔不能为0</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">场次间隔时间：</label>
                                <div class="col-sm-6 " >
                                    <div class="input-group spinner" data-trigger="spinner" >
                                        <input type="text" class="form-control" value="0" data-max="3600" data-min="0" data-step="1" name="intervalTime" id="intervalTime_add">
                                        <span class="input-group-addon">
                                                <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
                                                <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
                                            </span>
                                    </div>
                                    <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 时间间隔单位：秒；如果场次为1，则时间间隔为0</span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="saveExam()">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改考试modal -->
    <div id="modal-form-update" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="exam-update-form">
                            <input type="hidden" name="id">
                            <input type="hidden" name="_method" value="put">
                            <p>欢迎修改考试(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">名称：</label>
                                <div class="col-sm-6">
                                    <input type="text" placeholder="专业名称" class="form-control" name="name">
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

    <!-- Spinner -->
    <script src="/js/plugins/spinner/jquery.spinner.min.js"></script>

    <!-- iCheck -->
    <script src="/js/plugins/iCheck/icheck.min.js"></script>

    <!-- DateTime picker -->
    <script src="/js/plugins/datetimepicker/bootstrap-datetimepicker.min.js"></script>
    <script src="/js/plugins/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>

    <!-- Sweet alert -->
    <script src="/js/plugins/sweetalert/sweetalert.min.js"></script>

    <!-- Bootstrap table -->
    <script src="/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script src="/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>


    <script type="text/javascript">


        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });

        $("#course").val("${course.id}").trigger("chosen:updated");
        $("#course_add").val("${course.id}").trigger("chosen:updated");

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

        function saveExam(){
            //表单校验
            var startTime=$("#exam-add-form [name=startTime]").val();
            if(startTime.trim()==''){
                layer.msg("开考时间不能为空!");
                return ;
            }
            var endTime=$("#exam-add-form [name=endTime]").val();
            if(endTime.trim()==''){
                layer.msg("结束时间不能为空!");
                return ;
            }
            if($("#exam-add-form [name=schoolYearId]").val().trim()==''){
                layer.msg("学年不能为空!");
                return ;
            }
            if($("#partNum_add").val()>1&&$("#intervalTime_add").val()=="0"){
                layer.msg("时间间隔不能为空!");
                return ;
            }
            //ajax的post方式提交表单
            //$("#itemAddForm").serialize()将表单序列号为key-value形式的字符串

            $.ajax({
                type: "POST",
                url: "/v1/exam",
                data: $("#exam-add-form").serialize(),
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-save").modal('hide');
                        swal(data.message, "您已经永久添加了该考试。", "success");
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

        function searchData(e,data){
            var value=$(e).attr("data-"+data);
            $("#exam-search-form [name='"+data+"']").val(value);
            $("#exampleTableEvents").bootstrapTable('refresh');
        }

        //获取查询参数
        function queryParams(params){
            var temp={
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                courseId:$("#course").val().trim(),
                schoolYearId:$("#schoolYear").val(),
                term:$("#term").val().trim(),
                status:$("#status").val().trim()
            };
            return temp;
        }

        (function () {
            $('.input-group.date').datetimepicker({
                showSecond:true,
                showHoues:true,
                format:'yyyy-mm-dd hh:ii:ss',
                minuteStep:5,
                todayHighlight:true,
                changeYear:true,
                autoclose:true,
                language:'zh-CN',
                forceParse:true
            });

            //构建bootstraptable
            $('#exampleTableEvents').bootstrapTable({
                url: "/v1/exam",
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
                        field: 'courseName',
                        title: '名字'
                    },{
                        field: 'courseId',
                        visible:false
                    },{
                        field: 'schoolYearName',
                        title: '学年'
                    },{
                        field: 'term',
                        title: '学期',
                        formatter:ES.formatTerm
                    },{
                        field: 'statusName',
                        title: '状态'
                    }, {
                        field: 'startTime',
                        title: '开考时间',
                        formatter: ES.formatDateTime //自定义方法，格式化时间
                    }, {
                        field: 'endTime',
                        title: '结束时间',
                        formatter: ES.formatDateTime //自定义方法，格式化时间
                    }
                ]
            });

        })();

        $("#course_add").on('change',function () {
            $.ajax({
                type: "GET",
                url: "/v1/testPaper",
                data: {courseId:$(this).val()},
                success: function(data){
                    if(data.status == 200){
                        $("#testPaper_add").children().remove();

                        //构造
                        var testPapers=data.data;
                        var html="";
                        $.each(testPapers,function(index,testPaper){
                            html+='<option value="'+testPaper.id+'" hassubinfo="true">'+testPaper.name+'</option>';
                        });
                        $("#testPaper_add").append(html);
                        $("#testPaper_add").trigger("chosen:updated");
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
        });

        $("#modal-form-save").on('shown.bs.modal',function () {
            $("#course_add_chosen").width(270);
            $("#testPaper_add_chosen").width(270);
            $("#schoolYear_add_chosen").width(270);

            $(".i-checks").iCheck("uncheck");
            $("#exam-add-form input[value='1']").iCheck('check');
            $("#partNum_add").val(1);
            $("#intervalTime_add").val(0);
            $(".input-group.date").val("");
            $("#schoolYear_add").val("").trigger("chosen:updated");
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
