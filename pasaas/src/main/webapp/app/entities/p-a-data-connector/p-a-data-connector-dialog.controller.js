(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PADataConnectorDialogController', PADataConnectorDialogController);

    PADataConnectorDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PADataConnector', 'PAOrganization', 'PAProject'];

    function PADataConnectorDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PADataConnector, PAOrganization, PAProject) {
        var vm = this;

        vm.pADataConnector = entity;
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
            if (vm.pADataConnector.id !== null) {
                PADataConnector.update(vm.pADataConnector, onSaveSuccess, onSaveError);
            } else {
                PADataConnector.save(vm.pADataConnector, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pADataConnectorUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
