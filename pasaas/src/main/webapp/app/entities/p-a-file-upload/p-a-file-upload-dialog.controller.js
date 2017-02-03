(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAFileUploadDialogController', PAFileUploadDialogController);

    PAFileUploadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAFileUpload', 'PAOrganization', 'PAProject'];

    function PAFileUploadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAFileUpload, PAOrganization, PAProject) {
        var vm = this;

        vm.pAFileUpload = entity;
        vm.clear = clear;
        vm.save = save;
        vm.paorganizations = PAOrganization.query();
        vm.paprojects = PAProject.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pAFileUpload.id !== null) {
                PAFileUpload.update(vm.pAFileUpload, onSaveSuccess, onSaveError);
            } else {
                PAFileUpload.save(vm.pAFileUpload, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAFileUploadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
