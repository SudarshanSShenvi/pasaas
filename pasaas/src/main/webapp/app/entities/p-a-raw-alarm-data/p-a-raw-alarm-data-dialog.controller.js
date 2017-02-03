(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PARawAlarmDataDialogController', PARawAlarmDataDialogController);

    PARawAlarmDataDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PARawAlarmData'];

    function PARawAlarmDataDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PARawAlarmData) {
        var vm = this;

        vm.pARawAlarmData = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pARawAlarmData.id !== null) {
                PARawAlarmData.update(vm.pARawAlarmData, onSaveSuccess, onSaveError);
            } else {
                PARawAlarmData.save(vm.pARawAlarmData, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pARawAlarmDataUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.starteddate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
