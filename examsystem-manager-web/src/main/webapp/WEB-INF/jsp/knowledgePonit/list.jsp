<!DOCTYPE html>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/WEB-INF/jsp/tag.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>

<head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>知识点</title>
    <meta name="keywords" content="在线考试系统,吉林大学珠海学院考试">
    <meta name="description" content="在线考试系统是一个服务于教师与学生的学习平台">

    <link rel="shortcut icon" href="favicon.ico"> <link href="/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="/css/font-awesome.css?v=4.4.0" rel="stylesheet">
    <link href="/css/plugins/jsTree/style.min.css" rel="stylesheet">
    <link href="/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">
    <link href="/css/plugins/chosen/chosen.css" rel="stylesheet">
    <link href="/css/animate.css" rel="stylesheet">
    <link href="/css/style.css?v=4.1.0" rel="stylesheet">

    <!-- Sweet Alert -->
    <link href="/css/plugins/sweetalert/sweetalert.css" rel="stylesheet">
</head>

<body class="gray-bg">
    <div class="wrapper wrapper-content animated fadeInRight">

        <!--开始 知识点table -->
        <div class="ibox float-e-margins">
            <div class="ibox">
                <div class="ibox-title">
                    <h5>知识点</h5>
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
                            <div class="example-wrap" >
                                <h4 class="example-title">查询</h4>
                                <div class="ibox float-e-margins ">
                                    <form role="form" class="form-inline ">
                                        <div class="form-group " >
                                            <div class="input-group ">
                                                <select data-placeholder="选择课程..." class="form-control chosen-select " style="width:150px;" tabindex="2" id="course" name="courseId">
                                                    <option value="">请选择课程</option>
                                                    <c:forEach items="${courses}" var="course" >
                                                        <option value="${course.id}" hassubinfo="true">${course.name}</option>
                                                    </c:forEach>
                                                </select>
                                            </div>
                                        </div>
                                        <button class="btn btn-primary m-t-xs btn-lg" type="button" onclick="searchKnowledgePoint()"><i class="fa fa-search"></i>&nbsp;&nbsp;搜索</button>
                                    </form>
                                </div>
                                <div id="jstree1" ></div>
                            </div>
                            <!-- End Example Events -->
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- 结束 标签table -->
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

    <!-- Sweet alert -->
    <script src="/js/plugins/sweetalert/sweetalert.min.js"></script>

    <script type="text/javascript">

        $.ajax({
            type: "GET",
            url: "/v1/knowledgePoint/course/"+"${course.id}",
            data: null,
            success: function(data){
                if(data.status == 200){
                    $('#jstree1').jstree({
                        'core': {
                            'check_callback': function (operation, node, parent, position, more){
                                if (operation === 'rename_node') {

                                    var parentId=node.parent;
                                    if(node.id=="0"){
                                        // 新增节点
                                        $.ajax({
                                            type: "POST",
                                            url: "/v1/knowledgePoint",
                                            data: {parentId:parentId,name:position},
                                            success: function(data){

                                                if(data.status == 201){

                                                    var knowledgePoint=data.data;
                                                    var inst = $.jstree.reference(node),
                                                            obj = inst.get_node(node);

                                                    if(inst.is_selected(obj)) {
                                                        inst.delete_node(inst.get_selected());
                                                    }
                                                    else {
                                                        inst.delete_node(obj);
                                                    }

                                                   var inst = $.jstree.reference(parentId),
                                                            obj = inst.get_node(parentId);

                                                    inst.create_node(obj, {id:knowledgePoint.id,text:knowledgePoint.name}, "last",function (new_node) {
                                                        try {
                                                        } catch (ex) {
                                                            setTimeout(function () { inst.edit(new_node); },0);
                                                        }
                                                    });

                                                    swal(data.message, "您已经成功添加了该知识点。", "success");
                                                }
                                                else{
                                                    var inst = $.jstree.reference(node),
                                                            obj = inst.get_node(node);

                                                    if(inst.is_selected(obj)) {
                                                        inst.delete_node(inst.get_selected());
                                                    }
                                                    else {
                                                        inst.delete_node(obj);
                                                    }
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
                                    }else{
                                        $.post("/content/category/update",{id:node.id,name:node.text});
                                        $.ajax({
                                            type: "POST",
                                            url: "/v1/knowledgePoint/"+node.id,
                                            data: {_method:"put",name:position},
                                            success: function(data){

                                                if(data.status == 201){
                                                    swal(data.message, "您已经成功修改了该知识点。", "success");
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
                                }
                                return true;
                            },
                            'data': data.data
                        },
                        'types': {
                            '#' : {
                                "max_depth" : 3,
                                'icon': 'fa fa-folder'
                            },
                            'default': {
                                'icon': 'fa fa-folder'
                            }
                        },
                        "contextmenu":{
                            select_node:false,
                            show_at_node:true,
                            items:function(node){
                                var items = {
                                    "新建知识点":{
                                        "label":"新建知识点",
                                        "icon": "glyphicon glyphicon-plus",
                                        "action":function(data){
                                            var inst = $.jstree.reference(data.reference),
                                                    obj = inst.get_node(data.reference);

                                            inst.create_node(obj, {id:"0",text:"新建知识点"}, "last", function (new_node) {
                                                try {
                                                    inst.edit(new_node);
                                                } catch (ex) {
                                                    setTimeout(function () { inst.edit(new_node); },0);
                                                }
                                            });
                                        }
                                    },
                                    "修改知识点":{
                                        "separator_before": false,
                                        "separator_after": false,
                                        "_disabled": false, //(this.check("rename_node", data.reference, this.get_parent(data.reference), "")),
                                        "label": "修改知识点",
                                        "shortcut_label": 'F2',
                                        "icon": "glyphicon glyphicon-leaf",
                                        "action": function (data) {
                                            var inst = $.jstree.reference(data.reference),
                                                    obj = inst.get_node(data.reference);
                                            inst.edit(obj);
                                        }
                                    },
                                    "删除知识点":{
                                        "separator_before": false,
                                        "icon": false,
                                        "separator_after": false,
                                        "_disabled": false, //(this.check("delete_node", data.reference, this.get_parent(data.reference), "")),
                                        "label": "删除知识点",
                                        "icon":"glyphicon glyphicon-remove",
                                        "action": function (data) {
                                            var inst = $.jstree.reference(data.reference),
                                                    obj = inst.get_node(data.reference);
                                            swal({
                                                        title: "确定删除所选知识点吗",
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
                                                            var params = {"parentId":obj.parent,_method:'delete'};
                                                            $.ajax({
                                                                type: "POST",
                                                                url: "/v1/knowledgePoint/"+obj.id,
                                                                data: params,
                                                                success: function(data){
                                                                    if(data.status == 204){
                                                                        if(inst.is_selected(obj)) {
                                                                            inst.delete_node(inst.get_selected());
                                                                        }
                                                                        else {
                                                                            inst.delete_node(obj);
                                                                        }
                                                                        swal(data.message, "您已经永久删除了该知识点。", "success");
                                                                    }
                                                                    else{
                                                                        swal(data.message, "无法删除该知识点。", "error");
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
                                                    }
                                            );
                                        }
                                    }
                                };
                                if(this.get_type(node)=="#"){
                                    delete items.删除知识点;
                                    delete items.修改知识点;
                                }
                                if(this.is_leaf(node)==false){
                                    delete items.删除知识点;
                                }
                                return items;
                            }
                        },
                        'plugins': ['types','contextmenu']
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
        function searchKnowledgePoint(){
            var courseId=$("#course").val();

            $.ajax({
                type: "GET",
                url: "/v1/knowledgePoint/course/" + courseId,
                data: null,
                success: function (data) {
                    if (data.status == 200) {
                        $('#jstree1').jstree("destroy");
                        $('#jstree1').jstree({
                            'core': {
                                'check_callback': function (operation, node, parent, position, more){
                                    if (operation === 'rename_node') {

                                        var parentId=node.parent;
                                        if(node.id=="0"){
                                            // 新增节点
                                            $.ajax({
                                                type: "POST",
                                                url: "/v1/knowledgePoint",
                                                data: {parentId:parentId,name:position},
                                                success: function(data){

                                                    if(data.status == 201){

                                                        var knowledgePoint=data.data;
                                                        var inst = $.jstree.reference(node),
                                                                obj = inst.get_node(node);

                                                        if(inst.is_selected(obj)) {
                                                            inst.delete_node(inst.get_selected());
                                                        }
                                                        else {
                                                            inst.delete_node(obj);
                                                        }

                                                        var inst = $.jstree.reference(parentId),
                                                                obj = inst.get_node(parentId);

                                                        inst.create_node(obj, {id:knowledgePoint.id,text:knowledgePoint.name}, "last",function (new_node) {
                                                            try {
                                                            } catch (ex) {
                                                                setTimeout(function () { inst.edit(new_node); },0);
                                                            }
                                                        });

                                                        swal(data.message, "您已经成功添加了该知识点。", "success");
                                                    }
                                                    else{
                                                        var inst = $.jstree.reference(node),
                                                                obj = inst.get_node(node);

                                                        if(inst.is_selected(obj)) {
                                                            inst.delete_node(inst.get_selected());
                                                        }
                                                        else {
                                                            inst.delete_node(obj);
                                                        }
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
                                        }else{
                                            $.post("/content/category/update",{id:node.id,name:node.text});
                                            $.ajax({
                                                type: "POST",
                                                url: "/v1/knowledgePoint/"+node.id,
                                                data: {_method:"put",name:position},
                                                success: function(data){

                                                    if(data.status == 201){
                                                        swal(data.message, "您已经成功修改了该知识点。", "success");
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
                                    }
                                    return true;
                                },
                                'data': data.data
                            },
                            'types': {
                                '#' : {
                                    "max_depth" : 3,
                                    'icon': 'fa fa-folder'
                                },
                                'default': {
                                    'icon': 'fa fa-folder'
                                }
                            },
                            "contextmenu":{
                                select_node:false,
                                show_at_node:true,
                                items:function(node){
                                    var items = {
                                        "新建知识点":{
                                            "label":"新建知识点",
                                            "icon": "glyphicon glyphicon-plus",
                                            "action":function(data){
                                                var inst = $.jstree.reference(data.reference),
                                                        obj = inst.get_node(data.reference);

                                                inst.create_node(obj, {id:"0",text:"新建知识点"}, "last", function (new_node) {
                                                    try {
                                                        inst.edit(new_node);
                                                    } catch (ex) {
                                                        setTimeout(function () { inst.edit(new_node); },0);
                                                    }
                                                });
                                            }
                                        },
                                        "修改知识点":{
                                            "separator_before": false,
                                            "separator_after": false,
                                            "_disabled": false, //(this.check("rename_node", data.reference, this.get_parent(data.reference), "")),
                                            "label": "修改知识点",
                                            "shortcut_label": 'F2',
                                            "icon": "glyphicon glyphicon-leaf",
                                            "action": function (data) {
                                                var inst = $.jstree.reference(data.reference),
                                                        obj = inst.get_node(data.reference);
                                                inst.edit(obj);
                                            }
                                        },
                                        "删除知识点":{
                                            "separator_before": false,
                                            "icon": false,
                                            "separator_after": false,
                                            "_disabled": false, //(this.check("delete_node", data.reference, this.get_parent(data.reference), "")),
                                            "label": "删除知识点",
                                            "icon":"glyphicon glyphicon-remove",
                                            "action": function (data) {
                                                var inst = $.jstree.reference(data.reference),
                                                        obj = inst.get_node(data.reference);
                                                swal({
                                                            title: "确定删除所选知识点吗",
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
                                                                var params = {"parentId":obj.parent,_method:'delete'};
                                                                $.ajax({
                                                                    type: "POST",
                                                                    url: "/v1/knowledgePoint/"+obj.id,
                                                                    data: params,
                                                                    success: function(data){
                                                                        if(data.status == 204){
                                                                            if(inst.is_selected(obj)) {
                                                                                inst.delete_node(inst.get_selected());
                                                                            }
                                                                            else {
                                                                                inst.delete_node(obj);
                                                                            }
                                                                            swal(data.message, "您已经永久删除了该知识点。", "success");
                                                                        }
                                                                        else{
                                                                            swal(data.message, "无法删除该知识点。", "error");
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
                                                        }
                                                );
                                            }
                                        }
                                    };
                                    if(this.get_type(node)=="#"){
                                        delete items.删除知识点;
                                        delete items.修改知识点;
                                    }
                                    if(this.is_leaf(node)==false){
                                        delete items.删除知识点;
                                    }
                                    return items;
                                }
                            },
                            'plugins': ['types','contextmenu']
                        });
                    }
                    else {
                        swal("加载知识点失败", data.message, "error");
                    }
                },
                error: function (XMLHttpRequest, textStatus, errorThrown) {
                    var status = XMLHttpRequest.status;
                    if (status == 403) {
                        to403();
                    } else if (status == 500) {
                        to500();
                    }
                }
            });
        }
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
        $("#course").val("${course.id}").trigger("chosen:updated");
    </script>


</body>

</html>
