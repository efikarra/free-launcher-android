angular.module('e-lancer')
    .controller('AppCtrl', function ($scope, $state, $ionicPopup, $rootScope, AuthService, AUTH_EVENTS, $cordovaPush,$ionicHistory) {

        // With the new view caching in Ionic, Controllers are only called
        // when they are recreated or on app start, instead of every page change.
        // To listen for when this page is active (for example, to refresh data),
        // listen for the $ionicView.enter event:
         $scope.$on('$ionicView.enter', function (e) {
             $scope.username = AuthService.username();
             $scope.role = AuthService.role();
             $scope.userId = AuthService.userId();
             console.log($scope.userId);
         });
        $scope.$on('$ionicView.afterLeave', function (e) {
            $ionicHistory.clearCache();
         });
        $scope.logout = function () {
            AuthService.logout();
            $scope.setCurrentUsername(AuthService.username);
            $scope.setCurrentUserId(AuthService.userId());
            $scope.setCurrentUserRole(AuthService.role());
            $ionicHistory.clearCache();
	        $ionicHistory.clearHistory();
            $state.go('login');
        };
      

        $scope.$on(AUTH_EVENTS.notAuthenticated, function (event) {
            AuthService.logout();
            $state.go('login');
            var alertPopup = $ionicPopup.alert({
                title: 'Session Lost!',
                template: 'Sorry, You have to login again.'
            });
        });

        $scope.setCurrentUsername = function (name) {
            $scope.username = name;
        };
        $scope.setCurrentUserId = function (userId) {
            $scope.userId = userId;
        };
        $scope.setCurrentUserRole = function (role) {
            $scope.role = role;
        };
    });