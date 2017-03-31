(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAErrorMessageDialogController', PAErrorMessageDialogController);

    PAErrorMessageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAErrorMessage', 'PAOrganization', 'PAProject'];

    function PAErrorMessageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAErrorMessage, PAOrganization, PAProject) {
        var vm = this;

        vm.pAErrorMessage = entity;
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
            if (vm.pAErrorMessage.id !== null) {
                PAErrorMessage.update(vm.pAErrorMessage, onSaveSuccess, onSaveError);
            } else {
                PAErrorMessage.save(vm.pAErrorMessage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAErrorMessageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.errortime = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
