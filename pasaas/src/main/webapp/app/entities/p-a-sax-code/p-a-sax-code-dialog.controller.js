(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASaxCodeDialogController', PASaxCodeDialogController);

    PASaxCodeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PASaxCode', 'PAOrganization', 'PAProject'];

    function PASaxCodeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PASaxCode, PAOrganization, PAProject) {
        var vm = this;

        vm.pASaxCode = entity;
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
            if (vm.pASaxCode.id !== null) {
                PASaxCode.update(vm.pASaxCode, onSaveSuccess, onSaveError);
            } else {
                PASaxCode.save(vm.pASaxCode, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pASaxCodeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
