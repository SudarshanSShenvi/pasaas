(function () {	'use strict';

angular
.module('pasaasApp')
.service('service_CORS', ['$http', service_CORS])
;

function service_CORS($http){

	var service = this;

	service.createCORSRequest = function(method, url){

	    var xhr = new XMLHttpRequest();

	    if ("withCredentials" in xhr){
	        xhr.open(method, url, true);
	        xhr.setRequestHeader("Content-Type", "application/json;charset=UTF-8");

	    } else if (typeof XDomainRequest != "undefined"){
	        xhr = new XDomainRequest();
	        xhr.open(method, url);

	    } else {
	        xhr = null;
	    }

	    return xhr;
	};

	service.serve_cors_request = function(request, request_params){
		if (!request) {
		    alert('CORS not supported');
		    return;
		}else{

			request.onload = function() {
			    alert("CORS alert : " + request.responseText);
			};

		    request.onerror = function() {
			    alert('Woops, there was an error making the request.');
			};

			var request_body = JSON.stringify(request_params);
		    request.send(request_body);
		}
	};

}

})();
