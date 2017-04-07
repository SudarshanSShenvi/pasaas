(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('DashboardController', DashboardController);

    DashboardController.$inject = ['PAPredictionScorePieChart', 'previousState', '$scope', '$state', 'PAOrganization'];

    function DashboardController (PAPredictionScorePieChart, previousState, $scope, $state, PAOrganization) {
        var vm = this;

        vm.previousState = previousState.name;
        $scope.project_id = previousState.coming_project_id;

        vm.fetch_pie_data = function(selected_pie_size){
            vm.pieData = [];
            PAPredictionScorePieChart.query({
                // page: vm.page,
                // projectId: vm.selected_project.id,
                // sort: "bscore,desc"
                // size: 20,
                size: selected_pie_size,
                projectId: $scope.project_id
            }, onSuccess, onError);
        }
        vm.fetch_pie_data(10);

        // vm.pieData = [];

        function onSuccess(data, headers) {
            console.log(data);
            for (var i = 0; i < data.length; i++) {
                vm.pieData[i] = {};
                vm.pieData[i].label = data[i].alarmno;
                vm.pieData[i].data = data[i].count;
                vm.pieData[i].color = '#'+Math.floor(Math.random()*16777215).toString(16);
            }
        }

        function onError(error) {
            AlertService.error(error.data.message);
        }

        /**
         * Pie Chart Data
         */
        // vm.pieData = [
        //     {
        //         label: "Sales 1",
        //         data: 21,
        //         color: "#d3d3d3",
        //     },
        //     {
        //         label: "Sales 2",
        //         data: 3,
        //         color: "#bababa",
        //     },
        //     {
        //         label: "Sales 3",
        //         data: 15,
        //         color: "#79d2c0",
        //     },
        //     {
        //         label: "Sales 4",
        //         data: 52,
        //         color: "#1ab394",
        //     }
        // ];

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
            },
            legend: {
                noColumns: 2,
                // show: boolean,
                // labelFormatter: null or (fn: string, series object -> string),
                // labelBoxBorderColor: color,
                // position: "ne" or "nw" or "se" or "sw",
                // margin: number of pixels or [x margin, y margin],
                // backgroundColor: null or color,
                // backgroundOpacity: number between 0 and 1,
                // container: null or jQuery object/DOM element/jQuery expression,
                // sorted: null/false, true, "ascending", "descending", "reverse", or a comparator,
            }
        };

    }
})();
