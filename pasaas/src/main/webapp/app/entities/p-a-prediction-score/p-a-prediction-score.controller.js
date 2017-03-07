(function() {
    'use strict';

    angular
        .module('pasaasApp')
        .controller('PAPredictionScoreController', PAPredictionScoreController);

    PAPredictionScoreController.$inject = ['$scope', '$state', 'PAPredictionScore', 'ParseLinks', 'AlertService', 'paginationConstants', 'PAPredictionScoreC1'];

    function PAPredictionScoreController ($scope, $state, PAPredictionScore, ParseLinks, AlertService, paginationConstants, PAPredictionScoreC1) {
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

        loadAll();
        loadAllC1();

        function loadAll () {
            PAPredictionScore.query({
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
            	loadChartTopNEs(data);
            	loadScatterChart(data);
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
        
        function loadScatterChart(data) {
        	var output = new Array();

        	for(var i=0;i<data.length;i++)
        	{
        		if(data[i].cscore > 0){
        	   output.push([new Date(data[i].date), data[i].cscore]);
        		}
        	}
        	$('#predictionsscat').highcharts({
                chart: {
                	type: 'scatter',
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
                    layout: 'vertical',
                    align: 'left',
                    verticalAlign: 'top',
                    x: 100,
                    y: 70,
                    floating: true,
                    backgroundColor: (Highcharts.theme && Highcharts.theme.legendBackgroundColor) || '#FFFFFF',
                    borderWidth: 1
                },
                plotOptions: {
                    scatter: {
                        marker: {
                            radius: 5,
                            states: {
                                hover: {
                                    enabled: true,
                                    lineColor: 'rgb(100,100,100)'
                                }
                            }
                        },
                        states: {
                            hover: {
                                marker: {
                                    enabled: false
                                }
                            }
                        },
                        tooltip: {
                            headerFormat: '<b>{series.name}</b><br>',
                            pointFormat: '{point.x} cm, {point.y} kg'
                        }
                    }
                },
			    series: [{
			    	color: 'rgba(223, 83, 83, .5)',
			    	data: output
			    }]
	    	});
		
    		}
        
function loadChartTopNEs(data) {
        	
        	var topUserArray = new Array();
        	for(var i=0;i<data.length;i++)
        	{
        		if(data[i].cscore >0){
        		topUserArray.push([data[i].dist,data[i].cscore]);
        		}
        	}
        	$('#topNEs').highcharts({
                title: {
                    text: 'Top 10 MSISDN Usage over last 3 months'
                },
                xAxis: {
                	categories : topUserArray
			    },
			    yAxis: {
                    title: {
                        text: 'Total traffic'
                    }
                },
                legend: {
                    enabled: false
                },
                series: [{
			    	type: 'column',
			    	data: topUserArray,
			    	pointWidth: 28
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
