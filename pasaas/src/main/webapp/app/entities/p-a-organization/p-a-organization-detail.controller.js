(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAOrganizationDetailController', PAOrganizationDetailController);

    PAOrganizationDetailController.$inject = ['$rootScope', '$state', '$scope', '$stateParams', 'previousState', 'entity', 'PAOrganization', 'PAProject', 'PABusinessPlan', 'PAReliabilityScore', 'PAReliabilityConf', 'PAPredictionScore', 'PASaxCodeTmp', 'PASaxCode', 'PAAlarmActuality', 'PAPrediction', 'PAReport', 'PAAccPrecision', 'PAFileUpload', 'PAPMTRequest', 'PADataConnector', 'PAScheduler', 'PASchedulerInterval', 'PAAlarmRCA', 'PANEDetails'];

    function PAOrganizationDetailController($rootScope, $state, $scope, $stateParams, previousState, entity, PAOrganization, PAProject, PABusinessPlan, PAReliabilityScore, PAReliabilityConf, PAPredictionScore, PASaxCodeTmp, PASaxCode, PAAlarmActuality, PAPrediction, PAReport, PAAccPrecision, PAFileUpload, PAPMTRequest, PADataConnector, PAScheduler, PASchedulerInterval, PAAlarmRCA, PANEDetails) 
    {
        var vm = this;

        vm.pAOrganization = entity;
        vm.previousState = previousState.name;

        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;

        vm.datePickerOpenStatus.validfrom = false;
        vm.datePickerOpenStatus.validto = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.calling_new_project = calling_new_project; 

        function calling_new_project(){
            alert("Inside calling_new_project");
            $rootScope.this_organization = vm.pAOrganization;
            $state.go('p-a-project.new');
        }

        var unsubscribe = $rootScope.$on('pasaasApp:pAOrganizationUpdate', function(event, result) {
            vm.pAOrganization = result;
        });
        $scope.$on('$destroy', unsubscribe);
        
        vm.user_slider = {
                min: 0,
                max: vm.pAOrganization.pabporg.users,
                from:vm.pAOrganization.pausers.length,
                type: 'single',
                prefix: "",
                maxPostfix: "",
                prettify: false,
                hasGrid: false
            };
            vm.project_slider = {
                min: 0,
                max: vm.pAOrganization.pabporg.projects,
                from:vm.pAOrganization.paprojects.length,
                type: 'single',
                prefix: "",
                maxPostfix: "",
                prettify: false,
                hasGrid: false
            };
    }
})();
