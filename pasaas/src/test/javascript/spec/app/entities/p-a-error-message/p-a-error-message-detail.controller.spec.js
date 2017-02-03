'use strict';

describe('Controller Tests', function() {

    describe('PAErrorMessage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPAErrorMessage, MockPAOrganization, MockPAProject;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPAErrorMessage = jasmine.createSpy('MockPAErrorMessage');
            MockPAOrganization = jasmine.createSpy('MockPAOrganization');
            MockPAProject = jasmine.createSpy('MockPAProject');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PAErrorMessage': MockPAErrorMessage,
                'PAOrganization': MockPAOrganization,
                'PAProject': MockPAProject
            };
            createController = function() {
                $injector.get('$controller')("PAErrorMessageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pasaasApp:pAErrorMessageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
