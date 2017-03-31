(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAPredictionScoreDetailController', PAPredictionScoreDetailController);

    PAPredictionScoreDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAPredictionScore', 'PAOrganization', 'PAProject'];

    function PAPredictionScoreDetailController($scope, $rootScope, $stateParams, previousState, entity, PAPredictionScore, PAOrganization, PAProject) {
        var vm = this;

        vm.pAPredictionScore = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('pasaasApp:pAPredictionScoreUpdate', function(event, result) {
            vm.pAPredictionScore = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
