<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>单选题</title>
    <meta name="keywords" content="在线考试系统,吉林大学珠海学院考试">
    <meta name="description" content="在线考试系统是一个服务于教师与学生的学习平台">

    <link rel="shortcut icon" href="favicon.ico"> <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/plugins/jsTree/style.min.css" rel="stylesheet">
    <link href="/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/plugins/webuploader/webuploader.css" rel="stylesheet">
    <link href="/css/plugins/simditor/simditor.css" rel="stylesheet" />
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
                                        <button type="button" class="btn btn-outline btn-default" onclick="openExcelModal()">
                                            <i class="glyphicon glyphicon-file" aria-hidden="true"></i>
                                        </button>
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
    <div id="modal-form-save" class="modal fade" aria-hidden="true" >
        <div class="modal-dialog" style="width: 800px">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="question-add-form">
                            <p>欢迎添加题目(⊙o⊙)</p>
                            <div class="form-group " id="content_add">
                                <label class="col-sm-3 control-label">内容：</label>
                                <div class="col-sm-8">
                                    <textarea id="content_addEditor" name="content" placeholder="题目内容" class="form-control" autofocus></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">括号：</label>
                                <div class="col-sm-8">
                                    <p class="form-control">( )</p>
                                </div>
                            </div>
                            <div class="form-group option">
                                <label class="col-sm-3 control-label">A：</label>
                                <div class="col-sm-8">
                                    <textarea id="optionA_add" name="optionA" placeholder="选项A" class="form-control" autofocus></textarea>
                                </div>
                            </div>
                            <div class="form-group option">
                                <label class="col-sm-3 control-label">B：</label>
                                <div class="col-sm-8">
                                    <textarea id="optionB_add" name="optionB" placeholder="选项B" class="form-control" autofocus></textarea>
                                </div>
                            </div>
                            <div class="form-group option">
                                <label class="col-sm-3 control-label">C：</label>
                                <div class="col-sm-8">
                                    <textarea id="optionC_add" name="optionC" placeholder="选项C" class="form-control" autofocus></textarea>
                                </div>
                            </div>
                            <div class="form-group option">
                                <label class="col-sm-3 control-label">D：</label>
                                <div class="col-sm-8">
                                    <textarea id="optionD_add" name="optionD" placeholder="选项D" class="form-control" autofocus></textarea>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">答案：</label>
                                <div class=" col-sm-8">
                                    <select data-placeholder="选择答案..." class="form-control chosen-select " style="width:270px;" tabindex="2" id="answer_add"   name="answer">
                                        <option value="A" hassubinfo="true">A</option>
                                        <option value="B" hassubinfo="true">B</option>
                                        <option value="C" hassubinfo="true">C</option>
                                        <option value="D" hassubinfo="true">D</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">课程名称：</label>
                                <div class=" col-sm-8">
                                    <select data-placeholder="选择课程..." class="form-control chosen-select " style="width:270px;" tabindex="2" id="course_add"   name="courseId">
                                        <c:forEach items="${courses}" var="course" >
                                            <option value="${course.id}" hassubinfo="true">${course.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">难度：</label>
                                <div class=" col-sm-8">
                                    <select data-placeholder="选择难度..." class="form-control chosen-select " style="width:270px;" tabindex="2" id="difficulty_add"   name="difficulty">
                                        <c:forEach items="${difficultys}" var="difficulty" >
                                            <option value="${difficulty.code}" hassubinfo="true">${difficulty.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="saveQuestion()">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改题目modal -->
    <div id="modal-form-update" class="modal fade" aria-hidden="true">
        <div class="modal-dialog" style="width: 800px">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="question-update-form">
                            <input type="hidden" name="id">
                            <input type="hidden" name="isChecked">
                            <input type="hidden" name="_method" value="put">
                            <p>欢迎修改题目(⊙o⊙)</p>
                            <div class="form-group" id="content_update">
                                <label class="col-sm-3 control-label">内容：</label>
                                <div class="col-sm-8">
                                    <textarea id='content_updateEditor' name='content' class='form-control' autofocus></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">括号：</label>
                                <div class="col-sm-8">
                                    <p class="form-control">( )</p>
                                </div>
                            </div>
                            <div class="form-group option" id="optionA_update">
                                <label class="col-sm-3 control-label">A：</label>
                                <div class="col-sm-8">
                                    <textarea id='optionA_updateEditor' name='optionA' class='form-control' autofocus></textarea>
                                </div>
                            </div>
                            <div class="form-group option" id="optionB_update">
                                <label class="col-sm-3 control-label">B：</label>
                                <div class="col-sm-8">
                                    <textarea id='optionB_updateEditor' name='optionB' class='form-control' autofocus></textarea>
                                </div>
                            </div>
                            <div class="form-group option" id="optionC_update">
                                <label class="col-sm-3 control-label">C：</label>
                                <div class="col-sm-8">
                                    <textarea id='optionC_updateEditor' name='optionC' class='form-control' autofocus></textarea>
                                </div>
                            </div>
                            <div class="form-group option" id="optionD_update">
                                <label class="col-sm-3 control-label">D：</label>
                                <div class="col-sm-8">
                                    <textarea id='optionD_updateEditor' name='optionD' class='form-control' autofocus></textarea>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">答案：</label>
                                <div class=" col-sm-8">
                                    <select data-placeholder="选择答案..." class="form-control chosen-select " style="width:270px;" tabindex="2" id="answer_update"   name="answer">
                                        <option value="A" hassubinfo="true">A</option>
                                        <option value="B" hassubinfo="true">B</option>
                                        <option value="C" hassubinfo="true">C</option>
                                        <option value="D" hassubinfo="true">D</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">课程名称：</label>
                                <div class=" col-sm-8">
                                    <select data-placeholder="选择课程..." class="form-control chosen-select " style="width:270px;" tabindex="2" id="course_update"   name="courseId">
                                        <c:forEach items="${courses}" var="course" >
                                            <option value="${course.id}" hassubinfo="true">${course.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">难度：</label>
                                <div class=" col-sm-8">
                                    <select data-placeholder="选择难度..." class="form-control chosen-select " style="width:270px;" tabindex="2" id="difficulty_update"   name="difficulty">
                                        <c:forEach items="${difficultys}" var="difficulty" >
                                            <option value="${difficulty.code}" hassubinfo="true">${difficulty.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="updateQuestion()">修改</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 查看详情题目modal -->
    <div id="modal-form-details" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="question-details-form">
                            <p>欢迎查看该题目(⊙o⊙)</p>
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
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- excel添加题目modal -->
    <div class="modal inmodal fade" id="modal-form-excel" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h4 class="modal-title">Excel导入</h4>
                </div>
                <div class="modal-body">
                    <div id="uploader" class="wu-example">
                        <!--用来存放文件信息-->
                        <div id="thelist" class="uploader-list"></div>
                        <div id="picker">选择文件</div>
                        <span class="help-block m-b-none"><i class="fa fa-info-circle"></i>默认导进来的题目为审核通过,题目所属课程在操作教师任课范围之内,支持.xlsx和.xls后缀文件</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="excelAddButton" >导入</button>
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

    <!-- Sweet alert -->
    <script src="/js/plugins/sweetalert/sweetalert.min.js"></script>

    <!-- Bootstrap table -->
    <script src="/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script src="/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>

    <script src="/js/plugins/webuploader/webuploader.min.js"></script>

    <!-- simditor -->
    <script type="text/javascript" src="/js/plugins/simditor/module.js"></script>
    <script type="text/javascript" src="/js/plugins/simditor/uploader.js"></script>
    <script type="text/javascript" src="/js/plugins/simditor/hotkeys.js"></script>
    <script type="text/javascript" src="/js/plugins/simditor/simditor.js"></script>

    <style>
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
            $(':input',"#question-add-form").not(':button,:submit,:reset,:hidden').val('').removeAttr('checked').removeAttr('selected');
            $("#answer_add").val("A").trigger("chosen:updated");
            $("#difficulty_add").val("0").trigger("chosen:updated");

            $("#question-add-form .simditor-body").children().remove();
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

        function openExcelModal() {
            $("#modal-form-excel").modal('show');
        }

        function checkQuestion(){
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("必须选择一个题目才能审核!");
                return ;
            }else if(sels.length >1){
                layer.msg("只能选择一个题目!");
                return ;
            }

            var isChecked=sels[0].isChecked;
            var id=sels[0].id;

            var title="";
            if(isChecked){
                title="确定不让该题目通过审核吗"
            }else{
                title="确定让该题目通过审核吗"
            }
            swal({
                        title: title,
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
                            var params = {_method:'put',questionType:'0'};
                            $.ajax({
                                type: "POST",
                                url: "/v1/checker/question/"+id,
                                data: params,
                                success: function(data){
                                    if(data.status == 201){
                                        swal(data.message, "您已经审核了该题目。", "success");
                                        $("#exampleTableEvents").bootstrapTable('refresh');
                                    }
                                    else{
                                        swal(data.message, "无法审核该题目。", "error");
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
                                url: "/v1/singleChoiceQuestion",
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

        function saveQuestion(){
            //表单校验
            if($("#question-add-form [name=content]").val().trim()==''){
                layer.msg("题目内容不能为空!");
                return ;
            }
            if($("#question-add-form [name=optionA]").val().trim()==''){
                layer.msg("选项A不能为空!");
                return ;
            }
            if($("#question-add-form [name=optionB]").val().trim()==''){
                layer.msg("选项B不能为空!");
                return ;
            }
            if($("#question-add-form [name=optionC]").val().trim()==''){
                layer.msg("选项C不能为空!");
                return ;
            }
            if($("#question-add-form [name=optionD]").val().trim()==''){
                layer.msg("选项D不能为空!");
                return ;
            }

            //ajax的post方式提交表单
            //$("#itemAddForm").serialize()将表单序列号为key-value形式的字符串
            $.ajax({
                type: "POST",
                url: "/v1/singleChoiceQuestion",
                data: $("#question-add-form").serialize(),
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-save").modal('hide');
                        clearForm();
                        swal(data.message, "您已经永久添加了该题目。可将该题目添加到相应知识点中", "success");
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

        function updateQuestion(){
            //表单校验
            if($("#question-update-form [name=content]").val().trim()==''){
                layer.msg("题目内容不能为空!");
                return ;
            }
            if($("#question-update-form [name=optionA]").val().trim()==''){
                layer.msg("选项A不能为空!");
                return ;
            }
            if($("#question-update-form [name=optionB]").val().trim()==''){
                layer.msg("选项B不能为空!");
                return ;
            }
            if($("#question-update-form [name=optionC]").val().trim()==''){
                layer.msg("选项C不能为空!");
                return ;
            }
            if($("#question-update-form [name=optionD]").val().trim()==''){
                layer.msg("选项D不能为空!");
                return ;
            }

            var id=$("#question-update-form [name=id]").val();

            $.ajax({
                type: "POST",
                url: "/v1/singleChoiceQuestion/"+id,
                data: $("#question-update-form").serialize(),
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-update").modal('hide');
                        swal(data.message, "您已经成功修改了该题目。", "success");
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
            var optionA=sels[0].optionA;
            var optionB=sels[0].optionB;
            var optionC=sels[0].optionC;
            var optionD=sels[0].optionD;

            var answer=sels[0].answer;
            var courseId=sels[0].courseId;
            var difficulty=sels[0].difficulty;
            var isChecked=sels[0].isChecked;

            $("#question-update-form").attr('selected',false);

            var contentHtml="<textarea id='content_updateEditor' name='content' class='form-control' autofocus>"+content+"</textarea>";

            var optionAHtml="<textarea id='optionA_updateEditor' name='optionA' class='form-control' autofocus>"+optionA+"</textarea>";
            var optionBHtml="<textarea id='optionB_updateEditor' name='optionB' class='form-control' autofocus>"+optionB+"</textarea>";
            var optionCHtml="<textarea id='optionC_updateEditor' name='optionC' class='form-control' autofocus>"+optionC+"</textarea>";
            var optionDHtml="<textarea id='optionD_updateEditor' name='optionD' class='form-control' autofocus>"+optionD+"</textarea>";

            $("#question-update-form .simditor").remove();
            $("#question-update-form textarea").remove();

            $("#content_update").children().last().append(contentHtml);
            $("#optionA_update").children().last().append(optionAHtml);
            $("#optionB_update").children().last().append(optionBHtml);
            $("#optionC_update").children().last().append(optionCHtml);
            $("#optionD_update").children().last().append(optionDHtml);

            $("#question-update-form [name=id]").val(id);
            $("#question-update-form [name=isChecked]").val(isChecked);
            $("#answer_update").val(answer).trigger("chosen:updated");
            $("#course_update").val(courseId).trigger("chosen:updated");
            $("#difficulty_update").val(difficulty).trigger("chosen:updated");

            $("#modal-form-update").modal('show');

            var contentUpdateEditor = new Simditor({
                textarea: $('#content_updateEditor'),
                toolbarHidden:true
            });

            var optionAUpdateEditor = new Simditor({
                textarea: $('#optionA_updateEditor'),
                toolbarHidden:true
            });

            var optionBUpdateEditor = new Simditor({
                textarea: $('#optionB_updateEditor'),
                toolbarHidden:true
            });

            var optionCUpdateEditor = new Simditor({
                textarea: $('#optionC_updateEditor'),
                toolbarHidden:true
            });

            var optionDUpdateEditor = new Simditor({
                textarea: $('#optionD_updateEditor'),
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
                    url: "/v1/singleChoiceQuestion/"+row.id,
                    data: null,
                    success: function(data){
                        if(data.status == 200){

                            //赋值
                            var courseDetails=data.data;
                            $("#course_details").text(courseDetails.courseName);
                            $("#difficulty_details").text(courseDetails.difficultyName);
                            $("#created_teacher_details").text(courseDetails.createdTeacher.sysuserId+"-"+courseDetails.createdTeacher.name);
                            if(courseDetails.isChecked=='1'){
                                $("#is_checked_details").text("通过");
                            }else{
                                $("#is_checked_details").text("待通过");
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
        (function () {
            //构建bootstraptable
            $('#exampleTableEvents').bootstrapTable({
                url: "/v1/singleChoiceQuestion",
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



        })();



        $("#modal-form-save").on('shown.bs.modal',function () {
            $("#course_add_chosen").width(270);
            $("#answer_add_chosen").width(270);
            $("#difficulty_add_chosen").width(270);
            $("#course_add").val($("#course").val()).trigger("chosen:updated");
        });
        $("#modal-form-update").on('shown.bs.modal',function () {
            $("#course_update_chosen").width(270);
            $("#answer_update_chosen").width(270);
            $("#difficulty_update_chosen").width(270);
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



        var uploader;
        //在点击弹出模态框的时候再初始化WebUploader，解决点击上传无反应问题
        $("#modal-form-excel").on("shown.bs.modal",function(){
            uploader = WebUploader.create({

                // swf文件路径
                swf: 'http://localhost:8081/js/plugins/webuploader/Uploader.swf',

                // 文件接收服务端。
                server: '',

                // 选择文件的按钮。可选。
                // 内部根据当前运行是创建，可能是input元素，也可能是flash.
                pick: {
                    "id":'#picker',
                    "multiple":false   //禁止多选。
                },
                //去重
                duplicate:true,
                //上传文件个数限制
                fileNumLimit:1,
                //重要参数:跟后台文件组件的对接参数，后台文件组件叫做upload。
                fileVal:"upload"
            });

            // 当有文件被添加进队列的时候
            uploader.on( 'fileQueued', function( file ) {
                $("#thelist").append( '<div id="' + file.id + '" class="item">' +
                    '<h4 class="info">' + file.name + '</h4>' +
                    '<p class="state">等待上传...</p>' +
                    '</div>' );
            });

            uploader.on( 'uploadSuccess',function(file,response){
                if(response.status == 201){
                    $("#modal-form-excel").modal('hide');
                    var length=response.details.length;
                    var detailMessage="";
                    $.each(response.details,function (index,resultInfo) {
                        if(index!=length-1){
                            detailMessage+=resultInfo.message+"   ";
                        }
                        else{
                            detailMessage+=resultInfo.message;
                        }
                    });
                    swal(response.message+":"+response.data+"条", detailMessage, "success");
                    $("#exampleTableEvents").bootstrapTable('refresh');
                }
                else{
                    if(response.status!=null)
                        swal("添加失败",response.message, "error");
                    else
                        to500();
                }
            });

            uploader.on('uploadError', function(file,response) {
                to500();
            });
        });

        $("#excelAddButton").on('click', function() {
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            uploader.options.server="/v1/singleChoiceQuestion/file";
            uploader.upload();
        });

        //关闭模态框销毁WebUploader，解决再次打开模态框时按钮越变越大问题
        $('#modal-form-excel').on('hide.bs.modal', function () {

            $("#thelist").children().remove();
            uploader.destroy();
        });
    </script>

    <script>
            var contentAddEditor = new Simditor({
                textarea: $('#content_addEditor'),
                toolbarHidden:true
            });

            var optionAEditor = new Simditor({
                textarea: $('#optionA_add'),
                toolbarHidden:true
            });

            var optionBEditor = new Simditor({
                textarea: $('#optionB_add'),
                toolbarHidden:true
            });
            var optionCEditor = new Simditor({
                textarea: $('#optionC_add'),
                toolbarHidden:true
            });
            var optionDEditor = new Simditor({
                textarea: $('#optionD_add'),
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
