(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAFileUploadDetailController', PAFileUploadDetailController);

    PAFileUploadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAFileUpload', 'PAOrganization', 'PAProject'];

    function PAFileUploadDetailController($scope, $rootScope, $stateParams, previousState, entity, PAFileUpload, PAOrganization, PAProject) {
        var vm = this;

        vm.pAFileUpload = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pAFileUploadUpdate', function(event, result) {
            vm.pAFileUpload = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
