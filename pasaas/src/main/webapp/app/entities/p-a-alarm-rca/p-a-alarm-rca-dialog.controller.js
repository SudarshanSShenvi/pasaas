(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAAlarmRCADialogController', PAAlarmRCADialogController);

    PAAlarmRCADialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAAlarmRCA', 'PAProject', 'PAOrganization'];

    function PAAlarmRCADialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAAlarmRCA, PAProject, PAOrganization) {
        var vm = this;

        vm.pAAlarmRCA = entity;
        vm.clear = clear;
        vm.save = save;
        vm.paprojects = PAProject.query();
        vm.paorganizations = PAOrganization.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pAAlarmRCA.id !== null) {
                PAAlarmRCA.update(vm.pAAlarmRCA, onSaveSuccess, onSaveError);
            } else {
                PAAlarmRCA.save(vm.pAAlarmRCA, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAAlarmRCAUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
