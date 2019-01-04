angular.module('e-lancer.user')
    .controller('ProfileCtrl', function ($scope, $ionicPopup, $ionicModal, ExpertService, EmployerService, _, AuthService, $state) {
        $scope.editSelected = false;
        $scope.editUser = {};
        $scope.oldUser = {};
        $scope.editUser.qualifications = [];
        $scope.errorData = [];
        $scope.rating;
        if ($scope.role == "EMPLOYEE_ROLE") {
            $scope.editUser = ExpertService.get({}, {
                expId: $scope.userId
            }, function () {
                $scope.oldUser = _.clone($scope.editUser);
                if ($scope.editUser.rating == null) {
                    $scope.rating = "no evaluations for you have been made yet"
                } else {
                    $scope.rating = $scope.editUser.rating;
                }
            });
        } else {
            $scope.editUser = EmployerService.get({}, {
                id1: $scope.userId
            }, function () {
                $scope.oldUser = _.clone($scope.editUser);
                if ($scope.editUser.rating == null) {
                    $scope.rating = "no evaluations for you have been made yet"
                } else {
                    $scope.rating = $scope.editUser.rating;
                }
            });
        }
        $scope.enableEdit = function () {
            $scope.editSelected = true;
        };
        $scope.disableEdit = function () {
            $scope.editSelected = false;
        };
        $scope.changePasswordFun = function () {
            $ionicModal.fromTemplateUrl('templates/changePassword.html', {
                scope: $scope,
                animation: 'slide-in-up'
            }).then(function (modal) {
                $scope.modal = modal;
                $scope.modal.show();
            });
            $scope.openModal = function () {
                $scope.modal.show();
            };
            $scope.closeModal = function () {
                $scope.modal.hide();
            };
            //Cleanup the modal when we're done with it!
            $scope.$on('$destroy', function () {
                $scope.modal.remove();
            });
        };
        $scope.editUserChanges = function () {
            console.log($scope.editUser);
            if (!_.isEqual($scope.editUser, $scope.oldUser)) {
                if ($scope.role == "EMPLOYEE_ROLE") {

                    ExpertService.update($scope.editUser).$promise.then(function () {
                        $scope.disableEdit();
                        if ($scope.editUser.username != $scope.oldUser.username) {

                            var alertPopup1 = $ionicPopup.alert({
                                template: "You just changed your username! <br> You will be navigated to login page to login with your new credentials!"
                            });
                            alertPopup1.then(function (res) {
                                AuthService.logout();
                                $state.go('login');
                            });
                        } else {
                            $scope.oldUser = _.clone($scope.editUser);
                        }
                    }, function (data) {
                        $scope.errorData = data.data;
                        var alertPopup2 = $ionicPopup.alert({
                            title: 'Editing user profile failed!',
                            templateUrl: 'templates/errorsPopup.html',
                            scope: $scope
                        });

                    });
                } else {
                    $scope.editUser = _.omit($scope.editUser, "qualifications");
                    EmployerService.update($scope.editUser).$promise.then(function () {
                        $scope.disableEdit();
                        if ($scope.editUser.username != $scope.oldUser.username) {

                            var alertPopup1 = $ionicPopup.alert({
                                templateUrl: "You just changed your username! <br> You will be navigated to login page to login with your new credentials!"
                            });
                            alertPopup1.then(function (res) {
                                AuthService.logout();
                                $state.go('login');
                            });
                        } else {
                            $scope.oldUser = _.clone($scope.editUser);
                        }

                    }, function (data) {
                        $scope.errorData = data.data;
                        var alertPopup = $ionicPopup.alert({
                            title: 'Editing user profile failed!',
                            templateUrl: 'templates/errorsPopup.html',
                            scope: $scope
                        });
                    });
                }
            }
        };
    })
    .controller('ChangePasswordCtrl', function ($scope, $ionicPopup, $ionicModal, $state, UserService, AuthService) {
        $scope.editPassword = {};
        $scope.editPassword.newPassword = "";
        $scope.saveNewPassword = function () {
            UserService.updatePassword({
                userId: $scope.userId
            }, $scope.editPassword.newPassword, function () {
                $scope.closeModal();
                AuthService.logout();
                $state.go('login');
            });
        };
    });