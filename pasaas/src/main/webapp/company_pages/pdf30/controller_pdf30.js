(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf30', ['$scope', 'service_api_users',controller_pdf30])
;
function controller_pdf30($scope, service_api_users){
    $scope.page_data1 = {
        "organization_summary" :{
            "image" : "company_pages/pdf13/images/img_file2.png",
            "company_description" : "A Telco, telephone service provider, or telecommunicationsoperator, is a kind of communications service provider (CSP).",
            "industry_type" : "Telecommunication",
            "website" : "http://www.tele-com.com",
            "email" : "info@tele-com.com",
            "address" : "999 Street Avenu, 999 Miami, CT 445000",
            "phone" : "(130) 999 999999",
        },
    };
    $scope.quick_actions = [
        "Create a Project",
        "Upgrade Plan",
        "Action",
        "Invite Users",
        "Action"
    ];

	$scope.my_slider1 = {
        min: 0,
        max: 5,
        type: 'single',
        prefix: "",
        maxPostfix: "",
        prettify: false,
        hasGrid: true
    };
    $scope.my_slider2 = {
        min: 0,
        max: 2,
        type: 'single',
        prefix: "",
        maxPostfix: "",
        prettify: false,
        hasGrid: true
    };

	$scope.get_users_list = function(){
		service_api_users.fetch_full_list()
		.then(
			function on_success(response){
		    	$scope.users_list = response.data;
			}, 
			function on_error(response){
				console.log("ON ERROR: " + response.statusText);
			}
		);
	};
	$scope.get_users_list();


    $scope.page_meta_data = {
        "has_header" : true,
        "page_header_title" : "Organization",
        "breadcrumb_data" : [
            {
                "link" : "index.html",
                "label" : "Home",
                "class" : "",
                "is_active" : false
            },          
            {
                "link" : "/",
                "label" : "Organization",
                "class" : "active",
                "is_active" : true
            }
        ]

    };
	
}

})();
