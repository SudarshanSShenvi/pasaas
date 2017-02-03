(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PANEDetailsDialogController', PANEDetailsDialogController);

    PANEDetailsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PANEDetails', 'PAOrganization', 'PAProject'];

    function PANEDetailsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PANEDetails, PAOrganization, PAProject) {
        var vm = this;

        vm.pANEDetails = entity;
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
            if (vm.pANEDetails.id !== null) {
                PANEDetails.update(vm.pANEDetails, onSaveSuccess, onSaveError);
            } else {
                PANEDetails.save(vm.pANEDetails, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pANEDetailsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
