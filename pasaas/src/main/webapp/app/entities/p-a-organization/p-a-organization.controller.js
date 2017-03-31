(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAOrganizationController', PAOrganizationController);

    PAOrganizationController.$inject = ['$scope', '$state', 'PAOrganizationSu', 'ParseLinks', 'AlertService', 'paginationConstants'];

    function PAOrganizationController ($scope, $state, PAOrganizationSu, ParseLinks, AlertService, paginationConstants) {
        var vm = this;

        vm.pAOrganizations = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        loadAll();

        vm.print_now = function(coming_data){
            console.log(coming_data);
        }

        function loadAll () {
            PAOrganizationSu.query({
                page: vm.page,
                size: vm.itemsPerPage,
                sort: sort()
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.pAOrganizations.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }

        function reset () {
            vm.page = 0;
            vm.pAOrganizations = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }





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

        /*$scope.user_slider = {
            min: 0,
            max: 5,//vm.pAOrganization.pabporg.users,
            type: 'single',
            prefix: "",
            maxPostfix: "",
            prettify: false,
            hasGrid: true
        };
        $scope.project_slider = {
            min: 0,
            max: vm.pAOrganization.pabporg.projects,
            type: 'single',
            prefix: "",
            maxPostfix: "",
            prettify: false,
            hasGrid: true
        };*/

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
        // $scope.get_users_list();


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
