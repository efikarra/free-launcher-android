angular.module('e-lancer.register')
    .controller('RegisterCtrl', function ($scope, $state, $ionicPopup, _, QualificationService, ExpertService, EmployerService) {
        $scope.user = {};
        $scope.user.qualifications = [];
        $scope.errorData = [];
        $scope.saveUser = function () {
            //$scope.user.qualifications=$scope.user.qualifications;
            if ($scope.user.role == "EMPLOYEE_ROLE") {

                ExpertService.save($scope.user).$promise.then(function () {
                    $state.go('login');

                }, function (data) {
                    $scope.errorData = data.data;
                    console.log(data);
                    var alertPopup = $ionicPopup.alert({
                        title: 'Registration failed!',
                        templateUrl: 'templates/errorsPopup.html',
                        scope: $scope
                    });
                });
            } else {
                _.omit($scope.user, "qualifications");
                EmployerService.save($scope.user).$promise.then(function () {
                    $state.go('login');

                }, function (data) {
                    $scope.errorData = data.data;
                    var alertPopup = $ionicPopup.alert({
                        title: 'Registration failed!',
                        templateUrl: 'templates/errorsPopup.html',
                        scope: $scope
                    });
                });
            }
        };

    });