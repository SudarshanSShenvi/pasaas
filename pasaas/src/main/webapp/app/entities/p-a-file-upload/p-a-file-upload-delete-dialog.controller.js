(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAFileUploadDeleteController',PAFileUploadDeleteController);

    PAFileUploadDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAFileUpload'];

    function PAFileUploadDeleteController($uibModalInstance, entity, PAFileUpload) {
        var vm = this;

        vm.pAFileUpload = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAFileUpload.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
