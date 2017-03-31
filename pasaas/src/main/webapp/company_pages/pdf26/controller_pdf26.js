(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf26', ['$scope', 'service_invoice_item_list', controller_pdf26])
;
function controller_pdf26($scope, service_invoice_item_list){

	$scope.invoice_data = {
		"from": {
			"name": "Pervazive, Inc.",
			"address": "67 Yonge Street Toronto Ontrio, M5E 1JB",
			"contact": "1-888-638-1653",
		},
		"to": {
			"name": "Sudarshan Shenvi",
			"address": "Telecom Company Miami, CT 445611",
			"contact": "(120) 9000-4321",
		},
		"dates": {
			"invoice_date": "Marh 18, 2014",
			"due_date": "March 24, 2014",
		},
		// "list_item" : [
		// 	{
		// 		"name": "",
		// 		"quantity": "",
		// 		"unit_price": "",
		// 		"tax": "",
		// 		"total_price": "",
		// 	}
		// ],
		// "totals": {
		// 	"sub_total": "",
		// 	"tax_calculated": "",
		// 	"grand_total": "",
		// }
		// "dates": {
		// 	"invoice_date": "",
		// 	"": "",
		// 	"": "",
		// },
	};

	$scope.get_invoice_item_list = function(){
		service_invoice_item_list.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.invoice_item_list = response.data;
			}, 
			function on_error(response){
				console.log(response.statusText);
			}
		);
	};
	$scope.get_invoice_item_list();

	$scope.get_sub_total = function(){
	    var total = 0;
	    for(var i = 0; i < $scope.invoice_item_list.length; i++){
	        var product = $scope.invoice_item_list[i].totalprice;
	        total += product;
	    }
	    return total;
	}
	$scope.get_tax_amount = function(){
	    var tax_amount = 23.20;
	    return tax_amount;
	}
	$scope.get_grand_total = function(){
	    var grand_total = $scope.get_sub_total() - $scope.get_tax_amount();
	    return grand_total;
	}

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
