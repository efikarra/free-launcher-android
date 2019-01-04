// Ionic Starter App

// angular.module is a global place for creating, registering and retrieving Angular modules
// 'starter' is the name of this angular module example (also set in a <body> attribute in index.html)
// the 2nd parameter is an array of 'requires'
// 'starter.controllers' is found in controllers.js
angular.module('e-lancer', ['ionic', 'ngCordova', 'e-lancer.bid', 'e-lancer.employer', 'e-lancer.expert', 'e-lancer.login', 'e-lancer.project', 'e-lancer.qualification', 'e-lancer.register', 'e-lancer.search', 'e-lancer.myJob', 'ngResource', 'ngMessages', 'ngMatch', 'ionic-datepicker', 'checklist-model', 'underscore', 'jett.ionic.filter.bar', 'e-lancer-notification', 'e-lancer.user', 'e-lancer.map', 'e-lancer.myProject'])

.run(function ($ionicPlatform, $rootScope, $state, AuthService) {
    $ionicPlatform.ready(function () {
        // Hide the accessory bar by default (remove this to show the accessory bar above the keyboard
        // for form inputs)
        if (window.cordova && window.cordova.plugins.Keyboard) {
            cordova.plugins.Keyboard.hideKeyboardAccessoryBar(true);
            cordova.plugins.Keyboard.disableScroll(true);

        }
        if (window.StatusBar) {
            // org.apache.cordova.statusbar required
            StatusBar.styleDefault();
        }

        $rootScope.$on('$stateChangeStart', function (event, next, nextParams, fromState) {
            if (!AuthService.isAuthenticated()) {
                if (next.name !== 'login' && next.name !== 'register') {
                    event.preventDefault();
                    $state.go('login');
                }
            }
        });
        $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState) {
            $state.previous = fromState;
        });
        
        window.plugin.notification.local.onadd = function (id, state, json) {
            var notification = {
                id: id,
                state: state,
                json: json
            };
            $timeout(function() {
                $rootScope.$broadcast("$cordovaLocalNotification:added", notification);
            });
        };

         


    });
});