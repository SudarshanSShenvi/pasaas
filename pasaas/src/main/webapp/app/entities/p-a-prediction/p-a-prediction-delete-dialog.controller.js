(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAPredictionDeleteController',PAPredictionDeleteController);

    PAPredictionDeleteController.$inject = ['$uibModalInstance', 'entity', 'PAPrediction'];

    function PAPredictionDeleteController($uibModalInstance, entity, PAPrediction) {
        var vm = this;

        vm.pAPrediction = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PAPrediction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
