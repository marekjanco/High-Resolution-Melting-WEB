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
    }).when('/admin/refCurves/create', {
        templateUrl: 'html/auth/admin.create.html',
        controller: 'CreateController as createController'
    }).when('/admin/refCurves/update', {
        templateUrl: 'html/auth/admin.update.html',
        controller: 'UpdateController as updateController'
    }).otherwise({
        redirectTo: '/'
    });

    $locationProvider.html5Mode(true);
});

