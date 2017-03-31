(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf28', ['$scope',controller_pdf28])
;
function controller_pdf28($scope){
	$scope.list_orders = [
		{
			"order_id" : "0105",
			"customer" : "Predictive Analytics - Platinum Membership - TRAIL",
			"amount" : "000.00",
			"date_added" : "18/01/2015",
			"date_modified" : "18/01/2015",
			"clmenu" : "btn btn-sm btn-primary",
			"status" : "Success"
		},{
			"order_id" : "0103",
			"customer" : "Predictive Analytics - Bronze Membership",
			"amount" : "20.00",
			"date_added" : "17/01/2015",
			"date_modified" : "17/01/2015",
			"clmenu" : "btn btn-sm btn-danger",
			"status" : "Cancelled"
		},
	];

	$scope.page_meta_data = {
		"has_header" : true,
		"page_header_title" : "Orders",
		"breadcrumb_data" : [
			{
				"link" : "index.html",
				"label" : "Home",
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : "Account",
				"class" : "",
				"is_active" : false
			},			
			{
				"link" : "/",
				"label" : "Orders",
				"class" : "active",
				"is_active" : true
			}
		]

	};
	
}

})();
