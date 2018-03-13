<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>试卷</title>
    <meta name="keywords" content="H+后台主题,后台bootstrap框架,会员中心主题,后台HTML,响应式后台">
    <meta name="description" content="H+是一个完全响应式，基于Bootstrap3最新版本开发的扁平化主题，她采用了主流的左右两栏式布局，使用了Html5+CSS3等现代技术">

    <link rel="shortcut icon" href="favicon.ico"> <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/plugins/awesome-bootstrap-checkbox/awesome-bootstrap-checkbox.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">
    <link href="/css/plugins/toastr/toastr.min.css" rel="stylesheet">

</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content  animated fadeInRight article">
        <div class="row">
            <div class="col-lg-10 col-lg-offset-1">
                <div class="ibox">
                    <div class="ibox-content m-b-sm border-bottom">
                        <div class="pull-right">
                            <button class="btn btn-white btn-xs" type="button">${examStudent.schoolYearName}</button>
                            <button class="btn btn-white btn-xs" type="button">
                                <c:choose>
                                    <c:when test="${examStudent.term eq 1}">
                                        上
                                    </c:when>
                                    <c:otherwise>
                                        下
                                    </c:otherwise>
                                </c:choose>
                            </button>
                            <button class="btn btn-white btn-xs" type="button">${examStudent.courseName}</button>
                            <button class="btn btn-white btn-xs" type="button">${examStudent.partOrder}场次</button>
                        </div>
                        <div class="p-xs">
                            <h2 id="time"></h2>
                            <span id="startTime"></span>
                        </div>
                        <div class="text-center">
                            <h2>
                                ${testPaper.name} (${testPaper.score}分)
                            </h2>
                        </div>
                    </div>
                    <div class="ibox-content">
                        <div class="panel blank-panel">

                            <div class="panel-heading">
                                <div class="panel-options">

                                    <ul class="nav nav-tabs" id="nav_tabs_questions">
                                        <c:if test="${not empty singleChoiceQuestions}">
                                            <li class=""><a data-toggle="tab" href="list.jsp#tab-0">单选题</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${not empty trueOrFalseQuestions}">
                                            <li class=""><a data-toggle="tab" href="list.jsp#tab-1">判断题</a>
                                            </li>
                                        </c:if>
                                        <c:if test="${not empty fillInBlankQuestions}">
                                            <li class=""><a data-toggle="tab" href="list.jsp#tab-2">填空题</a>
                                            </li>
                                        </c:if>
                                    </ul>
                                </div>
                            </div>

                            <div class="panel-body">
                                <div class="tab-content" id="content_questions">
                                    <c:if test="${not empty singleChoiceQuestions}">
                                        <div id="tab-0" class="tab-pane ">
                                            <form class="form-horizontal" id="singleChoiceQuestions-form">
                                                <input type="hidden" name="singleChoiceQuestionNum" value="${fn:length(singleChoiceQuestions)}">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="wrapper wrapper-content">
                                                            <div class="row animated fadeInRight">
                                                                <div class="col-sm-12">
                                                                    <div class="ibox float-e-margins">
                                                                        <div class="text-center float-e-margins p-md">
                                                                            <span>预览：</span>
                                                                            <a href="#" class="btn btn-xs btn-primary" id="lightVersion">浅色</a>
                                                                            <a href="#" class="btn btn-xs btn-primary" id="darkVersion">深色</a>
                                                                            <a href="#" class="btn btn-xs btn-primary" id="leftVersion">布局切换</a>
                                                                        </div>
                                                                        <div class="" id="ibox-content">

                                                                            <div id="vertical-timeline" class="vertical-container light-timeline">

                                                                                <c:forEach items="${singleChoiceQuestions}" var="question" varStatus="vs">
                                                                                    <div class="vertical-timeline-block">
                                                                                        <div class="vertical-timeline-icon navy-bg">
                                                                                            <i class="">${vs.count}</i>
                                                                                        </div>

                                                                                        <div class="vertical-timeline-content gray-bg">
                                                                                            <p>${question.questionContent}
                                                                                            </p>
                                                                                            <div class="checkbox checkbox-success checkbox-circle">
                                                                                                <input id="singleChoiceQuestion${vs.count}A" value="A" type="radio" name="singleChoiceQuestionAnswer[${vs.count}]">
                                                                                                <label for="singleChoiceQuestion${vs.count}A">
                                                                                                    A:${question.optionA}
                                                                                                </label>
                                                                                            </div>
                                                                                            <div class="checkbox checkbox-success checkbox-circle">
                                                                                                <input id="singleChoiceQuestion${vs.count}B" value="B" type="radio" name="singleChoiceQuestionAnswer[${vs.count}]">
                                                                                                <label for="singleChoiceQuestion${vs.count}B">
                                                                                                    B:${question.optionB}
                                                                                                </label>
                                                                                            </div>
                                                                                            <div class="checkbox checkbox-success checkbox-circle">
                                                                                                <input id="singleChoiceQuestion${vs.count}C" value="C" type="radio" name="singleChoiceQuestionAnswer[${vs.count}]">
                                                                                                <label for="singleChoiceQuestion${vs.count}C">
                                                                                                    C:${question.optionC}
                                                                                                </label>
                                                                                            </div>
                                                                                            <div class="checkbox checkbox-success checkbox-circle">
                                                                                                <input id="singleChoiceQuestion${vs.count}D" value="D" type="radio" name="singleChoiceQuestionAnswer[${vs.count}]">
                                                                                                <label for="singleChoiceQuestion${vs.count}D">
                                                                                                    D:${question.optionD}
                                                                                                </label>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>

                                                                                </c:forEach>
                                                                            </div>

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-lg-12">
                                                        <button class="btn btn-primary center-block" type="button" onclick="saveSingleChoiceQuestion()"><i class="fa fa-check"></i>&nbsp;保存</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty trueOrFalseQuestions}">
                                        <div id="tab-1" class="tab-pane ">
                                            <form id="trueOrFalseQuestions-form">
                                                <input type="hidden" name="trueOrFalseQuestionNum" value="${fn:length(trueOrFalseQuestions)}">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="wrapper wrapper-content">
                                                            <div class="row animated fadeInRight">
                                                                <div class="col-sm-12">
                                                                    <div class="ibox float-e-margins">
                                                                        <div class="text-center float-e-margins p-md">
                                                                            <span>预览：</span>
                                                                            <a href="#" class="btn btn-xs btn-primary" id="l">浅色</a>
                                                                            <a href="#" class="btn btn-xs btn-primary" id="k">深色</a>
                                                                            <a href="#" class="btn btn-xs btn-primary" id="i">布局切换</a>
                                                                        </div>
                                                                        <div class="" >

                                                                            <div  class="vertical-container light-timeline">

                                                                                <c:forEach items="${trueOrFalseQuestions}" var="question" varStatus="vs">
                                                                                    <div class="vertical-timeline-block">
                                                                                        <div class="vertical-timeline-icon navy-bg">
                                                                                            <i class="">${vs.count}</i>
                                                                                        </div>

                                                                                        <div class="vertical-timeline-content gray-bg">
                                                                                            <p>${question.questionContent}
                                                                                            </p>
                                                                                            <div class="checkbox checkbox-success checkbox-circle">
                                                                                                <input id="trueOrFalseQuestion${vs.count}-1" value="1" type="radio" name="trueOrFalseQuestionAnswer[${vs.count}]">
                                                                                                <label for="trueOrFalseQuestion${vs.count}-1">
                                                                                                    √
                                                                                                </label>
                                                                                            </div>
                                                                                            <div class="checkbox checkbox-success checkbox-circle">
                                                                                                <input id="trueOrFalseQuestion${vs.count}-0" value="0" type="radio" name="trueOrFalseQuestionAnswer[${vs.count}]">
                                                                                                <label for="trueOrFalseQuestion${vs.count}-0">
                                                                                                    ×
                                                                                                </label>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>

                                                                                </c:forEach>
                                                                            </div>

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-lg-12">
                                                        <button class="btn btn-primary center-block" type="button" onclick="saveTrueOrFalseQuestion()"><i class="fa fa-check"></i>&nbsp;保存</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty fillInBlankQuestions}">
                                        <div id="tab-2" class="tab-pane ">
                                            <form id="fillInBlankQuestions-form">
                                                <input type="hidden" name="fillInBlankQuestionNum" value="${fn:length(fillInBlankQuestions)}">
                                                <div class="row">
                                                    <div class="col-sm-12">
                                                        <div class="wrapper wrapper-content">
                                                            <div class="row animated fadeInRight">
                                                                <div class="col-sm-12">
                                                                    <div class="ibox float-e-margins">
                                                                        <div class="text-center float-e-margins p-md">
                                                                            <span>预览：</span>
                                                                            <a href="#" class="btn btn-xs btn-primary" id="al">浅色</a>
                                                                            <a href="#" class="btn btn-xs btn-primary" id="kx">深色</a>
                                                                            <a href="#" class="btn btn-xs btn-primary" id="ib">布局切换</a>
                                                                        </div>
                                                                        <div class="" >

                                                                            <div  class="vertical-container light-timeline">

                                                                                <c:forEach items="${fillInBlankQuestions}" var="question" varStatus="vs">
                                                                                    <div class="vertical-timeline-block">
                                                                                        <div class="vertical-timeline-icon navy-bg">
                                                                                            <i class="">${vs.count}</i>
                                                                                        </div>

                                                                                        <div class="vertical-timeline-content gray-bg">
                                                                                            <p>${question.questionContent}
                                                                                            </p>
                                                                                            <div class="form-group">
                                                                                                <div class="col-md-4">
                                                                                                    <c:forEach var="i" begin="1" end="${question.blankNum }">
                                                                                                        <input type="text" placeholder="" class="form-control  m-b" name="fillInBlankQuestionAnswer[${vs.count }][${i-1 }]" id="fillInBlankQuestion${vs.count}-${i}">
                                                                                                    </c:forEach>
                                                                                                </div>
                                                                                            </div>
                                                                                        </div>
                                                                                    </div>

                                                                                </c:forEach>
                                                                            </div>

                                                                        </div>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>

                                                    </div>
                                                </div>

                                                <div class="row">
                                                    <div class="col-lg-12 ">
                                                        <button class="btn btn-primary center-block" type="button" onclick="saveFillInBlankQuestion()"><i class="fa fa-check"></i>&nbsp;保存</button>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                    </c:if>
                                </div>
                                <hr>

                                <div class="row">
                                    <div class="col-lg-12">
                                        <button class="btn btn-success center-block" type="button" id="submitTestPaper" onclick="submitTestPaper()"><i class=" fa fa-upload"></i>&nbsp;提交试卷</button>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

    <c:if test="${not empty singleChoiceQuestionAnswer }">
        <script type="text/javascript">
            function initSingleChoiceQuestionAnswer() {
                <c:forEach var="answer" items="${singleChoiceQuestionAnswer }">
                    <c:choose>
                        <c:when test="${answer.value eq 'A'}">
                            $("#singleChoiceQuestion${answer.key }A").attr("checked","checked");
                        </c:when>
                        <c:when test="${answer.value eq 'B' }">
                            $("#singleChoiceQuestion${answer.key }B").attr("checked","checked");
                        </c:when>
                        <c:when test="${answer.value eq 'C' }">
                            $("#singleChoiceQuestion${answer.key }C").attr("checked","checked");
                        </c:when>
                        <c:when test="${answer.value eq 'D' }">
                            $("#singleChoiceQuestion${answer.key }D").attr("checked","checked");
                        </c:when>
                    </c:choose>
                </c:forEach>
            }
        </script>
    </c:if>

    <c:if test="${not empty trueOrFalseQuestionAnswer }">
        <script type="text/javascript">
            function initTrueOrFalseQuestionAnswer() {
                <c:forEach var="answer" items="${trueOrFalseQuestionAnswer }">
                console.log("${answer.value}");
                    <c:choose>
                        <c:when test="${answer.value eq '0'}">
                            $("#trueOrFalseQuestion${answer.key}-0").attr("checked","checked");
                        </c:when>
                        <c:when test="${answer.value eq '1' }">
                            $("#trueOrFalseQuestion${answer.key}-1").attr("checked","checked");
                        </c:when>
                    </c:choose>
                </c:forEach>
            }
        </script>
    </c:if>

    <c:if test="${not empty fillInBlankQuestionAnswer }">
        <script type="text/javascript">
            function initFillInBlankQuestionAnswer() {
                <c:forEach items="${fillInBlankQuestionAnswer }" var="question">
                    <c:forEach var="answer" items="${question.value }" varStatus="vs" >
                        $("#fillInBlankQuestion${question.key}-${vs.count}").val("${answer}");
                    </c:forEach>
                </c:forEach>
            }
        </script>
    </c:if>

    <!-- 全局js -->
    <script src="/js/jquery.min.js?v=2.1.4"></script>
    <script src="/js/bootstrap.min.js?v=3.3.6"></script>
    <script src="/js/plugins/layer/layer.min.js"></script>

    <!-- 自定义js -->
    <script src="/js/examsystem/common.js"></script>

    <!-- Toastr script -->
    <script src="/js/plugins/toastr/toastr.min.js"></script>

    <script>

        function checkTime(i){
            if(i<10){
                i="0"+i;
            }
            return i;
        }

        var remainTime="${remainTime}";
        var leftTime =parseInt(remainTime);

        var h = parseInt(leftTime / 1000 / 60 / 60 % 24 , 10); //计算剩余的小时
        var m = parseInt(leftTime / 1000 / 60 % 60, 10);//计算剩余的分钟
        var s = parseInt(leftTime / 1000 % 60, 10);//计算剩余的秒数
        h = checkTime(h);
        m = checkTime(m);
        s = checkTime(s);
        $("#time").text("考试剩余时间："+h+"小时" + m+"分"+s+"秒");
        if(leftTime!=0)
            leftTime=leftTime-1000;

        setInterval(function(){
            var hours = parseInt(leftTime / 1000 / 60 / 60 % 24 , 10); //计算剩余的小时
            var minutes = parseInt(leftTime / 1000 / 60 % 60, 10);//计算剩余的分钟
            var seconds = parseInt(leftTime / 1000 % 60, 10);//计算剩余的秒数
            hours = checkTime(hours);
            minutes = checkTime(minutes);
            seconds = checkTime(seconds);
            $("#time").text("考试剩余时间："+hours+"小时" + minutes+"分"+seconds+"秒");

            if(leftTime!=0)
                leftTime=leftTime-1000;
            var temLeftTime=Math.round(leftTime/1000);
            console.log(temLeftTime);
            if(temLeftTime==10||temLeftTime==5){
                //弹出倒计时提示提交卷显示框
                toastr.success(temLeftTime+"秒后将自动提交试卷")
            }
            if(temLeftTime==0){
                $("#submitTestPaper").trigger('click');
            }
        },1000);
        $("#startTime").text("开始时间："+ES.formatDateTime(parseInt("${partOrderStartTime}")));

        $($("#nav_tabs_questions").children().get(0)).addClass("active");
        $($("#content_questions").children().get(0)).addClass("active");

        if(typeof (initSingleChoiceQuestionAnswer)=="function")
            initSingleChoiceQuestionAnswer();

        if(typeof (initTrueOrFalseQuestionAnswer)=="function"){
            initTrueOrFalseQuestionAnswer();
        }

        if(typeof (initFillInBlankQuestionAnswer)=="function")
            initFillInBlankQuestionAnswer();
    </script>

    <script>
        
        function submitTestPaper() {

            $.ajax({
                type: "POST",
                url: "/v1/test/singleChoiceQuestion/answer",
                data: decodeURIComponent($("#singleChoiceQuestions-form").serialize().replace(/\+/g,"")),
                success: function(data){
                    if(data.status == 201){
                        $.ajax({
                            type: "POST",
                            url: "/v1/test/trueOrFalseQuestion/answer",
                            data: decodeURIComponent($("#trueOrFalseQuestions-form").serialize().replace(/\+/g,"")),
                            success: function(data){
                                if(data.status == 201){
                                    $.ajax({
                                        type: "POST",
                                        url: "/v1/test/fillInBlankQuestion/answer",
                                        data: decodeURIComponent($("#fillInBlankQuestions-form").serialize().replace(/\+/g,"")),
                                        success: function(data){
                                            if(data.status == 201){
                                                $.ajax({
                                                    type: "POST",
                                                    url: "/v1/test/testPaper/"+"${examStudent.testPaperId}",
                                                    success: function(data){
                                                        if(data.status == 201){
                                                            layer.msg("提交成功");
                                                            location.href = "http://localhost:8082";
                                                        }
                                                        else{
                                                            layer.msg("提交失败");
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
                                            else{
                                                layer.msg("填空题保存失败");
                                            }
                                        },
                                        error:function(XMLHttpRequest, textStatus, errorThrown){
                                            layer.msg("填空题保存失败");
                                        }
                                    });
                                }
                                else{
                                    layer.msg("判断题保存失败");
                                }
                            },
                            error:function(XMLHttpRequest, textStatus, errorThrown){
                                layer.msg("判断题保存失败");
                            }
                        });
                    }else{
                        layer.msg("单选题保存失败");
                    }
                },
                error:function(XMLHttpRequest, textStatus, errorThrown){
                    layer.msg("单选题保存失败");
                }
            });
        }
        
        function saveSingleChoiceQuestion() {
            $.ajax({
                type: "POST",
                url: "/v1/test/singleChoiceQuestion/answer",
                data: decodeURIComponent($("#singleChoiceQuestions-form").serialize().replace(/\+/g,"")),
                success: function(data){
                    if(data.status == 201){
                        layer.msg("保存成功");
                    }
                    else{
                        layer.msg("保存失败");
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

        function saveTrueOrFalseQuestion() {
            $.ajax({
                type: "POST",
                url: "/v1/test/trueOrFalseQuestion/answer",
                data: decodeURIComponent($("#trueOrFalseQuestions-form").serialize().replace(/\+/g,"")),
                success: function(data){
                    if(data.status == 201){
                        layer.msg("保存成功");
                    }
                    else{
                        layer.msg("保存失败");
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
        
        function saveFillInBlankQuestion() {
            $.ajax({
                type: "POST",
                url: "/v1/test/fillInBlankQuestion/answer",
                data: decodeURIComponent($("#fillInBlankQuestions-form").serialize().replace(/\+/g,"")),
                success: function(data){
                    if(data.status == 201){
                        layer.msg("保存成功");
                    }
                    else{
                        layer.msg("保存失败");
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
        

    </script>

    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
    <!--统计代码，可删除-->

</body>

</html>
