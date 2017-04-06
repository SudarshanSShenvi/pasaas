(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['previousState', '$scope', '$state', 'PAOrganization'];

    function DashboardController (previousState, $scope, $state, PAOrganization) {
        var vm = this;

        vm.previousState = previousState.name;
        $scope.project_id = previousState.coming_project_id;

        /**
         * Pie Chart Data
         */
        vm.pieData = [
            {
                label: "Sales 1",
                data: 21,
                color: "#d3d3d3",
            },
            {
                label: "Sales 2",
                data: 3,
                color: "#bababa",
            },
            {
                label: "Sales 3",
                data: 15,
                color: "#79d2c0",
            },
            {
                label: "Sales 4",
                data: 52,
                color: "#1ab394",
            }
        ];

        /**
         * Pie Chart Options
         */
        vm.pieOptions = {
            series: {
                pie: {
                    show: true
                }
            },
            grid: {
                hoverable: true
            },
            tooltip: true,
            tooltipOpts: {
                content: "%p.0%, %s", // show percentages, rounding to 2 decimal places
                shifts: {
                    x: 20,
                    y: 0
                },
                defaultTheme: false
            }
        };

    }
})();
