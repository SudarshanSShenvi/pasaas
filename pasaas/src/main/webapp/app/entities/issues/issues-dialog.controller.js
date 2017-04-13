(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('IssuesDialogController', IssuesDialogController);

    IssuesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Issues'];

    function IssuesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Issues) {
        var vm = this;

        vm.issues = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.issues.id !== null) {
                Issues.update(vm.issues, onSaveSuccess, onSaveError);
            } else {
                Issues.save(vm.issues, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:issuesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setAttachment = function ($file, issues) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        issues.attachment = base64Data;
                        issues.attachmentContentType = $file.type;
                    });
                });
            }
        };

    }
})();
