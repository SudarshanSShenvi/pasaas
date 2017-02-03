'use strict';

describe('Controller Tests', function() {

    describe('PADataConnector Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPADataConnector, MockPAOrganization, MockPAProject;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPADataConnector = jasmine.createSpy('MockPADataConnector');
            MockPAOrganization = jasmine.createSpy('MockPAOrganization');
            MockPAProject = jasmine.createSpy('MockPAProject');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PADataConnector': MockPADataConnector,
                'PAOrganization': MockPAOrganization,
                'PAProject': MockPAProject
            };
            createController = function() {
                $injector.get('$controller')("PADataConnectorDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pasaasApp:pADataConnectorUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
