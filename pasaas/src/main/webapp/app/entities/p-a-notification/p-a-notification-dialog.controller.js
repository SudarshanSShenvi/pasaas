(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PANotificationDialogController', PANotificationDialogController);

    PANotificationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'PANotification', 'PAOrganization', 'PAProject'];

    function PANotificationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, PANotification, PAOrganization, PAProject) {
        var vm = this;

        vm.pANotification = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
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
            if (vm.pANotification.id !== null) {
                PANotification.update(vm.pANotification, onSaveSuccess, onSaveError);
            } else {
                PANotification.save(vm.pANotification, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pANotificationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.msgtouchtime = false;

        vm.setMsgattachments = function ($file, pANotification) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        pANotification.msgattachments = base64Data;
                        pANotification.msgattachmentsContentType = $file.type;
                    });
                });
            }
        };

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
