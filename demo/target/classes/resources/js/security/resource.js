$(function() {
	var columns = [ [ {
		field : 'caption',
		title : '名称',
		width : 80,
		hidden : false
	}, {
		field : 'resourceType',
		title : '类型',
		width : 80,
		hidden : false
	}, {
		field : 'linkurl',
		title : '链接',
		width : 80,
		hidden : false
	},
	{
		field : 'opt',
		title : '操作',
		width : 80,
		align : 'center',
		formatter : function(value, rowData, rowIndex) {
			return '<a href="javascript:void(0);" onclick="edit('+rowIndex+')">编辑</a>&nbsp;'
			+ '<a href="javascript:void(0);" onclick="doDelete('+rowIndex+')" class="tb-btn delete1">删除</a>';
		}
	} ] ];

	var page = new $.MyPage({
		tableId : "table",
		url : ctx + '/resource/queryPage',
		columns : columns
	});
	page.init(false);

	$('#queryBtn').click(function() {
		var selected = $('#wtree').tree('getSelected');
		if(selected){
			$('#q_parentId').val(selected.id);
		}
		var param = $("#q_form").serialize();
		page.queryData(param);
	});
	

	$('#addBtn').click(function() {
		$('#dlg').dialog("open").dialog('setTitle', '新增');
		$('#fm').form('clear');
		var selected = $('#wtree').tree('getSelected');
		if(selected){
			$('#parentId').combotree('setValue',selected.id);
		}
		   
	});
	var resource = parent.windowCache.getResource();
	$('#wtree').tree({    
	    data:  resource,
		onSelect:function(node){
			$('#q_parentId').val(node.id);
			var param = $("#q_form").serialize();
			page.queryData(param);
		}
	});  
	$('#organizationId').combotree({    
		data:  resource,
	});  

});

function doSave() {
	if ($("#fm").form("validate")) {
		var data = $('#fm').serialize();
		$.ajax({
			type : 'POST',
			dataType : "json",
			data : data,
			url : ctx + "/resource/save",
			success : function(result) {
				if (result.status == "error") {
					$.messager.alert("提示信息", result.message, "info");
				} else if (result.status == "success") {
					$.messager.alert("提示信息", "数据保存成功", "info", function() {
					
						refresh();
						
					});
				}
			},
			cache : false,
		});
	}
}
function doDelete(rowIndex) {
	$('#table').datagrid('selectRow',rowIndex);
	var row = $('#table').datagrid('getSelected');
	var data = {'id':row.id};
	$.messager.confirm('确认', '	确认删除该信息?', function(r) {
		if(r && row){
			$.ajax({
				type : 'POST',
				dataType : "json",
				data : data,
				url : ctx + "/resource/delete",
				success : function(result) {
					if (result.status == "error") {
						$.messager.alert("提示信息", result.message, "info");
					} else if (result.status == "success") {
						$.messager.alert("提示信息", "删除成功", "info"); 
						refresh();
						
					}
				},
				cache : false,
			});
		}
	})
}
function refresh(){
	$('#dlg').dialog('close');
	var pager = $('#table').datagrid('getPager');
	pager.pagination("select");
	var data = parent.windowCache.reloadResource();
	$('#wtree').tree({ 
		data:data
	});
	$('#parentId').combotree({    
		data:data,
	});
}

function edit(rowIndex) {
	$('#table').datagrid('selectRow',rowIndex);
	var row = $('#table').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog("open").dialog('setTitle', '编辑');
		$('#fm').form('clear');
		$('#id').val(row.id);
		$('#caption').textbox('setValue',row.caption);
		$('#resourceType').textbox('setValue', row.resourceType);
		$('#linkurl').textbox('setValue', row.linkurl);
		$('#organizationId').combotree('setValue', row.parentId);
	}
}
