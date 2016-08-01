$(function() {
	var columns = [ [ {
		field : 'organizationName',
		title : '名称',
		width : 80,
		hidden : false
	}, {
		field : 'organizationType',
		title : '类型',
		width : 80,
		hidden : false
	}, {
		field : 'organizationCode',
		title : '编码',
		width : 80,
		hidden : false
	}, {
		field : 'parentId',
		title : '所属机构',
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
		url : ctx + '/organization/queryPage',
		columns : columns
	});
	page.init(false);

	$('#queryBtn').click(function() {
		var selected = $('#wtree').tree('getSelected');
		if(selected){
			$('#q_parentId').val(selected.id);
		}
		var param = $('#q_form').serialize();
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
	
	var organization = parent.windowCache.getOrganization();
	$('#wtree').tree({    
	    data:  organization,
		onSelect:function(node){
			$('#q_parentId').val(node.id);
			var param = $("#q_form").serialize();
			page.queryData(param);
		}
	});  
	$('#parentId').combotree({    
		data:  organization,
	});  

});

function doSave() {
	if ($("#fm").form("validate")) {
		var data = $('#fm').serialize();
		$.ajax({
			type : 'POST',
			dataType : "json",
			data : data,
			url : ctx + "/organization/save",
			success : function(result) {
				if (result.status == "error") {
					$.messager.alert("提示信息", result.message, "info");
				} else if (result.status == "success") {
					$.messager.alert("提示信息", "数据保存成功", "info");
					refresh();
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
				url : ctx + "/organization/delete",
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
	var data = parent.windowCache.reloadOrganization();
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
		$('#dlg').dialog("open").dialog('setTitle', '编辑城市信息');
		$('#fm').form('clear');
		$('#id').val(row.id);
		$('#organizationName').textbox('setValue', row.organizationName);
		$('#organizationType').textbox('setValue', row.organizationType);
		$('#organizationCode').textbox('setValue', row.organizationCode);
		$('#parentId').combotree('setValue', row.parentId);
	}
}
