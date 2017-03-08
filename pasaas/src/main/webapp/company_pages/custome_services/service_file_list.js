(function () {	'use strict';

angular
.module('pasaasApp')
.constant('ENDPOINT', 'company_pages/')
.service('service_file_list', ['$http', 'ENDPOINT', service_file_list])
;

function service_file_list($http, ENDPOINT){
	var service = this;
	
	// var path = 'chapter21/file_list.json';
	var path = 'pdf11/file_list.json';
		
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