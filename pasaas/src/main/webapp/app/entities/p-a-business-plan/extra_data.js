/*(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PABusinessPlanDialogController', PABusinessPlanDialogController);

    PABusinessPlanDialogController.$inject = ['$state', 'previousState', '$timeout', '$scope', '$stateParams', 'entity', 'PABusinessPlan', 'PAOrganization'];

    function PABusinessPlanDialogController ($state, previousState, $timeout, $scope, $stateParams, entity, PABusinessPlan, PAOrganization) {
        var vm = this;

        vm.pABusinessPlan = entity;
        vm.clear = clear;
        vm.save = save;
        vm.paorganizations = PAOrganization.query();

        vm.previousState = previousState.name;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function save () {
            vm.isSaving = true;
            if (vm.pABusinessPlan.id !== null) {
                PABusinessPlan.update(vm.pABusinessPlan, onSaveSuccess, onSaveError);
            } else {
                PABusinessPlan.save(vm.pABusinessPlan, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pABusinessPlanUpdate', result);
            $state.go(vm.previousState);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();



        .state('p-a-business-plan.edit', {
            parent: 'p-a-business-plan',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-business-plan/p-a-business-plan-dialog1.html',
                    controller: 'PABusinessPlanDialogController',
                    controllerAs: 'vm',
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('pABusinessPlan');
                    $translatePartialLoader.addPart('planType');
                    $translatePartialLoader.addPart('pAStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PABusinessPlan', function($stateParams, PABusinessPlan) {
                    return PABusinessPlan.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-business-plan',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })


        .state('p-a-business-plan.new', {
            parent: 'p-a-business-plan',
            url: '/new',
            data: {
                authorities: ['ROLE_SUPERADMIN']
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/p-a-business-plan/p-a-business-plan-dialog1.html',
                    controller: 'PABusinessPlanDialogController',
                    controllerAs: 'vm',
                }
            },
            resolve: {
                entity: function () {
                    return {
                        businessplan: null,
                        users: null,
                        description: null,
                        pastatus: null,
                        projects: null,
                        id: null
                    };
                },
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'p-a-business-plan',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
*/