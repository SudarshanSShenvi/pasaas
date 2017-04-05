(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAPredictionScoreController', PAPredictionScoreController);

    PAPredictionScoreController.$inject = ['PAPredictionScoreSu', 'PAProjectSu', '$scope', '$state', 'PAPredictionScore', 'ParseLinks', 'AlertService', 'paginationConstants', 'PAPredictionScoreC1'];

    function PAPredictionScoreController (PAPredictionScoreSu, PAProjectSu, $scope, $state, PAPredictionScore, ParseLinks, AlertService, paginationConstants, PAPredictionScoreC1) {
        var vm = this;

        vm.project_list = PAProjectSu.query();

        vm.selected_project = {};

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

        // loadAllC1();

        vm.get_patterns = function(){
            vm.pAPredictionScores = [];
            loadAll();
        }

        function loadAll () {
            PAPredictionScoreSu.query({
                page: vm.page,
                size: vm.itemsPerPage,
                projectId: vm.selected_project.id,
                sort: sort()
            }, onSuccess, onError);
            // PAPredictionScore.query({
            //     page: vm.page,
            //     size: vm.itemsPerPage,
            //     sort: sort()
            // }, onSuccess, onError);
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
        
        function loadAllC1 () {
        	PAPredictionScoreC1.query({
        		page: vm.page,
        		size: vm.itemsPerPage,
        		sort: sort()
        		}, onSuccess, onError);
        	function sort() {
        		var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
        		if (vm.predicate !== 'id') {
        			result.push('id');
        			}
        		return result;
        		}
        	function onSuccess(data, headers) {
        		loadChart(data);
        		}
        	function onError(error) {
        		AlertService.error(error.data.message);
        		}
        	}
        
        function loadChart(data) {
        	var output = new Array();
        	for(var i=0;i<data.length;i++)
        	{
        		output.push([new Date(data[i].createdon).getTime(), data[i].cscore]);
        		}
        	$('#predictions').highcharts({
        		chart: {
        			zoomType: 'x'
        				},
        				title: {
        					text: 'Abnormal Termination day on day'
        						},
        						xAxis: {
        							type: 'datetime'
        								},
        						yAxis: {
        							title: {
        								text: 'Abnormal Terminations'
        									}
        								},
        				legend: {
        					enabled: false
        					},
        					plotOptions: {
        						lineWidth: 1
        						},
        						series: [{
        							type: 'line',
        							data: output
        							}]
        						});
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
    }
})();
