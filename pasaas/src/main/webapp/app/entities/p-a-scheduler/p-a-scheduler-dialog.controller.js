(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASchedulerDialogController', PASchedulerDialogController);

    PASchedulerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAScheduler', 'PAOrganization', 'PAProject'];

    function PASchedulerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAScheduler, PAOrganization, PAProject) {
        var vm = this;

        vm.pAScheduler = entity;
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
            if (vm.pAScheduler.id !== null) {
                PAScheduler.update(vm.pAScheduler, onSaveSuccess, onSaveError);
            } else {
                PAScheduler.save(vm.pAScheduler, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pASchedulerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
