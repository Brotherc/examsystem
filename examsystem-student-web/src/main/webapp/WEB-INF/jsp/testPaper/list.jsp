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
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty trueOrFalseQuestions}">
                                        <div id="tab-1" class="tab-pane ">
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
                                                                                            <input id="trueOrFalseQuestion${vs.count}-0" value="B" type="radio" name="trueOrFalseQuestionAnswer[${vs.count}]">
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
                                        </div>
                                    </c:if>
                                    <c:if test="${not empty fillInBlankQuestions}">
                                        <div id="tab-2" class="tab-pane">
                                            <strong>填空题</strong>

                                            <p>题目</p>
                                        </div>
                                    </c:if>
                                </div>

                            </div>

                        </div>

                        <hr>

                        <div class="row">
                            <div class="col-lg-12">

                            </div>
                        </div>


                    </div>
                </div>
            </div>
        </div>

    </div>

    <c:if test="${not empty singleChoiceQuestionAnswer }">
        <c:forEach var="answer" items="${singleChoiceQuestionAnswer }">
            <c:choose>
                <c:when test="${answer.value eq 'A'}">
                    <script type="text/javascript">
                        $("#singleChoiceQuestion${answer.key }A").attr("checked","checked");
                    </script>
                </c:when>
                <c:when test="${answer.value eq 'B' }">
                    <script type="text/javascript">
                        $("##singleChoiceQuestion${answer.key }B").attr("checked","checked");
                    </script>
                </c:when>
                <c:when test="${answer.value eq 'C' }">
                    <script type="text/javascript">
                        $("##singleChoiceQuestion${answer.key }C").attr("checked","checked");
                    </script>
                </c:when>
                <c:when test="${answer.value eq 'D' }">
                    <script type="text/javascript">
                        $("##singleChoiceQuestion${answer.key }D").attr("checked","checked");
                    </script>
                </c:when>
            </c:choose>
        </c:forEach>
    </c:if>


    <!-- 全局js -->
    <script src="/js/jquery.min.js?v=2.1.4"></script>
    <script src="/js/bootstrap.min.js?v=3.3.6"></script>

    <!-- 自定义js -->
    <script src="/js/examsystem/common.js"></script>

    <script>
        $("#time").text("考试时长："+ES.formatSeconds("${examStudent.time}"));
        var startTime=new Date("${examStudent.partOrderStartTime}");
        $("#startTime").text("开始时间："+startTime.format("yyyy-MM-dd hh:mm:ss"));

        $($("#nav_tabs_questions").children().get(0)).addClass("active");
        $($("#content_questions").children().get(0)).addClass("active");
    </script>

    <script>
        $(document).ready(function () {

            // Local script for demo purpose only
            $('#lightVersion').click(function (event) {
                event.preventDefault()
                $('#ibox-content').removeClass('ibox-content');
                $('#vertical-timeline').removeClass('dark-timeline');
                $('#vertical-timeline').addClass('light-timeline');
            });

            $('#darkVersion').click(function (event) {
                event.preventDefault()
                $('#ibox-content').addClass('ibox-content');
                $('#vertical-timeline').removeClass('light-timeline');
                $('#vertical-timeline').addClass('dark-timeline');
            });

            $('#leftVersion').click(function (event) {
                event.preventDefault()
                $('#vertical-timeline').toggleClass('center-orientation');
            });


        });
    </script>

    <script type="text/javascript" src="http://tajs.qq.com/stats?sId=9051096" charset="UTF-8"></script>
    <!--统计代码，可删除-->

</body>

</html>
