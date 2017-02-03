'use strict';

describe('Controller Tests', function() {

    describe('PAAccPrecision Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPAAccPrecision, MockPAProject, MockPAOrganization;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPAAccPrecision = jasmine.createSpy('MockPAAccPrecision');
            MockPAProject = jasmine.createSpy('MockPAProject');
            MockPAOrganization = jasmine.createSpy('MockPAOrganization');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PAAccPrecision': MockPAAccPrecision,
                'PAProject': MockPAProject,
                'PAOrganization': MockPAOrganization
            };
            createController = function() {
                $injector.get('$controller')("PAAccPrecisionDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'pasaasApp:pAAccPrecisionUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
