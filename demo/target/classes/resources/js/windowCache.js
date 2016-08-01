windowCache=(function(){
	var _resource=null;
	var _organization=null;
	var publicMethod ={
			getResource:function(){
				var data = _resource;
				if(!data){
					data = loadResource();
				}
				return data;
			},
			reloadResource:function(){
				var data = loadResource();
				this.resource = data;
				return data;
			},
			
			getOrganization:function(){
				var data = this.organization;
				if(!data){
					data = loadOrganization();
				}
				return data;
			},
			reloadOrganization:function(){
				var data = loadOrganization();
				this.resource = data;
				return data;
			}	
	}
	
	var loadResource = function(){
		var r;
		$.ajax({
			url : ctx + "/resource/findOne?id=0",
			type : "POST",
			cache : false,
			async : false,
			error : function(request) {
				layer.msg('获取资源树失败');
			},
			success : function(result) {
				windowCache.resource = [result];
				r=[result];
			}
		});
		return r;
	}
	var loadOrganization = function(){
		var r;
		$.ajax({
			url : ctx + "/organization/findOne?id=0",
			type : "POST",
			cache : false,
			async : false,
			error : function(request) {
				layer.msg('获取组织机构树失败');
			},
			success : function(result) {
				windowCache.organization = [result];
				r=[result];
			}
		});
		return r;
	}
	
	return publicMethod;
})();



