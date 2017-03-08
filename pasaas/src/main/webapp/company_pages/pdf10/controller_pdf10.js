(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf10', ['$scope', 'service_search_list', controller_pdf10])
;
function controller_pdf10($scope, service_search_list){

	$scope.get_search_list = function(){
		service_search_list.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.search = response.data;
			}, 
			function on_error(response){
				console.log(response.statusText);
			}
		);
	};
	$scope.get_search_list();

	$scope.page_meta_data = {
		"has_header" : true,
		"page_header_title" : "Search Page",
		"breadcrumb_data" : [
			{
				"link" : "index.html",
				"label" : "Home",
				"class" : "",
				"is_active" : false
			},
			{
				"link" : "/",
				"label" : " Search Page ",
				"class" : "active",
				"is_active" : true
			}
		]

	};

	// $scope.insert_into_search_list = function(){
	// 	$scope.new_search_item = 
	// 	{
	// 	    "heading": "Kishor Pachawadkar Admin Theme",
	// 	    "link": "https://wrapbootstrap.com/‎",
	// 	    "description": "Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still their infancy. Various versions have evolved over the years"
	// 	};

    	// service_search_list.create($scope.new_search_item)
	// 	.then(
	// 		function on_success(response){
	// 	    	console.log(response.data);
	// 		}, 
	// 		function on_error(response){
	// 			console.log(response.statusText);
	// 		}
	// 	);

	// };
	// $scope.insert_into_search_list();
}

})();
