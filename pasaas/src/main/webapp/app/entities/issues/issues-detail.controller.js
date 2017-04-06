(function() {
    'use strict';

    angular
        .module('imgcheckApp')
        .controller('IssuesDetailController', IssuesDetailController);

    IssuesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Issues'];

    function IssuesDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Issues) {
        var vm = this;

        vm.issues = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('imgcheckApp:issuesUpdate', function(event, result) {
            vm.issues = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
