(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf6', ['$scope', 'service_user_register',controller_pdf6])
;
function controller_pdf6($scope, service_user_register){
	$scope.submit_form = function(coming_object){
		alert(JSON.stringify(coming_object));

		var item = {
			"login": coming_object.name,
			"password": coming_object.password,
			"firstName": coming_object.name,
			"lastName": coming_object.name,
			"email": coming_object.email_id_company,
			"activated": true,
			"langKey": "en"
		};
		
		service_user_register.create(item)
		.then(
			function on_success(response){
				swal({
					title: "Success!", 
					text: "Record created successfully!", 
					type: "success"
				});
		    	console.log("ON SUCCESS: " + response.data);
			}, 
			function on_error(response){
				console.log("ON ERROR: " + response.statusText);
			}
		);
	};
	
	// {
	// 	"login": "sudi11",
	// 	"password": "temp123",
	// 	"firstName": "sudi11",
	// 	"lastName": "sudi11",
	// 	"email": "sudi11@localhost",
	// 	"activated": true,
	// 	"langKey": "en"
	// }
	
}

})();
