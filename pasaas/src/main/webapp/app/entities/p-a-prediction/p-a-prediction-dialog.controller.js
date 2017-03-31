(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAPredictionDialogController', PAPredictionDialogController);

    PAPredictionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAPrediction', 'PAOrganization', 'PAProject'];

    function PAPredictionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAPrediction, PAOrganization, PAProject) {
        var vm = this;

        vm.pAPrediction = entity;
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
            if (vm.pAPrediction.id !== null) {
                PAPrediction.update(vm.pAPrediction, onSaveSuccess, onSaveError);
            } else {
                PAPrediction.save(vm.pAPrediction, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAPredictionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.predictiondate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
