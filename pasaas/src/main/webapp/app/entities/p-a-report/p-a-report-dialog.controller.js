(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReportDialogController', PAReportDialogController);

    PAReportDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAReport', 'PAOrganization'];

    function PAReportDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAReport, PAOrganization) {
        var vm = this;

        vm.pAReport = entity;
        vm.clear = clear;
        vm.save = save;
        vm.paorganizations = PAOrganization.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pAReport.id !== null) {
                PAReport.update(vm.pAReport, onSaveSuccess, onSaveError);
            } else {
                PAReport.save(vm.pAReport, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAReportUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
