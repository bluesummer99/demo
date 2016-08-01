$(function (){
		var tableId;
	 	var url;
	 	var param;
	 	var singleSelect = true;
	 	var checkOnSelect=true;
	 	var selectOnCheck=true;
	 	var onSelectAll=true;
	 	var auditable = false;
	 	
	 	var auditableColumn = [{
				field : 'auditable.createdBy',
				title : '创建人',
				width : 50,
				formatter : function(value, rowData, rowIndex) {
					if(rowData.auditable && rowData.auditable.createdBy){
						return rowData.auditable.createdBy;
					}
					return '';
				}
			},
			{
				field : 'auditable.dateCreated',
				title : '创建时间',
				width : 70,
				formatter : function(value, rowData, rowIndex) {
					if(rowData.auditable && rowData.auditable.dateCreated){
						return rowData.auditable.dateCreated;
					}
					return '';
				}
			},
			{
				field : 'auditable.updatedBy',
				title : '更新人',
				width : 50,
				formatter : function(value, rowData, rowIndex) {
					if(rowData.auditable && rowData.auditable.updatedBy){
						return rowData.auditable.updatedBy;
					}
					return '';
				}
			},
			{
				field : 'auditable.dateUpdated',
				title : '更新时间',
				width : 70,
				formatter : function(value, rowData, rowIndex) {
					if(rowData.auditable && rowData.auditable.dateUpdated){
						return rowData.auditable.dateUpdated;
					}
					return '';
				}
			}];
	  	$.MyPage = function (arg) {
	  		 //初始化页面
	         this.init = function (loadData) {     //this.init方法，加上了this，就是公有方法了，外部可以访问。
	             
	        	url = arg.url;
	        	param = arg.param;
	        	tableId = arg.tableId;
	        	if(loadData==undefined){
	        		loadData = true;
	        	}
	        	if(arg.singleSelect!=undefined){
	        		singleSelect = arg.singleSelect;
	        	}
	        	if(arg.checkOnSelect!=undefined){
	        		checkOnSelect = arg.checkOnSelect;
	        	}
	        	if(arg.onSelectAll!=undefined){
	        		onSelectAll = arg.onSelectAll;
	        	}
	        	if(arg.initPageNum == undefined||!arg.initPageNum){
	        		arg.initPageNum=1;
	        	}
	        	
	        	if(arg.initPageSize == undefined || !arg.initPageSize){
	        		arg.initPageSize=10
	        	}
	        	if(arg.auditable!=undefined&& arg.auditable==true){
	        		var originalCol = arg.columns[0];
	        		var last = originalCol.pop();
	        		if(last.field == 'opt'){
	        			originalCol.push(auditableColumn[0]);
	        			originalCol.push(auditableColumn[1]);
	        			originalCol.push(auditableColumn[2]);
	        			originalCol.push(auditableColumn[3]);
	        			originalCol.push(last);
	        		}else{
	        			originalCol.push(last);
	        			originalCol.push(auditableColumn[0]);
	        			originalCol.push(auditableColumn[1]);
	        			originalCol.push(auditableColumn[2]);
	        			originalCol.push(auditableColumn[3]);
	        		}
	        	}
	        	
	           
	           //构建表格所有属性
	     	  	$('#'+tableId).datagrid({
	     			collapsible:true,
	     			fitColumns: true, 
	     			remoteSort: false,
	     			singleSelect: singleSelect,
	     			selectOnCheck:selectOnCheck,
	     			checkOnSelect:checkOnSelect,
	     			onSelectAll:onSelectAll,
	     			nowrap: false,
	     	  		striped: true,
	     			method:'get',
	     			loadMsg:'数据努力加载中...',
	     			pagination: true,
	     			pageSize: arg.initPageSize,
	     			pageList: [arg.initPageSize, arg.initPageSize*2, arg.initPageSize*4],
	     			columns: arg.columns,
	     			onSelect:function (rowIndex, rowData){ //用户选择一行的时候触发
	     				if(arg.onSelect!=undefined){
	     					onSelect(rowIndex,rowData);
	     				}
	     			},
	     			onUnselect:function (rowIndex, rowData){ //用户取消选择一行的时候触发
	     				if(arg.onUnselect!=undefined){
	     					onUnselect(rowIndex,rowData);
	     				}
	     			},
	     			onSelectAll:function (rows){ //在用户选择所有行的时候触发
	     				if(arg.onSelectAll!=undefined){
	     					onSelectAll(rows);
	     				}
	     			},
	     			onUnselectAll:function (rows){//在用户取消选择所有行的时候触发
	     				if(arg.onUnselectAll!=undefined){
	     					onUnselectAll(rows);
	     				}
	     			},
	     			onLoadSuccess:function(data){ 
	     				if(arg.onLoadSuccess!=undefined){
	     					onLoadSuccess(data);
	     				}
	     			},
	     			onCheck:function(rowIndex, rowData){
	     				if(arg.onCheck!=undefined){
	     					onCheck(rowIndex, rowData);
	     				}
	     			},
	     			onClickRow:function(rowIndex, rowData){
	     				if(arg.onClickRow!=undefined){
	     					onClickRow(rowIndex, rowData);
	     				}
	     			}
	     	  	});
	     	  	var pager = $('#'+tableId).datagrid('getPager');
	     	  	pager.pagination({
	     	  		total:0,
					showRefresh: false,
					beforePageText: '第', //页数文本框前显示的汉字  
					afterPageText: '页    共 {pages} 页',
					displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
	     	  	});
	     	  	
	     	   if(loadData){
		        	requestData(arg.initPageNum,arg.initPageSize,arg.url,arg.param);
		        }
	         };
	         
	         this.queryData = function(queryParam){
	        	 $('#'+tableId).datagrid("load"); //点击查询后 ，初始化页面分页内容
	 			 var opts = $('#'+tableId).datagrid('options');
	 			 pageNum = opts.pageNumber;
	 			 pageSize = opts.pageSize;
	 			 
	 			 
	        	 param = queryParam;  //接收页面参数
	        	 requestData(pageNum,pageSize,arg.url,queryParam);
	         };
	         
	       //初始化页面查询数据
	    };
	    
	  	
	  	//分页方法
	  	function pagerFilter(data) {

			if (typeof data.length == 'number' && typeof data.splice == 'function') { // is array
				data = {
					total: data.length,
					rows: data
				};
			}
			var dg = $(this);
			var opts = dg.datagrid('options');
			var pager = dg.datagrid('getPager');
			pager.pagination({
				showRefresh: false,
				beforePageText: '第', //页数文本框前显示的汉字  
				afterPageText: '页    共 {pages} 页',
				displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录',
				onSelectPage: function(pageNum, pageSize) {
					
					requestData(pageNum,pageSize,url,param);
					opts.pageNumber = pageNum;
					opts.pageSize = pageSize;
					pager.pagination('refresh', {
						pageNumber: pageNum,
						pageSize: pageSize
					});
				}
			});
			if (!data.originalRows) {
				data.originalRows = (data.rows);
			}
			data.rows = data.originalRows;
			return data;
		}
	  	
	  	
	  	//获取请求数据
	  	function requestData(pageNum,pageSize,url,param) {
				var baseData; 
				var paramVar = "page="+(pageNum-1)+"&size="+pageSize;
				if(param!=""){
					paramVar = paramVar+"&" + param;
				}

				$.ajax({
					url:url,  //请求地址
					dataType: "text",
					type: "GET",
					data: paramVar,
					success: function(jsonDate) {
						eval("baseData="+jsonDate);  //将json串转换成json对象

						$('#'+tableId).datagrid({  
							loadFilter: pagerFilter
						}).datagrid("loadData",baseData.content);  
						
					},
					cache: false
				});
		}

})