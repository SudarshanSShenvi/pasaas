(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PADataConnectorDeleteController',PADataConnectorDeleteController);

    PADataConnectorDeleteController.$inject = ['$uibModalInstance', 'entity', 'PADataConnector'];

    function PADataConnectorDeleteController($uibModalInstance, entity, PADataConnector) {
        var vm = this;

        vm.pADataConnector = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PADataConnector.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
