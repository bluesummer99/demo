$(function() {
	var columns = [ [ {
		field : 'roleName',
		title : '角色名称',
		width : 80,
		hidden : false
	}, {
		field : 'project',
		title : '项目',
		width : 80,
		hidden : false
	}, {
		field : 'roleType',
		title : '角色类型',
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
			+ '<a href="javascript:void(0);" onclick="editResource('+rowIndex+')">资源分配</a>&nbsp;'
			+ '<a href="javascript:void(0);" onclick="editOrganization('+rowIndex+')">数据范围</a>&nbsp;'
			+ '<a href="javascript:void(0);" onclick="doDelete('+rowIndex+')" class="tb-btn delete1">删除</a>';
		}
	} ] ];

	var page = new $.MyPage({
		tableId : "table",
		url : ctx + '/role/queryPage',
//		auditable : true,
		columns : columns
	});
	page.init(false);

	$('#queryBtn').click(function() {
		var param = $("#q_form").serialize();
		page.queryData(param);
	});

	$('#addBtn').click(function() {
		$('#dlg').dialog("open").dialog('setTitle', '新增');
		$('#fm').form('clear');
		var selected = $('#wtree').tree('getSelected');
		if(selected){
			$('#organizationId').combotree('setValue',selected.id);
		}
	});
	var organization = parent.windowCache.getOrganization();
	$('#wtree').tree({    
	    data:  organization,
		onSelect:function(node){
			$('#q_organizationId').val(node.id);
			var param = $("#q_form").serialize();
			page.queryData(param);
		}
	});  
	$('#organizationId').combotree({    
		data: organization,
	});  
});

function doSave() {
	if ($("#fm").form("validate")) {
		var data = $('#fm').serialize();
		$.ajax({
			type : 'POST',
			dataType : "json",
			data : data,
			url : ctx + "/role/save",
			success : function(result) {
				if (result.status == "error") {
					$.messager.alert("提示信息", result.message, "info");
				} else if (result.status == "success") {
					$.messager.alert("提示信息", "数据保存成功", "info", function() {
						$('#dlg').dialog('close');
						var pager = $('#table').datagrid('getPager');
						pager.pagination("select");
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
				url : ctx + "/role/delete",
				success : function(result) {
					if (result.status == "error") {
						$.messager.alert("提示信息", result.message, "info");
					} else if (result.status == "success") {
						$.messager.alert("提示信息", "删除成功", "info", function() {
							var pager = $('#table').datagrid('getPager');
							pager.pagination("select");
						});
					}
				},
				cache : false,
			});
		}
	})
}

function edit(rowIndex) {
	$('#table').datagrid('selectRow',rowIndex);
	var row = $('#table').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog("open").dialog('setTitle', '编辑');
		$('#fm').form('clear');
		$('#id').val(row.id);
		$('#project').textbox('setValue', row.project).textbox('readonly');
		$('#roleName').textbox('setValue', row.roleName);
		$('#roleType').textbox('setValue', row.roleType);
		$('#organizationId').combotree('setValue', row.organizationId);
	}
}

//--------------资源------------------
function editResource(rowIndex){
	$('#table').datagrid('selectRow',rowIndex);
	var row = $('#table').datagrid('getSelected');
	if (row) {
		$('#dlg-resource').dialog("open").dialog('setTitle', '资源信息');
		$('#fm-resource').form('clear');
		$('#roleId').val(row.id);
		var resource = parent.windowCache.getResource();
		$('#resource-tree').tree({
			data:resource
		});
		//回显
		var roleResourceXref = row.resources;
		for(var i=0;i<roleResourceXref.length;i++){
			var r = roleResourceXref[i];
			var node = $('#resource-tree').tree('find',r.id);
			$('#resource-tree').tree('check', node.target);
		}
	}
}
function doSaveResource(){
	var resourcesArray =[];
	var nodes = $('#resource-tree').tree('getChecked');
	for(var i=0;i<nodes.length;i++){
		resourcesArray.push(nodes[i].id)
	}
	$("#resourcesId").val(resourcesArray.join('|'));
	var data = $('#fm-resource').serialize();
	$.ajax({
		type : 'POST',
		dataType : "json",
		data : data,
		url : ctx + "/role/resource_xref/save",
		success : function(result) {
			if (result.status == "error") {
				$.messager.alert("提示信息", result.message, "info");
			} else if (result.status == "success") {
				$.messager.alert("提示信息", "数据保存成功", "info",function() {
					$('#dlg-resource').dialog('close');
					var pager = $('#table').datagrid('getPager');
					pager.pagination("select");
				});
			}
		},
		cache : false,
	});
}
//----------------数据范围----------------------------
function editOrganization(rowIndex){
	$('#table').datagrid('selectRow',rowIndex);
	var row = $('#table').datagrid('getSelected');
	if (row) {
		$('#dlg-organization').dialog("open").dialog('setTitle', '数据范围');
		$('#fm-organization').form('clear');
		$('#roleId2').val(row.id);
		var organization = parent.windowCache.getOrganization();
		$('#organization-tree').tree({
			data:organization
		});
		//回显
		var roleOrganizationXref = row.organizations;
		for(var i=0;i<roleOrganizationXref.length;i++){
			var r = roleOrganizationXref[i];
			var node = $('#organization-tree').tree('find',r.id);
			$('#organization-tree').tree('check', node.target);
		}
	}
}
function doSaveOrganization(){
	var organizationsArray =[];
	var nodes = $('#organization-tree').tree('getChecked');
	for(var i=0;i<nodes.length;i++){
		organizationsArray.push(nodes[i].id)
	}
	$("#organizationsId").val(organizationsArray.join('|'));
	var data = $('#fm-organization').serialize();
	$.ajax({
		type : 'POST',
		dataType : "json",
		data : data,
		url : ctx + "/role/organization_xref/save",
		success : function(result) {
			if (result.status == "error") {
				$.messager.alert("提示信息", result.message, "info");
			} else if (result.status == "success") {
				$.messager.alert("提示信息", "数据保存成功", "info",function() {
					$('#dlg-organization').dialog('close');
					var pager = $('#table').datagrid('getPager');
					pager.pagination("select");
				});
			}
		},
		cache : false,
	});
}
