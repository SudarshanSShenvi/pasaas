(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_project_details', ['$scope', '$rootScope',controller_project_details])
;
function controller_project_details($scope, $rootScope){
	$rootScope.progress_now = 20;
}

})();
