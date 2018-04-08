<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>成绩</title>
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
    <link href="/css/plugins/webuploader/webuploader.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/css/plugins/jasny/jasny-bootstrap.min.css" rel="stylesheet">

    <!-- Sweet Alert -->
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!--开始 评分table -->
        <div class="ibox float-e-margins">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>评分</h5>
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
                                        <button class="btn btn-primary m-t-xs btn-lg" type="button" onclick="searchExam()"><i class="fa fa-search"></i>&nbsp;&nbsp;搜索</button>
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
                            </div>
                            <!-- End Example Events -->
                        </div>
                    </div>
                    <div class="row row-lg">
                        <div class="col-sm-12 ">
                            <table id="exampleTableEvents" data-height="400" data-mobile-responsive="true">
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 结束 评分table -->
    </div>

    <!-- 学生成绩modal -->
    <div class="modal inmodal fade" id="modal-form-StudentScore" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h5 class="modal-title">学生成绩</h5>
                </div>
                <div class="modal-body">
                    <div class="ibox-content">
                        <div class="row row-lg">

                            <div class="col-sm-12">
                                <!-- Example Events -->
                                <div class="example-wrap">
                                    <h4 class="example-title">查询</h4>
                                    <div class="ibox float-e-margins ">
                                        <form role="form" class="form-inline " id="student-score-form">
                                            <input type="hidden" name="isGraded" id="isGraded_score">
                                            <input type="hidden" name="examId" id="examId_score">
                                            <div class="form-group m-l-none" >
                                                <label for="studentId_score" class="sr-only">学号</label>
                                                <input type="text" placeholder="请输入学号" id="studentId_score" class="form-control" style="width: 100px">
                                            </div>
                                            <div class="form-group m-l-none" >
                                                <label for="studentName_score" class="sr-only">名字</label>
                                                <input type="text" placeholder="请输入名字" id="studentName_score" class="form-control" style="width: 100px">
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
                                            <button class="btn btn-primary m-t-xs btn-lg" type="button" onclick="searchStudentScore()"><i class="fa fa-search"></i>&nbsp;&nbsp;搜索</button>
                                        </form>
                                    </div>
                                    <div class=" float-e-margins ">
                                        <form role="form" class="form-inline ">
                                            <div class=" row row-lg">
                                                <div class=" col-sm-1 m-t-sm">
                                                    <span class="label label-success ">状态</span>
                                                </div>
                                                <div class="col-lg-10 m-t-xs m-l-n-xl" >
                                                    <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchStudentData(this,'isGraded')" data-isGraded="">全部</button>
                                                    <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchStudentData(this,'isGraded')" data-isGraded="1">已完成评分</button>
                                                    <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchStudentData(this,'isGraded')" data-isGraded="0">未完成评分</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="example">
                                        <table id="studentScoreTableEvents" data-height="400" data-mobile-responsive="true">
                                        </table>
                                    </div>
                                </div>
                                <!-- End Example Events -->
                            </div>
                        </div>
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 学生成绩详情modal -->
    <div id="modal-form-StudentScoreDetails" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="student-update-form">
                            <div class="ibox-content">
                                <div class="panel blank-panel">

                                    <div class="panel-heading">
                                        <div class="panel-options">

                                            <ul class="nav nav-tabs" id="nav_tabs_questions">

                                            </ul>
                                        </div>
                                    </div>

                                    <div class="panel-body">
                                        <div class="tab-content" id="content_questions">

                                        </div>
                                    </div>


                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>


    <!-- 修改分数modal -->
    <div id="modal-form-updateScore" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="score-update-form">
                            <input type="hidden" id="exam_student_id" value="">
                            <input type="hidden" name="_method" value="put">
                            <input type="hidden" id="testPaperQuestionId" value="">
                            <div class="form-group">
                                <label class="col-sm-3 control-label">分数：</label>
                                <div class="col-sm-6 " >
                                    <input type="text" class="form-control" data-mask="9.99" placeholder="" id="score_update" name="score">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="updateQuestionScore()">修改</button>
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

    <script src="/js/plugins/webuploader/webuploader.min.js"></script>

    <!-- Spinner -->
    <script src="/js/plugins/spinner/jquery.spinner.min.js"></script>

    <!-- iCheck -->
    <script src="/js/plugins/iCheck/icheck.min.js"></script>

    <!-- DateTime picker -->
    <script src="/js/plugins/datetimepicker/bootstrap-datetimepicker.min.js"></script>
    <script src="/js/plugins/datetimepicker/bootstrap-datetimepicker.zh-CN.js"></script>

    <!-- Sweet alert -->
    <script src="/js/plugins/sweetalert/sweetalert.min.js"></script>

    <!-- Input Mask-->
    <script src="/js/plugins/jasny/jasny-bootstrap.min.js"></script>

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
            return $.map($('#studentScoreTableEvents').bootstrapTable('getSelections'), function (row) {
                return row.id
            });
        }

        function searchExam() {
            $("#exampleTableEvents").bootstrapTable('refresh');
        }

        function searchStudentScore() {

            //设置url
            var newUrl="/v1/score/exam/"+$("#examId_score").val();
            $("#studentScoreTableEvents").bootstrapTable('refresh',{url:newUrl});
        }

        function addStudent(){
            var sels = $('#studentScoreTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("必须至少选择一个学生才能分配!");
                return ;
            }

            var exam = $('#exampleTableEvents').bootstrapTable('getSelections');

            var ids = getSelectionsIds(sels);
            swal({
                    title: "确定将所选学生分配至对应考试吗",
                    text: "系统将随机为学生分配场次！",
                    type: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#DD6B55",
                    confirmButtonText: "是的，我要分配！",
                    cancelButtonText: "让我再考虑一下…",
                    closeOnConfirm: false,
                    closeOnCancel: false
                },
                function (isConfirm) {
                    if (isConfirm) {
                        var params = {"studentIds":ids};
                        $.ajax({
                            type: "POST",
                            url: "/v1/exam/"+exam[0].id+"/students",
                            data: params,
                            success: function(data){
                                if(data.status == 201){
                                    swal(data.message, "您已经将所选学生分配至对应考试。", "success");
                                    $("#studentScoreTableEvents").bootstrapTable('refresh');
                                }
                                else{
                                    swal(data.message, "无法分配。", "error");
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
                        swal("已取消", "您取消了分配操作！", "error");
                    }
                });
        }

        function searchData(e,data){
            var value=$(e).attr("data-"+data);
            $("#exam-search-form [name='"+data+"']").val(value);
            $("#exampleTableEvents").bootstrapTable('refresh');
        }

        function searchStudentData(e,data){
            var value=$(e).attr("data-"+data);
            $("#isGraded_score").val(value);
            $("#studentScoreTableEvents").bootstrapTable('refresh');
        }

        //获取查询参数
        function queryParams(params){
            var temp={
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                courseId:$("#course").val().trim(),
                schoolYearId:$("#schoolYear").val(),
                term:$("#term").val().trim(),
                status:2
            };
            return temp;
        }

        function studentQueryParams(params) {
            var temp={
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                studentStudentId:$("#studentId_score").val().trim(),
                studentName:$("#studentName_score").val().trim(),
                studentClassId:$("#class_search").val(),
                isGraded:$("#isGraded_score").val()
            };
            return temp;
        }


        function addDetailsButton(value,row,index){
            return ['<button class="btn btn-outline btn-info" type="button" id="examStudentScoreDetails">学生成绩查看</button>',
                '<button class="btn btn-outline btn-success m-l-xs" type="button" id="autoGrade">智能评分</button>'].join("");


        }

        function addDetailsButtonStudentScore(value,row,index){
            return ['<button class="btn btn-outline btn-info" type="button" id="openScoreDetailsModal">详情</button>'].join("");
        }

        window.operateEvents={
            "click #examStudentScoreDetails":function (e,value,row,index) {
                $("#examId_score").val(row.id);
                //设置url
                var newUrl="/v1/score/exam/"+row.id;
                $("#studentScoreTableEvents").bootstrapTable('refresh',{url:newUrl});

                $("#modal-form-StudentScore").modal('show');

            },
            "click #autoGrade":function (e,value,row,index) {
                $.ajax({
                    type: "POST",
                    url: "/v1/score/exam/"+row.id,
                    data: {_method:"put"},
                    success: function(data){
                        if(data.status == 201){
                            swal(data.message, "已成功为该考试智能评分", "success");
                            $("#exampleTableEvents").bootstrapTable('refresh');
                        }
                        else{
                            swal("评分失败",data.message, "error");
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
            "click #openScoreDetailsModal":function (e,value,row,index) {

                $("#exam_student_id").val(row.id);

/*                var studentId=row.studentStudentId;
                var studentName=row.studentName;
                var studentClassId=row.studentClassId;
                $("#modal-form-updateStudent [name=studentId]").val(studentId);
                $("#modal-form-updateStudent [name=name]").val(studentName);
                $("#studentClassId_update").val(studentClassId).trigger("chosen:updated");
                $("#studentClassId_update_chosen").width(270);
                $("#studentId_update").val(row.studentId);*/

                $("#nav_tabs_questions").children().remove();
                $("#content_questions").children().remove();

                $.ajax({
                    type: "GET",
                    url: "/v1/score/exam/student/"+row.id+"/testPaper",
                    success: function(data){
                        if(data.status == 200){
                            var testPaper=data.data;

                            if(testPaper.singleChoiceQuestions!=null){

                                $("#nav_tabs_questions").append('<li class=""><a data-toggle="tab" href="list.jsp#tab-0">单选题</a> </li>');

                                var html="";

                                html+='<div id="tab-0" class="tab-pane ">';
                                html+='<form class="form-horizontal" id="singleChoiceQuestions-form">';
                                html+='<div class="row">';
                                html+='<div class="col-sm-12">';
                                html+='<div class="wrapper wrapper-content">';
                                html+='<div class="row animated fadeInRight">';
                                html+='<div class="col-sm-12">';
                                html+='<div class="ibox float-e-margins">';
                                html+='<div class="text-center float-e-margins p-md">';
                                html+='<span>每道题目分数：</span>';
                                html+='<a href="#" class="btn btn-xs btn-success">'+testPaper.singleChoiceQuestions[0].questionScore+'</a>';
                                html+='</div>';
                                html+='<div class="" id="ibox-content">';

                                html+='<div id="vertical-timeline" class="vertical-container light-timeline">';

                                $.each(testPaper.singleChoiceQuestions,function (index,question) {
                                    html+='<div class="vertical-timeline-block">';
                                    html+='<div class="vertical-timeline-icon navy-bg">';
                                    html+='<i class="">'+(index+1)+'</i>';
                                    html+='</div>';

                                    html+='<div class="vertical-timeline-content gray-bg">';
                                    html+='<p>'+question.questionContent;
                                    html+='</p>';
                                    html+='<div class="checkbox checkbox-success checkbox-circle">';
                                    html+='<input id="singleChoiceQuestion'+(index+1)+'A" value="A" type="radio" disabled="" name="singleChoiceQuestionAnswer['+(index+1)+']">';
                                    html+='<label for="singleChoiceQuestion'+(index+1)+'A">';
                                    html+='A:'+question.optionA;
                                    html+='</label>';
                                    html+='</div>';
                                    html+='<div class="checkbox checkbox-success checkbox-circle">';
                                    html+='<input id="singleChoiceQuestion'+(index+1)+'B" value="B" type="radio" disabled="" name="singleChoiceQuestionAnswer['+(index+1)+']">';
                                    html+='<label for="singleChoiceQuestion'+(index+1)+'B">';
                                    html+='B:'+question.optionB;
                                    html+='</label>';
                                    html+='</div>';
                                    html+='<div class="checkbox checkbox-success checkbox-circle">';
                                    html+='<input id="singleChoiceQuestion'+(index+1)+'C" value="C" type="radio" disabled="" name="singleChoiceQuestionAnswer['+(index+1)+']">';
                                    html+='<label for="singleChoiceQuestion'+(index+1)+'C">';
                                    html+='C:'+question.optionC;
                                    html+='</label>';
                                    html+='</div>';
                                    html+='<div class="checkbox checkbox-success checkbox-circle">';
                                    html+='<input id="singleChoiceQuestion'+(index+1)+'D" value="D" type="radio" disabled="" name="singleChoiceQuestionAnswer['+(index+1)+']">';
                                    html+='<label for="singleChoiceQuestion'+(index+1)+'D">';
                                    html+='D:'+question.optionD;
                                    html+='</label>';
                                    html+='</div>';
                                    html+='<p>得分：'+testPaper.singleChoiceQuestionAnswerScore[(index+1)]+'</p>';

                                    html+='<button class="btn btn-primary center-block" type="button" id="btn_singleChoiceQuestion'+(index+1)+'" value="'+question.id+'" onclick="openUpdateQuestionScore(this)">修改分数</button>';

                                    html+='</div></div>';
                                });
                                html+='</div></div></div></div></div></div></div></div></form></div>';
                                $("#content_questions").append(html);
                            }
                            if(testPaper.trueOrFalseQuestions!=null){
                                $("#nav_tabs_questions").append('<li class=""><a data-toggle="tab" href="list.jsp#tab-1">判断题</a> </li>');

                                var html='';

                                html+='<div id="tab-1" class="tab-pane ">';
                                html+='  <form id="trueOrFalseQuestions-form">';
                                html+='<div class="row">';
                                html+='<div class="col-sm-12">';
                                html+='<div class="wrapper wrapper-content">';
                                html+='<div class="row animated fadeInRight">';
                                html+='<div class="col-sm-12">';
                                html+='<div class="ibox float-e-margins">';
                                html+='<div class="text-center float-e-margins p-md">';
                                html+='<span>每道题目分数：</span>';
                                html+='<a href="#" class="btn btn-xs btn-success">'+testPaper.trueOrFalseQuestions[0].questionScore+'</a>';
                                html+='</div>';
                                html+='<div class="" >';
                                html+='<div  class="vertical-container light-timeline">';

                                $.each(testPaper.trueOrFalseQuestions,function (index,question) {

                                    html+='<div class="vertical-timeline-block">';
                                    html+='<div class="vertical-timeline-icon navy-bg">';
                                    html+='<i class="">'+(index+1)+'</i>';
                                    html+='</div>';

                                    html+='<div class="vertical-timeline-content gray-bg">';
                                    html+='<p>'+question.questionContent;
                                    html+='</p>';
                                    html+='<div class="checkbox checkbox-success checkbox-circle">';
                                    html+='<input id="trueOrFalseQuestion'+(index+1)+'-1" value="1" type="radio" disabled="" name="trueOrFalseQuestionAnswer['+(index+1)+']">';
                                    html+='<label for="trueOrFalseQuestion'+(index+1)+'-1">';
                                    html+='√';
                                    html+='</label>';
                                    html+='</div>';
                                    html+='<div class="checkbox checkbox-success checkbox-circle">';
                                    html+='<input id="trueOrFalseQuestion'+(index+1)+'-0" value="B" type="radio" disabled="" name="trueOrFalseQuestionAnswer['+(index+1)+']">';
                                    html+='<label for="trueOrFalseQuestion'+(index+1)+'-0">';
                                    html+='×';
                                    html+='</label></div>';
                                    html+='<p>得分：'+testPaper.trueOrFalseQuestionAnswerScore[(index+1)]+'</p>';


                                    html+='<button class="btn btn-primary center-block" type="button" id="btn_trueOrFalseQuestion'+(index+1)+'" value="'+question.id+'" onclick="openUpdateQuestionScore(this)">修改分数</button>';
                                    html+='</div></div>';
                                });
                                html+="</div></div></div></div></div></div></div></div></form></div>";

                                $("#content_questions").append(html);
                            }
                            if(testPaper.fillInBlankQuestions!=null){
                                $("#nav_tabs_questions").append('<li class=""><a data-toggle="tab" href="list.jsp#tab-2">填空题</a> </li>');

                                var html="";

                                html+='<div id="tab-2" class="tab-pane ">';
                                html+='<form id="fillInBlankQuestions-form">';
                                html+='<div class="row">';
                                html+='<div class="col-sm-12">';
                                html+='<div class="wrapper wrapper-content">';
                                html+='<div class="row animated fadeInRight">';
                                html+='<div class="col-sm-12">';
                                html+='<div class="ibox float-e-margins">';
                                html+='<div class="text-center float-e-margins p-md">';
                                html+='<span>每个空分数：</span>';
                                html+='<a href="#" class="btn btn-xs btn-success">'+parseFloat(testPaper.fillInBlankQuestions[0].questionScore)/parseFloat(testPaper.fillInBlankQuestions[0].blankNum)+'</a>';
                                html+='</div>';
                                html+='<div class="" >';
                                html+='<div  class="vertical-container light-timeline">';

                                $.each(testPaper.fillInBlankQuestions,function (index,question) {

                                    html+='<div class="vertical-timeline-block">';
                                    html+='<div class="vertical-timeline-icon navy-bg">';
                                    html+='<i class="">'+(index+1)+'</i>';
                                    html+='</div>';

                                    html+='<div class="vertical-timeline-content gray-bg">';
                                    html+='<p>'+question.questionContent;
                                    html+='</p>';
                                    html+='<div class="form-group">';
                                    html+='<div class="col-md-4">';

                                    for(var i=1;i<=question.blankNum;i++){
                                        html+='<input type="text" placeholder="" class="form-control  m-b" disabled="" name="fillInBlankQuestionAnswer['+(index+1)+']['+(i-1)+']" id="fillInBlankQuestion'+(index+1)+'-'+i+'">';
                                    }

                                    html+='<p>得分：'+testPaper.fillInBlankQuestionAnswerScore[(index+1)]+'</p>';

                                    html+='<button class="btn btn-primary center-block" type="button" id="btn_fillInBlankQuestion'+(index+1)+'" value="'+question.id+'" onclick="openUpdateQuestionScore(this)">修改分数</button>';
                                    html+='</div></div></div></div>';
                                });

                                html+='</div></div></div></div></div></div></div></div></form></div>';

                                $("#content_questions").append(html);
                            }
                            if(testPaper.programQuestions!=null){
                                $("#nav_tabs_questions").append('<li class=""><a data-toggle="tab" href="list.jsp#tab-3">程序题</a> </li>');

                                var html='';

                                var programQuestions=testPaper.programQuestions;
                                console.log(programQuestions);
                                html+='<div id="tab-3" class="tab-pane ">';
                                html+='  <form id="programQuestions-form">';
                                html+='<div class="row">';
                                html+='<div class="col-sm-12">';
                                html+='<div class="wrapper wrapper-content">';
                                html+='<div class="row animated fadeInRight">';
                                html+='<div class="col-sm-12">';
                                html+='<div class="ibox float-e-margins">';
                                html+='<div class="text-center float-e-margins p-md">';
                                html+='<span>每道题目分数：</span>';
                                html+='<a href="#" class="btn btn-xs btn-success">'+testPaper.programQuestions[0].questionScore+'</a>';
                                html+='</div>';
                                html+='<div class="" >';
                                html+='<div  class="vertical-container light-timeline">';

                                $.each(testPaper.programQuestions,function (index,question) {

                                    html+='<div class="vertical-timeline-block">';
                                    html+='<div class="vertical-timeline-icon navy-bg">';
                                    html+='<i class="">'+(index+1)+'</i>';
                                    html+='</div>';

                                    html+='<div class="vertical-timeline-content gray-bg">';
                                    html+='<p>'+question.questionContent;
                                    html+='</p>';
                                    html+='<textarea disabled="disabled" id="programQuestion'+(index+1)+'" style="resize: none;width: 100%;height: 200px" ></textarea>';


                                    html+='<p>得分：'+testPaper.programQuestionAnswerScore[(index+1)]+'</p>';


                                    html+='<button class="btn btn-primary center-block" type="button" id="btn_programQuestion'+(index+1)+'" value="'+question.id+'" onclick="openUpdateQuestionScore(this)">修改分数</button>';
                                    html+='</div></div>';
                                });
                                html+="</div></div></div></div></div></div></div></div></form></div>";

                                $("#content_questions").append(html);
                            }

                            $($("#nav_tabs_questions").children().get(0)).addClass("active");
                            $($("#content_questions").children().get(0)).addClass("active");

                            if(testPaper.singleChoiceQuestionAnswer!=null){

                                $.each(testPaper.singleChoiceQuestionAnswer,function (index,answer) {
                                    if(answer=='A'){
                                        $("#singleChoiceQuestion"+index+"A").attr("checked","checked");
                                    }else if(answer=='B'){
                                        $("#singleChoiceQuestion"+index+"B").attr("checked","checked");
                                    }else if(answer=='C'){
                                        $("#singleChoiceQuestion"+index+"C").attr("checked","checked");
                                    }else if(answer=='D'){
                                        $("#singleChoiceQuestion"+index+"D").attr("checked","checked");
                                    }



                                });
                            }

                            if(testPaper.trueOrFalseQuestionAnswer!=null){
                                $.each(testPaper.trueOrFalseQuestionAnswer,function (index,answer) {
                                    if(answer=='0'){
                                        $("#trueOrFalseQuestion"+index+"-0").attr("checked","checked");
                                    }else if(answer=='1'){
                                        $("#trueOrFalseQuestion"+index+"-1").attr("checked","checked");
                                    }
                                });
                            }

                            if(testPaper.fillInBlankQuestionAnswer!=null){

                                $.each(testPaper.fillInBlankQuestionAnswer,function (index,q) {

                                    for(var i=0;i<q.length;i++){
                                        $("#fillInBlankQuestion"+index+"-"+(i+1)).val(q[i]);
                                    }

                                });
                            }

                            if(testPaper.programQuestionAnswer!=null){
                                $.each(testPaper.programQuestionAnswer,function (index,answer) {
                                    $("#programQuestion"+index).text(answer);
                                });
                            }


                            $("#modal-form-StudentScoreDetails").modal('show');
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

        function openUpdateQuestionScore(e) {
            $("#testPaperQuestionId").val($(e).val());
            $("#modal-form-updateScore").modal('show');
        }

        function updateQuestionScore(){

            var score=$("#score_update").val();

            if(score==""){
                layer.msg("分数不能为空");
                return ;
            }

            var examStudentId=$("#exam_student_id").val();
            var testPaperQuestionId=$("#testPaperQuestionId").val();

            $.ajax({
                type: "POST",
                url: "/v1/score/exam/student/"+examStudentId+"/testPaperQuestion/"+testPaperQuestionId,
                data: decodeURIComponent($("#score-update-form").serialize().replace(/\+/g,"")),
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-updateScore").modal('hide');
                        $("#modal-form-StudentScoreDetails").modal('hide');
                        swal(data.message, "您已经成功修改了分数。", "success");
                        $("#studentScoreTableEvents").bootstrapTable('refresh');
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
                    }, {
                        field: 'detailds',
                        title: '详情',
                        events:operateEvents,
                        formatter:addDetailsButton
                    }
                ]
            });

            $('#studentScoreTableEvents').bootstrapTable({
                url: "",
                search: false,
                pagination: true,
                showRefresh: true,
                showToggle: true,
                showColumns: true,
                iconSize: 'outline',
                idField:'id',
                queryParams:studentQueryParams,
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
                        field : 'num',
                        title : '序号',
                        formatter : ES.showNumber
                    },{
                        field: 'studentStudentId',
                        title: '学号'
                    },{
                        field: 'studentName',
                        title: '名字'
                    }
                    ,{
                        field: 'score',
                        title: '成绩'
                    },{
                        field: 'status',
                        title: '状态',
                        formatter : ES.formatExamStudentStatus
                    },{
                        field: 'isGraded',
                        title: '评分',
                        formatter : ES.formatIsGraded
                    },{
                        field: 'detailds',
                        title: '答题',
                        events:operateEvents,
                        formatter:addDetailsButtonStudentScore
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

        $("#modal-form-StudentScore").on('shown.bs.modal',function () {
            $("#class_search_chosen").width(200);
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
