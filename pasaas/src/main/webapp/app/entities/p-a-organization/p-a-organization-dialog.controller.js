(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAOrganizationDialogController', PAOrganizationDialogController);

    PAOrganizationDialogController.$inject = ['previousState', 'moment', '$state', '$timeout', '$scope', '$stateParams', 'entity', 'PAOrganization', 'PAProject', 'PABusinessPlan', 'PAReliabilityScore', 'PAReliabilityConf', 'PAPredictionScore', 'PASaxCodeTmp', 'PASaxCode', 'PAAlarmActuality', 'PAPrediction', 'PAReport', 'PAAccPrecision', 'PAFileUpload', 'PAPMTRequest', 'PADataConnector', 'PAScheduler', 'PASchedulerInterval', 'PAAlarmRCA', 'PANEDetails'];

    function PAOrganizationDialogController (previousState, moment, $state, $timeout, $scope, $stateParams, entity, PAOrganization, PAProject, PABusinessPlan, PAReliabilityScore, PAReliabilityConf, PAPredictionScore, PASaxCodeTmp, PASaxCode, PAAlarmActuality, PAPrediction, PAReport, PAAccPrecision, PAFileUpload, PAPMTRequest, PADataConnector, PAScheduler, PASchedulerInterval, PAAlarmRCA, PANEDetails) {
        var vm = this;

        vm.pAOrganization = entity;
        // vm.clear = clear;

        vm.previousState = previousState.name;

        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.paprojects = PAProject.query();
        vm.pabusinessplans = PABusinessPlan.query();
        /*vm.pareliabilityscores = PAReliabilityScore.query();
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
*/
        
        // vm.pAOrganization.validto = moment().toDate();
        // var current_date = moment().hour(8).minute(0).second(0).toDate();

        vm.pAOrganization.pabporg = vm.pabusinessplans[0];

        vm.change_to_date = function(){
            if(vm.pAOrganization.pabporg.businessplan == 'Trial'){
                vm.pAOrganization.validto = moment(vm.pAOrganization.validfrom).add({
                    years: 0, 
                    months: 1, 
                    days: 0
                }).toDate();
            }
            if(vm.pAOrganization.pabporg.businessplan == 'Silver'){
                vm.pAOrganization.validto = moment(vm.pAOrganization.validfrom).add({
                    years: 0, 
                    months: 3, 
                    days: 0
                }).toDate();
            }
            if(vm.pAOrganization.pabporg.businessplan == 'Gold'){
                vm.pAOrganization.validto = moment(vm.pAOrganization.validfrom).add({
                    years: 0, 
                    months: 6, 
                    days: 0
                }).toDate();
            }
            if(vm.pAOrganization.pabporg.businessplan == 'Platinum'){
                vm.pAOrganization.validto = moment(vm.pAOrganization.validfrom).add({
                    years: 1, 
                    months: 0, 
                    days: 0
                }).toDate();
            }
        }

        // vm.pAOrganization.validto = current_date.add({
        //     years: 0, 
        //     months: 2, 
        //     days: 0
        // });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        // function clear () {
        //     // $state.go(vm.previousState);
        //     $state.go('p-a-organization.name',{organizationName : vm.pAOrganization.organization});
        //     $uibModalInstance.dismiss('cancel');
        // }

        function save () {
            vm.isSaving = true;
            if (vm.pAOrganization.id !== null) {
                PAOrganization.update(vm.pAOrganization, onSaveSuccess1, onSaveError);
            } else {
                PAOrganization.save(vm.pAOrganization, onSaveSuccess2, onSaveError);
            }
        }

        function onSaveSuccess1 (result) {
            console.log(vm.previousState);
            $scope.$emit('pasaasApp:pAOrganizationUpdate', result);
            // $state.go(vm.previousState);
            vm.isSaving = false;
            if(vm.previousState === 'p-a-organization.su'){
                $state.go(vm.previousState);
            }else{
                $state.go('p-a-organization.name',{organizationName : previousState.prev_state_param});
            }
        }

        function onSaveSuccess2 (result) {
            console.log(vm.previousState);
            $scope.$emit('pasaasApp:pAOrganizationUpdate', result);
            // $state.go(vm.previousState);
            vm.isSaving = false;
            if(vm.previousState === 'p-a-organization.su'){
                $state.go(vm.previousState);
            }else{
                $state.go('p-a-organization.name',{organizationName : vm.pAOrganization.organization});
            }
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




// (function() {
//     'use strict';

//     angular
//         .module('pasaasApp')
//         .controller('PAOrganizationDialogController', PAOrganizationDialogController);

//     PAOrganizationDialogController.$inject = ['moment', '$state', '$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAOrganization', 'PAProject', 'PABusinessPlan', 'PAReliabilityScore', 'PAReliabilityConf', 'PAPredictionScore', 'PASaxCodeTmp', 'PASaxCode', 'PAAlarmActuality', 'PAPrediction', 'PAReport', 'PAAccPrecision', 'PAFileUpload', 'PAPMTRequest', 'PADataConnector', 'PAScheduler', 'PASchedulerInterval', 'PAAlarmRCA', 'PANEDetails'];

//     function PAOrganizationDialogController (moment, $state, $timeout, $scope, $stateParams, $uibModalInstance, entity, PAOrganization, PAProject, PABusinessPlan, PAReliabilityScore, PAReliabilityConf, PAPredictionScore, PASaxCodeTmp, PASaxCode, PAAlarmActuality, PAPrediction, PAReport, PAAccPrecision, PAFileUpload, PAPMTRequest, PADataConnector, PAScheduler, PASchedulerInterval, PAAlarmRCA, PANEDetails) {
//         var vm = this;

//         vm.pAOrganization = entity;
//         vm.clear = clear;

//         vm.datePickerOpenStatus = {};
//         vm.openCalendar = openCalendar;
//         vm.save = save;
//         vm.paprojects = PAProject.query();
//         vm.pabusinessplans = PABusinessPlan.query();
//         /*vm.pareliabilityscores = PAReliabilityScore.query();
//         vm.pareliabilityconfs = PAReliabilityConf.query();
//         vm.papredictionscores = PAPredictionScore.query();
//         vm.pasaxcodetmps = PASaxCodeTmp.query();
//         vm.pasaxcodes = PASaxCode.query();
//         vm.paalarmactualities = PAAlarmActuality.query();
//         vm.papredictions = PAPrediction.query();
//         vm.pareports = PAReport.query();
//         vm.paaccprecisions = PAAccPrecision.query();
//         vm.pafileuploads = PAFileUpload.query();
//         vm.papmtrequests = PAPMTRequest.query();
//         vm.padataconnectors = PADataConnector.query();
//         vm.paschedulers = PAScheduler.query();
//         vm.paschedulerintervals = PASchedulerInterval.query();
//         vm.paalarmrcas = PAAlarmRCA.query();
//         vm.panedetails = PANEDetails.query();
// */
        
//         // vm.pAOrganization.validto = moment().toDate();
//         // var current_date = moment().hour(8).minute(0).second(0).toDate();

//         vm.pAOrganization.pabporg = vm.pabusinessplans[0];

//         vm.change_to_date = function(){
//             if(vm.pAOrganization.pabporg.businessplan == 'Trial'){
//                 vm.pAOrganization.validto = moment(vm.pAOrganization.validfrom).add({
//                     years: 0, 
//                     months: 1, 
//                     days: 0
//                 }).toDate();
//             }
//             if(vm.pAOrganization.pabporg.businessplan == 'Silver'){
//                 vm.pAOrganization.validto = moment(vm.pAOrganization.validfrom).add({
//                     years: 0, 
//                     months: 3, 
//                     days: 0
//                 }).toDate();
//             }
//             if(vm.pAOrganization.pabporg.businessplan == 'Gold'){
//                 vm.pAOrganization.validto = moment(vm.pAOrganization.validfrom).add({
//                     years: 0, 
//                     months: 6, 
//                     days: 0
//                 }).toDate();
//             }
//             if(vm.pAOrganization.pabporg.businessplan == 'Platinum'){
//                 vm.pAOrganization.validto = moment(vm.pAOrganization.validfrom).add({
//                     years: 1, 
//                     months: 0, 
//                     days: 0
//                 }).toDate();
//             }
//         }

//         // vm.pAOrganization.validto = current_date.add({
//         //     years: 0, 
//         //     months: 2, 
//         //     days: 0
//         // });

//         $timeout(function (){
//             angular.element('.form-group:eq(1)>input').focus();
//         });

//         function clear () {
//             // $state.go(vm.previousState);
//             $state.go('p-a-organization.name',{organizationName : vm.pAOrganization.organization});
//             $uibModalInstance.dismiss('cancel');
//         }

//         function save () {
//             vm.isSaving = true;
//             if (vm.pAOrganization.id !== null) {
//                 PAOrganization.update(vm.pAOrganization, onSaveSuccess, onSaveError);
//             } else {
//                 PAOrganization.save(vm.pAOrganization, onSaveSuccess, onSaveError);
//             }
//         }

//         function onSaveSuccess (result) {
//             $scope.$emit('pasaasApp:pAOrganizationUpdate', result);
//             $state.go('p-a-organization.name',{organizationName : vm.pAOrganization.organization});
//             $uibModalInstance.close(result);
//             vm.isSaving = false;
//         }

//         function onSaveError () {
//             vm.isSaving = false;
//         }

//         vm.datePickerOpenStatus.validfrom = false;
//         vm.datePickerOpenStatus.validto = false;

//         function openCalendar (date) {
//             vm.datePickerOpenStatus[date] = true;
//         }
//     }
// })();
