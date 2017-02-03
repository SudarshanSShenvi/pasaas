(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAErrorMessageDeleteController',PAErrorMessageDeleteController);

    PAErrorMessageDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAErrorMessage'];

    function PAErrorMessageDeleteController($uibModalInstance, entity, PAErrorMessage) {
        var vm = this;

        vm.pAErrorMessage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAErrorMessage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
