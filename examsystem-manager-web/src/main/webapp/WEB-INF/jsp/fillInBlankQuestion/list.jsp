<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>填空题</title>
    <meta name="keywords" content="在线考试系统,吉林大学珠海学院考试">
    <meta name="description" content="在线考试系统是一个服务于教师与学生的学习平台">

    <link rel="shortcut icon" href="favicon.ico"> <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/plugins/jsTree/style.min.css" rel="stylesheet">
    <link href="/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="/css/plugins/steps/jquery.steps.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/plugins/simditor/simditor.css" rel="stylesheet" />
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">

    <link href="/css/plugins/spinner/bootstrap-spinner.css" rel="stylesheet">
    <!-- Sweet Alert -->
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">



</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!--开始 题目table -->
        <div class="ibox float-e-margins">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>填空题</h5>
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
                                <div class=" float-e-margins ">
                                    <form role="form" class="form-inline ">
                                        <div class="form-group">
                                            <div class=" col-lg-2 m-t-sm">
                                                <span class="label label-success ">审核</span>
                                            </div>
                                            <div class=" col-lg-10 m-t-xs" >
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" data-isChecked="" onclick="searchData(this,'isChecked')">全部</button>
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" data-isChecked="1" onclick="searchData(this,'isChecked')">通过</button>
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" data-isChecked="0" onclick="searchData(this,'isChecked')">待通过</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="ibox float-e-margins ">
                                    <form role="form" class="form-inline ">
                                        <div class="form-group">
                                            <div class=" col-lg-2 m-t-sm">
                                                <span class="label label-success ">难度</span>
                                            </div>
                                            <div class=" col-lg-10 m-t-xs">
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'difficulty')" data-difficulty="">全部</button>
                                                <c:forEach items="${difficultys}" var="difficulty" >
                                                    <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'difficulty')" data-difficulty="${difficulty.code}">${difficulty.name}</button>
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
                                        <button type="button" class="btn btn-outline btn-default" onclick="openUpdateQuestionModal()">
                                            <i class="glyphicon glyphicon-pencil" aria-hidden="true"></i>
                                        </button>
<%--                                        <button type="button" class="btn btn-outline btn-default" onclick="btchDeleteQuestion()">
                                            <i class="glyphicon glyphicon-trash" aria-hidden="true"></i>
                                        </button>--%>
                                        <c:if test="${not empty role_checker  }">
                                            <button type="button" class="btn btn-outline btn-default" onclick="checkQuestion()">
                                                <i class="glyphicon glyphicon-ok-circle" aria-hidden="true"></i>
                                            </button>
                                        </c:if>
                                    </div>
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
                            <table id="exampleTableEvents" data-height="400" data-mobile-responsive="true">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 结束 题目table -->
    </div>

    <!-- 添加题目modal -->
    <div id="modal-form-save" class="modal fade " aria-hidden="true" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row" >
                        <form class="form-horizontal wizard-big" id="question-add-form">
                            <h1>题目内容</h1>
                            <fieldset style="overflow-x: hidden;">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">内容：</label>
                                    <div class="col-sm-7">
                                        <textarea id="content_addEditor" name="content" placeholder="题目内容" id="content_add" class="form-control" autofocus></textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">空格：</label>
                                    <div class="col-sm-7">
                                        <p class="form-control">____</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">空格数：</label>
                                    <div class="col-sm-7 " >
                                        <div class="input-group spinner" data-trigger="spinner" id="blankNum">
                                            <input type="text" class="form-control" value="1" data-max="20" data-min="1" data-step="1" name="blankNum" id="blankNum_add">
                                            <span class="input-group-addon">
                                                <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
                                                <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group " >
                                    <label class="col-sm-3 control-label">答案匹配模式：</label>
                                    <div class=" col-sm-6">
                                        <select data-placeholder="匹配模式..." class="form-control chosen-select " multiple style="width:270px;" tabindex="4" id="matcher_add"  name="matchersCode">
                                            <c:forEach items="${matchers}" var="matcher">
                                                <option value="${matcher.code}" hassubinfo="true">${matcher.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
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
                                <div class="form-group " >
                                    <label class="col-sm-3 control-label">难度：</label>
                                    <div class=" col-sm-6">
                                        <select data-placeholder="选择难度..." class="form-control chosen-select " style="width:270px;" tabindex="2" id="difficulty_add"   name="difficulty">
                                            <c:forEach items="${difficultys}" var="difficulty" >
                                                <option value="${difficulty.code}" hassubinfo="true">${difficulty.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </fieldset>
                            <h1>答案</h1>
                            <fieldset style="overflow-x: hidden;">
                                <div class="form-group ">
                                    <div class="tabs-container " >
                                        <div class="tabs-left" >
                                            <ul class="nav nav-tabs" id="nav_answer">
                                            </ul>
                                            <div class="tab-content " id="content_answer">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改题目modal -->
    <div id="modal-form-update" class="modal fade " aria-hidden="true" >
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row" >
                        <form class="form-horizontal wizard-big" id="question-update-form">
                            <input type="hidden" name="isChecked">
                            <input type="hidden" name="_method" value="put">
                            <h1>题目内容</h1>
                            <fieldset style="overflow-x: hidden;">
                                <div class="form-group" id="content_update">
                                    <label class="col-sm-3 control-label">内容：</label>
                                    <div class="col-sm-7">
                                        <textarea id='content_updateEditor' name='content' class='form-control' autofocus></textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">空格：</label>
                                    <div class="col-sm-7">
                                        <p class="form-control">____</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">空格数：</label>
                                    <div class="col-sm-7 " >
                                        <div class="input-group spinner" data-trigger="spinner" id="blankNum2">
                                            <input type="text" class="form-control" value="1" data-max="20" data-min="1" data-step="1" name="blankNum" id="blankNum_update">
                                            <span class="input-group-addon">
                                                <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
                                                <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
                                            </span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group " >
                                    <label class="col-sm-3 control-label">答案匹配模式：</label>
                                    <div class=" col-sm-6">
                                        <select data-placeholder="匹配模式..." class="form-control chosen-select " multiple style="width:270px;" tabindex="4" id="matcher_update"  name="matchersCode">
                                            <c:forEach items="${matchers}" var="matcher">
                                                <option value="${matcher.code}" hassubinfo="true">${matcher.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group " >
                                    <label class="col-sm-3 control-label">课程名称：</label>
                                    <div class=" col-sm-6">
                                        <select data-placeholder="选择课程..." class="form-control chosen-select " style="width:270px;" tabindex="2" id="course_update"   name="courseId">
                                            <c:forEach items="${courses}" var="course" >
                                                <option value="${course.id}" hassubinfo="true">${course.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group " >
                                    <label class="col-sm-3 control-label">难度：</label>
                                    <div class=" col-sm-6">
                                        <select data-placeholder="选择难度..." class="form-control chosen-select " style="width:270px;" tabindex="2" id="difficulty_update"   name="difficulty">
                                            <c:forEach items="${difficultys}" var="difficulty" >
                                                <option value="${difficulty.code}" hassubinfo="true">${difficulty.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                            </fieldset>
                            <h1>答案</h1>
                            <fieldset style="overflow-x: hidden;">
                                <div class="form-group ">
                                    <div class="tabs-container " >
                                        <div class="tabs-left" >
                                            <ul class="nav nav-tabs" id="nav_answer_update">
                                            </ul>
                                            <div class="tab-content " id="content_answer_update">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </fieldset>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 查看详情课程modal -->
    <div id="modal-form-details" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="question-details-form">
                            <p>欢迎查看该题目(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">内容：</label>
                                <div class="col-sm-6">
                                    <p class="form-control-static" id="content_details"></p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">课程：</label>
                                <div class="col-sm-6">
                                    <p class="form-control-static" id="course_details"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">难度：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="difficulty_details"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">创建题目教师名称：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="created_teacher_details"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">审核：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="is_checked_details"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">答案匹配模式：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="matcher_details"></p>
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
        .option .simditor-wrapper{
            height: 34px;
        }
        .option .simditor .simditor-body{
            padding: 6px 12px;
            height: 34px;
        }
        .option .simditor .simditor-placeholder{
            padding: 6px 12px;
            height: 34px;
        }
    </style>

    <script type="text/javascript">



        $("#course").val("${course.id}");

        $("#course").trigger("chosen:updated");

        $.ajax({
            type: "GET",
            url: "/v1/knowledgePoint/course/"+"${course.id}",
            data: null,
            success: function(data){
                if(data.status == 200){
                    $('#jstree1').jstree({
                        'core': {
                            'data': data.data,
                            'check_callback':true
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
                        "contextmenu": {
                            select_node: false,
                            show_at_node: true,
                            items: function (node) {
                                var items = {
                                    "录入题目":{
                                        "label":"录入题目",
                                        "icon": "glyphicon glyphicon-plus",
                                        "action":function(data){
                                            var inst = $.jstree.reference(data.reference),
                                                    obj = inst.get_node(data.reference);
                                            var knowledgePointId=obj.id;

                                            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
                                            if(sels.length == 0){
                                                layer.msg("必须选择一个题目才能录入到该知识点!");
                                                return ;
                                            }else if(sels.length >1){
                                                layer.msg("只能选择一个题目!");
                                                return ;
                                            }
                                            var questionId=sels[0].id;

                                            $.ajax({
                                                type: "POST",
                                                url: "/v1/question/"+questionId+"/knowledgePoint/"+knowledgePointId,
                                                data: {questionType:"0"},
                                                success: function(data){
                                                    if(data.status == 201){
                                                        swal(data.message, "您已经永久将该知识点录入对应题目中", "success");
                                                        $("#exampleTableEvents").bootstrapTable('refresh');
                                                    }
                                                    else{
                                                        swal("录入失败",data.message, "error");
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
                                    },
                                    "移除题目":{
                                        "label":"移除题目",
                                        "icon": "glyphicon glyphicon-remove",
                                        "action":function(data){
                                            var inst = $.jstree.reference(data.reference),
                                                    obj = inst.get_node(data.reference);
                                            var knowledgePointId=obj.id;

                                            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
                                            if(sels.length == 0){
                                                layer.msg("必须选择一个题目才能从题目中移除知识点!");
                                                return ;
                                            }else if(sels.length >1){
                                                layer.msg("只能选择一个题目!");
                                                return ;
                                            }
                                            var questionId=sels[0].id;

                                            $.ajax({
                                                type: "POST",
                                                url: "/v1/question/"+questionId+"/knowledgePoint/"+knowledgePointId,
                                                data: {_method:"delete",questionType:"0"},
                                                success: function(data){
                                                    if(data.status == 201){
                                                        swal(data.message, "您已经永久将该知识点从对应题目中移除", "success");
                                                        $("#exampleTableEvents").bootstrapTable('refresh');
                                                    }
                                                    else{
                                                        swal("移除失败",data.message, "error");
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
                                    }
                                };
                                if(!this.is_leaf(node)){
                                    delete items.录入题目;
                                    delete items.移除题目;
                                }
                                return items;
                            }
                        },
                        'plugins': ['types','contextmenu']
                    });
                    $('#jstree1').on("changed.jstree", function (e, data) {
                        if(data.node.type!="#"){
                            $("#knowledgePointId").val(data.node.id);
                        }
                        else{
                            $("#knowledgePointId").val("");
                        }
                        $("#exampleTableEvents").bootstrapTable('refresh');
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

    <!-- Spinner -->
    <script src="/js/plugins/spinner/jquery.spinner.min.js"></script>

    <!-- Steps -->
    <script src="/js/plugins/staps/jquery.steps.min.js"></script>

    <!-- Sweet alert -->
    <script src="/js/plugins/sweetalert/sweetalert.min.js"></script>

    <!-- Bootstrap table -->
    <script src="/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script src="/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

    <!-- simditor -->
    <script type="text/javascript" src="/js/plugins/simditor/module.js"></script>
    <script type="text/javascript" src="/js/plugins/simditor/uploader.js"></script>
    <script type="text/javascript" src="/js/plugins/simditor/hotkeys.js"></script>
    <script type="text/javascript" src="/js/plugins/simditor/simditor.js"></script>

    <script>
            $("#question-add-form").steps({
                bodyTag: "fieldset",
                onStepChanging: function (event, currentIndex, newIndex) {

                    if (currentIndex > newIndex) {
                        return true;
                    }

                    //表单校验
                    if(newIndex === 1&&($("#question-add-form [name=content]").val().trim()=='')){
                        layer.msg("题目内容不能为空!");
                        return false;
                    }
                    return true;
                },
                onStepChanged: function (event, currentIndex, priorIndex) {

                    if (currentIndex === 0 && ($("#question-add-form [name=content]").val().trim()!='')) {
                        $(this).steps("next");
                    }
                    if (currentIndex === 0 && priorIndex === 1) {
                        $(this).steps("previous");
                    }
                },
                onFinishing: function (event, currentIndex) {

                    var finished=true;

                    if ( ($("#question-add-form [name=content]").val().trim()=='')) {
                        layer.msg("题目内容不能为空!");
                        return false;
                    }

                    //答案校验，验证成功就将填空题答案解析，添加到参数中
                        var blankNum=$("#nav_answer").children().length;
                        for(var i=1;i<=blankNum;i++){
                            var blanki=$("#question-add-form input[name=blank"+i+"]");
                            $.each(blanki,function (index,blank) {
                                if(index>=1){
                                    if($(blank).val().trim()==''){
                                        layer.msg("答案不能为空!");
                                        finished=false;
                                        return ;
                                    }
                                }
                            });
                        }
                        if(!finished)
                            return false;


                        for(var i=1;i<=blankNum;i++){
                            var blanki=$("#question-add-form input[name=blank"+i+"]");
                            var blankString="";
                            $.each(blanki,function (index,blank) {
                                if(index==blanki.length-1)
                                    blankString+=$(blank).val();
                                else
                                    blankString+=$(blank).val()+",";
                            });
                            var index=i-1;
                            $("#question-add-form input[name='answerList["+index+"]']").val(blankString);
                        }
                    return true;
                },
                onFinished: function (event, currentIndex) {
                    //发ajax添加
                    $.ajax({
                        type: "POST",
                        url: "/v1/fillInBlankQuestion",
                        data: $("#question-add-form").serialize(),
                        success: function(data){
                            if(data.status == 201){
                                $("#modal-form-save").modal('hide');
                                swal(data.message, "您已经永久添加了该题目。", "success");
                                $("#exampleTableEvents").bootstrapTable('refresh');
                            }
                            else{
                                swal("添加失败",data.message, "error");
                            }
                        },
                        error:function(){
                            var status=XMLHttpRequest.status;
                            if(status==403){
                                to403();
                            }else if(status==500){
                                to500();
                            }
                        }
                    });

                },
                onCanceled: function (event, currentIndex) {

                    $("#modal-form-save").modal('hide');
                    $("#question-add-form").steps("previous");
                }
            });

            $("#question-update-form").steps({
                bodyTag: "fieldset",
                onStepChanging: function (event, currentIndex, newIndex) {

                    if (currentIndex > newIndex) {
                        return true;
                    }

                    //表单校验
                    if(newIndex === 1&&($("#question-update-form [name=content]").val().trim()=='')){
                        layer.msg("题目内容不能为空!");
                        return false;
                    }
                    return true;
                },
                onStepChanged: function (event, currentIndex, priorIndex) {

                    if (currentIndex === 0 && ($("#question-update-form [name=content]").val().trim()!='')) {
                        $(this).steps("next");
                    }
                    if (currentIndex === 0 && priorIndex === 1) {
                        $(this).steps("previous");
                    }
                },
                onFinishing: function (event, currentIndex) {

                    var finished=true;

                    if (($("#question-update-form [name=content]").val().trim()=='')) {
                        layer.msg("题目内容不能为空!");
                        return false;
                    }

                    //答案校验，验证成功就将填空题答案解析，添加到参数中

                        var blankNum=$("#nav_answer_update").children().length;
                        for(var i=1;i<=blankNum;i++){
                            var blanki=$("#question-update-form input[name=blank"+i+"]");
                            $.each(blanki,function (index,blank) {
                                    if($(blank).val().trim()==''){
                                        layer.msg("答案不能为空!");
                                        finished=false;
                                        return ;
                                    }
                            });
                        }
                        if(!finished)
                            return false;


                        for(var i=1;i<=blankNum;i++){
                            var blanki=$("#question-update-form input[name=blank"+i+"]");
                            var blankString="";
                            $.each(blanki,function (index,blank) {
                                if(index==blanki.length-1)
                                    blankString+=$(blank).val();
                                else
                                    blankString+=$(blank).val()+",";
                            });
                            var index=i-1;
                            $("#question-update-form input[name='answerList["+index+"]']").val(blankString);
                        }

                    return true;
                },
                onFinished: function (event, currentIndex) {

                    var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
                    //发ajax添加
                    $.ajax({
                        type: "POST",
                        url: "/v1/fillInBlankQuestion/"+sels[0].id,
                        data: $("#question-update-form").serialize(),
                        success: function(data){
                            if(data.status == 201){
                                $("#modal-form-update").modal('hide');
                                swal(data.message, "您已经永久修改了该题目。", "success");
                                $("#exampleTableEvents").bootstrapTable('refresh');
                            }
                            else{
                                swal("修改失败",data.message, "error");
                            }
                        },
                        error:function(){
                            var status=XMLHttpRequest.status;
                            if(status==403){
                                to403();
                            }else if(status==500){
                                to500();
                            }
                        }
                    });

                },
                onCanceled: function (event, currentIndex) {

                    $("#modal-form-update").modal('hide');
                    $("#question-update-form").steps("previous");
                }
            });

            $("#blankNum").spinner('changed',function(e,newVal,oldVal){

                $("#question-add-form").children("input[name^='answerList']").remove();

                $("#nav_answer").children().remove();
                $(".tab-pane").remove();
                var contentAnswer=$("#content_answer");
                var navAnswer=$("#nav_answer");

                for(var i=1;i<=newVal;i++){
                    var nav="";
                    var content="";

                    var button_addAnswer="";
                    var initBlank="";

                    if(i!=1){
                        nav='<li class=""><a data-toggle="tab" href="#tab-'+i+'"> 第'+i+'个空</a> </li>';

                        button_addAnswer='<button type="button" class="btn btn-outline btn-default" onclick="addAnswer(this)"> <i class="glyphicon glyphicon-plus" aria-hidden="true"></i> </button>';
                        initBlank='<div class="form-group " style="height: 34px;"> <div class="col-sm-6"> <input type="text" placeholder="标准答案" class="form-control" name="blank'+i+'"> </div>'+button_addAnswer+'</div>';

                        content='<div id="tab-'+i+'"  class="tab-pane"><div class="panel-body">'+initBlank+'</div></div>';
                    } else{
                        nav='<li class="active"><a data-toggle="tab" href="#tab-'+i+'"> 第'+i+'个空</a> </li>';
                        button_addAnswer='<button type="button" class="btn btn-outline btn-default" onclick="addAnswer(this)"> <i class="glyphicon glyphicon-plus" aria-hidden="true"></i> </button>';
                        initBlank='<div class="form-group " style="height: 34px;"> <div class="col-sm-6"> <input type="text" placeholder="标准答案" class="form-control" name="blank1"> </div> '+button_addAnswer+'</div>';

                        content='<div id="tab-'+i+'"  class="tab-pane active"><div class="panel-body">'+initBlank+'</div></div>';
                    }
                    navAnswer.append(nav);
                    contentAnswer.append(content);

                    var index=i-1;

                    var input='<input type="hidden" name="answerList['+index+']" value="">';
                    $("#question-add-form").prepend(input);
                }
            });

            $("#blankNum2").spinner('changed',function(e,newVal,oldVal){

                $("#question-update-form").children("input[name^='answerList']").remove();

                $("#nav_answer_update").children().remove();
                $(".tab-pane").remove();
                var contentAnswer=$("#content_answer_update");
                var navAnswer=$("#nav_answer_update");

                for(var i=1;i<=newVal;i++){
                    var nav="";
                    var content="";

                    var button_addAnswer="";
                    var initBlank="";

                    if(i!=1){
                        nav='<li class=""><a data-toggle="tab" href="#tab-'+i+'"> 第'+i+'个空</a> </li>';

                        button_addAnswer='<button type="button" class="btn btn-outline btn-default" onclick="addAnswer(this)"> <i class="glyphicon glyphicon-plus" aria-hidden="true"></i> </button>';
                        initBlank='<div class="form-group " style="height: 34px;"> <div class="col-sm-6"> <input type="text" placeholder="标准答案" class="form-control" name="blank'+i+'"> </div>'+button_addAnswer+'</div>';

                        content='<div id="tab-'+i+'"  class="tab-pane"><div class="panel-body">'+initBlank+'</div></div>';
                    } else{
                        nav='<li class="active"><a data-toggle="tab" href="#tab-'+i+'"> 第'+i+'个空</a> </li>';
                        button_addAnswer='<button type="button" class="btn btn-outline btn-default" onclick="addAnswer(this)"> <i class="glyphicon glyphicon-plus" aria-hidden="true"></i> </button>';
                        initBlank='<div class="form-group " style="height: 34px;"> <div class="col-sm-6"> <input type="text" placeholder="标准答案" class="form-control" name="blank1"> </div> '+button_addAnswer+'</div>';

                        content='<div id="tab-'+i+'"  class="tab-pane active"><div class="panel-body">'+initBlank+'</div></div>';
                    }
                    navAnswer.append(nav);
                    contentAnswer.append(content);

                    var index=i-1;

                    var input='<input type="hidden" name="answerList['+index+']" value="">';
                    $("#question-update-form").prepend(input);
                }
            });
    </script>



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
                                'data': data.data,
                                'check_callback':true
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
                            "contextmenu": {
                                select_node: false,
                                show_at_node: true,
                                items: function (node) {
                                    var items = {
                                        "录入题目":{
                                            "label":"录入题目",
                                            "icon": "glyphicon glyphicon-plus",
                                            "action":function(data){
                                                var inst = $.jstree.reference(data.reference),
                                                        obj = inst.get_node(data.reference);
                                                var knowledgePointId=obj.id;

                                                var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
                                                if(sels.length == 0){
                                                    layer.msg("必须选择一个题目才能录入到该知识点!");
                                                    return ;
                                                }else if(sels.length >1){
                                                    layer.msg("只能选择一个题目!");
                                                    return ;
                                                }
                                                var questionId=sels[0].id;

                                                $.ajax({
                                                    type: "POST",
                                                    url: "/v1/question/"+questionId+"/knowledgePoint/"+knowledgePointId,
                                                    data: {questionType:"0"},
                                                    success: function(data){
                                                        if(data.status == 201){
                                                            swal(data.message, "您已经永久将该知识点录入对应题目中", "success");
                                                            $("#exampleTableEvents").bootstrapTable('refresh');
                                                        }
                                                        else{
                                                            swal("录入失败",data.message, "error");
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
                                        },
                                        "移除题目":{
                                            "label":"移除题目",
                                            "icon": "glyphicon glyphicon-remove",
                                            "action":function(data){
                                                var inst = $.jstree.reference(data.reference),
                                                        obj = inst.get_node(data.reference);
                                                var knowledgePointId=obj.id;

                                                var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
                                                if(sels.length == 0){
                                                    layer.msg("必须选择一个题目才能从题目中移除知识点!");
                                                    return ;
                                                }else if(sels.length >1){
                                                    layer.msg("只能选择一个题目!");
                                                    return ;
                                                }
                                                var questionId=sels[0].id;

                                                $.ajax({
                                                    type: "POST",
                                                    url: "/v1/question/"+questionId+"/knowledgePoint/"+knowledgePointId,
                                                    data: {_method:"delete",questionType:"0"},
                                                    success: function(data){
                                                        if(data.status == 201){
                                                            swal(data.message, "您已经永久将该知识点从对应题目中移除", "success");
                                                            $("#exampleTableEvents").bootstrapTable('refresh');
                                                        }
                                                        else{
                                                            swal("移除失败",data.message, "error");
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
                                        }
                                    };
                                    if(!this.is_leaf(node)){
                                        delete items.录入题目;
                                        delete items.移除题目;
                                    }
                                    return items;
                                }
                            },
                            'plugins': ['types','contextmenu']
                        });
                        $('#jstree1').on("changed.jstree", function (e, data) {
                            if(data.node.type!="#"){
                                $("#knowledgePointId").val(data.node.id);
                            }
                            else{
                                $("#knowledgePointId").val("");
                            }
                            $("#exampleTableEvents").bootstrapTable('refresh');
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

            $("#exampleTableEvents").bootstrapTable('refresh');
        }

        function searchData(e,data){
            var value=$(e).attr("data-"+data);
            $("#question-search-form [name='"+data+"']").val(value);
            $("#exampleTableEvents").bootstrapTable('refresh');
        }

/*        function btchDeleteQuestion(){
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("未选中题目!");
                return ;
            }

            var ids = getSelectionsIds(sels);
            swal({
                        title: "确定删除所选题目吗",
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
                                url: "/v1/fillInBlankQuestion",
                                data: params,
                                success: function(data){
                                    if(data.status == 201){
                                        swal(data.message, "您已经永久删除了这些题目。", "success");
                                        $("#exampleTableEvents").bootstrapTable('refresh');
                                    }
                                    else{
                                        swal(data.message, "无法删除这些题目。", "error");
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
        }*/

        function checkQuestion(){
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("必须至少选择一个题目才能审核!");
                return ;
            }

            var ids = getSelectionsIds(sels);

            swal({
                        title: "确定审核通过所选题目吗",
                        text: "请谨慎操作！",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "是的，我已确认！",
                        cancelButtonText: "让我再考虑一下…",
                        closeOnConfirm: false,
                        closeOnCancel: false
                    },
                    function (isConfirm) {
                        if (isConfirm) {
                            var params = {_method:'put',questionIds:ids};
                            $.ajax({
                                type: "POST",
                                url: "/v1/checker/question/2",
                                data: params,
                                success: function(data){
                                    if(data.status == 201){
                                        swal(data.message, "您已经审核通过了所选题目。", "success");
                                        $("#exampleTableEvents").bootstrapTable('refresh');
                                    }
                                    else{
                                        swal(data.message, "无法审核通过所选题目。", "error");
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
                            swal("已取消", "您取消了审核操作！", "error");
                        }
                    });
        }

        function openUpdateQuestionModal(){
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("必须选择一个题目才能修改!");
                return ;
            }else if(sels.length >1){
                layer.msg("只能选择一个题目!");
                return ;
            }
            var content=sels[0].content;
            var id=sels[0].id;

            var blankNum=sels[0].blankNum;
            var matcherCodes=sels[0].matcherCodes;
            var courseId=sels[0].courseId;
            var difficulty=sels[0].difficulty;
            var isChecked=sels[0].isChecked;

            $("#question-update-form").attr('selected',false);

            var contentHtml="<textarea id='content_updateEditor' name='content' class='form-control' autofocus>"+content+"</textarea>";

            $("#question-update-form .simditor").remove();
            $("#question-update-form textarea").remove();

            $("#content_update").children().last().append(contentHtml);

            $("#question-update-form [name=content]").val(content);
            $("#question-update-form [name=id]").val(id);
            $("#question-update-form [name=isChecked]").val(isChecked);
            $("#question-update-form [name=blankNum]").val(blankNum);

            $("#course_update").val(courseId).trigger("chosen:updated");
            $("#difficulty_update").val(difficulty).trigger("chosen:updated");

            var matchersArr=matcherCodes.split(",");
            $("#matcher_update").val(matchersArr).trigger("chosen:updated");

            $("#modal-form-update").modal('show');

            var contentUpdateEditor = new Simditor({
                textarea: $('#content_updateEditor'),
                toolbarHidden:true
            });
        }

        //获取查询参数
        function queryParams(params){
            var temp={
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                difficulty:$("#difficulty").val(),
                isChecked:$("#isChecked").val(),
                courseId:$("#course").val(),
                knowledgePointId:$("#knowledgePointId").val()
            };
            return temp;
        }

        function addDetailsButton(value,row,index){
            return ['<button class="btn btn-outline btn-info" type="button" id="questionDetails">查看</button>'].join("");
        }

        window.operateEvents={
            "click #questionDetails":function (e,value,row,index) {

                console.log(row);
                //发送ajax请求题目详情
                $.ajax({
                    type: "GET",
                    url: "/v1/fillInBlankQuestion/"+row.id,
                    data: null,
                    success: function(data){
                        if(data.status == 200){

                            //赋值
                            var questionDetails=data.data;

                            $("#content_details").text(questionDetails.content);
                            $("#course_details").text(questionDetails.courseName);
                            $("#difficulty_details").text(questionDetails.difficultyName);
                            $("#created_teacher_details").text(questionDetails.createdTeacher.sysuserId+"-"+questionDetails.createdTeacher.name);
                            if(questionDetails.isChecked=='1'){
                                $("#is_checked_details").text("通过");
                            }else{
                                $("#is_checked_details").text("待通过");
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
                                $("#matcher_details").text(matcherDetails);
                            }
                            else{
                                $("#matcher_details").text("-");
                            }

                            $("#question-details-form .blank").remove();

                            var answerObj=JSON.parse(questionDetails.answer);
                            var blankNum=questionDetails.blankNum;
                            for(var i=1;i<=blankNum;i++){
                                var text=answerObj[i];
                                var html='<div class="form-group blank"> <label class="col-sm-3 control-label ">空格'+i+'：</label> <div class="col-sm-6"> <p class="form-control-static" id="question_details">'+text+'</p> </div> </div>';
                                $("#question-details-form").append(html);
                            }

                            $("#modal-form-details").modal('show');
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
        };

        function deleteAnswer(e){
            var blank=$(e).parent();
            blank.remove();
        }
        function addAnswer(e){
            var parent=$(e).parent().parent();

            var oneGroup=$(e).prev().children()[0];
            console.log(oneGroup);
            var blankName=$(oneGroup).attr("name");

            var initBlank='<div class="form-group " style="height: 34px;"> <div class="col-sm-6"> <input type="text" placeholder="答案" class="form-control" name="'+blankName+'"> </div> <button type="button" class="btn btn-outline btn-default" onclick="deleteAnswer(this)"> <i class="glyphicon glyphicon-remove" aria-hidden="true"></i> </button> </div>';
            parent.append(initBlank);
        }

        (function () {
            //构建bootstraptable
            $('#exampleTableEvents').bootstrapTable({
                url: "/v1/fillInBlankQuestion",
                search: false,
                pagination: true,
                showRefresh: true,
                showToggle: true,
                showColumns: true,
                cardView:true,
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
                    $("#exampleTableEvents").bootstrapTable("load",data);
                }
            });
        })();

        $("#modal-form-save").on('shown.bs.modal',function () {
            $("#course_add_chosen").width(270);
            $("#matcher_add_chosen").width(270);
            $("#difficulty_add_chosen").width(270);

            $("#course_add").val($("#course").val()).trigger("chosen:updated");

            $("#question-add-form").children("input[name^='answerList']").remove();

            $("#nav_answer").children().remove();
            $(".tab-pane").remove();
            var contentAnswer=$("#content_answer");
            var navAnswer=$("#nav_answer");

            var nav='<li class="active"><a data-toggle="tab" href="#tab-1"> 第1个空</a> </li>';

            button_addAnswer='<button type="button" class="btn btn-outline btn-default" onclick="addAnswer(this)"> <i class="glyphicon glyphicon-plus" aria-hidden="true"></i> </button>';
            initBlank='<div class="form-group " style="height: 34px;"> <div class="col-sm-6"> <input type="text" placeholder="标准答案" class="form-control" name="blank1"> </div> '+button_addAnswer+'</div>';

            var content='<div id="tab-1"  class="tab-pane active"><div class="panel-body">'+initBlank+'</div></div>';

            navAnswer.append(nav);
            contentAnswer.append(content);

            var input='<input type="hidden" name="answerList[0]" value="">';
            $("#question-add-form").prepend(input);

            $("#content_add").val("");
            $("#blankNum_add").val(1);
            $("#matcher_add").val("").trigger("chosen:updated");
            $("#difficulty_add").val(0).trigger("chosen:updated");
        });

        $("#modal-form-save").on('hidden.bs.modal',function () {
            $("#question-add-form").steps("previous");
        });
        $("#modal-form-update").on('hidden.bs.modal',function () {
            $("#question-update-form").steps("previous");
        });

        $("#modal-form-update").on('shown.bs.modal',function () {
            $("#course_update_chosen").width(270);
            $("#matcher_update_chosen").width(270);
            $("#difficulty_update_chosen").width(270);

            $("#question-update-form").children("input[name^='answerList']").remove();

            $("#nav_answer_update").children().remove();
            $(".tab-pane").remove();
            var contentAnswer=$("#content_answer_update");
            var navAnswer=$("#nav_answer_update");

            //解析答案
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');

            var answerObj=JSON.parse(sels[0].answer);
            var blankNum=sels[0].blankNum;
            for(var i=1;i<=blankNum;i++){

                var nav="";
                var content="";

                if(i==1){
                    nav='<li class="active"><a data-toggle="tab" href="#tab-'+i+'"> 第'+i+'个空</a> </li>';
                    content='<div id="tab-'+i+'"  class="tab-pane active"><div class="panel-body">';
                }else{
                    nav='<li class=""><a data-toggle="tab" href="#tab-'+i+'"> 第'+i+'个空</a> </li>';
                    content='<div id="tab-'+i+'"  class="tab-pane "><div class="panel-body">';
                }

                navAnswer.append(nav);

                var ansArr=answerObj[i];
                for(var j=0;j<ansArr.length;j++){
                    if(j==0){
                        button_addAnswer='<button type="button" class="btn btn-outline btn-default" onclick="addAnswer(this)"> <i class="glyphicon glyphicon-plus" aria-hidden="true"></i> </button>';
                        initBlank='<div class="form-group " style="height: 34px;"> <div class="col-sm-6"> <input type="text" placeholder="标准答案" class="form-control" name="blank'+i+'" value="'+ansArr[j]+'"> </div> '+button_addAnswer+'</div>';
                        content+=initBlank;
                    }else{
                        button_delAnswer='<button type="button" class="btn btn-outline btn-default" onclick="deleteAnswer(this)"> <i class="glyphicon glyphicon-remove" aria-hidden="true"></i> </button>';
                        initBlank='<div class="form-group " style="height: 34px;"><div class="col-sm-6"> <input type="text" placeholder="答案" class="form-control" name="blank'+i+'" value="'+ansArr[j]+'"></div>'+button_delAnswer+'</div>';
                        content+=initBlank;
                    }
                }

                content+='</div></div>';
                contentAnswer.append(content);

                var index=i-1;

                var input='<input type="hidden" name="answerList['+index+']" value="">';
                $("#question-update-form").prepend(input);

            }
        });

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
                                'data': data.data,
                                'check_callback':true
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
                            "contextmenu": {
                                select_node: false,
                                show_at_node: true,
                                items: function (node) {
                                    var items = {
                                        "录入题目":{
                                            "label":"录入题目",
                                            "icon": "glyphicon glyphicon-plus",
                                            "action":function(data){
                                                var inst = $.jstree.reference(data.reference),
                                                        obj = inst.get_node(data.reference);
                                                var knowledgePointId=obj.id;

                                                var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
                                                if(sels.length == 0){
                                                    layer.msg("必须选择一个题目才能录入到该知识点!");
                                                    return ;
                                                }else if(sels.length >1){
                                                    layer.msg("只能选择一个题目!");
                                                    return ;
                                                }
                                                var questionId=sels[0].id;

                                                $.ajax({
                                                    type: "POST",
                                                    url: "/v1/question/"+questionId+"/knowledgePoint/"+knowledgePointId,
                                                    data: {questionType:"0"},
                                                    success: function(data){
                                                        if(data.status == 201){
                                                            swal(data.message, "您已经永久将该知识点录入对应题目中", "success");
                                                        }
                                                        else{
                                                            swal("录入失败",data.message, "error");
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
                                        },
                                        "移除题目":{
                                            "label":"移除题目",
                                            "icon": "glyphicon glyphicon-remove",
                                            "action":function(data){
                                                var inst = $.jstree.reference(data.reference),
                                                        obj = inst.get_node(data.reference);
                                                var knowledgePointId=obj.id;

                                                var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
                                                if(sels.length == 0){
                                                    layer.msg("必须选择一个题目才能从题目中移除知识点!");
                                                    return ;
                                                }else if(sels.length >1){
                                                    layer.msg("只能选择一个题目!");
                                                    return ;
                                                }
                                                var questionId=sels[0].id;

                                                $.ajax({
                                                    type: "POST",
                                                    url: "/v1/question/"+questionId+"/knowledgePoint/"+knowledgePointId,
                                                    data: {_method:"delete",questionType:"0"},
                                                    success: function(data){
                                                        if(data.status == 201){
                                                            swal(data.message, "您已经永久将该知识点从对应题目中移除", "success");
                                                        }
                                                        else{
                                                            swal("移除失败",data.message, "error");
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
                                        }
                                    };
                                    if(!this.is_leaf(node)){
                                        delete items.录入题目;
                                        delete items.移除题目;
                                    }
                                    return items;
                                }
                            },
                            'plugins': ['types','contextmenu']
                        });
                        $('#jstree1').on("changed.jstree", function (e, data) {
                            if(data.node.type!="#"){
                                $("#knowledgePointId").val(data.node.id);
                            }
                            else{
                                $("#knowledgePointId").val("");
                            }
                            $("#exampleTableEvents").bootstrapTable('refresh');
                        });

                        $("#exampleTableEvents").bootstrapTable('refresh');
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

    <script>
        var contentAddEditor = new Simditor({
            textarea: $('#content_addEditor'),
            toolbarHidden:true
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
