'use strict';

angular.module('hrm').config(function ($stateProvider, $urlRouterProvider) {

    $stateProvider.state('home', {
        url: '/home',
        views: {
            'main': {
                templateUrl: 'html/home.html',
                controller: 'HomeController as homeController'
            }
        }
    });

    $urlRouterProvider.otherwise('home');
});

angular.module('hrm').config(['ChartJsProvider', function (ChartJsProvider) {
    // Configure all charts
    ChartJsProvider.setOptions({
        chartColors: ['#FF5252', '#3339FF', '#FCFF33', '#FF33FC', '#33FF33', '#33FFDA', '#FF8A80'],
        responsive: true
    });
}]);
