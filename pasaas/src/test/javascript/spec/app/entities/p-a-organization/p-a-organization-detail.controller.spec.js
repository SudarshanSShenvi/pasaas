'use strict';

describe('Controller Tests', function() {

    describe('PAOrganization Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPAOrganization, MockPAProject, MockPABusinessPlan, MockPAReliabilityScore, MockPAReliabilityConf, MockPAPredictionScore, MockPASaxCodeTmp, MockPASaxCode, MockPAAlarmActuality, MockPAPrediction, MockPAReport, MockPAAccPrecision, MockPAFileUpload, MockPAPMTRequest, MockPADataConnector, MockPAScheduler, MockPASchedulerInterval, MockPAAlarmRCA, MockPANEDetails;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPAOrganization = jasmine.createSpy('MockPAOrganization');
            MockPAProject = jasmine.createSpy('MockPAProject');
            MockPABusinessPlan = jasmine.createSpy('MockPABusinessPlan');
            MockPAReliabilityScore = jasmine.createSpy('MockPAReliabilityScore');
            MockPAReliabilityConf = jasmine.createSpy('MockPAReliabilityConf');
            MockPAPredictionScore = jasmine.createSpy('MockPAPredictionScore');
            MockPASaxCodeTmp = jasmine.createSpy('MockPASaxCodeTmp');
            MockPASaxCode = jasmine.createSpy('MockPASaxCode');
            MockPAAlarmActuality = jasmine.createSpy('MockPAAlarmActuality');
            MockPAPrediction = jasmine.createSpy('MockPAPrediction');
            MockPAReport = jasmine.createSpy('MockPAReport');
            MockPAAccPrecision = jasmine.createSpy('MockPAAccPrecision');
            MockPAFileUpload = jasmine.createSpy('MockPAFileUpload');
            MockPAPMTRequest = jasmine.createSpy('MockPAPMTRequest');
            MockPADataConnector = jasmine.createSpy('MockPADataConnector');
            MockPAScheduler = jasmine.createSpy('MockPAScheduler');
            MockPASchedulerInterval = jasmine.createSpy('MockPASchedulerInterval');
            MockPAAlarmRCA = jasmine.createSpy('MockPAAlarmRCA');
            MockPANEDetails = jasmine.createSpy('MockPANEDetails');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PAOrganization': MockPAOrganization,
                'PAProject': MockPAProject,
                'PABusinessPlan': MockPABusinessPlan,
                'PAReliabilityScore': MockPAReliabilityScore,
                'PAReliabilityConf': MockPAReliabilityConf,
                'PAPredictionScore': MockPAPredictionScore,
                'PASaxCodeTmp': MockPASaxCodeTmp,
                'PASaxCode': MockPASaxCode,
                'PAAlarmActuality': MockPAAlarmActuality,
                'PAPrediction': MockPAPrediction,
                'PAReport': MockPAReport,
                'PAAccPrecision': MockPAAccPrecision,
                'PAFileUpload': MockPAFileUpload,
                'PAPMTRequest': MockPAPMTRequest,
                'PADataConnector': MockPADataConnector,
                'PAScheduler': MockPAScheduler,
                'PASchedulerInterval': MockPASchedulerInterval,
                'PAAlarmRCA': MockPAAlarmRCA,
                'PANEDetails': MockPANEDetails
            };
            createController = function() {
                $injector.get('$controller')("PAOrganizationDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pasaasApp:pAOrganizationUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
