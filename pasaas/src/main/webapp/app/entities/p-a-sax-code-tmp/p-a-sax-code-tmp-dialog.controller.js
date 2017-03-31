(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASaxCodeTmpDialogController', PASaxCodeTmpDialogController);

    PASaxCodeTmpDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PASaxCodeTmp', 'PAOrganization', 'PAProject'];

    function PASaxCodeTmpDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PASaxCodeTmp, PAOrganization, PAProject) {
        var vm = this;

        vm.pASaxCodeTmp = entity;
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
            if (vm.pASaxCodeTmp.id !== null) {
                PASaxCodeTmp.update(vm.pASaxCodeTmp, onSaveSuccess, onSaveError);
            } else {
                PASaxCodeTmp.save(vm.pASaxCodeTmp, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pASaxCodeTmpUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
