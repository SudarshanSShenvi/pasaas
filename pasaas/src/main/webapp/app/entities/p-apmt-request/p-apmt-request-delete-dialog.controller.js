(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAPMTRequestDeleteController',PAPMTRequestDeleteController);

    PAPMTRequestDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAPMTRequest'];

    function PAPMTRequestDeleteController($uibModalInstance, entity, PAPMTRequest) {
        var vm = this;

        vm.pAPMTRequest = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAPMTRequest.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
