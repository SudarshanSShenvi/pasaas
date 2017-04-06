(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('controller_prediction_score_chart_C', controller_prediction_score_chart_C);

    controller_prediction_score_chart_C.$inject = ['$scope', '$timeout', 'ParseLinks', 'paginationConstants', 'PAPredictionScoreChartC'];

    function controller_prediction_score_chart_C ($scope, $timeout, ParseLinks, paginationConstants, PAPredictionScoreChartC) {
        var vm = this;

        vm.pAPredictionScores = [];
        vm.loadPage = loadPage;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reset = reset;
        vm.reverse = true;

        // vm.get_patterns = function(){
        //     vm.pAPredictionScores = [];
        //     loadAll();
        // }
        loadAll();

        function loadAll () {
            PAPredictionScoreChartC.query({
                // page: vm.page,
                // projectId: vm.selected_project.id,
                size: 20,
                projectId: $scope.project_id,
                sort: "cscore,desc"
            }, onSuccess, onError);
            function sort() {
                var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                if (vm.predicate !== 'id') {
                    result.push('id');
                }
                return result;
            }

            function onSuccess(data, headers) {
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                for (var i = 0; i < data.length; i++) {
                    vm.pAPredictionScores.push(data[i]);
                }
            }

            function onError(error) {
                AlertService.error(error.data.message);
            }
        }
        
        function reset () {
            vm.page = 0;
            vm.pAPredictionScores = [];
            loadAll();
        }

        function loadPage(page) {
            vm.page = page;
            loadAll();
        }


        /**
         * Options for Bar chart
         */
        vm.barOptions = {
            scaleBeginAtZero : true,
            scaleShowGridLines : true,
            scaleGridLineColor : "rgba(0,0,0,.05)",
            scaleGridLineWidth : 1,
            barShowStroke : true,
            barStrokeWidth : 2,
            barValueSpacing : 5,
            barDatasetSpacing : 1,
        };

        /**
         * Data for Bar chart
         */
        $timeout(function () {
            var arr_labels = [];
            var arr_data = [];
            for(var i = 0; i < vm.pAPredictionScores.length ; i++){
                arr_labels.push(vm.pAPredictionScores[i].dist);
            }
            for(var i = 0; i < vm.pAPredictionScores.length ; i++){
                arr_data.push(vm.pAPredictionScores[i].cscore * 100);
            }
            vm.barData = {
                // labels: ["January", "February", "March", "April", "May", "June", "July"],
                labels: arr_labels,
                datasets: [
                    {
                        label: "My First dataset",
                        fillColor: "rgba(26,179,148,0.5)",
                        strokeColor: "rgba(26,179,148,0.8)",
                        highlightFill: "rgba(26,179,148,0.75)",
                        highlightStroke: "rgba(26,179,148,1)",
                        // data: [65, 59, 80, 81, 56, 55, 40]
                        // ]
                        data: arr_data
                    },
                ]
            };
        }, 1000);

    }
})();