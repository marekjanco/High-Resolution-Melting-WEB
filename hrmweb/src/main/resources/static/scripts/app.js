'use strict';

angular.module('hrm', ['chart.js', 'ngResource', 'ngRoute'])
    .run(function ($window, $location, $http, $q, $timeout, $rootScope) {
    });

//interceptors
angular.module('hrm').config(['$httpProvider',
    function ($httpProvider) {
        $httpProvider.interceptors.push('AuthInterceptor');
    }
]);


angular.module('hrm').config(['ChartJsProvider', '$httpProvider', function (ChartJsProvider, $httpProvider) {
    // Configure all charts
    ChartJsProvider.setOptions({
        chartColors: ['#FF5252', '#3339FF', '#FF33FC', '#33FF33', '#33FFDA', '#FF8A80', '#FCFF33'],
        responsive: true
    });
}]);
/*
angular.module('hrm').directive('fileModel', ['$parse', function ($parse) {
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
*/