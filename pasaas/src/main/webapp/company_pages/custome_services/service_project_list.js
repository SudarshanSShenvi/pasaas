(function () {	'use strict';

angular
.module('pasaasApp')
.constant('ENDPOINT', 'company_pages/')
.service('service_project_list', ['$http', 'ENDPOINT', service_project_list])
;

function service_project_list($http, ENDPOINT){
	var service = this;
	
	// var path = 'chapter8/project_list.json';
	var path = 'pdf16/project_list.json';
		
	function get_url(){
		return ENDPOINT + path;
	}
	
	function get_url_for_id(item_id){
		return get_url(path) + item_id;
	}

	service.fetch_full_list = function(){
		return $http.get(get_url());
	};
	
}


})();
