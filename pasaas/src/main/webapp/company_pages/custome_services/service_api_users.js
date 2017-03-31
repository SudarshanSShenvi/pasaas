(function () {	'use strict';

angular
.module('pasaasApp')
.constant('ENDPOINT_API_USERS', 'http://123.201.55.196:8085/pasaas/api/')
.service('service_api_users', ['$http', 'ENDPOINT_API_USERS', 'service_CORS', service_api_users])
;

function service_api_users($http, ENDPOINT_API_USERS, service_CORS){
	var service = this;
	
	// var path = 'chapter8/project_list.json';
	var path = 'users';
		
	function get_api_url(){
		return ENDPOINT_API_USERS + path;
	}
	
	function get_api_url_for_id(item_id){
		return get_api_url(path) + item_id;
	}

	service.fetch_full_list = function(){
		return $http.get(get_api_url());

		// var request = service_CORS.createCORSRequest("GET", get_api_url());

		// // service_CORS.serve_cors_request(request, request_params);

		// if (!request) {
		//     alert('CORS not supported');
		//     return;
		// }else{
		// 	request.onload = function() {
		// 	    alert("CORS alert : " + request.responseText);
		// 	    // if(request.responseText){
		// 	    //     var redirect_url = JSON.parse(request.responseText);
		// 	    //     $window.location.href = redirect_url;
		// 	    // }
		// 	};

		//     request.onerror = function() {
		// 	    alert('Woops, there was an error making API the request.');
		// 	};

		// 	// var request_body = JSON.stringify(coming_object);
		//     return request.send();
		// }
	};
	
}


})();
