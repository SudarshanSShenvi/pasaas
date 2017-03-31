(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAPMTRequestDialogController', PAPMTRequestDialogController);

    PAPMTRequestDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAPMTRequest', 'PAOrganization', 'PAProject'];

    function PAPMTRequestDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAPMTRequest, PAOrganization, PAProject) {
        var vm = this;

        vm.pAPMTRequest = entity;
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
            if (vm.pAPMTRequest.id !== null) {
                PAPMTRequest.update(vm.pAPMTRequest, onSaveSuccess, onSaveError);
            } else {
                PAPMTRequest.save(vm.pAPMTRequest, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAPMTRequestUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
