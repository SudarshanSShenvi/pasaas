(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PANotificationDetailController', PANotificationDetailController);

    PANotificationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'PANotification', 'PAOrganization', 'PAProject'];

    function PANotificationDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, PANotification, PAOrganization, PAProject) {
        var vm = this;

        vm.pANotification = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('pasaasApp:pANotificationUpdate', function(event, result) {
            vm.pANotification = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
