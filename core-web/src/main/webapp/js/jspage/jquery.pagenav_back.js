/**
*  jquery分页插件
*  $.fn.pageNav.author : longyt
*  使用方法 $(selector).pageNav();
*   或  
*	var options = {"pageNo" : 1,"pageSize" : 10};  //用来替换默认值中 $.fn.pageNav.defaults 需要替换的值
*	$(selector).pageNav(options);    //  $(selector).pageNav({"pageNo" : 1,"pageSize" : 10,"pervPage_text" : "perv","nextPage_text" : "next"})
*   version 1.0
*/
(function($){
	
//		var _pageNav[]; //全局对象, 等于 $.fn.pageNav
		var _version = "version 1.0"; //版本
		
		$.fn.pageNav = function (options) {
			//实现参数的替换
			var opts  = $.extend({},$.fn.pageNav.defaults,options || {}); 
			methods._generateNav.apply(this,[opts]); //生成导航条		
//			_pageNav = methods._bindEvent.apply(this,[opts]); //绑定事件
			methods._bindEvent.apply(this,[opts]); //绑定事件
			return this;
		}
		
		//扩展插件的方法
		$.extend($.fn.pageNav,{
			setOptions : function(options){
				this._opts = $.extend({},this._opts,options || {});
			},
			loadData : function(){
				//重新加载数据
//				methods._generateNav.apply(_pageNav[0],[this._opts]);
			}
			,getVersion : function() {
				return _version;
			}
		});
		
		$.fn.pageNav.defaults = {
			"pageNo" : 1, //当前页
			"pageSize" : 10, //每页数
			"async" : false, //默认同步
			"totalPage" : 20, //总页数，如果是ajax方式,根据url算出和params算出
			"showPageCount" : 9, //显示多少页  1 ... 20 21 22 23 24 25 26 ... 50
			"url" : "",		//服务器地址
			"type" : "POST", //提交方式
			"dataType" : "json" , //数据类型
			"params" : [],  //提交到服务器的参数,json对像数组[{name : "",value : ""}],
			"pervPage_text" : "上一页", //上一页显示的文字
			"nextPage_text" : "下一页", //下一页显示的文字
			"onSuccess" : null , //异步加载数据成功或跳转页码成功后调用的function ,参数 data ,
			"onPervPage" : null, //点击上一页事件 ，参数值为当前页,可以通过return false 阻止事件继续
			"onNextPage" : null, //点击下一页事件 ，参数值为当前页,可以通过return false 阻止事件继续
			"onChangePage" : null  //页数改变前事件, {currentPage,changePage},可以通过return false 阻止事件继续,如果每次翻页都刷新页面，可以这里处理
		};	
		
		//私有的方法
		var methods = {
			_requestData : function(opts){
				//请求数据
				if(opts.async == true) {
					var _newParam = opts.params;
					_newParam.pageNo = opts.pageNo;
					_newParam.pageSize = opts.pageSize;
					$.ajax({
						url : opts.url,
						type : opts.type,
						dataType : opts.dataType,
						async : false,
						data : _newParam,
						success : function(data){
							//todo......................
							opts.totalPage = data.totalPage;
							if(typeof opts.onSuccess =="function") {
								opts.onSuccess(data);
							}
						}
					});
				}	
			},
			_bindEvent : function(opts){
				return $(this).each(function(){
					var $this = $(this);		
					$this.pageNav._opts = opts; //将合并后的参数保存在当前的pageNav对象中
					$this.bind("mousedown",function(e){
						var event = e || window.event;
						var srcElem = event.srcElement || event.target;
						if(!srcElem.tagName == "span" || !srcElem.tagName == "SPAN") return false;
						var _opts = $this.pageNav._opts; //从上面保存的地方取得参数 
						var $pageBtn = $(srcElem);
						//上一页，事件	
						var _html = $pageBtn.html().replace(/(^\s*)|(\s*$)/g,"");
						var isContinue = true;
						var _currentPage = _opts.pageNo;
						if(_html == _opts.pervPage_text) {
							if(typeof _opts.onPervPage == "function") {
								isContinue = _opts.onPervPage(_currentPage);
								if(1 == _currentPage) {return false;}
								if(typeof isContinue == "undefined") isContinue = true;
							}
							if(_currentPage <= 1 || !isContinue) return false;
							 _opts.pageNo = _currentPage - 1;
						} else if (_html == _opts.nextPage_text) {
							//下一页，事件
							if(typeof _opts.onNextPage == "function") {
								isContinue = _opts.onNextPage(_currentPage);
								if(_opts.totalPage == _currentPage) {return false;}
								if(typeof isContinue == "undefined") isContinue = true;
							}
							if(_currentPage >= _opts.totalPage || !isContinue) return false;
							_opts.pageNo = _currentPage + 1;
						} else {
							if($pageBtn.hasClass("pageBtnNum") && !$pageBtn.hasClass("moreBtnNum")) {
								_opts.pageNo = Number(_html);
							} else {return false;}
						}
						if(typeof _opts.onChangePage == "function") {
							isContinue = _opts.onChangePage({currentPage : _currentPage,changePage : _opts.pageNo});
							if(typeof isContinue == "undefined") isContinue = true;
						}
						if(isContinue) {
							methods._generateNav.apply($this,[_opts]);
						} else {
							_opts.pageNo = _currentPage;
						}
						
					});	
				});
			}, _generateNav : function(opts){
				return $(this).each(function(){
					var $this = $(this);
//					methods._requestData(opts);
					methods._requestData.apply(this,[opts]); //生成导航条	
					// 生成分页导航
					var htmlArray = [];
					htmlArray.push("<span type=\"button\" class=\"pageBtn\">"+ opts.pervPage_text +"</span>");
					//总页数大于显示的页数时
					if(opts.totalPage > opts.showPageCount){
						var start = 1;
						var end = opts.showPageCount - 1;
						var showPageSelf = Math.floor(opts.showPageCount / 2);
						//当前页大于 显示页数的一半(取最小整)加1时。并且当前页小于 当前页数加上显示页数的一半(取最小整)
						if(opts.pageNo > (showPageSelf + 1) && (opts.pageNo + showPageSelf) < opts.totalPage) {
							htmlArray.push("<span type=\"button\" class=\"pageBtnNum\">1</span>");
							htmlArray.push("<span type=\"button\" class=\"pageBtnNum moreBtnNum\" >...</span>");
							//此时开始处应该等于当前面减去 (显示页减去第一页和最后一面，除2后再取最小整)
							//此时结尾处应该等于当前面加上 (显示页减去第一页和最后一面，除2后再取最小整)
							var _perPage = Math.floor((opts.showPageCount - 2) / 2);
							start = opts.pageNo - _perPage;
							end = opts.pageNo + _perPage;
							methods._eachPush(start,end,htmlArray,opts);
							htmlArray.push("<span type=\"button\" class=\"pageBtnNum moreBtnNum\" >...</span>");
							htmlArray.push("<span type=\"button\" class=\"pageBtnNum\">" + opts.totalPage + "</span>");		
						} else if(opts.pageNo + showPageSelf >= opts.totalPage) {
							//当前页加上 显示页的一半(取最小整)大于 总页数时 进入此分支
							htmlArray.push("<span type=\"button\" class=\"pageBtnNum\">1</span>");
							htmlArray.push("<span type=\"button\" class=\"pageBtnNum moreBtnNum\" >...</span>");
							start = opts.totalPage - (opts.showPageCount - 2);
							end = opts.totalPage;
							methods._eachPush(start,end,htmlArray,opts);
						} else {
							methods._eachPush(start,end,htmlArray,opts);
							htmlArray.push("<span type=\"button\" class=\"pageBtnNum moreBtnNum\" >...</span>");
							htmlArray.push("<span type=\"button\" class=\"pageBtnNum\">" + opts.totalPage + "</span>");
						}
					} else {
						//小于显示的页数时
						methods._eachPush(1,opts.totalPage,htmlArray,opts);
					}
					htmlArray.push("<span type=\"button\" class=\"pageBtn\">"+opts.nextPage_text+"</span>");
					$this.addClass("paging");
					$this.html("");
					$this.append(htmlArray.join(""));
				});	
			}, _eachPush : function(start,end,htmlArray,opts){
				for(var i = start; i <= end ; i++) {	
					if(i == opts.pageNo) {
						htmlArray.push("<span type=\"button\" class=\"pageBtnNum selectBtnNum\">" + i + "</span>");			
					} else {
						htmlArray.push("<span type=\"button\" class=\"pageBtnNum\">" + i + "</span>");
					}
				}
			}
		};
	
})(jQuery);