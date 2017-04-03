(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReportDialogController', PAReportDialogController);

    PAReportDialogController.$inject = ['$state', 'previousState', '$timeout', '$scope', '$stateParams', 'entity', 'PAReport', 'PAOrganization', 'DataUtils', '$window'];

    function PAReportDialogController ($state, previousState, $timeout, $scope, $stateParams, entity, PAReport, PAOrganization, DataUtils, $window) {
        var vm = this;

        vm.pAReport = entity;
        vm.save = save;
        vm.paorganizations = PAOrganization.query();
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.download = download;

        vm.previousState = previousState.name;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        
        function save () {
            vm.isSaving = true;
            if (vm.pAReport.id !== null) {
                PAReport.update(vm.pAReport, onSaveSuccess, onSaveError);
            } else {
                PAReport.save(vm.pAReport, onSaveSuccess, onSaveError);
            }
        }
        
        function onSaveSuccess (result) {
            swal({
                title: "Success!",
                text: "Record Edited Successfully",
                type: "success"
            });
            $scope.$emit('pasaasApp:pAReportUpdate', result);
            vm.isSaving = false;
            $state.go(vm.previousState);
        }

        function onSaveError () {
            swal({
                title: "Error!",
                text: "Record Edit Failed",
                type: "error"
            });
            vm.isSaving = false;
        }

        function download (file, $window) {
            vm.blob = new Blob([file], {type: 'data:attachment;charset=utf-8'});
            vm.url = ($window.URL || $window.webkitURL).createObjectURL(vm.blob);
        }

        
        vm.setMsgattachments = function ($file, pAReport) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        pAReport.reportfile = base64Data;
                        pAReport.reportfileContentType = $file.type;
                    });
                });
            }
        };
        
    }
})();


// (function() {
//     'use strict';

//     angular
//         .module('pasaasApp')
//         .controller('PAReportDialogController', PAReportDialogController);

//     PAReportDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAReport', 'PAOrganization', 'DataUtils', '$window'];

//     function PAReportDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAReport, PAOrganization, DataUtils, $window) {
//         var vm = this;

//         vm.pAReport = entity;
//         vm.clear = clear;
//         vm.save = save;
//         vm.paorganizations = PAOrganization.query();
//         vm.byteSize = DataUtils.byteSize;
//         vm.openFile = DataUtils.openFile;
//         vm.download = download;

//         $timeout(function (){
//             angular.element('.form-group:eq(1)>input').focus();
//         });

//         function clear () {
//             $uibModalInstance.dismiss('cancel');
//         }

//         function save () {
//             vm.isSaving = true;
//             if (vm.pAReport.id !== null) {
//                 PAReport.update(vm.pAReport, onSaveSuccess, onSaveError);
//             } else {
//                 PAReport.save(vm.pAReport, onSaveSuccess, onSaveError);
//             }
//         }
        
//         function download (file, $window) {
//         	vm.blob = new Blob([file], {type: 'data:attachment;charset=utf-8'});
//             vm.url = ($window.URL || $window.webkitURL).createObjectURL(vm.blob);
//         }

//         function onSaveSuccess (result) {
//             $scope.$emit('pasaasApp:pAReportUpdate', result);
//             $uibModalInstance.close(result);
//             vm.isSaving = false;
//         }

//         function onSaveError () {
//             vm.isSaving = false;
//         }
        
//         vm.setMsgattachments = function ($file, pAReport) {
//             if ($file) {
//                 DataUtils.toBase64($file, function(base64Data) {
//                     $scope.$apply(function() {
//                     	pAReport.reportfile = base64Data;
//                     	pAReport.reportfileContentType = $file.type;
//                     });
//                 });
//             }
//         };
        
//     }
// })();
