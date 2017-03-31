(function () {	'use strict';

angular
.module('pasaasApp')
.controller('controller_pdf38', ['$scope', controller_pdf38])
;
function controller_pdf38($scope){
	$scope.list_status = [
		{
            "back_color" : "Green",
            "front_color" : "white",
            "label" : "Active"
        },{
            "back_color" : "Gray",
            "front_color" : "white",
            "label" : "Inactive"
        },{
            "back_color" : "Red",
			"front_color" : "white",
			"label" : "Deleted"
		}
	];
    
    $scope.page_meta_data = {
        "has_header" : true,
        "page_header_title" : "BDA Alarms",
        "breadcrumb_data" : [
            {
                "link" : "index.html",
                "label" : "Home",
                "class" : "",
                "is_active" : false
            },
            {
                "link" : "/",
                "label" : "Platform",
                "class" : "",
                "is_active" : false
            },
            {
                "link" : "/",
                "label" : "BDA",
                "class" : "",
                "is_active" : false
            },
            {
                "link" : "/",
                "label" : " Alarms  ",
                "class" : "",
                "is_active" : false
            },
            {
                "link" : "/",
                "label" : "Alarms RC - Create",
                "class" : "active",
                "is_active" : true
            }
        ]

    };

    // $scope.selected_status = {
    //     "back_color" : "Green",
    //     "front_color" : "white",
    //     "label" : "Active"
    // }
}

})();


angular
.module('pasaasApp')
.directive('optionsClass', function ($parse) {
  return {
    require: 'select',
    link: function(scope, elem, attrs, ngSelect) {
      // get the source for the items array that populates the select.
      var optionsSourceStr = attrs.ngOptions.split(' ').pop(),
      // use $parse to get a function from the options-class attribute
      // that you can use to evaluate later.
          getOptionsClass = $parse(attrs.optionsClass);
          
      scope.$watch(optionsSourceStr, function(items) {
        // when the options source changes loop through its items.
        angular.forEach(items, function(item, index) {
          // evaluate against the item to get a mapping object for
          // for your classes.
          var classes = getOptionsClass(item),
          // also get the option you're going to need. This can be found
          // by looking for the option with the appropriate index in the
          // value attribute.
              option = elem.find('option[value=' + index + ']');
              
          // now loop through the key/value pairs in the mapping object
          // and apply the classes that evaluated to be truthy.
          angular.forEach(classes, function(add, className) {
            if(add) {
              angular.element(option).addClass(className);
            }
          });
        });
      });
    }
  };
});



angular
.module('pasaasApp')
.directive('optionClassExpr', function ($compile, $parse) {
    var NG_OPTIONS_REGEXP = /^\s*([\s\S]+?)(?:\s+as\s+([\s\S]+?))?(?:\s+group\s+by\s+([\s\S]+?))?\s+for\s+(?:([\$\w][\$\w]*)|(?:\(\s*([\$\w][\$\w]*)\s*,\s*([\$\w][\$\w]*)\s*\)))\s+in\s+([\s\S]+?)(?:\s+track\s+by\s+([\s\S]+?))?$/;

    return {
        restrict: 'A',
        link: function optionClassExprPostLink(scope, elem, attrs) {
            var optionsExp = attrs.ngOptions;
            if (!optionsExp) return;
            
            var match = optionsExp.match(NG_OPTIONS_REGEXP);
            if (!match) return;
            
            var values = match[7];

            scope.$watchCollection(function () {
                return elem.children();
            }, function (newValue) {
                angular.forEach(newValue, function (child) {
                    var child = angular.element(child);
                    var val   = child.val();
                    if (val) {
                        child.attr('ng-class', values + '[' + val + '].' +
                                               attrs.optionClassExpr);
                        $compile(child)(scope);
                    }
                });
            });
        }
    };
});

angular
.module('pasaasApp')
.directive('optionClassExprStatic', function ($compile, $parse) {
    var NG_OPTIONS_REGEXP = /^\s*([\s\S]+?)(?:\s+as\s+([\s\S]+?))?(?:\s+group\s+by\s+([\s\S]+?))?\s+for\s+(?:([\$\w][\$\w]*)|(?:\(\s*([\$\w][\$\w]*)\s*,\s*([\$\w][\$\w]*)\s*\)))\s+in\s+([\s\S]+?)(?:\s+track\s+by\s+([\s\S]+?))?$/;

    return {
        restrict: 'A',
        link: function optionClassExprStaticPostLink(scope, elem, attrs) {
            var optionsExp = attrs.ngOptions;
            if (!optionsExp) return;
            
            var match = optionsExp.match(NG_OPTIONS_REGEXP);
            if (!match) return;
            
            var values    = scope.$eval(match[7]);
            var classExpr = $parse(attrs.optionClassExprStatic);

            scope.$watchCollection(function () {
                return elem.children();
            }, function (newValue) {
                angular.forEach(newValue, function (child) {
                    var child = angular.element(child);
                    var val   = child.val();
                    if (val) {
                        var cls = classExpr(values[val]);
                        child.addClass(cls);
                        $compile(child)(scope);
                    }
                });
            });
        }
    };
});
