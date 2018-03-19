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
    <link href="/css/plugins/webuploader/webuploader.css" rel="stylesheet">
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

                                </div>
                            </div>
                            <!-- End Example Events -->
                        </div>
                    </div>
                    <div class="row row-lg">
                        <div  class="col-sm-2 m-t-sm" style="overflow-x: auto">
                            <a class="btn btn-block btn-primary compose-mail" href="#">考试人员分配</a>
                            <div class="space-25"></div>
                            <ul class="folder-list m-b-md" style="padding: 0">
                                <li>
                                    <a href="#" onclick="openCustomAddStudentModal()"> <i class="fa fa-plus-square "></i> 自定义添加
                                    </a>
                                </li>
                                <li>
                                    <a href="#" onclick="openAddStudentModal()"> <i class="fa fa-hand-pointer-o"></i> 选择添加</a>
                                </li>
                                <li>
                                    <a href="#" onclick="openExcelAddStudentModal()"> <i class="fa fa-file-excel-o"></i> Excel添加</a>
                                </li>
                                <li>
                                    <a href="#" onclick="openStudentDetailsModal()"> <i class="fa fa-users"></i> 考试人员详情</a>
                                </li>
                            </ul>
                        </div>
                        <div class="col-sm-10 ">
                            <table id="exampleTableEvents" data-height="400" data-mobile-responsive="true">
                            </table>
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
                            <div class="form-group" >
                                <label class="col-sm-3 control-label">开考时间：</label>
                                <div class="col-sm-6 " >
                                    <div class="input-group date"  >
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control"  name="startTime" id="startTime_update">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group" >
                                <label class="col-sm-3 control-label">结束时间：</label>
                                <div class="col-sm-6 " >
                                    <div class="input-group date" >
                                        <span class="input-group-addon"><i class="fa fa-calendar"></i></span>
                                        <input type="text" class="form-control"  name="endTime" id="endTime_update">
                                    </div>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">试卷：</label>
                                <div class=" col-sm-6">
                                    <select data-placeholder="选择试卷..." class="form-control chosen-select " style="width:50px;" tabindex="2" id="testPaper_update"  name="testPaperId">
                                        <c:forEach items="${testPapers}" var="testPaper">
                                            <option value="${testPaper.id}" hassubinfo="true">${testPaper.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">学年：</label>
                                <div class=" col-sm-6">
                                    <select data-placeholder="选择学年..." class="form-control chosen-select " style="width:270px;" tabindex="2" id="schoolYear_update"  name="schoolYearId">
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
                                        <input type="text" class="form-control" value="1" data-max="20" data-min="1" data-step="1" name="partNum" id="partNum_update">
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
                                        <input type="text" class="form-control" value="0" data-max="3600" data-min="0" data-step="1" name="intervalTime" id="intervalTime_update">
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
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="updateExam()">修改</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 查看详情考试modal -->
    <div id="modal-form-details" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="question-details-form">
                            <p>欢迎查看该考试(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">试卷：</label>
                                <div class="col-sm-6">
                                    <p class="form-control-static" id="testPaper_details"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">场次：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="partNum_details"></p>
                                </div>
                            </div>
                            <div class="form-group " >
                                <label class="col-sm-3 control-label">场次间隔时间：</label>
                                <div class=" col-sm-6">
                                    <p class="form-control-static" id="intervalTime_details"></p>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 自定义添加考试学生modal -->
    <div id="modal-form-customAddStudent" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="custom-addStudent-form">
                            <p>欢迎添加学生(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">学号：</label>
                                <div class="col-sm-6">
                                    <input type="text" placeholder="学号" maxlength="8" class="form-control" name="studentId">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">姓名：</label>
                                <div class="col-sm-6">
                                    <input type="text" placeholder="姓名" maxlength="4" class="form-control" name="name">
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
                                <label class="col-sm-3 control-label">场次：</label>
                                <div class="col-sm-6 " >
                                    <div class="input-group spinner" data-trigger="spinner" id="partOrder">
                                        <input type="text" class="form-control" value="1" data-max="20" data-min="1" data-step="1" name="partOrder" id="partOrder_add">
                                        <span class="input-group-addon">
                                                <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
                                                <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
                                            </span>
                                    </div>
                                    <span class="help-block m-b-none"><i class="fa fa-info-circle" id="max_partNum">最大场次：</i></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="customAddStudent()">提交</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- excel添加考试学生modal -->
    <div class="modal inmodal fade" id="modal-form-excelAddStudent" tabindex="-1" role="dialog"  aria-hidden="true">
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
                        <span class="help-block m-b-none"><i class="fa fa-info-circle"></i>注意excel文件中学生场次不要超出所选考试的场次范围，将以导入信息作为学生的最新信息</span>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-white" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary" id="excelAddButton" >导入</button>
                </div>
            </div>
        </div>
    </div>

    <!-- 考试学生详情modal -->
    <div class="modal inmodal fade" id="modal-form-studentDetails" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h5 class="modal-title">考试学生</h5>
                </div>
                <div class="modal-body">
                    <div class="ibox-content">
                        <div class="row row-lg">

                            <div class="col-sm-12">
                                <!-- Example Events -->
                                <div class="example-wrap">
                                    <h4 class="example-title">查询</h4>
                                    <div class="ibox float-e-margins ">
                                        <form role="form" class="form-inline ">
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
                                            <div class="form-group m-l-none" >
                                                <label for="partOrder_search" class="sr-only">场次</label>
                                                <input type="text" placeholder="请输入场次" id="partOrder_search" class="form-control" style="width: 100px">
                                            </div>
                                            <button class="btn btn-primary m-t-xs btn-lg" type="button" onclick="searchStudent()"><i class="fa fa-search"></i>&nbsp;&nbsp;搜索</button>
                                        </form>
                                    </div>
                                    <div class="example">
                                        <table id="examStudentTableEvents" data-height="400" data-mobile-responsive="true">
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

    <!-- 未添加的考试学生modal -->
    <div class="modal inmodal fade" id="modal-form-addStudent" tabindex="-1" role="dialog"  aria-hidden="true">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
                    <h5 class="modal-title">学生</h5>
                </div>
                <div class="modal-body">
                    <div class="ibox-content">
                        <div class="row row-lg">

                            <div class="col-sm-12">
                                <!-- Example Events -->
                                <div class="example-wrap">
                                    <h4 class="example-title">查询</h4>
                                    <div class="ibox float-e-margins ">
                                        <form role="form" class="form-inline ">
                                            <div class="form-group m-l-none" >
                                                <label for="studentId" class="sr-only">学号</label>
                                                <input type="text" placeholder="请输入学号" id="studentId_add" class="form-control" style="width: 100px">
                                            </div>
                                            <div class="form-group m-l-none" >
                                                <label for="studentName" class="sr-only">名字</label>
                                                <input type="text" placeholder="请输入名字" maxlength="4" id="studentName_add" class="form-control" style="width: 100px">
                                            </div>
                                            <div class="form-group " >
                                                <div class="input-group ">
                                                    <select data-placeholder="选择班级..." class="form-control chosen-select " style="width:200px;" tabindex="2" id="class_add_search" name="classId">
                                                        <option value="">请选择班级</option>
                                                        <c:forEach items="${classes}" var="aClass">
                                                            <option value="${aClass.id}" hassubinfo="true">${aClass.gradeName}-${aClass.majorName}-${aClass.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>
                                            <button class="btn btn-primary m-t-xs btn-lg" type="button" onclick="searchNotAddStudent()"><i class="fa fa-search"></i>&nbsp;&nbsp;搜索</button>
                                        </form>
                                    </div>
                                    <div class="example">
                                        <div class="btn-group hidden-xs" id="studentTableEventsToolbar" role="group">
                                            <button type="button" class="btn btn-outline btn-default" onclick="addStudent()">
                                                <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                            </button>
                                        </div>
                                        <table id="studentTableEvents" data-height="400" data-mobile-responsive="true">
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

    <!-- 修改场次modal -->
    <div id="modal-form-updatePartOrder" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="partOrder-update-form">
                            <input type="hidden" name="id" id="examStudent">
                            <input type="hidden" name="_method" value="put">
                            <p>欢迎修改场次(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">场次：</label>
                                <div class="col-sm-6 " >
                                    <div class="input-group spinner" data-trigger="spinner" >
                                        <input type="text" class="form-control" value="1" data-max="20" data-min="1" data-step="1" name="partOrder">
                                        <span class="input-group-addon">
                                                <a href="javascript:;" class="spin-up" data-spin="up"><i class="fa fa-caret-up"></i></a>
                                                <a href="javascript:;" class="spin-down" data-spin="down"><i class="fa fa-caret-down"></i></a>
                                        </span>
                                    </div>
                                    <span class="help-block m-b-none"><i class="fa fa-info-circle" id="maxPartOrder"></i></span>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="updatePartOrder()">修改</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改学生modal -->
    <div id="modal-form-updateStudent" class="modal fade" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-body">
                    <div class="row">
                        <form class="form-horizontal" id="student-update-form">
                            <input type="hidden" id="studentId_update" >
                            <input type="hidden" name="_method" value="put">
                            <p>欢迎修改学生(⊙o⊙)</p>
                            <div class="form-group">
                                <label class="col-sm-3 control-label">学号：</label>
                                <div class="col-sm-6">
                                    <input type="text" placeholder="学号" maxlength="8" class="form-control" name="studentId">
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
                                    <select data-placeholder="选择班级..." class="form-control chosen-select "  style="width:270px;" tabindex="4" id="studentClassId_update"  name="classId">
                                        <c:forEach items="${classes}" var="aClass">
                                            <option value="${aClass.id}" hassubinfo="true">${aClass.gradeName}-${aClass.majorName}-${aClass.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6">
                                    <button data-toggle="button" class="btn btn-primary btn-outline" type="button" onclick="updateStudent()">提交</button>
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
            return $.map($('#studentTableEvents').bootstrapTable('getSelections'), function (row) {
                return row.id
            });
        }

        //清空表单
        function clearForm(){
            $(':input',"#modal-form-customAddStudent").not(':button,:submit,:reset,:hidden').val('').removeAttr('checked').removeAttr('selected');
            $("#class_add").val("").trigger("chosen:updated");
        }

        function searchExam() {
            $("#exampleTableEvents").bootstrapTable('refresh');
        }
        function searchStudent() {
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');

            //设置url
            var newUrl="/v1/exam/"+sels[0].id+"/student";
            $("#examStudentTableEvents").bootstrapTable('refresh',{url:newUrl});
        }

        function searchNotAddStudent() {
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');

            //设置url
            var newUrl="/v1/examStudent/exam/"+sels[0].id;
            $("#studentTableEvents").bootstrapTable('refresh',{url:newUrl});
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

        function openUpdateExamModal() {
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("必须选择一门考试才能进行修改!");
                return ;
            }else if(sels.length >1){
                layer.msg("只能选择一门考试!");
                return ;
            }

            var exam=sels[0];
            //数据回显
            $("#exam-update-form [name=id]").val(exam.id);

            $(".i-checks").iCheck("uncheck");
            $("#exam-update-form input[value='"+exam.term+"']").iCheck('check');
            $("#partNum_update").val(exam.partNum);
            $("#intervalTime_update").val(exam.intervalTime);
            $("#startTime_update").val(ES.formatDateTime(exam.startTime));
            $("#endTime_update").val(ES.formatDateTime(exam.endTime));

            $("#schoolYear_update").val(exam.schoolYearId).trigger("chosen:updated");
            $("#testPaper_update").val(exam.testPaperId).trigger("chosen:updated");

            $("#modal-form-update").modal('show');
        }

        function openCustomAddStudentModal() {
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("必须选择一门考试才能分配!");
                return ;
            }else if(sels.length >1){
                layer.msg("只能选择一门考试!");
                return ;
            }
            $("#max_partNum").text("最大场次："+sels[0].partNum);

            $("#modal-form-customAddStudent").modal('show');

        }
        function customAddStudent() {
            //表单校验
            if($("#custom-addStudent-form [name=studentId]").val().trim()==''){
                layer.msg("学号不能为空!");
                return ;
            }
            if($("#custom-addStudent-form [name=name]").val().trim()==''){
                layer.msg("名字不能为空!");
                return ;
            }
            if($("#custom-addStudent-form [name=classId]").val()==null){
                layer.msg("班级不能为空!");
                return ;
            }
            var partOrder=$("#custom-addStudent-form [name=partOrder]").val();
            if(partOrder==null){
                layer.msg("场次不能为空!");
                return ;
            }
            //不能超过最大场次
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(partOrder>sels[0].partNum){
                layer.msg("超出最大场次");
                return ;
            }


            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            var examId=sels[0].id;

            $.ajax({
                type: "POST",
                url: "/v1/exam/"+examId+"/student",
                data: decodeURIComponent($("#custom-addStudent-form").serialize().replace(/\+/g,"")),
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-customAddStudent").modal('hide');
                        clearForm();
                        swal(data.message, "您已经永久将该学生分配至对应考试。", "success");
                        $("#exampleTableEvents").bootstrapTable('refresh');
                    }
                    else{
                        swal("分配失败",data.message, "error");
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

        function openExcelAddStudentModal() {
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("必须选择一门考试才能分配!");
                return ;
            }else if(sels.length >1){
                layer.msg("只能选择一门考试!");
                return ;
            }

            $("#modal-form-excelAddStudent").modal('show');
        }
        function openStudentDetailsModal() {
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("必须选择一门考试才能查看考试人员详情!");
                return ;
            }else if(sels.length >1){
                layer.msg("只能选择一门考试!");
                return ;
            }

            //设置url
            var newUrl="/v1/exam/"+sels[0].id+"/student";
            $("#examStudentTableEvents").bootstrapTable('refresh',{url:newUrl});

            $("#modal-form-studentDetails").modal('show');
        }

        function openAddStudentModal() {
            var sels = $('#exampleTableEvents').bootstrapTable('getSelections');
            if(sels.length == 0){
                layer.msg("必须选择一门考试才能分配!");
                return ;
            }else if(sels.length >1){
                layer.msg("只能选择一门考试!");
                return ;
            }

            //设置url
            var newUrl="/v1/examStudent/exam/"+sels[0].id;
            $("#studentTableEvents").bootstrapTable('refresh',{url:newUrl});

            $("#modal-form-addStudent").modal('show');
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

        function addStudent(){
            var sels = $('#studentTableEvents').bootstrapTable('getSelections');
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
                                    $("#studentTableEvents").bootstrapTable('refresh');
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

        function updateExam(){
            //表单校验
            var startTime=$("#exam-update-form [name=startTime]").val();
            if(startTime.trim()==''){
                layer.msg("开考时间不能为空!");
                return ;
            }
            var endTime=$("#exam-update-form [name=endTime]").val();
            if(endTime.trim()==''){
                layer.msg("结束时间不能为空!");
                return ;
            }
            if($("#exam-update-form [name=schoolYearId]").val().trim()==''){
                layer.msg("学年不能为空!");
                return ;
            }
            if($("#partNum_update").val()>1&&$("#intervalTime_update").val()=="0"){
                layer.msg("时间间隔不能为空!");
                return ;
            }
            //ajax的post方式提交表单
            //$("#itemAddForm").serialize()将表单序列号为key-value形式的字符串

            var id=$("#exam-update-form [name=id]").val();

            $.ajax({
                type: "POST",
                url: "/v1/exam/"+id,
                data: $("#exam-update-form").serialize(),
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-update").modal('hide');
                        swal(data.message, "您已经成功修改了该考试。", "success");
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

            var id=$("#major-update-form [name=id]").val();

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

        function examStudentQueryParams(params) {
            var temp={
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                studentStudentId:$("#studentId").val().trim(),
                studentName:$("#studentName").val(),
                studentClassId:$("#class_search").val().trim(),
                partOrder:$("#partOrder_search").val().trim()
            };
            return temp;
        }

        function examNotAddStudentQueryParams(params) {
            var temp={
                pageSize:params.pageSize,
                pageNumber:params.pageNumber,
                studentId:$("#studentId_add").val().trim(),
                name:$("#studentName_add").val(),
                classId:$("#class_add_search").val().trim(),
            };
            return temp;
        }


        function addDetailsButton(value,row,index){

            if(row.status==0){
                return ['<button class="btn btn-outline btn-info" type="button" id="examDetails">查看</button>',
                    '<button class="btn btn-outline btn-success m-l-xs" type="button" id="startExam">启动</button>'].join("");
            }else{
                return ['<button class="btn btn-outline btn-info" type="button" id="examDetails">查看</button>',
                    '<button class="btn btn-outline btn-success m-l-xs" disabled="" type="button" id="startExam">启动</button>'].join("");
            }


        }

        function addDetailsButtonExamStudent(value,row,index){
            return ['<button class="btn btn-outline btn-info" type="button" id="openUpdateStudentModal">编辑</button>',
                '<button class="btn btn-outline btn-success m-l-xs" type="button" id="openUpdatePartOrderModal">场次</button>',
                '<button class="btn btn-outline btn-warning m-l-xs" type="button" id="removeStudent">移除</button>'].join("");
        }

        function updatePartOrder(){
            //不能超过最大场次
            var exam = $('#exampleTableEvents').bootstrapTable('getSelections');
            var partOrder=$("#modal-form-updatePartOrder [name=partOrder]").val();
            if(partOrder>exam[0].partNum){
                layer.msg("超出最大场次");
                return ;
            }
            var examStudentId=$("#examStudent").val();
            $.ajax({
                type: "POST",
                url: "/v1/exam/student/"+examStudentId,
                data: {partOrder:partOrder,_method:"put"},
                success: function(data){
                    if(data.status == 201){
                        swal(data.message, "您已经成功将该学生的场次修改。", "success");
                        $("#modal-form-updatePartOrder").modal('hide');
                        $("#examStudentTableEvents").bootstrapTable('refresh');
                    }else
                        swal("修改失败",data.message, "error");
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

            var studentId=$("#studentId_update").val();

            $.ajax({
                type: "POST",
                url: "/v1/examStudent/"+studentId,
                data: decodeURIComponent($("#student-update-form").serialize().replace(/\+/g,"")),
                success: function(data){
                    if(data.status == 201){
                        $("#modal-form-updateStudent").modal('hide');
                        swal(data.message, "您已经成功修改了该学生信息。", "success");
                        $("#examStudentTableEvents").bootstrapTable('refresh');
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

        window.operateEvents={
            "click #examDetails":function (e,value,row,index) {
                console.log(row);
                //发送ajax请求题目详情
                $.ajax({
                    type: "GET",
                    url: "/v1/exam/"+row.id,
                    data: null,
                    success: function(data){
                        if(data.status == 200){

                            //赋值
                            var examDetails=data.data;

                            $("#testPaper_details").text(examDetails.testPaperName);
                            $("#partNum_details").text(examDetails.partNum);
                            $("#intervalTime_details").text(examDetails.intervalTime);

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
            "click #startExam":function (e,value,row,index) {
                $.ajax({
                    type: "POST",
                    url: "/v1/exam/"+row.id+"/status",
                    data: {_method:"put"},
                    success: function(data){
                        if(data.status == 201){
                            swal(data.message, "您已经成功启动该考试", "success");
                            $("#exampleTableEvents").bootstrapTable('refresh');
                        }
                        else{
                            swal("启动失败",data.message, "error");
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
            "click #openUpdateStudentModal":function (e,value,row,index) {
                var studentId=row.studentStudentId;
                var studentName=row.studentName;
                var studentClassId=row.studentClassId;
                $("#modal-form-updateStudent [name=studentId]").val(studentId);
                $("#modal-form-updateStudent [name=name]").val(studentName);
                $("#studentClassId_update").val(studentClassId).trigger("chosen:updated");
                $("#studentClassId_update_chosen").width(270);
                $("#studentId_update").val(row.studentId);
                $("#modal-form-updateStudent").modal('show');
            },
            "click #openUpdatePartOrderModal":function (e,value,row,index) {
                var exam = $('#exampleTableEvents').bootstrapTable('getSelections');
                var examPartNum=exam[0].partNum;
                if(examPartNum==1){
                    layer.msg("该门考试只有一个场次");
                }else{
                    $("#modal-form-updatePartOrder [name=partOrder]").val(row.partOrder);
                    $("#maxPartOrder").text("最大场次："+examPartNum);
                    $("#examStudent").val(row.id);
                    $("#modal-form-updatePartOrder").modal('show');
                }
            },
            "click #removeStudent":function (e,value,row,index) {
                $.ajax({
                    type: "POST",
                    url: "/v1/exam/student/"+row.id,
                    data: {_method:"delete"},
                    success: function(data){
                        if(data.status == 204){
                            swal(data.message, "您已经成功将该学生从对应考试你中移除。", "success");
                            $("#examStudentTableEvents").bootstrapTable('refresh');
                        }else
                            swal("移除失败",data.message, "error");
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
                    }, {
                        field: 'detailds',
                        title: '详情',
                        events:operateEvents,
                        formatter:addDetailsButton
                    }
                ]
            });

            $('#examStudentTableEvents').bootstrapTable({
                url: "",
                search: false,
                pagination: true,
                showRefresh: true,
                showToggle: true,
                showColumns: true,
                iconSize: 'outline',
                idField:'id',
                queryParams:examStudentQueryParams,
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
                    }, {
                        field: 'detailds',
                        title: '详情',
                        events:operateEvents,
                        formatter:addDetailsButtonExamStudent
                    }
                ]
            });

            $('#studentTableEvents').bootstrapTable({
                url: "",
                search: false,
                pagination: true,
                showRefresh: true,
                showToggle: true,
                showColumns: true,
                iconSize: 'outline',
                idField:'id',
                toolbar: '#studentTableEventsToolbar',
                queryParams:examNotAddStudentQueryParams,
                icons: {
                    refresh: 'glyphicon-repeat',
                    toggle: 'glyphicon-list-alt',
                    columns: 'glyphicon-list'
                },
                columns: [
                    {
                        field: 'id',
                        visible:false
                    }, {
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
            $("#testPaper_update_chosen").width(270);
            $("#schoolYear_update_chosen").width(270);
        });
        $("#modal-form-customAddStudent").on('shown.bs.modal',function () {
            $("#class_add_chosen").width(270);
        });

        $("#modal-form-studentDetails").on('shown.bs.modal',function () {
            $("#class_search_chosen").width(200);
        });

        $("#modal-form-addStudent").on('shown.bs.modal',function () {
            $("#class_add_search_chosen").width(200);
        });

        var uploader;
        //在点击弹出模态框的时候再初始化WebUploader，解决点击上传无反应问题
        $("#modal-form-excelAddStudent").on("shown.bs.modal",function(){
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
                    $("#modal-form-excelAddStudent").modal('hide');
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
            uploader.options.server="/v1/exam/"+sels[0].id+"/student/file";
            uploader.upload();
        });

        //关闭模态框销毁WebUploader，解决再次打开模态框时按钮越变越大问题
        $('#modal-form-excelAddStudent').on('hide.bs.modal', function () {

            $("#thelist").children().remove();
            uploader.destroy();
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
