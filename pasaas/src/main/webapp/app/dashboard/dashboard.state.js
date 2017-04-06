(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('dashboard', {
            parent: 'entity',
            url: '/dashboard/{id}/{organization}',
            data: {
                authorities: [],
            },
            views: {
                'content@': {
                    templateUrl: 'app/dashboard/dashboard.html',
                    controller: 'DashboardController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('home');
                    return $translate.refresh();
                }],
                previousState: ["$state", "$stateParams", function($state, $stateParams) {
                    var orgn = $stateParams.organization;
                    var project_id = $stateParams.id;
                    var currentStateData = {
                        coming_project_id: project_id,
                        // name: 'p-a-organization.name({organizationName : "Vodafone"})',
                        // name: 'p-a-organization.name({organizationName : "'+ orgn +'"})',
                        name: $state.current.name + '({organizationName : "' + orgn + '"})',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        });
    }
})();
