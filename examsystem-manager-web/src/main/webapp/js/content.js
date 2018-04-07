/*
 *
 *   H+ - 后台主题UI框架
 *   version 4.9
 *
*/

var $parentNode = window.parent.document;

var ip="localhost";
//var ip="172.16.16.221";

function $childNode(name) {
    return window.frames[name]
}

function tofirst(){
    if(parent.parent.parent){
        parent.parent.parent.location='http://'+ip+':8087/index.html';
    }else if(parent.parent){
        parent.parent.location='http://'+ip+':8087/index.html';
    }else if(parent){
        parent.location='http://'+ip+':8087/index.html';
    }else{
        window.location='http://'+ip+':8087/index.html';
    }
}

function to500(){
    if(parent.parent.parent){
        parent.parent.parent.location='http://'+ip+':8087/500.html';
    }else if(parent.parent){
        parent.parent.location='http://'+ip+':8087/500.html';
    }else if(parent){
        parent.location='http://'+ip+':8087/500.html';
    }else{
        window.location='http://'+ip+':8087/500.html';
    }
}

function to403(){
    if(parent.parent.parent){
        parent.parent.parent.location='http://'+ip+':8087/403.html';
    }else if(parent.parent){
        parent.parent.location='http://'+ip+':8087/403.html';
    }else if(parent){
        parent.location='http://'+ip+':8087/403.html';
    }else{
        window.location='http://'+ip+':8087/403.html';
    }
}

// tooltips
$('.tooltip-demo').tooltip({
    selector: "[data-toggle=tooltip]",
    container: "body"
});

// 使用animation.css修改Bootstrap Modal
$('.modal').appendTo("body");

$("[data-toggle=popover]").popover();

//折叠ibox
$('.collapse-link').click(function () {
    var ibox = $(this).closest('div.ibox');
    var button = $(this).find('i');
    var content = ibox.find('div.ibox-content');
    content.slideToggle(200);
    button.toggleClass('fa-chevron-up').toggleClass('fa-chevron-down');
    ibox.toggleClass('').toggleClass('border-bottom');
    setTimeout(function () {
        ibox.resize();
        ibox.find('[id^=map-]').resize();
    }, 50);
});

//关闭ibox
$('.close-link').click(function () {
    var content = $(this).closest('div.ibox');
    content.remove();
});

//判断当前页面是否在iframe中
if (top == this) {
    var gohome = '<div class="gohome"><a class="animated bounceInUp" href="#" onclick="tofirst()" title="返回首页"><i class="fa fa-home"></i></a></div>';
    $('body').append(gohome);
}

//animation.css
function animationHover(element, animation) {
    element = $(element);
    element.hover(
        function () {
            element.addClass('animated ' + animation);
        },
        function () {
            //动画完成之前移除class
            window.setTimeout(function () {
                element.removeClass('animated ' + animation);
            }, 2000);
        });
}

//拖动面板
function WinMove() {
    var element = "[class*=col]";
    var handle = ".ibox-title";
    var connect = "[class*=col]";
    $(element).sortable({
            handle: handle,
            connectWith: connect,
            tolerance: 'pointer',
            forcePlaceholderSize: true,
            opacity: 0.8,
        })
        .disableSelection();
};
