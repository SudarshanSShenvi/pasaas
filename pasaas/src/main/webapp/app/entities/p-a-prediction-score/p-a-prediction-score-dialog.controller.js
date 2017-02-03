(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAPredictionScoreDialogController', PAPredictionScoreDialogController);

    PAPredictionScoreDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAPredictionScore', 'PAOrganization', 'PAProject'];

    function PAPredictionScoreDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAPredictionScore, PAOrganization, PAProject) {
        var vm = this;

        vm.pAPredictionScore = entity;
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
            if (vm.pAPredictionScore.id !== null) {
                PAPredictionScore.update(vm.pAPredictionScore, onSaveSuccess, onSaveError);
            } else {
                PAPredictionScore.save(vm.pAPredictionScore, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAPredictionScoreUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createdon = false;
        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
