(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_create_new_user', ['$scope', controller_create_new_user])
;
function controller_create_new_user($scope){
	$scope.create_user = function(coming_object){
		coming_object.defaultOrganization = "Pervazive";
		alert(JSON.stringify(coming_object));
	}
}

})();
