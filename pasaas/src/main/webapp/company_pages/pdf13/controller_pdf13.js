(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf13', ['$scope',controller_pdf13])
;
function controller_pdf13($scope){
	// All data will be store in this object
    $scope.formData = {};

    // After process wizard
    $scope.processForm = function() {
        alert('Wizard completed');
    };

    $scope.page_meta_data = {
		"has_header" : true,
		"page_header_title" : "Project",
		"breadcrumb_data" : [
			{
				"link" : "index.html",
				"label" : "Home",
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : "Project",
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : " Create Project",
				"class" : "active",
				"is_active" : true
			}
		]

	};
}

})();
