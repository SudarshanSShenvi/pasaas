(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PASaxCodeTmpDeleteController',PASaxCodeTmpDeleteController);

    PASaxCodeTmpDeleteController.$inject = ['$uibModalInstance', 'entity', 'PASaxCodeTmp'];

    function PASaxCodeTmpDeleteController($uibModalInstance, entity, PASaxCodeTmp) {
        var vm = this;

        vm.pASaxCodeTmp = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PASaxCodeTmp.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
