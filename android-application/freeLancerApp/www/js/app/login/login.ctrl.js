angular.module('e-lancer.login')
.controller('LoginCtrl', function ($scope, $state, $ionicPopup, AuthService,GcmPushRegistrationService) {
        $scope.loginData = {};

        $scope.login = function (loginData) {
            AuthService.login(loginData.username, loginData.password).then(function (authenticated) {
  
                $scope.setCurrentUsername(loginData.username);
                $scope.setCurrentUserId(AuthService.userId());
                $scope.setCurrentUserRole(AuthService.role());
                GcmPushRegistrationService.pushRegister($scope.userId);
                $state.go('app.tabs.home');

            }, function (err) {
                var alertPopup = $ionicPopup.alert({
                    title: 'Login failed!',
                    template: 'Please check your credentials!'
                });
            });
        };
        $scope.register = function () {
            $state.go('register');
        };

    });
