(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAAccPrecisionDeleteController',PAAccPrecisionDeleteController);

    PAAccPrecisionDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAAccPrecision'];

    function PAAccPrecisionDeleteController($uibModalInstance, entity, PAAccPrecision) {
        var vm = this;

        vm.pAAccPrecision = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAAccPrecision.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
