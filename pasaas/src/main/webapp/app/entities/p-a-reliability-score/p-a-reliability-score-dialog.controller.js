(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReliabilityScoreDialogController', PAReliabilityScoreDialogController);

    PAReliabilityScoreDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAReliabilityScore', 'PAOrganization', 'PAProject'];

    function PAReliabilityScoreDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAReliabilityScore, PAOrganization, PAProject) {
        var vm = this;

        vm.pAReliabilityScore = entity;
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
            if (vm.pAReliabilityScore.id !== null) {
                PAReliabilityScore.update(vm.pAReliabilityScore, onSaveSuccess, onSaveError);
            } else {
                PAReliabilityScore.save(vm.pAReliabilityScore, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAReliabilityScoreUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.created = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
