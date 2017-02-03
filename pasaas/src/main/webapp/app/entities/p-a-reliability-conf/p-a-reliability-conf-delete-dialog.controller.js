(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReliabilityConfDeleteController',PAReliabilityConfDeleteController);

    PAReliabilityConfDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAReliabilityConf'];

    function PAReliabilityConfDeleteController($uibModalInstance, entity, PAReliabilityConf) {
        var vm = this;

        vm.pAReliabilityConf = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAReliabilityConf.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
