/**
 * js 通用方法
 * @param obj
 * @returns {boolean}
 */

//================================2019/6/28=========================================
/**
 * 判断JS对象是否为空(字符串||数字||对象)
 * @author zhoujy
 * @param obj
 * @returns {Boolean}
 */
function isBlankObj(obj){
    if(typeof(obj) == "boolean"){ // 布尔类型判断
        return false;
    }
    if(typeof(obj) == "number"){ // 处理数字为0时的判断
        return false;
    }
    if(isJSON(obj) && isEmptyObject(obj)){ // TODO 判断Json是否空
        return true;
    }
    if(obj == "" || obj == undefined || obj == null){
        return true;
    }
    if (typeof(obj) == "string" && obj.replace(/(^\s*)|(\s*$)/g, "").length == 0){ // 过滤空格，制表符，换页符
        return true;
    }
    return false;
}
/**
 * 判断是否是JSON对象
 * @param obj
 * @returns {boolean}
 */
function isJSON(obj){
    return typeof(obj) == "object" && Object.prototype.toString.call(obj).toLowerCase() == "[object   object]" && !obj.length;
}


/**
 * 判断JSON对象是否为空
 * @param obj
 * @returns {boolean}
 */
function isEmptyObject(obj) {
    for ( var name in obj ) {
        return false;
    }
    return true;
}

/**
 *
 * ajax请求封装
 * @param url 请求URL
 * @param param
 * @param sync 是否启用同步请求 默认false 为异步请求
 * @param paramType 参数类型 默认json
 * @returns
 */
function ajaxPost(url, param, sync, paramType){
    var path = $("#path").val();
    try{
        var obj = [];
        if(isBlankObj(url)){
            // layer.alert("未传递URL参数，请检查！", {shade: 0.06});
            return;
        }

        if(isBlankObj(param)){
            param = {"iparamX": ""};
        }

        if(!(typeof(sync) == "boolean")){
            sync = false;
        }
        if(isBlankObj(paramType)){
            paramType = "json";
        }
            //添加新的属性并赋值
        $.ajax({
            type : "POST",
            data : param,
            async: sync,
            url :  url,
            dataType : paramType,
            success : function(result) {
                obj = result;
                if(result.msg != undefined){
                    // layer.alert("操作失败！"+result.msg, {shade: 0.06});
                }else{

                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown) {
            }
        });
    }catch(err){
        if(err == "InternalError: too much recursion"){
            err = "参数包含JSON数组，请先转换字符串";
        }
        // layer.alert("操作失败！</br>请求路径："+url+"</br>请求参数："+JSON.stringify(param)+"</br>错误信息：<span style='color:red;'>" + err + "</span>", {shade: 0.06});
    }
    return obj;
}

/**
 * JS将毫秒数时间格式化
 * @param time
 * @param format
 * @returns
 */
function formatTime(time, format){
    if(isBlankObj(time)){
        return "";
    }

    var newDate = new Date(time);
    newDate.getFullYear(); //获取完整的年份(4位,1970-????)
    newDate.getMonth(); //获取当前月份(0-11,0代表1月)
    newDate.getDate(); //获取当前日(1-31)
    newDate.getDay(); //获取当前星期X(0-6,0代表星期天)
    newDate.getTime(); //获取当前时间(从1970.1.1开始的毫秒数)
    newDate.getHours(); //获取当前小时数(0-23)
    newDate.getMinutes(); //获取当前分钟数(0-59)
    newDate.getSeconds(); //获取当前秒数(0-59)
    newDate.getMilliseconds(); //获取当前毫秒数(0-999)
    newDate.toLocaleDateString(); //获取当前日期
    var mytime = newDate.toLocaleTimeString(); //获取当前时间
    newDate.toLocaleString( ); //获取日期与时间

    var times = "";
    if(!isBlankObj(format)){
        if(format == "null"){ // 后台未指定日期格式则返回毫秒数
            return time;
        }else if(format == "yyyy-MM-dd"){
            times = newDate.getFullYear()+'-'+("0"+(newDate.getMonth()+1)).slice(-2)+'-'+("0"+newDate.getDate()).slice(-2); // 拼写出的日期2015-3-27;
        }else if(format == "yyyy/MM/dd"){
            times = newDate.getFullYear()+'/'+("0"+(newDate.getMonth()+1)).slice(-2)+'/'+("0"+newDate.getDate()).slice(-2); // 拼写出的日期2015-3-27;
        }else if(format == "yyyy/MM/dd HH:mm:ss"){
            times = newDate.getFullYear()+'/'+("0"+(newDate.getMonth()+1)).slice(-2)+'/'+("0"+newDate.getDate()).slice(-2) +"&nbsp;"+("0"+newDate.getHours()).slice(-2)+":"+("0"+newDate.getMinutes()).slice(-2)+":"+("0"+newDate.getSeconds()).slice(-2); // 拼写出的日期2015-3-27 23:59:58;
        }else{ // 默认格式 yyyy-MM-dd HH:mm:ss
            times = newDate.getFullYear()+'-'+("0"+(newDate.getMonth()+1)).slice(-2)+'-'+("0"+newDate.getDate()).slice(-2) +"&nbsp;"+("0"+newDate.getHours()).slice(-2)+":"+("0"+newDate.getMinutes()).slice(-2)+":"+("0"+newDate.getSeconds()).slice(-2); // 拼写出的日期2015-3-27 23:59:58;
        }
    }else{ // 默认格式 yyyy-MM-dd HH:mm:ss
        times = newDate.getFullYear()+'-'+("0"+(newDate.getMonth()+1)).slice(-2)+'-'+("0"+newDate.getDate()).slice(-2) +"&nbsp;"+("0"+newDate.getHours()).slice(-2)+":"+("0"+newDate.getMinutes()).slice(-2)+":"+("0"+newDate.getSeconds()).slice(-2); // 拼写出的日期2015-3-27 23:59:58;
    }
    return times.replaceAll("&nbsp;", " ");
}

/**
 * 获取当前日期时分秒
 * @returns Array
 */
function getTimeArray() {
    var timeArray = new Array();
    var now = new Date();
    var weekArray = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    timeArray[0] = now.getFullYear(); // 年
    timeArray[1] = ("0" + (now.getMonth() + 1)).slice(-2); // 月
    timeArray[2] = ("0" + now.getDate()).slice(-2); // 日
    timeArray[3] = ("0" + now.getHours()).slice(-2); // 时
    timeArray[4] = ("0" + now.getMinutes()).slice(-2); // 分
    timeArray[5] = ("0" + now.getSeconds()).slice(-2); // 秒
    timeArray[6] = weekArray[now.getDay()]; // 星期
    return timeArray;
}


/**
 * 图片上传  文件
 * @param url
 * @param fromId  表单Id
 * @param sync  同步
 * @param fun  异步执行的方法
 */
function uploadPic(url,fromId ,sync) {
    try{
        var obj = [];
        if(isBlankObj(url)){
            layer.alert("未传递URL参数，请检查！", {shade: 0.06});
            return;
        }
        if(isBlankObj(fromId)){
            layer.alert("未传递表单Id参数，请检查！", {shade: 0.06});
            return;
        }
        if(!(typeof(sync) == "boolean")){
            sync = false;
        }
        // 上传设置
        var options = {
            // 规定把请求发送到那个URL
            url: url,
            // 请求方式
            type: "post",
            // 服务器响应的数据类型
            dataType: "json",
            async: sync,
            // 请求成功时执行的回调函数
            success: function (result) {
                obj = result;
                if (result.msg != undefined) {
                    // layer.alert("操作失败！"+result.msg, {shade: 0.06});
                } else {

                }
            }
        };
        $("#"+fromId).ajaxSubmit(options);
    }catch(err){
        if(err == "InternalError: too much recursion"){
            err = "参数包含JSON数组，请先转换字符串";
        }
    }
    return obj;
}

/**
 * 让JS支持replaceAll()方法
 */
String.prototype.replaceAll = function(s1,s2){
    return this.replace(new RegExp(s1,"gm"),s2);
};


/**
 * 正则表达式
 * @param reg
 * @param param
 * @returns {boolean | *}
 * @constructor
 */
function RegularExpression(reg,param) {
    //默认检验数字
    if (isBlankObj(reg)) {
        reg = /^[0-9]+.?[0-9]*$/;
    }
    return reg.test(param);
}


/**
 * 切换行政区划下拉框
 * @param levels
 */
/*function changeRegion(levels){
    if(isBlankObj(levels)){
        return;
    }
    var PROVINCE, CITY, AREA, STREET;
    if(levels == 1){ //查询生成省数据
        var result = ajaxPost("/baseRegionGetData.jhtml", {"levels": 2}, false);
        if(isBlankObj(result.msg)){
            PROVINCE = result.data;
        }
        if(!isBlankObj(PROVINCE)){
            for(var i = 0; i < PROVINCE.length; i++){
                $("#provinceSelect").append("<option value='"+PROVINCE[i].id+"' title="+PROVINCE[i].name+">"+PROVINCE[i].name+"</option>");
            }
        }
    }else if(levels == 2){ //查询生成市数据
        $("#citySelect").empty(); // 清空之前加载的数据
        $("#citySelect").append("<option value='' title='' selected>未选择</option>");
        $("#areaSelect").empty(); // 清空之前加载的数据
        $("#areaSelect").append("<option value='' title='' selected>未选择</option>");
        $("#streetSelect").empty(); // 清空之前加载的数据
        $("#streetSelect").append("<option value='' title='' selected>未选择</option>");
        var parentId = $("#provinceSelect").val();
        if(isBlankObj(parentId)){
            layer.msg("获取省数据失败，请检查下拉框id是否正确！");
            return;
        }
        var result = ajaxPost("/baseRegionGetData.jhtml", {"levels": 3, "parentId": parentId}, false);
        if(isBlankObj(result.msg)){
            CITY = result.data;
        }
        if(!isBlankObj(CITY)){
            for(var i = 0; i < CITY.length; i++){
                $("#citySelect").append("<option value='"+CITY[i].id+"' title="+CITY[i].name+">"+CITY[i].name+"</option>");
            }
        }
    }else if(levels == 3){ //查询生成区数据
        $("#areaSelect").empty(); // 清空之前加载的数据
        $("#areaSelect").append("<option value='' title='' selected>未选择</option>");
        $("#streetSelect").empty(); // 清空之前加载的数据
        $("#streetSelect").append("<option value='' title='' selected>未选择</option>");
        var parentId = $("#citySelect").val();
        if(isBlankObj(parentId)){
            layer.msg("获取省数据失败，请检查下拉框id是否正确！");
            return;
        }
        var result = ajaxPost("/baseRegionGetData.jhtml", {"levels": 4, "parentId": parentId}, false);
        if(isBlankObj(result.msg)){
            AREA = result.data;
        }
        $("#areaSelect").empty(); // 清空之前加载的数据
        $("#areaSelect").append("<option value='' title='' selected>未选择</option>");
        if(!isBlankObj(AREA)){
            for(var i = 0; i < AREA.length; i++){
                $("#areaSelect").append("<option value='"+AREA[i].id+"' title="+AREA[i].name+">"+AREA[i].name+"</option>");
            }
        }
    }else if(levels == 4){ //查询生成街道数据
        $("#streetSelect").empty(); // 清空之前加载的数据
        $("#streetSelect").append("<option value='' title='' selected>未选择</option>");
        var parentId = $("#areaSelect").val();
        if(isBlankObj(parentId)){
            layer.msg("获取省数据失败，请检查下拉框id是否正确！");
            return;
        }
        var result = ajaxPost("/baseRegionGetData.jhtml", {"levels": 5, "parentId": parentId}, false);
        if(isBlankObj(result.msg)){
            STREET = result.data;
        }
        $("#streetSelect").empty(); // 清空之前加载的数据
        $("#streetSelect").append("<option value='' title='' selected>未选择</option>");
        if(!isBlankObj(STREET)){
            for(var i = 0; i < STREET.length; i++){
                $("#streetSelect").append("<option value='"+STREET[i].id+"' title="+STREET[i].name+">"+STREET[i].name+"</option>");
            }
        }
    }else{
        layer.msg("levels参数异常！");
        return;
    }
}*/
/**
 * 增加input元素校验
 * @param id 元素ID
 * @param rules 元素校验规则
 * @param showDIV 是否显示元素所在层
 */
function addValidate(id, rules, errmsg, showDIV){
    if(showDIV){
        $("#"+id+"DIV").show();
    }
    $("#"+id).attr("disabled", false); // 取消禁用
    $("#"+id).attr("valid", rules); // 增加非空校验
    $("#"+id).attr("errmsg", errmsg); // 增加非空校验
    $("#"+id+"Red").html("&nbsp;*"); // 增加红*
}

/**
 * 移除input元素校验
 * @param id 元素ID
 * @param isEmptyVal 是否清空元素当前值
 * @param hideDIV 是否隐藏元素所在层
 */
function removeValidate(id, isEmptyVal, hideDIV){
    if(hideDIV){
        $("#"+id+"DIV").hide();
    }
    if(isEmptyVal){
        $("#"+id).val("");
//		$("#"+id).attr("disabled", true);
    }
    $("#"+id).removeAttr("valid"); // 移除非空校验
    $("#"+id).removeAttr("errmsg"); // 移除错误提示信息
    $("#"+id).removeClass("layui-form-danger"); // 移除错误显示class
    $("#"+id+"Red").html(""); // 移除红*
}





/**
 * 处理空的参数   将对象置空
 * @param datas
 * @returns
 */
function cleanParams(datas){
    var v_data ={};
    for(var a in datas){
        if (datas[a] != null && datas[a] instanceof Array) {
            v_data[a]=[];
        }else {
            v_data[a] = null;
        }
    }
    return v_data;
}


/**
 * 文件下载
 * @param id
 */
function downloadFile(id) {
    //由于无法直接使用Ajax下载 所以先拼接成form表单提交然后下载
    var form = $("<form>");
    form.attr("style","display:none");
    form.attr("target", "");
    form.attr("method","post");
    form.attr("action",path + "/download.do");
    var input1 = $("<input>");
    input1.attr("type","hidden");
    input1.attr("name","id");
    input1.attr("value",id);
    $("body").append(form);
    form.append(input1);
    form.submit();
    form.remove();
}

var RegExps = {
    isRequired: /[\S]+/,
    isNumber: /^[-\+]?\d+(\.\d+)?$/,
    isEmail: /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/,
    isMobile: /^1[0-9]{10}$/, //手机号(中国)
    isTel: /0\d{2,3}-\d{7,8}/, // 座机号(中国)
    isMobileTel: /^(0|86|17951)?(13[0-9]|15[012356789]|17[01678]|18[0-9]|14[57])[0-9]{8}$|^(0[0-9]{2,3}\-)([2-9][0-9]{6,7})+(\-[0-9]{1,4})?$/, //同时允许手机号&座机号(中国) //86536455、0551-5555555、13888887777
    isIdCard: /(^\d{15}$)|(^\d{17}[0-9Xx]$)/,
    isMoney: /^\d{1,12}(?:\.\d{1,2})?$/,
    isZip: /^[1-9]\d{5}$/,
    isQQ: /^[1-9]\d{4,10}$/,
    isInt: /^[-\+]?\d+$/, //整数
    isPositive: /^[+]{0,1}(\d+)$|^[+]{0,1}(\d+\.\d+)$/, //正数(包含小数)
    isPositiveInt: /^[1-9]\d*$/, //正整数
    isPositiveString: /^[0-9]\d*$/, //可以为0的正整数字符串
    isPositiveZero: /^([1-9]\d*|[0]{1,1})$/, //非负整数(包括0)
    isEnglish: /^[A-Za-z]+$/, //英文校验
    isEnglishNumLine: /^[A-Za-z\d_]+$/, //只能输入英文、数字、下划线
    isEnglishDot: /^[A-Za-z\d.]+$/, //只能输入英文、数字、点
    isChineseEnglishNumLine: /^[\u4E00-\u9FA5-A-Za-z\d_\d/\//\（\）\(\)]+$/, //只能输入中文、英文、数字、下划线、正斜杠、小括号(下划线位置不限, 排除非法字符)
    isChinese: /^[\u0391-\uFFE5]+$/,
    isUrl: /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
    isDate: /^\d{4}-\d{1,2}-\d{1,2}$/,
    isTime: /^\d{4}-\d{1,2}-\d{1,2}\s\d{1,2}:\d{1,2}:\d{1,2}$/,
    isAccount: /^[a-z]\w{3,}$/i, //账号校验
    isPassword: /^.{5,20}[0-9a-zA-Z,.;?@]$/,
    isEnglishChinese: /^[A-Za-z\u0391-\uFFE5]+$/i,
    isTableName: /^[a-z]\w*$/i,
    isSymbols: /^[\w\u0391-\uFFE5]+$/i,
    isNum: /^\d+$/i,
    isXs: /^[0-9]+(\.[0-9]{0,2})?$/,
    isXs2: /^\d{1}(?:\.\d{1,2})?$/,
    isScore: /^[-\+]?\d+(\.\d{0,1})?$/,
    isScore1: /^[+]?\d+(\.\d{0,1})?$/,
    isZInt: /^[+]?\d+$/,
    isDateT: /^\d{4}-\d{1,2}-\d{1,2}.\d{1,2}:\d{1,2}$/,
    isNoNegativeInt: /^\d+$/,
    isAphone: /^([0-9-]+)$/,
    isGRF: /^[A-Za-z/_0-9]+\.grf$/,  // 只能输入字母、下划线、数字，并且以.grf结尾
    isCron: "",
    isRepsd: "",
    isBenchmark: /^[-\+]?\d{1,12}(\.\d{1,4})?$/,//基准进价(元) 12位整数后小数四位 //by gaojc begin
    isNumNew: /^[-\+]?\d{1,9}(\.\d{1,2})?$/,  //输入数字(9位整数后小数3位)
    isTaxation: /^[0](.[0-9]{1,4})?$/,    //进项税  输入数字(0点小数后四位)。
    isPackage:/^[-\+]?\d{1,11}(\.\d{1,3})?$/  , // 包装 输入数字(11位整数后小数3位)。
    isStock:/^[-\+]?\d{1,7}(\.\d{1,3})?$/,  // 7位整数后小数3位         //by gaojc  end
    isVehicle:/^[0-9]+(.[0-9]{1,4})?$/,   //0-9开头 后面1-4位小数(车辆位置用到)
    isPrice: /^(([1-9][0-9]*)|(([0]\.\d{1,2}|[1-9][0-9]*\.\d{1,2})))$/  //两位小数
};