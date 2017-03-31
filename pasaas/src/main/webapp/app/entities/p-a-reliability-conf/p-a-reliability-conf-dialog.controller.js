(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReliabilityConfDialogController', PAReliabilityConfDialogController);

    PAReliabilityConfDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAReliabilityConf', 'PAOrganization', 'PAProject'];

    function PAReliabilityConfDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAReliabilityConf, PAOrganization, PAProject) {
        var vm = this;

        vm.pAReliabilityConf = entity;
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
            if (vm.pAReliabilityConf.id !== null) {
                PAReliabilityConf.update(vm.pAReliabilityConf, onSaveSuccess, onSaveError);
            } else {
                PAReliabilityConf.save(vm.pAReliabilityConf, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAReliabilityConfUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
