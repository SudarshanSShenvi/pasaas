(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASaxCodeDeleteController',PASaxCodeDeleteController);

    PASaxCodeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PASaxCode'];

    function PASaxCodeDeleteController($uibModalInstance, entity, PASaxCode) {
        var vm = this;

        vm.pASaxCode = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PASaxCode.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
