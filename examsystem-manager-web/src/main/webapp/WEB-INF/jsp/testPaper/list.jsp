<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>我的组卷</title>
    <meta name="keywords" content="在线考试系统,吉林大学珠海学院考试">
    <meta name="description" content="在线考试系统是一个服务于教师与学生的学习平台">

    <link rel="shortcut icon" href="favicon.ico"> <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
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

        <!--开始 试卷table -->
        <div class="ibox float-e-margins">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>我的组卷</h5>
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
                                    <form role="form" class="form-inline " id="testPaper-search-form">
                                        <input type="hidden" name="term" id="term">
                                        <div class="form-group m-l-none" >
                                            <label for="name" class="sr-only">名字</label>
                                            <input type="text" placeholder="请输入名字" id="name" class="form-control">
                                        </div>
                                        <div class="form-group " >
                                                <select data-placeholder="选择课程..." class="form-control chosen-select " style="width:150px;" tabindex="2" id="course" name="courseId">
                                                    <c:forEach items="${courses}" var="course" >
                                                        <option value="${course.id}" hassubinfo="true">${course.name}</option>
                                                    </c:forEach>
                                                </select>
                                        </div>
                                        <div class="form-group " >
                                            <select data-placeholder="选择学年..." class="form-control chosen-select " style="width:150px;" tabindex="2" id="schoolYear" name="schoolYearId">
                                                <option value="">请选择学年</option>
                                                <c:forEach items="${schoolYears}" var="schoolYear">
                                                    <option value="${schoolYear.id}" hassubinfo="true">${schoolYear.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <button class="btn btn-primary m-t-xs btn-lg" type="button" onclick="searchTestPaper()"><i class="fa fa-search"></i>&nbsp;&nbsp;搜索</button>
                                    </form>
                                </div>
                                <div class="ibox float-e-margins ">
                                    <form role="form" class="form-inline ">
                                        <div class="form-group">
                                            <div class=" col-lg-2 m-t-sm">
                                                <span class="label label-success ">学期</span>
                                            </div>
                                            <div class=" col-lg-10 m-t-xs" >
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'term')" data-term="">全部</button>
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'term')" data-term="1">上</button>
                                                <button type="button" class="btn btn-outline btn-default m-l-sm" onclick="searchData(this,'term')" data-term="0">下</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                                <div class="example">
                                    <div class="btn-group hidden-xs" id="exampleTableEventsToolbar" role="group">
                                        <button type="button" class="btn btn-outline btn-default" onclick="openUpdateTestPaperModal()">
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
        <!-- 结束 试卷table -->
    </div>
    <!-- 修改试卷modal -->
    <div id="modal-form-update" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="testPaper-update-form">
                            <input type="hidden" name="id" value="" id="id_update">
                            <p>欢迎修改1试卷(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">名称：</label>
                                <div class="col-sm-6">
                                    <input type="text" placeholder="试卷名称" id="name_update" class="form-control" name="name">
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">学年：</label>
                                <div class=" col-sm-6">
                                    <select data-placeholder="选择学年..." class="form-control chosen-select " style="width:50px;" tabindex="2" id="schoolYear_update"  name="schoolYearId">
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
                                    <input type="text" class="form-control" data-mask="9.9" placeholder="" id="singleChoiceScore_update" name="singleChoiceScore">
                                    <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 每道题分数</span>
                                </div>
                            </div>
                            <div class="form-group " id="trueOrFalseQuestion">
                                <label class="col-sm-3 control-label">判断题：</label>
                                <div class=" col-sm-6">
                                    <input type="text" class="form-control" data-mask="9.9" placeholder="" id="trueOrFalseScore_update" name="trueOrFalseScore">
                                    <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 每道题分数</span>
                                </div>
                            </div>
                            <div class="form-group " id="fillInBlankQuestion">
                                <label class="col-sm-3 control-label">填空题：</label>
                                <div class=" col-sm-6">
                                    <input type="text" class="form-control" data-mask="9.9" placeholder="" id="fillInBlankScore_update" name="fillInBlankScore">
                                    <span class="help-block m-b-none"><i class="fa fa-info-circle"></i> 每个空分数</span>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">总分：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" name="score" id="score_update">0</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="updateTestPaper()">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 查看详情试卷modal -->
    <div id="modal-form-details" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="testPaper-details-form">
                            <p>欢迎查看该试卷(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">课程：</label>
                                <div class="col-sm-6">
                                    <p class="form-control-static" id="course_details"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">学年：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="schoolYear_details"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">学期：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="term_details"></p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 试卷题目详情modal -->
    <div id="modal-form-questionDetails" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="question-details-form">
                            <input type="hidden" id="testPaperId" value="">
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
                                <div class="btn-group hidden-xs" role="group">
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

    <!-- iCheck -->
    <script src="/js/plugins/iCheck/icheck.min.js"></script>

    <!-- Sweet alert -->
    <script src="/js/plugins/sweetalert/sweetalert.min.js"></script>

    <!-- Input Mask-->
    <script src="/js/plugins/jasny/jasny-bootstrap.min.js"></script>

    <!-- Bootstrap table -->
    <script src="/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
    <script src="/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
    <script src="/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>


    <script type="text/javascript">

        var singleChoiceQuestionNum=0;
        var trueOrFalseQuestionNum=0;
        var fillInBlankQuestionNum=0;

        $("#course").val("${course.id}").trigger("chosen:updated");

        $('.i-checks').iCheck({
            checkboxClass: 'icheckbox_square-green',
            radioClass: 'iradio_square-green',
        });

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
            $("#answer_add").val("true").trigger("chosen:updated");
            $("#difficulty_add").val("0").trigger("chosen:updated");
        }

        function searchTestPaper() {
            $("#exampleTableEvents").bootstrapTable('refresh');
        }

        function searchData(e,data){
            var value=$(e).attr("data-"+data);

            $("#testPaper-search-form [name='"+data+"']").val(value);
            $("#exampleTableEvents").bootstrapTable('refresh');
        }

        function openUpdateTestPaperModal(){
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("必须选择一张试卷才能修改!");
                return ;
            }else if(sels.length >1){
                layer.msg("只能选择一张试卷!");
                return ;
            }
            var name=sels[0].name;
            var id=sels[0].id;
            var score=sels[0].score;
            var corseId=sels[0].corseId;
            var schoolYearId=sels[0].schoolYearId;
            var term=sels[0].term;

            //发送ajax请求题目详情
            $.ajax({
                type: "GET",
                url: "/v1/testPaper/"+id,
                data: null,
                success: function(data){
                    if(data.status == 200){

                        $("#testPaper-update-form [name=name]").val(name);
                        $("#testPaper-update-form [name=id]").val(id);

                        $("#testPaper-update-form").attr('selected',false);
                        $(".i-checks").iCheck("uncheck");

                        $("#course_update").val(corseId).trigger("chosen:updated");
                        $("#schoolYear_update").val(schoolYearId).trigger("chosen:updated");
                        $("#score_update").text(score);

                        $("#testPaper-update-form input[value='"+term+"']").iCheck('check');

                        //赋值
                        var testPaperDetails=data.data;

                        singleChoiceQuestionNum=testPaperDetails.singleChoiceQuestionNum;
                        trueOrFalseQuestionNum=testPaperDetails.trueOrFalseQuestionNum;
                        fillInBlankQuestionNum=testPaperDetails.fillInBlankQuestionNum;

                        $("#singleChoiceQuestion").show();
                        $("#trueOrFalseQuestion").show();
                        $("#fillInBlankQuestion").show();
                        $("#testPaper-update-form input[name='singleChoiceScore']").val("0.0");
                        $("#testPaper-update-form input[name='trueOrFalseScore']").val("0.0");
                        $("#testPaper-update-form input[name='fillInBlankScore']").val("0.0");
                        var singleChoiceQuestionScore=testPaperDetails.singleChoiceQuestionScore;
                        var trueOrFalseQuestionScore=testPaperDetails.trueOrFalseQuestionScore;
                        var fillInBlankQuestionScore=testPaperDetails.fillInBlankQuestionScore;
                        if(singleChoiceQuestionScore==null)
                            $("#singleChoiceQuestion").hide();
                        else
                            $("#testPaper-update-form input[name='singleChoiceScore']").val(singleChoiceQuestionScore.toFixed(1));

                        if(trueOrFalseQuestionScore==null)
                            $("#trueOrFalseQuestion").hide()
                        else
                            $("#testPaper-update-form input[name='trueOrFalseScore']").val(trueOrFalseQuestionScore.toFixed(1));

                        if(fillInBlankQuestionScore==null)
                            $("#fillInBlankQuestion").hide()
                        else
                            $("#testPaper-update-form input[name='fillInBlankScore']").val(fillInBlankQuestionScore.toFixed(1));

                        $("#modal-form-update").modal('show');
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

        function removeQuestion() {
            var checkedQuestions=$("#content_questions .active .checked");
            if(checkedQuestions.length==0){
                layer.msg("请选择题目，再进行移除");
                return;
            }

            var ids=[];
            $.each(checkedQuestions,function (index,question) {
                //$(question).parent().parent().remove();
                //构造ids;
                console.log($(question).children().val());
                ids.push($(question).children().val());
            });

            var params = {"ids":ids,_method:'delete'};

            $.ajax({
                type: "POST",
                url: "/v1/testPaper/"+$("#testPaperId").val()+"/question",
                data: params,
                success: function(data){
                    if(data.status == 204){
                        $.each(checkedQuestions,function (index,question) {
                            $(question).parent().parent().remove();
                        });
                    }
                    else{
                        swal(data.message, "无法移除这些题目。", "error");
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
        function upQuestion() {
            var checkedQuestions=$("#content_questions .active .checked");
            if(checkedQuestions.length==0){
                layer.msg("请选择题目再进行移动");
                return;
            }
            if(checkedQuestions.length>1){
                layer.msg("只允许移动一个题目");
                return;
            }

            var questionId;

            var go=true;

            var checkedQuestion=$(checkedQuestions[0]).parent().parent()[0];
            var questions=$("#content_questions .active .ibox");
            $.each(questions,function (index,question) {

                console.log(index);
                if($(question)[0]==checkedQuestion){
                    if(index==0){
                        layer.msg("该题目已在最前，无法移动");
                        go=false;
                        return;
                    }else{
                        alert($(checkedQuestions).children().val());
                        questionId=$(checkedQuestions).children().val();
                    }
                }
            });

            if(go){
                var params = {"order":1,_method:'put'};

                $.ajax({
                    type: "POST",
                    url: "/v1/testPaper/"+$("#testPaperId").val()+"/question/"+questionId,
                    data: params,
                    success: function(data){
                        if(data.status == 201){
                            $.each(questions,function (index,question) {
                                console.log($(question));
                                if($(question)[0]==checkedQuestion){
                                    //上移
                                    var pre=$(question).prev()[0];
                                    $(question).after(pre);
                                }
                            });
                        }
                        else{
                            swal(data.message, "无法移动题目。", "error");
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
        function downQuestion() {
            var checkedQuestions=$("#content_questions .active .checked");
            if(checkedQuestions.length==0){
                layer.msg("请选择题目再进行移动");
                return;
            }
            if(checkedQuestions.length>1){
                layer.msg("只允许移动一个题目");
                return;
            }

            var questionId;

            var go=true;

            var checkedQuestion=$(checkedQuestions[0]).parent().parent()[0];
            console.log(checkedQuestion);
            var questions=$("#content_questions .active .ibox");
            $.each(questions,function (index,question) {

                console.log($(question));
                if($(question)[0]==checkedQuestion){
                    if(index==questions.length-1){
                        layer.msg("该题目已在最后，无法移动");
                        go=false;
                        return;
                    }
                    else{
                        alert($(checkedQuestions).children().val());
                        questionId=$(checkedQuestions).children().val();
                    }
                }
            });

            if(go){
                var params = {"order":0,_method:'put'};

                $.ajax({
                    type: "POST",
                    url: "/v1/testPaper/"+$("#testPaperId").val()+"/question/"+questionId,
                    data: params,
                    success: function(data){
                        if(data.status == 201){
                            $.each(questions,function (index,question) {
                                console.log($(question));
                                if($(question)[0]==checkedQuestion){
                                    //下移
                                    var next=$(question).next()[0];
                                    $(question).before(next);
                                }
                            });
                        }
                        else{
                            swal(data.message, "无法移动该题目。", "error");
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

        function updateTestPaper(){
            //非空校验
            if($("#testPaper-update-form [name=name]").val().trim()==''){
                layer.msg("试卷名字不能为空!");
                return ;
            }
            if($("#testPaper-update-form [name=schoolYearId]").val().trim()==""){
                layer.msg("学年名字不能为空!");
                return ;
            }

            var singleChoiceScore;
            if(singleChoiceQuestionNum!=null&&singleChoiceQuestionNum!=0){
                singleChoiceScore=$("#testPaper-update-form [name=singleChoiceScore]").val();
                if(singleChoiceScore==""||singleChoiceScore=="0.0"){
                    layer.msg("分数不能为空或0!");
                    return ;
                }
            }

            var trueOrFalseScore;
            if(trueOrFalseQuestionNum!=null&&trueOrFalseQuestionNum!=0){
                trueOrFalseScore=$("#testPaper-update-form [name=trueOrFalseScore]").val();
                if(trueOrFalseScore==""||trueOrFalseScore=="0.0"){
                    layer.msg("分数不能为空或0!");
                    return ;
                }
            }

            var fillInBlankScore;
            if(fillInBlankQuestionNum!=null&&fillInBlankQuestionNum!=0){
                fillInBlankScore=$("#testPaper-update-form [name=fillInBlankScore]").val();
                if(fillInBlankScore==""||fillInBlankScore=="0.0"){
                    layer.msg("分数不能为空或0!");
                    return ;
                }
            }

            //构造请求参数
            //添加试卷

            var id=$("#id_update").val();
            var name=$("#name_update").val();
            var score=$("#score_update").text();
            var schoolYearId=$("#schoolYear_update").val();
            var term=$('input:radio:checked').val();

            var param={};
            param.name=name;
            param.score=score;
            param.schoolYearId=schoolYearId;
            param.term=term;

            if(singleChoiceQuestionNum!=null&&singleChoiceQuestionNum!=0){
                param.singleChoiceQuestionNum=singleChoiceQuestionNum;
                param.singleChoiceQuestionScore=singleChoiceScore;
            }
            if(trueOrFalseQuestionNum!=null&&trueOrFalseQuestionNum!=0){
                param.trueOrFalseQuestionNum=trueOrFalseQuestionNum;
                param.trueOrFalseQuestionScore=trueOrFalseScore;
            }
            if(fillInBlankQuestionNum!=null&&fillInBlankQuestionNum!=0){
                param.fillInBlankQuestionNum=fillInBlankQuestionNum;
                param.fillInBlankQuestionScore=fillInBlankScore;
            }
            param._method='put';

            $.ajax({
                type: "POST",
                url: "/v1/testPaper/"+id,
                data: param,
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-update").modal('hide');
                        swal(data.message, "您已经永久修改了该试卷。", "success");
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

        //获取查询参数
        function queryParams(params){
            var temp={
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                name:$("#name").val(),
                courseId:$("#course").val(),
                schoolYearId:$("#schoolYear").val(),
                term:$("#term").val()
            };
            return temp;
        }

        function addDetailsButton(value,row,index){
            return ['<button class="btn btn-outline btn-info" type="button" id="testPaperDetails">查看</button>',
                '<button class="btn btn-outline btn-success m-l-xs" type="button" id="testPaperQuestion">试题</button>'].join("");
        }

        window.operateEvents={
            "click #testPaperDetails":function (e,value,row,index) {

                console.log(row);
                //发送ajax请求题目详情
                $.ajax({
                    type: "GET",
                    url: "/v1/testPaper/"+row.id,
                    data: null,
                    success: function(data){
                        if(data.status == 200){

                            //赋值
                            var testPaperDetails=data.data;

                            $("#course_details").text(testPaperDetails.courseName);
                            $("#schoolYear_details").text(testPaperDetails.schoolYearName);
                            if(testPaperDetails.term=='1'){
                                $("#term_details").text("上");
                            }else{
                                $("#term_details").text("下");
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
            "click #testPaperQuestion":function (e,value,row,index) {

                $("#nav_tabs_questions").children().remove();
                $("#content_questions").children().remove();

                $("#testPaperId").val(row.id);

                $.ajax({
                    type: "GET",
                    url: "/v1/testPaper/"+row.id+"/question",
                    data: null,
                    success: function(data){
                        if(data.status == 200){

/*                                <ul class="nav nav-tabs" id="nav_tabs_questions">

                                <div class="tab-content" id="content_questions">*/


                            //赋值
                            var testPaperDetails=data.data;

                            if(testPaperDetails!=null){
                                if(testPaperDetails.singleChoiceQuestions!=null){

                                    $("#nav_tabs_questions").append('<li class=""><a data-toggle="tab" href="list.jsp#tab-0">单选题</a> </li>');


                                    var html="";
                                    html+='<div id="tab-0" class="tab-pane">';
                                    html+='<div class="panel-body">';
                                    html+='<div class="row">';
                                    html+='<div class="col-lg-12" id="question-0">';

                                    $.each(testPaperDetails.singleChoiceQuestions,function (index,question) {
                                        var id=question.id;
                                        var content=question.questionContent;
                                        var optionA=question.optionA;
                                        var optionB=question.optionB;
                                        var optionC=question.optionC;
                                        var optionD=question.optionD;

                                        html+='<div class="ibox"><div class="ibox-content"><input type="checkbox" value="'+id+'" name="" class="i-checks" />';

                                        html+='<p>'+content+'</p><p>A:'+optionA+'</p><p>B:'+optionB+'</p><p>C:'+optionC+'</p><p>D:'+optionD+'</p>';


                                        html+='</div></div>';
                                    });

                                    html+='</div></div></div></div>';

                                    $("#content_questions").append(html);
                                }
                                if(testPaperDetails.trueOrFalseQuestions!=null){
                                    $("#nav_tabs_questions").append('<li class=""><a data-toggle="tab" href="list.jsp#tab-1">判断题</a> </li>');

                                    var html="";
                                    html+='<div id="tab-1" class="tab-pane">';
                                    html+='<div class="panel-body">';
                                    html+='<div class="row">';
                                    html+='<div class="col-lg-12" id="question-1">';

                                    $.each(testPaperDetails.trueOrFalseQuestions,function (index,question) {
                                        var id=question.id;
                                        var content=question.questionContent;

                                        html+='<div class="ibox"><div class="ibox-content"><input type="checkbox" value="'+id+'" name="" class="i-checks" />';

                                        html+='<p>'+content+'</p>';

                                        html+='</div></div>';
                                    });

                                    html+='</div></div></div></div>';

                                    $("#content_questions").append(html);
                                }
                                if(testPaperDetails.fillInBlankQuestions!=null){
                                    $("#nav_tabs_questions").append('<li class=""><a data-toggle="tab" href="list.jsp#tab-2">填空题</a> </li>');

                                    var html="";
                                    html+='<div id="tab-2" class="tab-pane">';
                                    html+='<div class="panel-body">';
                                    html+='<div class="row">';
                                    html+='<div class="col-lg-12" id="question-2">';

                                    $.each(testPaperDetails.fillInBlankQuestions,function (index,question) {
                                        var id=question.id;
                                        var content=question.questionContent;

                                        html+='<div class="ibox"><div class="ibox-content"><input type="checkbox" value="'+id+'" name="" class="i-checks" />';

                                        html+='<p>'+content+'</p>';

                                        html+='</div></div>';
                                    });

                                    html+='</div></div></div></div>';

                                    $("#content_questions").append(html);
                                }
                            }

                            $($("#nav_tabs_questions").children().get(0)).addClass("active");
                            $($("#content_questions").children().get(0)).addClass("active");

                            $('.i-checks').iCheck({
                                checkboxClass: 'icheckbox_square-green',
                                radioClass: 'iradio_square-green',
                            });

                            $("#modal-form-questionDetails").modal('show');
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

            $("#testPaper-update-form input[name='singleChoiceScore']").change(function () {

                var singleChoiceScore=$(this).val();
                var fillInBlankScore=$("#testPaper-update-form input[name='fillInBlankScore']").val();
                var trueOrFalseScore=$("#testPaper-update-form input[name='trueOrFalseScore']").val();

                if(singleChoiceScore!=""){
                    $("#score_update").text(trueOrFalseQuestionNum*parseFloat(trueOrFalseScore)+singleChoiceQuestionNum*parseFloat(singleChoiceScore)+fillInBlankQuestionNum*parseFloat(fillInBlankScore));
                }
                else{
                    $("#score_update").text(trueOrFalseQuestionNum*parseFloat(trueOrFalseScore)+fillInBlankQuestionNum*parseFloat(fillInBlankScore));
                }
            });
            $("#testPaper-update-form input[name='trueOrFalseScore']").change(function () {

                var trueOrFalseScore=$(this).val();
                var fillInBlankScore=$("#testPaper-update-form input[name='fillInBlankScore']").val();
                var singleChoiceScore=$("#testPaper-update-form input[name='singleChoiceScore']").val();

                if(trueOrFalseScore!=""){
                    $("#score_update").text(trueOrFalseQuestionNum*parseFloat(trueOrFalseScore)+singleChoiceQuestionNum*parseFloat(singleChoiceScore)+fillInBlankQuestionNum*parseFloat(fillInBlankScore));
                }
                else{
                    $("#score_update").text(singleChoiceQuestionNum*parseFloat(singleChoiceScore)+fillInBlankQuestionNum*parseFloat(fillInBlankScore));
                }
            });
            $("#testPaper-update-form input[name='fillInBlankScore']").change(function () {

                var fillInBlankScore=$(this).val();
                var singleChoiceScore=$("#testPaper-update-form input[name='singleChoiceScore']").val();
                var trueOrFalseScore=$("#testPaper-update-form input[name='trueOrFalseScore']").val();

                if(fillInBlankScore!=""){
                    $("#score_update").text(trueOrFalseQuestionNum*parseFloat(trueOrFalseScore)+singleChoiceQuestionNum*parseFloat(singleChoiceScore)+fillInBlankQuestionNum*parseFloat(fillInBlankScore));
                }
                else{
                    $("#score_update").text(trueOrFalseQuestionNum*parseFloat(trueOrFalseScore)+singleChoiceQuestionNum*parseFloat(singleChoiceScore));
                }
            });

            //构建bootstraptable
            $('#exampleTableEvents').bootstrapTable({
                url: "/v1/testPaper",
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
                        field: 'name',
                        title: '名字'
                    }, {
                        field: 'score',
                        title: '分数'
                    }, {
                        field: 'createdTime',
                        title: '创建时间',
                        formatter: ES.formatDateTime //自定义方法，格式化时间
                    }, {
                        field: 'updatedTime',
                        title: '更新时间',
                        formatter: ES.formatDateTime //自定义方法，格式化时间
                    },{
                        field: 'detailds',
                        title: '详情',
                        events:operateEvents,
                        formatter:addDetailsButton
                    }
                ]
            });
        })();

        $("#modal-form-update").on('shown.bs.modal',function () {
            $("#course_update_chosen").width(270);
            $("#schoolYear_update_chosen").width(270);
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
