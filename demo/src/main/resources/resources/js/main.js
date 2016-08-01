$(function() {
	var layerLoad = layer.load('资源努力加载中......', 1000000);
	$.ajax({
		url : ctx + "/resource/findOne?id=1",
		type : "POST",
		cache : false,
		async : true,
		error : function(request) {
			layer.close(layerLoad);
			layer.msg('获取菜单资源失败');
		},
		success : function(project) {
			layer.close(layerLoad);
			loadMainmenu(project.children);
		}
	});

	function loadMainmenu(menu) {
		var template = [];
		template.push('{{# for(var j = 0, lenj = d.length; j < lenj; j++){ }}');
		template.push('<ul class="tree">' + '<li class="first">');
		template.push('	<h3 class="hfirst">');
		template.push('		<a href="#">{{d[j].caption}}</a><b></b>');
		template.push('	</h3>');
		template.push('	<ul class="sub">');
		template.push('	{{# var  menu=d[j] }}');
		template.push('	{{# for(var i = 0, len = menu.children.length; i < len; i++){ }}');
		template.push('		<li class="submenu">');
		template.push('			<a ref="{{ menu.children[i].id }}" href="#" rel="{{ menu.children[i].linkurl }}" >');
		template.push('			<span class="nav">{{ menu.children[i].caption}}</span>');
		template.push('			</a>');
		template.push('		</li>');
		template.push('	{{# } }}');
		template.push('	</ul>');
		template.push('</li>');
		template.push('</ul>');
		template.push('	{{# } }}');
		var tpl = laytpl(template.join(''));
		var html = tpl.render(menu);
		$('#wnav').html(html);

		$('.hfirst').click(function(event) {
			$(this).parent().toggleClass("active");
		});
		$('.submenu').click(function() {
			$('.submenu').removeClass("current");
			$(this).addClass('current');
		});

		$('li ul li a').on('click', function() {
			var tabTitle = $(this).children('.nav').text();
			var url = $(this).attr("rel");
			var content = '<div style="width:100%;height:100%;overflow:hidden;"> <iframe  frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe></div>';
			if (!$('#tabs').tabs('exists', tabTitle)) {
				$('#tabs').tabs('add', {
					title : tabTitle,
					content : content,
					closable : true,
				});
			} else {
				$('#tabs').tabs('select', tabTitle);
				var tab = $('#tabs').tabs('getSelected'); // 获取选择的面板
				$('#tabs').tabs('update', {
					tab : tab,
					options : {
						content : content,
					}
				});
			}
			$('#wnav li ul').removeClass("selected");
			$(this).parent().addClass("selected");
			tabInit();
		});
		tabInit();
	}

	function tabInit() {
		/* 双击关闭TAB选项卡 */
		$(".tabs-inner").dblclick(function() {
			var subtitle = $(this).children(".tabs-closable").text();
			$('#tabs').tabs('close', subtitle);
		});
		/* 为选项卡绑定右键 */
		$(".tabs-inner").bind('contextmenu', function(e) {
			$('#mm').menu('show', {
				left : e.pageX,
				top : e.pageY
			});

			var subtitle = $(this).children(".tabs-closable").text();

			$('#mm').data("currtab", subtitle);
			$('#tabs').tabs('select', subtitle);
			return false;
		});

		// 刷新
		$('#mm-tabupdate').click(function() {
			var currTab = $('#tabs').tabs('getSelected');
			var content = '<div style="width:100%;height:100%;overflow:hidden;"> <iframe  frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe></div>';
			var url = $($(currTab.panel('options').content).html()).attr('src');
			$('#tabs').tabs('update', {
				tab : currTab,
				options : {
					content : content,
				}
			});
		});
		// 关闭当前
		$('#mm-tabclose').click(function() {
			var currtab_title = $('#mm').data("currtab");
			$('#tabs').tabs('close', currtab_title);
		});
		// 全部关闭
		$('#mm-tabcloseall').click(function() {
			$('.tabs-inner span').each(function(i, n) {
				var t = $(n).text();
				$('#tabs').tabs('close', t);
			});
		});
		// 关闭除当前之外的TAB
		$('#mm-tabcloseother').click(function() {
			$('#mm-tabcloseright').click();
			$('#mm-tabcloseleft').click();
		});
		// 关闭当前右侧的TAB
		$('#mm-tabcloseright').click(function() {
			var nextall = $('.tabs-selected').nextAll();
			if (nextall.length == 0) {
				return false;
			}
			nextall.each(function(i, n) {
				var t = $('a:eq(0) span', $(n)).text();
				$('#tabs').tabs('close', t);
			});
			return false;
		});
		// 关闭当前左侧的TAB
		$('#mm-tabcloseleft').click(function() {
			var prevall = $('.tabs-selected').prevAll();
			if (prevall.length == 0) {
				return false;
			}
			prevall.each(function(i, n) {
				var t = $('a:eq(0) span', $(n)).text();
				$('#tabs').tabs('close', t);
			});
			return false;
		});

		// 退出
		$("#mm-exit").click(function() {
			$('#mm').menu('hide');
		});
	}
	
		$('#logout').click(function() {
			$.messager.confirm('系统提示', '您确定要退出本次登录吗?', function(r) {
				if (r) {
					location.href = 'logout';
				}
			});
		});
});
