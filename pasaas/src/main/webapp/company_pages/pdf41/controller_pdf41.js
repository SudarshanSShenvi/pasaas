(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf41', ['$scope',controller_pdf41])
;
function controller_pdf41($scope){

	$scope.list_status = [
        {
            "back_color" : "Green",
            "front_color" : "white",
            "label" : "Active"
        },{
            "back_color" : "Gray",
            "front_color" : "white",
            "label" : "Inactive"
        },{
            "back_color" : "Red",
            "front_color" : "white",
            "label" : "Deleted"
        }
    ];
    
    

    $scope.page_meta_data = {
        "has_header" : true,
        "page_header_title" : "BDA Networks",
        "breadcrumb_data" : [
            {
                "link" : "index.html",
                "label" : "Home",
                "class" : "",
                "is_active" : false
            },
            {
                "link" : "/",
                "label" : "Platform",
                "class" : "",
                "is_active" : false
            },
            {
                "link" : "/",
                "label" : "BDA",
                "class" : "",
                "is_active" : false
            },
            {
                "link" : "/",
                "label" : "Networks",
                "class" : "",
                "is_active" : false
            },
            {
                "link" : "/",
                "label" : " Dist. Network Elements - Create",
                "class" : "active",
                "is_active" : true
            }
        ]

    };
	
}

})();
