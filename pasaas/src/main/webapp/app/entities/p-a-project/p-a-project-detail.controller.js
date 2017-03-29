(function() {
	'use strict';
	angular
		.module('pasaasApp').directive('fileModel', ['$parse', function ($parse) {
		return {
			restrict: 'A',
			link: function(scope, element, attrs) {
				var model = $parse(attrs.fileModel);
				var modelSetter = model.assign;
				
				element.bind('change', function(){
					scope.$apply(function(){
						modelSetter(scope, element[0].files[0]);
					});
				});
			}
		};
	}]);

	angular
		.module('pasaasApp')
		.service('fileUpload', ['$http', '$state', function ($http, $state) {
		this.uploadFileToUrl = function(file, uploadUrl){
			var fd = new FormData();
			fd.append('file', file);
			$http.post(uploadUrl, fd, {
				transformRequest: angular.identity,
				headers: {'Content-Type': undefined}
			})
			.success(function(){
				swal({
					title: "Success!",
					text: "File Uploaded Successfully",
					type: "success"
				});
				$state.reload();
			})
			.error(function(){
				swal({
					title: "Error!",
					text: "File Upload Failed",
					type: "error"
				});
			});
		}
	}]);

	angular
		.module('pasaasApp')
		.controller('PAProjectDetailController', PAProjectDetailController);

	PAProjectDetailController.$inject = ['$window', '$state', '$http', 'PADataTrain', 'fileUpload', 'PAProjectUpload', 'DataUtils', '$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PAProject', 'PAOrganization', 'PAErrorMessage', 'PANotification', 'PAFileUpload', 'PAAccPrecision', 'PAPrediction', 'PAAlarmActuality', 'PASaxCode', 'PASaxCodeTmp', 'PAPredictionScore', 'PAReliabilityConf', 'PAReliabilityScore', 'PAPMTRequest', 'PASchedulerInterval', 'PAAlarmRCA', 'PANEDetails', 'PADataConnector', 'PAScheduler'];

	function PAProjectDetailController($window, $state, $http, PADataTrain, fileUpload, PAProjectUpload, DataUtils, $scope, $rootScope, $stateParams, previousState, entity, PAProject, PAOrganization, PAErrorMessage, PANotification, PAFileUpload, PAAccPrecision, PAPrediction, PAAlarmActuality, PASaxCode, PASaxCodeTmp, PAPredictionScore, PAReliabilityConf, PAReliabilityScore, PAPMTRequest, PASchedulerInterval, PAAlarmRCA, PANEDetails, PADataConnector, PAScheduler) {
		var vm = this;

		vm.show_upload_button = false;
		$scope.fileNameChanged = function(){
			vm.show_upload_button = true;
		}
		vm.pAProject = entity;
		vm.previousState = previousState.name;
		vm.openFile = DataUtils.openFile;
		// vm.download = download;

		// function download (file, $window) {
  //       	vm.blob = new Blob([file], {type: 'data:attachment;charset=utf-8'});
  //           vm.url = ($window.URL || $window.webkitURL).createObjectURL(vm.blob);
  //       }

		var unsubscribe = $rootScope.$on('pasaasApp:pAProjectUpdate', function(event, result) {
			vm.pAProject = result;
		});
		$scope.$on('$destroy', unsubscribe);

		function on_download_success (result) {
			vm.openFile(result.data, "text/base64");
			// vm.download(result.data);
			console.log("Inside on_download_success : " + result.data.message);
			swal({
				title: "Success!",
				text: result.data.message,
				type: "success"
			});
			// $state.reload();
		}
		function on_download_error () {
			console.log("Inside on_download_error : " + "Error Occoured");
			swal({
				title: "Error!",
				text: "Error Occoured while File Download",
				type: "error"
			});
		}
		vm.download_file = function(file_name){
			var api = "/api/download/";
			var project_id = vm.pAProject.id;
			$http.get("/api/download/" + project_id + "/" + file_name)
			.then(on_download_success, on_download_error);
		}

		function on_delete_success (result) {
			console.log("Inside on_delete_success : " + result.data.message);
			swal({
				title: "Success!",
				text: result.data.message,
				type: "success"
			});
			$state.reload();
		}
		function on_delete_error () {
			console.log("Inside on_delete_error : " + "Error Occoured");
			swal({
				title: "Error!",
				text: "Error Occoured while File Deletion",
				type: "error"
			});
		}
		vm.delete_file = function(file_name){
			var api = "/api/delete/";
			var project_id = vm.pAProject.id;
			
			swal({
				title: "Are you sure?",
				text: "You will not be able to recover this file!",
				type: "warning",
				showCancelButton: true,
				confirmButtonColor: "#DD6B55",
				confirmButtonText: "Yes, delete it!",
				cancelButtonText: "No, cancel please!",
				closeOnConfirm: false,
				closeOnCancel: false
			},
			function(isConfirm){
				if (isConfirm) {
					$http.delete("/api/delete/" + project_id + "/" + file_name)
					.then(on_delete_success, on_delete_error);
				} else {
					swal("Cancelled", "Delete Operation Canceled", "error");
				}
			});
			
		}
		

		function formatBytes(bytes,decimals) {
		   if(bytes == 0) return '0 Byte';
		   var k = 1000;
		   var dm = decimals + 1 || 3;
		   var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'];
		   var i = Math.floor(Math.log(bytes) / Math.log(k));
		   return parseFloat((bytes / Math.pow(k, i)).toFixed(dm)) + ' ' + sizes[i];
		}

		vm.file_size_converter = function(file_size_in_bytes) {
			return formatBytes(file_size_in_bytes, 2);
		};
		// vm.file_size_converter = function() {
		//     return formatBytes(bytes);
		// };

		function onTrainingSuccess (result) {
			console.log("Inside onTrainingSuccess : " + result.data.message);
			swal({
				title: "Success!",
				text: result.data.message,
				type: "success"
			});
			// alert(result.data.message);
		}

		function onTrainingError () {
			console.log("Inside onTrainingError : " + "Error Occoured");
			swal({
				title: "Error!",
				text: "Error Occoured while Data Training",
				type: "error"
			});
		}
		// UserService.save({name: 'Saimon', email: 'saimon@devdactic.com'});


		vm.start_data_training = function(project_name){
			console.log("Inside start_data_training");
			// var data_training_url = "http://10.10.10.176:8088/api/triggerTrainingOps/Telco%20Project";
			var data_training_url = "api/triggerTrainingOps/" + project_name;
			$http.post(data_training_url).then(onTrainingSuccess, onTrainingError);
			// PADataTrain.save({projectName: project_name}, onTrainingSuccess, onTrainingError);
		}

		function onPredictionSuccess (result) {
			console.log("Inside onPredictionSuccess : " + result.data.message);
			swal({
				title: "Success!",
				text: result.data.message,
				type: "success"
			});
			// alert(result.data.message);
		}

		function onPredictionError () {
			console.log("Inside onPredictionError : " + "Error Occoured");
			swal({
				title: "Error!",
				text: "Error Occoured while Data Prediction",
				type: "error"
			});

		}
		// UserService.save({name: 'Saimon', email: 'saimon@devdactic.com'});


		vm.start_data_Prediction = function(project_name){
			console.log("Inside start_data_Prediction");
			// var data_Prediction_url = "http://10.10.10.176:8088/api/triggerPredictionOps/Telco%20Project";
			var data_Prediction_url = "api/triggerPredictionOps/" + project_name;
			$http.post(data_Prediction_url).then(onPredictionSuccess, onPredictionError);
			// PADataTrain.save({projectName: project_name}, onPredictionSuccess, onPredictionError);
		}

		vm.uploadFile = function(project_id){
			var file = $scope.myFile;
			console.log('file is ' );
			console.dir(file);
			var uploadUrl = "api/pushfile/" + project_id;
			fileUpload.uploadFileToUrl(file, uploadUrl);
		};

		vm.setImage = function ($file, user) {
			user = $file;
		};
		// vm.setImage = function ($file, user) {
		//     if ($file && $file.$error === 'pattern') {
		//         return;
		//     }
		//     if ($file) {
		//         DataUtils.toBase64($file, function(base64Data) {
		//             $scope.$apply(function() {
		//                 user.image = base64Data;
		//                 user.imageContentType = $file.type;
		//             });
		//         });
		//     }
		// };
		

		$scope.page_data1 = {
			"company_name" : "Telecom Company",
			"status" : "Active",
			"created_by" : "Sudarshan Shenvi",
			"messages" : "162",
			"client" : "Telecom Company",
			"version" : "v1.4.2",
			"last_updated" : "16.08.2014 12:15:57",
			"created" : "10.07.2014 23:36:57",
			"completed_progress" : "60",
			"paticipants" : [
				"img/a1.jpg",
				"img/a2.jpg",
				"img/a3.jpg",
				"img/a4.jpg",
				"img/a5.jpg",
			],
			"folder_list" : [
				"Project Files",
				"Data Load",
				"Results",
				"Testing",
				"Upgrades",
				"Omkar",
			],
			"tag_list" : [
				"Family",
				"Work",
				"Home",
				"Children",
				"Holidays",
				"Music",
				"Photography",
				"Film",
			],
			"project_description" : {
				"image" : "img/zender_logo.png",
				"text" : "There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look              even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing",
				"priority" : "Low",
				"tag_list" : [
					"North America",
					"Telecom",
					"Predictive Analytics",
					"Variations",
				],
				"files_list" : [
					{
						"fa_icon" : "fa-file",
						"path" : "Project_document.docx",
					},
					{
						"fa_icon" : "fa-file-picture-o",
						"path" : "Logo_zender_company.jpg",
					},
					{
						"fa_icon" : "fa-stack-exchange",
						"path" : "Email_from_Alex.mln",
					},
					{
						"fa_icon" : "fa-file",
						"path" : "Contract_20_11_2014.docx",
					},
				],
			}
			// "" : "",
		}
		$scope.get_file_list = function(){
			service_file_list.fetch_full_list()
			.then(
				function on_success(response){
					$scope.file_list = response.data;
				}, 
				function on_error(response){
					console.log(response.statusText);
				}
			);
		};
		// $scope.get_file_list();

		$scope.page_meta_data = {
			"has_header" : true,
			"page_header_title" : "Project",
			// "breadcrumb_data" : [
			//     {
			//         "link" : "index.html",
			//         "label" : "Home",
			//         "class" : "",
			//         "is_active" : false
			//     },
			//     {
			//         "link" : "/",
			//         "label" : "Platform",
			//         "class" : "",
			//         "is_active" : false
			//     },
			//     {
			//         "link" : "/",
			//         "label" : "Project Detail",
			//         "class" : "active",
			//         "is_active" : true
			//     }
			// ]

		};
	}
})();
