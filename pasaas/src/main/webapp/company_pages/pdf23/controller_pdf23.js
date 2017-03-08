(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf23', ['$scope',controller_pdf23])
;
function controller_pdf23($scope){
	// All data will be store in this object
    $scope.formData = {};

    // After process wizard
    $scope.processForm = function() {
        alert('Wizard completed');
    };

    $scope.page_meta_data = {
		"has_header" : true,
		"page_header_title" : "Accounts",
		"breadcrumb_data" : [
			{
				"link" : "index.html",
				"label" : "Home",
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : "Accounts",
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : "Setup Organization and Membership",
				"class" : "active",
				"is_active" : true
			}
		]

	};
}

})();
