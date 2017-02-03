(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAReliabilityScoreDeleteController',PAReliabilityScoreDeleteController);

    PAReliabilityScoreDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAReliabilityScore'];

    function PAReliabilityScoreDeleteController($uibModalInstance, entity, PAReliabilityScore) {
        var vm = this;

        vm.pAReliabilityScore = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAReliabilityScore.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
