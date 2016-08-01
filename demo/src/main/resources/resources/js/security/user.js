$(function() {
	var columns = [ [
			{
				field : 'loginName',
				title : '登录名',
				width : 80,
				hidden : false
			},
			{
				field : 'userName',
				title : '用户名',
				width : 80,
				hidden : false
			},
			{
				field : 'organizationId',
				title : '组织机构',
				width : 80,
				hidden : false
			},
			{
				field : 'opt',
				title : '操作',
				width : 80,
				align : 'center',
				formatter : function(value, rowData, rowIndex) {
					return '<a href="javascript:void(0);" onclick="info(' + rowIndex + ')">用户详情</a>&nbsp;' 
							+'<a href="javascript:void(0);" onclick="edit(' + rowIndex + ')">编辑</a>&nbsp;' 
							+ '<a href="javascript:void(0);" onclick="editRole(' + rowIndex + ')" class="tb-btn delete1">用户角色</a>&nbsp;'
							+ '<a href="javascript:void(0);" onclick="doDelete(' + rowIndex + ')" class="tb-btn delete1">删除</a>';
				}
			} ] ];

	var page = new $.MyPage({
		tableId : "table",
		url : ctx + '/user/queryPage',
		// auditable : true,
		columns : columns
	});
	page.init(false);

	$('#queryBtn').click(function() {
		var param = $('#q_form').serialize();
		page.queryData(param);
	});

	$('#addBtn').click(function() {
		$('#dlg').dialog("open").dialog('setTitle', '新增用户');
		$('#fm').form('clear');
		var selected = $('#wtree').tree('getSelected');
		if (selected) {
			$('#organizationId').combotree('setValue', selected.id);
		}
	});
	var organization = parent.windowCache.getOrganization();
	$('#wtree').tree({
		data : organization,
		onSelect : function(node) {
			$('#q_organizationId').val(node.id);
			var param = $("#q_form").serialize();
			page.queryData(param);
		}
	});
	$('#organizationId').combotree({
		data : organization,
	});
});

function doSave() {
	if ($("#fm").form("validate")) {
		var data = $('#fm').serialize();
		$.ajax({
			type : 'POST',
			dataType : "json",
			data : data,
			url : ctx + "/user/save",
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
	$('#table').datagrid('selectRow', rowIndex);
	var row = $('#table').datagrid('getSelected');
	var data = {
		'id' : row.id
	};
	$.messager.confirm('确认', '	确认删除该信息?', function(r) {
		if (r && row) {
			$.ajax({
				type : 'POST',
				dataType : "json",
				data : data,
				url : ctx + "/user/delete",
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
	$('#table').datagrid('selectRow', rowIndex);
	var row = $('#table').datagrid('getSelected');
	if (row) {
		$('#dlg').dialog("open").dialog('setTitle', '编辑城市信息');
		$('#fm').form('clear');
		$('#id').val(row.id);
		$('#roles').val(row.roles);
		$('#loginName').textbox('setValue', row.loginName).textbox('readonly');
		$('#userName').textbox('setValue', row.userName);
		$('#organizationId').combotree('setValue', row.organizationId);
	}
}

// --------------用户角色--------------------
function editRole(rowIndex) {
	$('#table').datagrid('selectRow', rowIndex);
	var row = $('#table').datagrid('getSelected');
	if (row) {
		$('#dlg-role').dialog("open").dialog('setTitle', '角色信息');
		$('#fm-role').form('clear');
		$('#userId').val(row.id); // 用户id
		var roles = row.roles;
		var organization = parent.windowCache.getOrganization();
		$('#organization-tree').combotree({
			data : organization,
			onSelect : function(node) {
				$('#role-tree').combotree({
					url : ctx + '/role/query?q_organizationId=' + node.id,
				})
			}
		});
		$('#role-tree').combotree({
			url : ctx + '/role/query?q_organizationId=' + row.organizationId,
		})
		$('#organization-tree').combotree('setValue', row.organizationId);

		$('#roles').datagrid({
			rownumbers : true,
			checkbox : true,
			data : roles,
			idField:'id',
			columns : [ [ {
				field : 'id',
				title : 'id',
					hidden : true
			}, {
				field : 'roleName',
				title : '角色名称',
				width : 80
			}, {
				field : 'roleType',
				title : '角色类型',
				width : 80
			}, {
				field : 'organizationId',
				title : '所属机构',
				width : 80
			} ] ]
		});
	}
}

function addRole() {
	var role = $('#role-tree').combotree('tree').tree('getSelected');
	if (role) {
		var index =$('#roles').datagrid('getRowIndex',role.id);
		if (index>=0) {
			$.messager.alert("提示信息", "已存在", "info");
		} else {
			$('#roles').datagrid('appendRow', role)
		}
	} else {
		$.messager.alert("提示信息", "请选择角色", "info");
	}
}
function deleteRole() {
	var nodes = $('#roles').datagrid('getSelections');
	if(nodes.length>0){
		$.messager.confirm('确认', '	确认删除所选信息?', function(r) {
			if (r) {
				for(var i=nodes.length;i>0;i--){
					var index =$('#roles').datagrid('getRowIndex',nodes[i-1]);
					$('#roles').datagrid('deleteRow',index);
				}
			}
		});
	}else{
		$.messager.alert("提示信息", "请选择要删除的行" ,"info");
	}
}
function doSaveRole() {
	var roleArray = [];
	var nodes = $('#roles').datagrid('getRows')
	for ( var i = 0; i < nodes.length; i++) {
		roleArray.push(nodes[i].id)
	}
	$("#rolesId").val(roleArray.join('|'));
	var data = $('#fm-role').serialize();
	$.ajax({
		type : 'POST',
		dataType : "json",
		data : data,
		url : ctx + "/user/role_xref/save",
		success : function(result) {
			if (result.status == "error") {
				$.messager.alert("提示信息", result.message, "info");
			} else if (result.status == "success") {
				$.messager.alert("提示信息", "数据保存成功", "info", function() {
					$('#dlg-role').dialog('close');
					var pager = $('#table').datagrid('getPager');
					pager.pagination("select");
				});
			}
		},
		cache : false,
	});
}
//----------用户详情--------
function info(rowIndex){
	$('#table').datagrid('selectRow', rowIndex);
	var row = $('#table').datagrid('getSelected');
	if (row) {
		var roles = row.roles;
		var organization = parent.windowCache.getOrganization();
		var resource = parent.windowCache.getResource();
		$('#user-resource').tree({    
			data: resource,
			cascadeCheck:false,
			checkbox:true
		});
		$('#user-organization').tree({
			data:organization,
			cascadeCheck:false,
			checkbox:true
		});
		for(var i=0;i<roles.length;i++){
			var resources = roles[i].resources;
			var organizations = roles[i].organizations;
			for(var r=0;r<resources.length;r++){
				var node = $('#user-resource').tree('find',resources[r].id);
				if(node)
				$('#user-resource').tree('check', node.target);
			}
			for(var o=0;o<organizations.length;o++){
				var node = $('#user-organization').tree('find',organizations[o].id);
				if(node)
				$('#user-organization').tree('check', node.target);
			}
		}
		layer.open({
			  type: 1,
			  title: false,
			  shade: 0.01,
			  skin: 'layui-layer-demo',
			  area: ['630px', '360px'],
			  closeBtn: 0,
			  shadeClose: true,
			  content: $('#info-content')
		});
	}
	
	
}
