(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PANotificationDeleteController',PANotificationDeleteController);

    PANotificationDeleteController.$inject = ['$uibModalInstance', 'entity', 'PANotification'];

    function PANotificationDeleteController($uibModalInstance, entity, PANotification) {
        var vm = this;

        vm.pANotification = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PANotification.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
