(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('UserManagementDetailController', UserManagementDetailController);

    UserManagementDetailController.$inject = ['previousState', '$stateParams', 'User'];

    function UserManagementDetailController (previousState, $stateParams, User) {
        var vm = this;

        vm.load = load;
        vm.user = {};


        vm.previousState = previousState.name;

        vm.load($stateParams.login);

        function load (login) {
            User.get({login: login}, function(result) {
                vm.user = result;
            });
        }
    }
})();
