<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>组卷</title>
    <meta name="keywords" content="在线考试系统,吉林大学珠海学院考试">
    <meta name="description" content="在线考试系统是一个服务于教师与学生的学习平台">

    <link rel="shortcut icon" href="favicon.ico"> <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/plugins/jsTree/style.min.css" rel="stylesheet">
    <link href="/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="/css/plugins/iCheck/custom.css" rel="stylesheet">
    <link href="/css/plugins/jasny/jasny-bootstrap.min.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">

    <!-- Sweet Alert -->
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">



</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!--开始 题目table -->
        <div class="ibox float-e-margins">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>组卷</h5>
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
                                    <form role="form" class="form-inline " id="question-search-form">
                                        <input type="hidden" name="difficulty" id="difficulty">
                                        <input type="hidden" name="isChecked" id="isChecked">
                                        <input type="hidden" name="knowledgePointId" id="knowledgePointId">
                                        <div class="form-group " >
                                                <select data-placeholder="选择课程..." class="form-control chosen-select " style="width:150px;" tabindex="2" id="course" name="courseId">
                                                    <c:forEach items="${courses}" var="course" >
                                                        <option value="${course.id}" hassubinfo="true">${course.name}</option>
                                                    </c:forEach>
                                                </select>
                                        </div>
                                        <button class="btn btn-primary m-t-xs btn-lg" type="button" onclick="searchQuestion()"><i class="fa fa-search"></i>&nbsp;&nbsp;搜索</button>
                                    </form>
                                </div>
                                <div class="ibox float-e-margins ">
                                    <form role="form" class="form-inline ">
                                        <div class="form-group">
                                            <div class=" col-lg-2 m-t-sm">
                                                <span class="label label-success ">难度</span>
                                            </div>
                                            <div class=" col-lg-10 m-t-xs" >
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'difficulty')" data-difficulty="">全部</button>
                                                <c:forEach items="${difficultys}" var="difficulty" >
                                                    <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'difficulty')" data-difficulty="${difficulty.code}">${difficulty.name}</button>
                                                </c:forEach>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <!-- End Example Events -->
                        </div>
                    </div>
                    <div class="row row-lg">
                        <div  class="col-sm-3 m-t-sm" style="overflow-x: auto">
                                <div id="jstree1"></div>
                        </div>
                        <div class="col-sm-9 ">
                            <h4 class="example-title">单选题</h4>
                            <table id="singleChoiceTableEvents" data-height="400" data-mobile-responsive="true">
                            </table>
                            <h4 class="example-title">判断题</h4>
                            <table id="trueOfFalseTableEvents" data-height="400" data-mobile-responsive="true">
                            </table>
                            <h4 class="example-title">填空题</h4>
                            <table id="fillInBlankTableEvents" data-height="400" data-mobile-responsive="true">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 结束 题目table -->
    </div>

    <!-- 添加试卷modal -->
    <div id="modal-form-save" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="testPaper-add-form">
                            <p>欢迎添加试卷(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">名称：</label>
                                <div class="col-sm-6">
                                    <input type="text" placeholder="试卷名称" id="name_add" class="form-control" name="name">
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">课程名称：</label>
                                <div class=" col-sm-6">
                                    <select data-placeholder="选择课程..." class="form-control chosen-select " style="width:50px;" tabindex="2" id="course_add"  name="courseId">
                                        <option value="">请选择课程</option>
                                        <c:forEach items="${courses}" var="course">
                                            <option value="${course.id}" hassubinfo="true">${course.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">学年：</label>
                                <div class=" col-sm-6">
                                    <select data-placeholder="选择学年..." class="form-control chosen-select " style="width:50px;" tabindex="2" id="schoolYear_add"  name="schoolYearId">
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
                            <div class="form-group " id="singleChoiceQuestion">
                                <label class="col-sm-3 control-label">单选题：</label>
                                <div class=" col-sm-6">
                                    <input type="text" class="form-control" data-mask="9.9" placeholder="" name="singleChoiceScore">
                                    <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 每道题分数</span>
                                </div>
                            </div>
                            <div class="form-group " id="trueOrFalseQuestion">
                                <label class="col-sm-3 control-label">判断题：</label>
                                <div class=" col-sm-6">
                                    <input type="text" class="form-control" data-mask="9.9" placeholder="" name="trueOrFalseScore">
                                    <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 每道题分数</span>
                                </div>
                            </div>
                            <div class="form-group " id="fillInBlankQuestion">
                                <label class="col-sm-3 control-label">填空题：</label>
                                <div class=" col-sm-6">
                                    <input type="text" class="form-control" data-mask="9.9" placeholder="" name="fillInBlankScore">
                                    <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 每个空分数</span>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">总分：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" name="score" id="score_add">0</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="saveTestPaper()">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!--mini聊天窗口开始-->
    <div class="small-chat-box fadeInRight animated">

        <div class="heading" draggable="true">
            <small class="chat-date pull-right">
            </small> 试题篮
        </div>

        <div class="content">
            <div class="row">
                <div class="col-sm-12">
                    <div class="tabs-container">
                        <ul class="nav nav-tabs">
                            <c:forEach items="${questionTypes}" var="questionType" varStatus="vs">
                                <c:choose>
                                    <c:when test="${vs.first }">
                                        <li class="active"><a data-toggle="tab" href="#tab-${vs.count}" aria-expanded="true"> ${questionType.name}</a>
                                        </li>
                                    </c:when>
                                    <c:otherwise>
                                        <li class=""><a data-toggle="tab" href="#tab-${vs.count}" aria-expanded="false">${questionType.name}</a>
                                        </li>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </ul>
                        <div class="tab-content" id="questionQube">
                            <c:forEach items="${questionTypes}" var="questionType" varStatus="vs">
                                <c:choose>
                                    <c:when test="${vs.first }">
                                        <div id="tab-${vs.count}" class="tab-pane active">
                                            <div class="panel-body">
                                                <div class="row">
                                                    <div class="col-lg-12" id="question-${questionType.code}">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div id="tab-${vs.count}" class="tab-pane">
                                            <div class="panel-body">
                                                <div class="row">
                                                    <div class="col-lg-12" id="question-${questionType.code}">
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-chat">

            <div class="btn-group hidden-xs" role="group">
                <button type="button" class="btn btn-outline btn-default" onclick="addQuestion()">
                    <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                    组卷
                </button>
                <button type="button" class="btn btn-outline btn-default" onclick="removeQuestion()">
                    <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                    移除
                </button>
                <button type="button" class="btn btn-outline btn-default" onclick="upQuestion()">
                    <i class="glyphicon glyphicon-menu-up" aria-hidden="true"></i>
                    上移
                </button>
                <button type="button" class="btn btn-outline btn-default" onclick="downQuestion()">
                    <i class="glyphicon glyphicon-menu-down" aria-hidden="true"></i>
                    下移
                </button>
            </div>
        </div>
    </div>
    <div id="small-chat">
        <span class="badge badge-warning pull-right" value="0">0</span>
        <a class="open-small-chat">
            <i class="fa fa-cube"></i>
        </a>
    </div>
    <!--mini聊天窗口结束-->

    <!-- 查看详情题目（填空题）modal -->
    <div id="modal-form-details-special" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="question-details-special-form">
                            <p>欢迎查看该题目(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">课程：</label>
                                <div class="col-sm-6">
                                    <p class="form-control-static" id="course_details_special"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">难度：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="difficulty_details_special"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">创建题目教师名称：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="created_teacher_details_special"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">审核：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="is_checked_details_special"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">答案匹配模式：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="matcher_details_special"></p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 查看详情题目（非填空题）modal -->
    <div id="modal-form-details-general" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="question-details-general-form">
                            <p>欢迎查看该题目(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">课程：</label>
                                <div class="col-sm-6">
                                    <p class="form-control-static" id="course_details_general"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">难度：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="difficulty_details_general"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">创建题目教师名称：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="created_teacher_details_general"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">审核：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="is_checked_details_general"></p>
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

    <script src="/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>

    <!-- iCheck -->
    <script src="/js/plugins/iCheck/icheck.min.js"></script>

    <!-- Input Mask-->
    <script src="/js/plugins/jasny/jasny-bootstrap.min.js"></script>

    <!-- 自定义js -->
    <script src="/js/examsystem/common.js"></script>

    <!-- jsTree plugin javascript -->
    <script src="/js/plugins/jsTree/jstree.min.js"></script>
    <style>
        .jstree-open > .jstree-anchor > .fa-folder:before {
            content: "\f07c";
        }
        .jstree-default .jstree-icon.none {
            width: 0;
        }
        .jstree-default-contextmenu{
            z-index: 200;
        }
    </style>

    <script type="text/javascript">

        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });
        $("#course").val("${course.id}").trigger("chosen:updated");

        $.ajax({
            type: "GET",
            url: "/v1/knowledgePoint/course/"+"${course.id}",
            data: null,
            success: function(data){
                if(data.status == 200){
                    $('#jstree1').jstree({
                        'core': {
                            'data': data.data
                        },
                        'types': {
                            '#' : {
                                "max_depth" : 3,
                                'icon': 'fa fa-tag'
                            },
                            'default': {
                                'icon': 'fa fa-tag'
                            }
                        },
                        'plugins': ['types']
                    });
                    $('#jstree1').on("changed.jstree", function (e, data) {
                        if(data.node.type!="#"){
                            $("#knowledgePointId").val(data.node.id);
                        }
                        else{
                            $("#knowledgePointId").val("");
                        }
                        $("#fillInBlankTableEvents").bootstrapTable('refresh');
                        $("#singleChoiceTableEvents").bootstrapTable('refresh');
                        $("#trueOfFalseTableEvents").bootstrapTable('refresh');
                    });
                }
                else{
                    swal("加载知识点失败",data.message, "error");
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

    </script>

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

        function searchQuestion() {
            var courseId=$("#course").val();

            $.ajax({
                type: "GET",
                url: "/v1/knowledgePoint/course/"+courseId,
                data: null,
                success: function(data){
                    if(data.status == 200){
                        $('#jstree1').jstree("destroy");
                        $('#jstree1').jstree({
                            'core': {
                                'data': data.data
                            },
                            'types': {
                                '#' : {
                                    "max_depth" : 3,
                                    'icon': 'fa fa-tag'
                                },
                                'default': {
                                    'icon': 'fa fa-tag'
                                }
                            },
                            'plugins': ['types']
                        });
                        $('#jstree1').on("changed.jstree", function (e, data) {
                            if(data.node.type!="#"){
                                $("#knowledgePointId").val(data.node.id);
                            }
                            else{
                                $("#knowledgePointId").val("");
                            }
                            $("#fillInBlankTableEvents").bootstrapTable('refresh');
                            $("#singleChoiceTableEvents").bootstrapTable('refresh');
                            $("#trueOfFalseTableEvents").bootstrapTable('refresh');
                        });
                    }
                    else{
                        swal("加载知识点失败",data.message, "error");
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

            $("#fillInBlankTableEvents").bootstrapTable('refresh');
            $("#singleChoiceTableEvents").bootstrapTable('refresh');
            $("#trueOfFalseTableEvents").bootstrapTable('refresh');
        }

        function saveTestPaper(){
            //非空校验
            if($("#testPaper-add-form [name=name]").val().trim()==''){
                layer.msg("试卷名字不能为空!");
                return ;
            }
            if($("#testPaper-add-form [name=courseId]").val().trim()==""){
                layer.msg("课程名字不能为空!");
                return ;
            }
            if($("#testPaper-add-form [name=schoolYearId]").val().trim()==""){
                layer.msg("学年名字不能为空!");
                return ;
            }
            var singleChoiceQuestions=$("#question-0 .i-checks");
            var trueOrFalseQuestions=$("#question-1 .i-checks");
            var fillInBlankQuestions=$("#question-2 .i-checks");

            var singleChoiceScore;
            if(singleChoiceQuestions.length!=0){
                singleChoiceScore=$("#testPaper-add-form [name=singleChoiceScore]").val();
                if(singleChoiceScore==""||singleChoiceScore=="0.0"){
                    layer.msg("分数不能为空或0!");
                    return ;
                }
            }

            var trueOrFalseScore;
            if(trueOrFalseQuestions.length!=0){
                trueOrFalseScore=$("#testPaper-add-form [name=trueOrFalseScore]").val();
                if(trueOrFalseScore==""||trueOrFalseScore=="0.0"){
                    layer.msg("分数不能为空或0!");
                    return ;
                }
            }

            var fillInBlankScore;
            if(fillInBlankQuestions.length!=0){
                fillInBlankScore=$("#testPaper-add-form [name=fillInBlankScore]").val();
                if(fillInBlankScore==""||fillInBlankScore=="0.0"){
                    layer.msg("分数不能为空或0!");
                    return ;
                }
            }

            //构造请求参数
            //添加试卷

            var name=$("#name_add").val();
            var score=$("#score_add").text();
            var courseId=$("#course_add").val();
            var schoolYearId=$("#schoolYear_add").val();
            var term=$('input:radio:checked').val();

            var param={};
            param.name=name;
            param.score=score;
            param.courseId=courseId;
            param.schoolYearId=schoolYearId;
            param.term=term;

            if(singleChoiceQuestions.length!=0){
                var ids="";
                $.each(singleChoiceQuestions,function(index,question){
                    var id=$(question).val();
                    if(index==0)
                        ids+=id;
                    else ids+=","+id;
                });
                param.singleChoiceQuestionIds=ids;
                param.singleChoiceQuestionScore=singleChoiceScore;
            }
            if(trueOrFalseQuestions.length!=0){
                var ids="";
                $.each(trueOrFalseQuestions,function(index,question){
                    var id=$(question).val();
                    if(index==0)
                        ids+=id;
                    else ids+=","+id;
                });
                param.trueOrFalseQuestionIds=ids;
                param.trueOrFalseQuestionScore=trueOrFalseScore;
            }
            if(fillInBlankQuestions.length!=0){
                var ids="";
                $.each(fillInBlankQuestions,function(index,question){
                    var id=$(question).val();
                    if(index==0)
                        ids+=id;
                    else ids+=","+id;
                });
                param.fillInBlankQuestionIds=ids;
                param.fillInBlankQuestionScore=fillInBlankScore;
            }

            $.ajax({
                type: "POST",
                url: "/v1/testPaper",
                data: param,
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-save").modal('hide');
                        swal(data.message, "您已经永久添加了该试卷。", "success");

                        //清空试题篮
                        $("#question-0").children().remove();
                        $("#question-1").children().remove();
                        $("#question-2").children().remove();
                        $("#small-chat span").text(0);
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

        function addQuestion(){
            var singleChoiceQuestions=$("#question-0").children();
            var trueOrFalseQuestions=$("#question-1").children();
            var fillInBlankQuestions=$("#question-2").children();
            //如果试题篮中没有试题，则不允许组卷
            if(singleChoiceQuestions.length==0&&trueOrFalseQuestions.length==0&&fillInBlankQuestions.length==0){
                layer.msg("试题篮中没有试题，请先选题");
                return;
            }

            //清空添加模态框内容，添加模态框显示，关闭试题篮
            $("#name_add").val("");
            $("#score_add").text(0);
            $("#course_add").val("").trigger("chosen:updated");
            $("#schoolYear_add").val("").trigger("chosen:updated");
            $(".i-checks").iCheck("uncheck");
            $("#testPaper-add-form input[value='1']").iCheck('check');

            $("#course_add_chosen").width(270);
            $("#schoolYear_add_chosen").width(270);

            $("#singleChoiceQuestion").show();
            $("#trueOrFalseQuestion").show();
            $("#fillInBlankQuestion").show();
            $("#testPaper-add-form input[name='singleChoiceScore']").val("0.0");
            $("#testPaper-add-form input[name='trueOrFalseScore']").val("0.0");
            $("#testPaper-add-form input[name='fillInBlankScore']").val("0.0");

            //根据用户试题篮所选题型，生成对应题型分数的录入框

            if(singleChoiceQuestions.length==0)
                $("#singleChoiceQuestion").hide();

            if(trueOrFalseQuestions.length==0)
                $("#trueOrFalseQuestion").hide();

            if(fillInBlankQuestions.length==0)
                $("#fillInBlankQuestion").hide();



            $("#modal-form-save").modal('show');

            $('.open-small-chat').trigger("click");
        }

        function removeQuestion(){
            var checkedQuestions=$("#questionQube .active .checked");
            if(checkedQuestions.length==0){
                layer.msg("请选择题目，再进行移除");
                return;
            }

            $.each(checkedQuestions,function (index,question) {
                $(question).parent().parent().remove();

                var num=$("#small-chat span").text();
                $("#small-chat span").text(parseInt(num)-1);
            });
        }

        function upQuestion(){
            var checkedQuestions=$("#questionQube .active .checked");
            if(checkedQuestions.length==0){
                layer.msg("请选择题目再进行移动");
                return;
            }
            if(checkedQuestions.length>1){
                layer.msg("只允许移动一个题目");
                return;
            }

            var checkedQuestion=$(checkedQuestions[0]).parent().parent()[0];
            console.log(checkedQuestion);
            var questions=$("#questionQube .active .ibox");
            $.each(questions,function (index,question) {

                console.log($(question));
                if($(question)[0]==checkedQuestion){
                    if(index==0){
                        layer.msg("该题目已在最前，无法移动");
                        return;
                    }
                    else{
                        //上移
                        var pre=$(question).prev()[0];
                        $(question).after(pre);
                    }
                }
            });
        }

        function downQuestion(){
            var checkedQuestions=$("#questionQube .active .checked");
            if(checkedQuestions.length==0){
                layer.msg("请选择题目再进行移动");
                return;
            }
            if(checkedQuestions.length>1){
                layer.msg("只允许移动一个题目");
                return;
            }

            var checkedQuestion=$(checkedQuestions[0]).parent().parent()[0];
            console.log(checkedQuestion);
            var questions=$("#questionQube .active .ibox");
            $.each(questions,function (index,question) {

                console.log($(question));
                if($(question)[0]==checkedQuestion){
                    if(index==questions.length-1){
                        layer.msg("该题目已在最后，无法移动");
                        return;
                    }
                    else{
                        //下移
                        var next=$(question).next()[0];
                        $(question).before(next);
                    }
                }
            });
        }

        function searchData(e,data){
            var value=$(e).attr("data-"+data);
            $("#question-search-form [name='"+data+"']").val(value);
            $("#fillInBlankTableEvents").bootstrapTable('refresh');
            $("#singleChoiceTableEvents").bootstrapTable('refresh');
            $("#trueOfFalseTableEvents").bootstrapTable('refresh');
        }

        //获取查询参数
        function queryParams(params){
            var temp={
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                difficulty:$("#difficulty").val(),
                isChecked:true,
                courseId:$("#course").val(),
                knowledgePointId:$("#knowledgePointId").val()
            };
            return temp;
        }

        function addDetailsButton(value,row,index){
            return ['<button class="btn btn-outline btn-info" type="button" id="questionDetails">查看</button>',
                '<button class="btn btn-outline btn-success m-l-xs" type="button" id="chooseQuestion">选题</button>'].join("");
        }

        window.operateEvents={
            "click #questionDetails":function (e,value,row,index) {

                var type=row.type;

                var url="/v1/";
                if(type==0)
                    url+="singleChoiceQuestion/";
                else if(type==1)
                    url+="trueOrFalseQuestion/";
                else if(type==2)
                    url+="fillInBlankQuestion/";
                //发送ajax请求题目详情
                $.ajax({
                    type: "GET",
                    url: url+row.id,
                    data: null,
                    success: function(data){
                        if(data.status == 200){

                            //赋值
                            var questionDetails=data.data;

                            if(type==2){//填空题
                                $("#course_details_special").text(questionDetails.courseName);
                                $("#difficulty_details_special").text(questionDetails.difficultyName);
                                $("#created_teacher_details_special").text(questionDetails.createdTeacher.sysuserId+"-"+questionDetails.createdTeacher.name);
                                if(questionDetails.isChecked=='1'){
                                    $("#is_checked_details_special").text("通过");
                                }else{
                                    $("#is_checked_details_special").text("待通过");
                                }
                                var matcherDetails="";
                                var matcherNames=questionDetails.matcherNames;
                                if(matcherNames!=null){

                                    $.each(matcherNames,function (index,matcherName) {
                                        if(index!=matcherNames.length-1){
                                            matcherDetails+=matcherName+",";
                                        }
                                        else{
                                            matcherDetails+=matcherName;
                                        }
                                    });
                                    $("#matcher_details_special").text(matcherDetails);
                                }
                                else{
                                    $("#matcher_details_special").text("-");
                                }

                                $("#question-details-special-form .blank").remove();

                                var answerObj=JSON.parse(questionDetails.answer);
                                var blankNum=questionDetails.blankNum;
                                for(var i=1;i<=blankNum;i++){
                                    var text=answerObj[i];
                                    var html='<div class="form-group blank"> <label class="col-sm-3 control-label ">空格'+i+'：</label> <div class="col-sm-6"> <p class="form-control-static" id="question_details_special">'+text+'</p> </div> </div>';
                                    $("#question-details-special-form").append(html);
                                }

                                $("#modal-form-details-special").modal('show');

                            }else{//非填空题
                                $("#course_details_general").text(questionDetails.courseName);
                                $("#difficulty_details_general").text(questionDetails.difficultyName);
                                $("#created_teacher_details_general").text(questionDetails.createdTeacher.sysuserId+"-"+questionDetails.createdTeacher.name);
                                if(questionDetails.isChecked=='1'){
                                    $("#is_checked_details_general").text("通过");
                                }else{
                                    $("#is_checked_details_general").text("待通过");
                                }
                                $("#modal-form-details-general").modal('show');
                            }
                        }
                        else{
                            swal("查看失败",data.message, "error");
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

            },
            "click #chooseQuestion":function (e,value,row,index) {
                var type=row.type;
                var content=row.content;
                var id=row.id;

                var repate=false;
                var ids=$("#question-"+type+" input[type='checkbox']");

                $.each(ids,function (index,_id) {
                   if(id==$(_id).attr("value")){
                       repate=true;
                       return ;
                   }
                });

                if(repate){
                    layer.msg("该题目已在试题篮中，无需添加!");
                    return;
                }

                var url="/v1/";
                if(type==0)
                    url+="singleChoiceQuestion/";
                else if(type==1)
                    url+="trueOrFalseQuestion/";
                else if(type==2)
                    url+="fillInBlankQuestion/";
                //发送ajax请求题目详情
                $.ajax({
                    type: "GET",
                    url: url+row.id,
                    data: null,
                    success: function(data){
                        if(data.status == 200){

                            //赋值
                            var questionDetails=data.data;

                            if(type==0){//选择题
                                var difficultyName=questionDetails.difficultyName;
                                var optionA=questionDetails.optionA;
                                var optionB=questionDetails.optionB;
                                var optionC=questionDetails.optionC;
                                var optionD=questionDetails.optionD;

                                var html='<div class="ibox"><div class="ibox-content"><input type="checkbox" value="'+id+'" name="" class="i-checks" />';

                                html+='<p>'+content+'</p><p>A:'+optionA+'</p><p>B:'+optionB+'</p><p>C:'+optionC+'</p><p>D:'+optionD+'</p>';
                                html+='<div class="row"><div class="col-md-6"><h5>知识点：</h5>';
                                if(questionDetails.knowledgePoints!=null){
                                    $.each(questionDetails.knowledgePoints,function (index,knowledgePoint) {
                                        if(index==0)
                                            html+='<button class="btn btn-success btn-xs" type="button">'+knowledgePoint+'</button>';
                                        else
                                            html+='<button class="btn btn-white btn-xs" type="button">'+knowledgePoint+'</button>';
                                    });
                                }
                                html+='</div><div class="col-md-6"><div class="small text-right"><h5>难度：</h5><div><i class="fa fa-flash"> </i>'+difficultyName+'</div>';
                                html+='</div></div></div>';

                                $("#question-"+type).append(html);



                                var num=$("#small-chat span").text();
                                $("#small-chat span").text(parseInt(num)+1);

                            }else{//非选择题
                                var difficultyName=questionDetails.difficultyName;

                                var html="";

                                if(type==2){//填空题
                                    var blank=questionDetails.blankNum;
                                    html='<div class="ibox"><div class="ibox-content"><input type="checkbox" value="'+id+'" data-blank="'+blank+'" name="" class="i-checks" />';
                                }else{
                                    html='<div class="ibox"><div class="ibox-content"><input type="checkbox" value="'+id+'"  name="" class="i-checks" />';
                                }

                                html+='<p>'+content+'</p>';
                                html+='<div class="row"><div class="col-md-6"><h5>知识点：</h5>';

                                var knowledgePoints=questionDetails.knowledgePoints;

                                if(knowledgePoints!=null){
                                    $.each(knowledgePoints,function (index,knowledgePoint) {
                                        if(index==0)
                                            html+='<button class="btn btn-success btn-xs" type="button">'+knowledgePoint+'</button>';
                                        else
                                            html+='<button class="btn btn-white btn-xs" type="button">'+knowledgePoint+'</button>';
                                    });
                                }

                                html+='</div><div class="col-md-6"><div class="small text-right"><h5>难度：</h5><div><i class="fa fa-flash"> </i>'+difficultyName+'</div>';
                                html+='</div></div></div>';

                                $("#question-"+type).append(html);

                                var num=$("#small-chat span").text();
                                $("#small-chat span").text(parseInt(num)+1);
                            }
                            $('.i-checks').iCheck({
                                checkboxClass: 'icheckbox_square-green',
                                radioClass: 'iradio_square-green',
                            });
                        }
                        else{
                            swal("查看失败",data.message, "error");
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
        };

        (function () {


            $("#testPaper-add-form input[name='singleChoiceScore']").change(function () {

                var trueOrFalseQuestions=$("#question-1").children().length;
                var fillInBlankQuestions=$("#question-2 .i-checks");
                var singleChoiceQuestions=$("#question-0").children().length;

                var singleChoiceScore=$(this).val();
                var fillInBlankScore=$("#testPaper-add-form input[name='fillInBlankScore']").val();
                var trueOrFalseScore=$("#testPaper-add-form input[name='trueOrFalseScore']").val();
                var fillInBlankSumScore=0;

                $.each(fillInBlankQuestions,function(index,question){
                    fillInBlankSumScore+=parseInt($(question).attr("data-blank"))*fillInBlankScore;
                });

                if(singleChoiceScore!=""){
                    $("#score_add").text(trueOrFalseQuestions*parseFloat(trueOrFalseScore)+singleChoiceQuestions*parseFloat(singleChoiceScore)+fillInBlankSumScore);
                }
                else{
                    $("#score_add").text(trueOrFalseQuestions*parseFloat(trueOrFalseScore)+fillInBlankSumScore);
                }
            });
            $("#testPaper-add-form input[name='trueOrFalseScore']").change(function () {
                var trueOrFalseQuestions=$("#question-1").children().length;
                var fillInBlankQuestions=$("#question-2 .i-checks");
                var singleChoiceQuestions=$("#question-0").children().length;

                var trueOrFalseScore=$(this).val();
                var fillInBlankScore=$("#testPaper-add-form input[name='fillInBlankScore']").val();
                var singleChoiceScore=$("#testPaper-add-form input[name='singleChoiceScore']").val();
                var fillInBlankSumScore=0;

                $.each(fillInBlankQuestions,function(index,question){
                    fillInBlankSumScore+=parseInt($(question).attr("data-blank"))*fillInBlankScore;
                });

                if(trueOrFalseScore!=""){
                    $("#score_add").text(trueOrFalseQuestions*parseFloat(trueOrFalseScore)+singleChoiceQuestions*parseFloat(singleChoiceScore)+fillInBlankSumScore);
                }
                else{
                    $("#score_add").text(singleChoiceQuestions*parseFloat(singleChoiceScore)+fillInBlankSumScore);
                }
            });
            $("#testPaper-add-form input[name='fillInBlankScore']").change(function () {
                var trueOrFalseQuestions=$("#question-1").children().length;
                var fillInBlankQuestions=$("#question-2 .i-checks");
                var singleChoiceQuestions=$("#question-0").children().length;

                var fillInBlankScore=$(this).val();
                var singleChoiceScore=$("#testPaper-add-form input[name='singleChoiceScore']").val();
                var trueOrFalseScore=$("#testPaper-add-form input[name='trueOrFalseScore']").val();

                if(fillInBlankScore!=""){

                    var fillInBlankSumScore=0;
                    $.each(fillInBlankQuestions,function(index,question){
                        fillInBlankSumScore+=parseInt($(question).attr("data-blank"))*fillInBlankScore;
                    });

                    $("#score_add").text(trueOrFalseQuestions*parseFloat(trueOrFalseScore)+singleChoiceQuestions*parseFloat(singleChoiceScore)+fillInBlankSumScore);
                }
                else{
                    $("#score_add").text(trueOrFalseQuestions*parseFloat(trueOrFalseScore)+singleChoiceQuestions*parseFloat(singleChoiceScore));
                }
            });

            // 打开聊天窗口
            $('.open-small-chat').click(function () {
                $(this).children().toggleClass('fa-cube').toggleClass('fa-remove');
                $('.small-chat-box').toggleClass('active');
            });

            // 聊天窗口使用slimscroll
            $('.small-chat-box .content').slimScroll({
                height: '365px',
                railOpacity: 0.4
            });

            // Small todo handler
            $('.check-link').click(function () {
                var button = $(this).find('i');
                var label = $(this).next('span');
                button.toggleClass('fa-check-square').toggleClass('fa-square-o');
                label.toggleClass('todo-completed');
                return false;
            });



            //构建bootstraptable
            $('#fillInBlankTableEvents').bootstrapTable({
                url: "/v1/fillInBlankQuestion",
                search: false,
                pagination: true,
                showRefresh: true,
                showToggle: true,
                showColumns: true,
                cardView:true,
                iconSize: 'outline',
                idField:'id',
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
                    },{
                        field: 'content',
                        title: '内容'
                    },{
                        field: 'answer',
                        title: '答案',
                        formatter : function(value, row, index){
                            var obj=JSON.parse(value);
                            var blankNum=row.blankNum;
                            var answer="";
                            for(var i=1;i<=blankNum;i++){
                                if(i!=blankNum)
                                    answer+=obj[i][0]+" / ";
                                else
                                    answer+=obj[i][0];
                            }
                            return answer;
                        }
                    },{
                        field: 'difficulty',
                        visible:false
                    },{
                        field: 'courseId',
                        visible:false
                    },{
                        field: 'isChecked',
                        visible:false
                    },{
                        field: 'blankNum',
                        visible:false
                    },{
                        field: 'detailds',
                        title: '详情',
                        events:operateEvents,
                        formatter:addDetailsButton
                    }
                ],
                onLoadSuccess:function (data) {
                    var questions=data.data;

                    $.each(questions,function (index,question) {
                        var matchers=question.matcherCodes;

                        var stringMatcherCodes="";

                        var matchersLength=matchers.length;

                        $.each(matchers,function (index,matcher) {
                            if(index!=matchersLength-1){
                                stringMatcherCodes+=matcher+",";
                            }
                            else{
                                stringMatcherCodes+=matcher;
                            }
                        });
                        question.matcherCodes=stringMatcherCodes;
                    });
                    $("#fillInBlankTableEvents").bootstrapTable("load",data);
                }
            });

            $('#singleChoiceTableEvents').bootstrapTable({
                url: "/v1/singleChoiceQuestion",
                search: false,
                pagination: true,
                showRefresh: true,
                showToggle: true,
                showColumns: true,
                cardView:true,
                iconSize: 'outline',
                idField:'id',
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
                    },{
                        field: 'content',
                        title: '内容'
                    },{
                        field: 'optionA',
                        title: 'A'
                    },{
                        field: 'optionB',
                        title: 'B'
                    }, {
                        field: 'optionC',
                        title: 'C',
                    }, {
                        field: 'optionD',
                        title: 'D',
                    }, {
                        field: 'answer',
                        title: '答案',
                    },{
                        field: 'difficulty',
                        visible:false
                    },{
                        field: 'courseId',
                        visible:false
                    },{
                        field: 'isChecked',
                        visible:false
                    }, {
                        field: 'detailds',
                        title: '详情',
                        events:operateEvents,
                        formatter:addDetailsButton
                    }
                ]
            });


            $('#trueOfFalseTableEvents').bootstrapTable({
                url: "/v1/trueOfFalseQuestion",
                search: false,
                pagination: true,
                showRefresh: true,
                showToggle: true,
                showColumns: true,
                cardView:true,
                iconSize: 'outline',
                idField:'id',
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
                    },{
                        field: 'content',
                        title: '内容'
                    }, {
                        field: 'answer',
                        visible:false
                    }, {
                        field: 'answerView',
                        title: '答案',
                        formatter : ES.formatAnswer
                    },{
                        field: 'difficulty',
                        visible:false
                    },{
                        field: 'courseId',
                        visible:false
                    },{
                        field: 'isChecked',
                        visible:false
                    }, {
                        field: 'detailds',
                        title: '详情',
                        events:operateEvents,
                        formatter:addDetailsButton
                    }
                ]
            });
        })();


        $("#course").on('change',function () {
            var courseId=$("#course").val();
            $("#question-search-form [name=knowledgePointId]").val("");
            $("#question-search-form [name=difficulty]").val("");
            $("#question-search-form [name=isChecked]").val("");
            $.ajax({
                type: "GET",
                url: "/v1/knowledgePoint/course/"+courseId,
                data: null,
                success: function(data){
                    if(data.status == 200){
                        $('#jstree1').jstree("destroy");
                        $('#jstree1').jstree({
                            'core': {
                                'data': data.data
                            },
                            'types': {
                                '#' : {
                                    "max_depth" : 3,
                                    'icon': 'fa fa-tag'
                                },
                                'default': {
                                    'icon': 'fa fa-tag'
                                }
                            },
                            'plugins': ['types']
                        });
                        $('#jstree1').on("changed.jstree", function (e, data) {
                            if(data.node.type!="#"){
                                $("#knowledgePointId").val(data.node.id);
                            }
                            else{
                                $("#knowledgePointId").val("");
                            }
                            $("#fillInBlankTableEvents").bootstrapTable('refresh');
                            $("#singleChoiceTableEvents").bootstrapTable('refresh');
                            $("#trueOfFalseTableEvents").bootstrapTable('refresh');
                        });

                        $("#fillInBlankTableEvents").bootstrapTable('refresh');
                        $("#singleChoiceTableEvents").bootstrapTable('refresh');
                        $("#trueOfFalseTableEvents").bootstrapTable('refresh');
                    }
                    else{
                        swal("加载知识点失败",data.message, "error");
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
