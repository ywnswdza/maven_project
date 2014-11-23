(function($){
	if(!$) return;
	$.myAjax = function(options){
		var opts = $.extend(true,{},$.myAjax.defaults,options || {});
		if(!opts.url) return;
		$.ajax(opts);
	};
	$.myAjax.defaults = {
		type : 'POST',
		url : null,
		dataType : 'json',
		data : null,
		success : function(data){
			if(typeof data === "object") {
				if(data.errorMsg) {
					if($.ligerDialog) {
						$.ligerDialog.error(data.errorMsg);
						try {$.ligerDialog.closeWaitting();} catch (e){}
					}
					else alert(data.errorMsg);
					return;
				}
			}
			if(typeof data === "string" && data.indexOf("errorMsg") != -1 ) {
				data = eval("(" + data + ")");
				if($.ligerDialog) {
					$.ligerDialog.error(data.errorMsg);
					try {$.ligerDialog.closeWaitting();} catch (e){}
				}
				else alert(data.errorMsg);
				return;
			}
			if(typeof this.ajaxSuccess === "function") this.ajaxSuccess(data);
		},
		error : function(XMLHttpRequest, textStatus, errorThrown){
			if(jQuery && jQuery.ligerDialog) jQuery.ligerDialog.error("操作失败，请与管理员取得联系！！！");
			else alert("操作失败，请与管理员取得联系！！！");
			var status = XMLHttpRequest.status;
			var responseText = XMLHttpRequest.responseText;
			console.error("ajax请求错误！！！status[" + status + "]," + responseText);
			if(typeof this.ajaxError === "function") this.ajaxError(XMLHttpRequest, textStatus, errorThrown);
		},
		ajaxSuccess : null,
		ajaxError : null
	};
})(jQuery);


// js 日期时格式化
Date.prototype.format =function(format){
	var o = {
	"M+" : this.getMonth()+1, //month
	"d+" : this.getDate(), //day
	"h+" : this.getHours(), //hour
	"m+" : this.getMinutes(), //minute
	"s+" : this.getSeconds(), //second
	"q+" : Math.floor((this.getMonth()+3)/3), //quarter
	"S" : this.getMilliseconds() //millisecond
	}
	if(/(y+)/.test(format)) format=format.replace(RegExp.$1,
	(this.getFullYear()+"").substr(4- RegExp.$1.length));
	for(var k in o)if(new RegExp("("+ k +")").test(format))
	format = format.replace(RegExp.$1,
	RegExp.$1.length==1? o[k] :
	("00"+ o[k]).substr((""+ o[k]).length));
	return format;
}

//null NULL 转为空串
function str_empty(obj){
	if(!obj) return "";
	obj = obj.toString();
	if(obj == "null" || obj == "NULL") return "";
	return obj;
}

//去两边空格
function tirm(obj) {
	//obj = v_str(obj);
	return obj.replace(/(^\s*)|(\s*$)/g,"");
}

//只允许输入数字或小数点
var doubleInput = function(id){
	var ele = document.getElementById(id);
	if(!ele) return;
	ele.setAttribute("t_value", "");
	ele.setAttribute("o_value", "");
	ele.onkeypress = function(){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/)) this.value=this.t_value;
		else  this.t_value=this.value;
		if(this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/)) this.o_value=this.value
	}
	ele.onkeyup = function(){
		if(!this.value.match(/^[\+\-]?\d*?\.?\d*?$/)) this.value=this.t_value;
		else this.t_value=this.value;
		if(ele.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?)?$/)) this.o_value=this.value
	}
	ele.onblur = function(){
		if(!this.value.match(/^(?:[\+\-]?\d+(?:\.\d+)?|\.\d*?)?$/)) this.value= this.o_value;
		else{
			if(this.value.match(/^\.\d+$/)) this.value= 0 + this.value;
			if(this.value.match(/^\.$/)) this.value=0;
			this.o_value=this.value
		}
	}
	return ele;
}

//获取屏幕的width和height，使用getWindowSize().w
function getWindowSize() {
	var client = {w:0,h:0};
	if(typeof document.compatMode != 'undefined' && document.compatMode == 'CSS1Compat') {
		client.w = document.documentElement.clientWidth;
		client.h = document.documentElement.clientHeight;
	}else if(typeof document.body != 'undefined' && (document.body.scrollLeft || document.body.scrollTop)){
		client.w = document.body.clientWidth;
		client.h = document.body.clientHeight;
	}
	return client;
}

// 获取随机整数，从区域[start,end]取值
function getRandom(start, end){
	var x = start; //x上限
	var y = end;  //y下限
	var rand = parseInt(Math.random() * (x - y + 1) + y);
}

//js跨域访问 
function corssVisit(visurl){
 var resultmsg="error";
 if(navigator.userAgent.indexOf("MSIE")>0){ 
				 var xdr = new XDomainRequest();
	       		 xdr.onload = function (e) {
	            resultmsg = xdr.responseText;
	        };
	        xdr.onerror = function (e) {
	            //error
	        }
	        xdr.open("GET", visurl);
	        xdr.send();
         } else
	   {
	  $.ajax({
		url:visurl,
		type:"GET",
		async:false,
		cache:false,
		success:function(msg){resultmsg=msg;},
		error:function(msg){}
	 });
			
	   }
	   return resultmsg;
}


//正则验证方法
function validationValue(value,type){
	var reqValueZH1 = new RegExp('^[_\\-\\u4e00-\\u9fa5]*$'); //中文可为空
	var reqValueZH2 = new RegExp('^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]+$'); //数字、中文
	var reqValueZH3 = new RegExp('^[A-Za-z0-9_\\-\\u4e00-\\u9fa5\\x00-\\xFF]+$'); //ACSII字符
	var reqValueNUM = new RegExp('^[0-9]+$');
	var reqValueID = new RegExp('^[A-Za-z0-9]+$'); // 字母、数字，不能为空
	var reqValuePwd = new RegExp('^[A-Z|a-z|0-9|_]+$');
	var reqValuePhone = new RegExp('^(0[0-9]{2,3}\-)?([1-9][0-9]{6,7})+(\-[0-9]{1,4})?$'); //电话
	var reqValueMobilePhone = new RegExp('^(13|15|14|18)[0-9]{9}$'); //手机
	var reqValueEmail = new RegExp('^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$');
	var reqValueIP4 = new RegExp('^(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)\\.(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)$');
	if(type == "Pwd" && !reqValuePwd.test(value)){
		return false;
	}else if(type == "Phone" && !reqValuePhone.test(value)){
		return false;
	}else if(type == "MobilePhone" && !reqValueMobilePhone.test(value)){
		return false;
	}else if(type == "Email" && !reqValueEmail.test(value)){
		return false;
	}else if(type == "ID" && !reqValueID.test(value)){
		return false;
	}else if(type == "ZH1" && !reqValueZH1.test(value)){
		return false;
	}else if(type == "ZH2" && !reqValueZH2.test(value)){
		return false;
	}else if(type == "ZH3" && !reqValueZH3.test(value)){
		return false;
	}else if(type == "NUM" && !reqValueNUM.test(value)){
		return false;
	}else if(type == "IP4" && !reqValueIP4.test(value)){
		return false;
	}
	return true;
}

function errorCall(XMLHttpRequest, textStatus, errorThrown) {
	//if(typeof show)
	if(jQuery && jQuery.ligerDialog) jQuery.ligerDialog.error("操作失败，请与管理员取得联系！！！");
	else alert("操作失败，请与管理员取得联系！！！");
	var status = XMLHttpRequest.status;
	var responseText = XMLHttpRequest.responseText;
	console.error("ajax请求错误！！！status[" + status + "]," + responseText);
}

/** ajax 提交参数转 post **/
function ajaxToPost(ajaxData){
	var postData = {};
	for(var i in ajaxData) {
		//queryParams
		var param = ajaxData[i];
		postData[param["name"]] = param["value"];
	}
	return postData;
}

/* 
 *  方法:Array.remove(dx) 
 *  功能:根据元素位置值删除数组元素. 
 *  参数:元素值 
 *  返回:在原数组上修改数组 
 */  
Array.prototype.remove = function (dx) {  
    if (isNaN(dx) || dx > this.length) {  
        return false;  
    }  
    for (var i = 0, n = 0; i < this.length; i++) {  
        if (this[i] != this[dx]) {  
            this[n++] = this[i];  
        }  
    }  
    this.length -= 1;  
};  



String.prototype.startWith=function(str){    
  var reg=new RegExp("^"+str);    
  return reg.test(this);       
} 
 
String.prototype.endWith=function(str){    
  var reg=new RegExp(str+"$");    
  return reg.test(this);       
} 


