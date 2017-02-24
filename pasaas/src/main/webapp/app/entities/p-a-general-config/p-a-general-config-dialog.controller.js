(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAGeneralConfigDialogController', PAGeneralConfigDialogController);

    PAGeneralConfigDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PAGeneralConfig', 'DataUtils'];

    function PAGeneralConfigDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PAGeneralConfig, DataUtils) {
        var vm = this;

        vm.pAGeneralConfig = entity;
        vm.clear = clear;
        vm.save = save;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.pAGeneralConfig.id !== null) {
                PAGeneralConfig.update(vm.pAGeneralConfig, onSaveSuccess, onSaveError);
            } else {
                PAGeneralConfig.save(vm.pAGeneralConfig, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('pasaasApp:pAGeneralConfigUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }
        
        vm.setMsgattachments = function ($file, pAGeneralConfig) {
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                    	pAGeneralConfig.exprfile = base64Data;
                    	pAGeneralConfig.exprfileContentType = $file.type;
                    });
                });
            }
        };


    }
})();
