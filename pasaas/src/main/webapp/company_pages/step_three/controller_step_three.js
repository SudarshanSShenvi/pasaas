(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_step_three', ['$scope',controller_step_three])
;
function controller_step_three($scope){
	$scope.page_data1 = {
		"date" : "1/18/2017",
		"owner_name" : "Sudarshan Shenvi",
		"plan" : "Platinum-TRAIL",
		"price" : "000.00/m",
		"paypal_summary" : {
			"product" : "Predictive Analytics",
			"price" : "000.00/m",
			"description" : "Here Description Description Description",
		},
	};

	$scope.open_acc = 'first';
}

})();
