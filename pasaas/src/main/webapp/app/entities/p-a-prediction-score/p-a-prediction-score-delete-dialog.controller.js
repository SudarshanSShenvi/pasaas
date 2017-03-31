(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAPredictionScoreDeleteController',PAPredictionScoreDeleteController);

    PAPredictionScoreDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAPredictionScore'];

    function PAPredictionScoreDeleteController($uibModalInstance, entity, PAPredictionScore) {
        var vm = this;

        vm.pAPredictionScore = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAPredictionScore.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
