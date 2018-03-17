'use strict';

angular.module('hrm').config(function ($httpProvider, $routeProvider, $locationProvider) {

    $routeProvider.when('/', {
        templateUrl: 'html/home.html',
        controller: 'HomeController as homeController'
    }).when('/admin/datasets', {
        templateUrl: 'html/auth/admin.html',
        controller: 'AdminController as adminController'
    }).when('/login', {
        templateUrl: 'html/login.html',
        controller: 'LoginController as loginController'
    }).when('/admin/datasets/create', {
        templateUrl: 'html/auth/admin.create.html',
        controller: 'CreateController as createController'
    }).when('/admin/datasets/update', {
        templateUrl: 'html/auth/admin.update.html',
        controller: 'UpdateController as updateController'
    }).otherwise({
        redirectTo: '/'
    });

    $locationProvider.html5Mode(true);
});

