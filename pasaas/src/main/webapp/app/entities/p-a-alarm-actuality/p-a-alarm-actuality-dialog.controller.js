(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAAlarmActualityDialogController', PAAlarmActualityDialogController);

    PAAlarmActualityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAAlarmActuality', 'PAOrganization', 'PAProject'];

    function PAAlarmActualityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAAlarmActuality, PAOrganization, PAProject) {
        var vm = this;

        vm.pAAlarmActuality = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.pAAlarmActuality.id !== null) {
                PAAlarmActuality.update(vm.pAAlarmActuality, onSaveSuccess, onSaveError);
            } else {
                PAAlarmActuality.save(vm.pAAlarmActuality, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAAlarmActualityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.occured = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
