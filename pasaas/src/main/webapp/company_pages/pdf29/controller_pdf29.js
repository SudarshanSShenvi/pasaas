(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf29', ['$scope',controller_pdf29])
;
function controller_pdf29($scope){
    $scope.page_data1 = {
        "organization_summary" :{
            "image" : "company_pages/pdf13/images/img_file2.png",
            "company_description" : "A Telco, telephone service provider, or telecommunicationsoperator, is a kind of communications service provider (CSP).",
            "industry_type" : "Telecommunication",
            "website" : "http://www.tele-com.com",
            "email" : "info@tele-com.com",
            "address" : "999 Street Avenu, 999 Miami, CT 445000",
            "phone" : "(130) 999 999999",
        }
    };

	$scope.my_slider1 = {
        min: 0,
        max: 2,
        type: 'single',
        prefix: "",
        maxPostfix: "",
        prettify: false,
        hasGrid: true
    };
    $scope.my_slider2 = {
        min: 0,
        max: 5,
        type: 'single',
        prefix: "",
        maxPostfix: "",
        prettify: false,
        hasGrid: false
    };
    $scope.my_slider3 = {
        min: 0,
        max: 2,
        type: 'single',
        prefix: "",
        maxPostfix: "",
        prettify: false,
        hasGrid: false
    };

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
