(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASchedulerIntervalDialogController', PASchedulerIntervalDialogController);

    PASchedulerIntervalDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PASchedulerInterval', 'PAOrganization', 'PAProject'];

    function PASchedulerIntervalDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PASchedulerInterval, PAOrganization, PAProject) {
        var vm = this;

        vm.pASchedulerInterval = entity;
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
            if (vm.pASchedulerInterval.id !== null) {
                PASchedulerInterval.update(vm.pASchedulerInterval, onSaveSuccess, onSaveError);
            } else {
                PASchedulerInterval.save(vm.pASchedulerInterval, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pASchedulerIntervalUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
