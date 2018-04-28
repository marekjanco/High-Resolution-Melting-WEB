'use strict';

angular.module('hrm').config(function ($httpProvider, $routeProvider, $locationProvider) {

    $routeProvider.when('/', {
        templateUrl: 'html/home.html',
        controller: 'HomeController as homeController'
    }).when('/admin/refCurves', {
        templateUrl: 'html/auth/admin.html',
        controller: 'AdminController as adminController'
    }).when('/login', {
        templateUrl: 'html/login.html',
        controller: 'LoginController as loginController'
    }).otherwise({
        redirectTo: '/'
    });

    $locationProvider.html5Mode(true);
});

