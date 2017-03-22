(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAOrganizationDialogController', PAOrganizationDialogController);

    PAOrganizationDialogController.$inject = ['$state', '$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAOrganization', 'PAProject', 'PABusinessPlan', 'PAReliabilityScore', 'PAReliabilityConf', 'PAPredictionScore', 'PASaxCodeTmp', 'PASaxCode', 'PAAlarmActuality', 'PAPrediction', 'PAReport', 'PAAccPrecision', 'PAFileUpload', 'PAPMTRequest', 'PADataConnector', 'PAScheduler', 'PASchedulerInterval', 'PAAlarmRCA', 'PANEDetails'];

    function PAOrganizationDialogController ($state, $timeout, $scope, $stateParams, $uibModalInstance, entity, PAOrganization, PAProject, PABusinessPlan, PAReliabilityScore, PAReliabilityConf, PAPredictionScore, PASaxCodeTmp, PASaxCode, PAAlarmActuality, PAPrediction, PAReport, PAAccPrecision, PAFileUpload, PAPMTRequest, PADataConnector, PAScheduler, PASchedulerInterval, PAAlarmRCA, PANEDetails) {
        var vm = this;

        vm.pAOrganization = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.paprojects = PAProject.query();
        vm.pabusinessplans = PABusinessPlan.query();
        vm.pareliabilityscores = PAReliabilityScore.query();
        vm.pareliabilityconfs = PAReliabilityConf.query();
        vm.papredictionscores = PAPredictionScore.query();
        vm.pasaxcodetmps = PASaxCodeTmp.query();
        vm.pasaxcodes = PASaxCode.query();
        vm.paalarmactualities = PAAlarmActuality.query();
        vm.papredictions = PAPrediction.query();
        vm.pareports = PAReport.query();
        vm.paaccprecisions = PAAccPrecision.query();
        vm.pafileuploads = PAFileUpload.query();
        vm.papmtrequests = PAPMTRequest.query();
        vm.padataconnectors = PADataConnector.query();
        vm.paschedulers = PAScheduler.query();
        vm.paschedulerintervals = PASchedulerInterval.query();
        vm.paalarmrcas = PAAlarmRCA.query();
        vm.panedetails = PANEDetails.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
        	$uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pAOrganization.id !== null) {
                PAOrganization.update(vm.pAOrganization, onSaveSuccess, onSaveError);
            } else {
                PAOrganization.save(vm.pAOrganization, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAOrganizationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.validfrom = false;
        vm.datePickerOpenStatus.validto = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
