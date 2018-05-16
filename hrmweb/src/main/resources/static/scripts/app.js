'use strict';

angular.module('hrm', ['chart.js', 'ngResource', 'ngRoute', 'ngFileSaver'])
    .run(function ($window, $location, $http, $q, $timeout, $rootScope) {
    });

//interceptors
angular.module('hrm').config(['$httpProvider',
    function ($httpProvider) {
        $httpProvider.interceptors.push('AuthInterceptor');
    }
]);


angular.module('hrm').config(['ChartJsProvider', '$httpProvider', function (ChartJsProvider) {
    // Configure all charts
    ChartJsProvider.setOptions({
        chartColors: [{
            backgroundColor: 'transparent',
            pointBackgroundColor: '#FF5252',
            borderColor: '#FF5252',
            pointBorderColor: '#FF5252',
        },{
            backgroundColor: 'transparent',
            pointBackgroundColor: '#33FFDA',
            borderColor: '#33FFDA',
            pointBorderColor: '#33FFDA',
        }, {
            backgroundColor: 'transparent',
            pointBackgroundColor: '#3339FF',
            borderColor: '#3339FF',
            pointBorderColor: '#3339FF',
        }, {
            backgroundColor: 'transparent',
            pointBackgroundColor: '#FCFF33',
            borderColor: '#FCFF33',
            pointBorderColor: '#FCFF33',
        }, {
            backgroundColor: 'transparent',
            pointBackgroundColor: '#FF33FC',
            borderColor: '#FF33FC',
            pointBorderColor: '#FF33FC',
        }, {
            backgroundColor: 'transparent',
            pointBackgroundColor: '#33FF33',
            borderColor: '#33FF33',
            pointBorderColor: '#33FF33',
        }, {
            backgroundColor: 'transparent',
            pointBackgroundColor: '#FF8A80',
            borderColor: '#FF8A80',
            pointBorderColor: '#FF8A80',
        }, {
            backgroundColor: 'transparent',
            pointBackgroundColor: '#FCFF33',
            borderColor: '#FCFF33',
            pointBorderColor: '#FCFF33',
        },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#333300',
                borderColor: '#333300',
                pointBorderColor: '#333300',
            },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#993333',
                borderColor: '#993333',
                pointBorderColor: '#993333',
            },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#ff9900',
                borderColor: '#ff9900',
                pointBorderColor: '#ff9900',
            },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#cccc00',
                borderColor: '#cccc00',
                pointBorderColor: '#cccc00',
            },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#666699',
                borderColor: '#666699',
                pointBorderColor: '#666699',
            },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#999966',
                borderColor: '#999966',
                pointBorderColor: '#999966',
            },
            {
                backgroundColor: 'transparent',
                pointBackgroundColor: '#FCFF33',
                borderColor: '#FCFF33',
                pointBorderColor: '#FCFF33',
            }
        ],
        labels:{
            display: true
        },
        responsive: true,
    });
}]);