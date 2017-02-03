(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAAccPrecisionDialogController', PAAccPrecisionDialogController);

    PAAccPrecisionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAAccPrecision', 'PAProject', 'PAOrganization'];

    function PAAccPrecisionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAAccPrecision, PAProject, PAOrganization) {
        var vm = this;

        vm.pAAccPrecision = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.pAAccPrecision.id !== null) {
                PAAccPrecision.update(vm.pAAccPrecision, onSaveSuccess, onSaveError);
            } else {
                PAAccPrecision.save(vm.pAAccPrecision, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAAccPrecisionUpdate', result);
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
