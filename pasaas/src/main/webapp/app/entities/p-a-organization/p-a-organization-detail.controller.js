(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAOrganizationDetailController', PAOrganizationDetailController);

    PAOrganizationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAOrganization', 'PAProject', 'PABusinessPlan', 'PAReliabilityScore', 'PAReliabilityConf', 'PAPredictionScore', 'PASaxCodeTmp', 'PASaxCode', 'PAAlarmActuality', 'PAPrediction', 'PAReport', 'PAAccPrecision', 'PAFileUpload', 'PAPMTRequest', 'PADataConnector', 'PAScheduler', 'PASchedulerInterval', 'PAAlarmRCA', 'PANEDetails'];

    function PAOrganizationDetailController($scope, $rootScope, $stateParams, previousState, entity, PAOrganization, PAProject, PABusinessPlan, PAReliabilityScore, PAReliabilityConf, PAPredictionScore, PASaxCodeTmp, PASaxCode, PAAlarmActuality, PAPrediction, PAReport, PAAccPrecision, PAFileUpload, PAPMTRequest, PADataConnector, PAScheduler, PASchedulerInterval, PAAlarmRCA, PANEDetails) {
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

        var unsubscribe = $rootScope.$on('pasaasApp:pAOrganizationUpdate', function(event, result) {
            vm.pAOrganization = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
