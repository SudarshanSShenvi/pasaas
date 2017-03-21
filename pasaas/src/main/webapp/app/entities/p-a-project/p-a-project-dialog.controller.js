(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAProjectDialogController', PAProjectDialogController);

    PAProjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAProject', 'PAOrganization', 'PAErrorMessage', 'PANotification', 'PAFileUpload', 'PAAccPrecision', 'PAPrediction', 'PAAlarmActuality', 'PASaxCode', 'PASaxCodeTmp', 'PAPredictionScore', 'PAReliabilityConf', 'PAReliabilityScore', 'PAPMTRequest', 'PASchedulerInterval', 'PAAlarmRCA', 'PANEDetails', 'PADataConnector', 'PAScheduler'];

    function PAProjectDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAProject, PAOrganization, PAErrorMessage, PANotification, PAFileUpload, PAAccPrecision, PAPrediction, PAAlarmActuality, PASaxCode, PASaxCodeTmp, PAPredictionScore, PAReliabilityConf, PAReliabilityScore, PAPMTRequest, PASchedulerInterval, PAAlarmRCA, PANEDetails, PADataConnector, PAScheduler) {
        var vm = this;

    // PAProjectDialogController.$inject = ['$timeout', '$scope', '$stateParams', 'entity', 'PAProject', 'PAOrganization', 'PAErrorMessage', 'PANotification', 'PAFileUpload', 'PAAccPrecision', 'PAPrediction', 'PAAlarmActuality', 'PASaxCode', 'PASaxCodeTmp', 'PAPredictionScore', 'PAReliabilityConf', 'PAReliabilityScore', 'PAPMTRequest', 'PASchedulerInterval', 'PAAlarmRCA', 'PANEDetails', 'PADataConnector', 'PAScheduler'];

    // function PAProjectDialogController ($timeout, $scope, $stateParams, entity, PAProject, PAOrganization, PAErrorMessage, PANotification, PAFileUpload, PAAccPrecision, PAPrediction, PAAlarmActuality, PASaxCode, PASaxCodeTmp, PAPredictionScore, PAReliabilityConf, PAReliabilityScore, PAPMTRequest, PASchedulerInterval, PAAlarmRCA, PANEDetails, PADataConnector, PAScheduler) {
    //     var vm = this;

        vm.pAProject = entity;
        vm.clear = clear;
        vm.save = save;
        vm.paorganizations = PAOrganization.query();
        vm.paerrormessages = PAErrorMessage.query();
        vm.panotifications = PANotification.query();
        vm.pafileuploads = PAFileUpload.query();
        vm.paaccprecisions = PAAccPrecision.query();
        vm.papredictions = PAPrediction.query();
        vm.paalarmactualities = PAAlarmActuality.query();
        vm.pasaxcodes = PASaxCode.query();
        vm.pasaxcodetmps = PASaxCodeTmp.query();
        vm.papredictionscores = PAPredictionScore.query();
        vm.pareliabilityconfs = PAReliabilityConf.query();
        vm.pareliabilityscores = PAReliabilityScore.query();
        vm.papmtrequests = PAPMTRequest.query();
        vm.paschedulerintervals = PASchedulerInterval.query();
        vm.paalarmrcas = PAAlarmRCA.query();
        vm.panedetails = PANEDetails.query();
        vm.padataconnectors = PADataConnector.query();
        vm.paschedulers = PAScheduler.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            console.log("Saving New Project");
            vm.isSaving = true;
            if (vm.pAProject.id !== null) {
                PAProject.update(vm.pAProject, onSaveSuccess, onSaveError);
            } else {
                PAProject.save(vm.pAProject, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAProjectUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
