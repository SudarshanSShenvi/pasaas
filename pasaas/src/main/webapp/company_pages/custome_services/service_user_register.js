(function () {	'use strict';

angular
.module('pasaasApp')
.constant('ENDPOINT_API_REGISTER', 'http://123.201.55.196:8085/pasaas/api/')
.service('service_user_register', ['$http', 'ENDPOINT_API_REGISTER', 'service_CORS', service_user_register])
;

function service_user_register($http, ENDPOINT_API_REGISTER, service_CORS){
	var service = this;
	
	var path = 'register';
		
	function get_api_url(){
		return ENDPOINT_API_REGISTER + path;
	}
	
	function get_api_url_for_id(item_id){
		return get_api_url(path) + item_id;
	}


//	for fetch_full_list
	service.fetch_full_list = function(){
		return $http.get(get_api_url());
	};
	
//	for create
	service.create = function(item){
		// alert(JSON.stringify(item));
		alert("In Service na");
		return $http.post(get_api_url(), item);

		// var request = service_CORS.createCORSRequest("POST", get_api_url());

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

		// 	var request_body = JSON.stringify(item);
		//     return request.send(request_body);
		// }

	};

			
//	for get_one_by_id	
	service.fetch_by_id = function(item_id){
		return $http.get(get_api_url_for_id(item_id));
	};

//	for delete_by_id
	service.delete_by_id = function(item_id){
		return $http.delete(get_api_url_for_id(item_id));
	};

//	for update_by_id
	service.update_by_id = function(item_id, item){
		return $http.put(get_api_url_for_id(item_id), item);
	};

}


})();
