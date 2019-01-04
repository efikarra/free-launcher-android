angular.module('e-lancer.project')
    .controller('NewProjectCtrl', function ($scope,ProjectService,$state,$ionicPopup) {
        $scope.restrictedLocation = {};
        $scope.restrictedLocation.restrictionChoice = {};
        $scope.newProject = {};
        $scope.newProject.qualifications = [];
        $scope.newProject.employer = {};
        $scope.newProject.employer.userId = $scope.userId;
         $scope.saveProject = function () {

            if (!_.isEmpty( $scope.restrictedLocation.restrictionChoice)) {
                
                $scope.newProject.country =  $scope.restrictedLocation.restrictionChoice.country;
                $scope.newProject.location =  $scope.restrictedLocation.restrictionChoice.location;
            };
            ProjectService.save($scope.newProject).$promise.then(function () {
                $state.go('app.tabs.home');
            }, function (err) {
                var alertPopup = $ionicPopup.alert({
                    title: 'Job submition failed!',
                    template: 'Please check your input fields!'
                });
            });
        };
     $scope.cancelRestriction = function () {
         $scope.restrictedLocation.restrictionChoice={};
          var okPopup = $ionicPopup.alert({
                    template: 'Locationrestriction cancelled!'
                });
     };
    })
    .controller('NewProjectFormCtrl', function ($scope, $stateParams, $ionicPopup, $ionicModal, _, $state) {

        var now = new Date();
        var tomorrow = new Date(now.getTime() + (24 * 60 * 60 * 1000));
        $scope.newProject.endDate = tomorrow.getTime();
        $scope.newProject.timeLimit = tomorrow.getTime();

        var datePickerEndDateCallback = function (val) {
            if (typeof (val) === 'undefined') {
                console.log('No date selected');
            } else {
                $scope.newProject.endDate = val.getTime();
                $scope.datepickerEndDate.inputDate = val;
            }
        };
        var datePickerTimeLimitCallback = function (val) {
            if (typeof (val) === 'undefined') {
            } else {
                $scope.newProject.timeLimit = val.getTime();
                $scope.datepickerTimeLimit.inputDate = val;
            }
        };
        $scope.datepickerEndDate = {
            titleLabel: 'Choose estimated date', //Optional
            todayLabel: 'Today', //Optional
            closeLabel: 'Close', //Optional
            setLabel: 'Set', //Optional
            errorMsgLabel: 'Please select time.', //Optional
            setButtonType: 'button-assertive', //Optional
            mondayFirst: true, //Optional
            templateType: 'popup', //Optional
            modalHeaderColor: 'bar-positive', //Optional
            modalFooterColor: 'bar-positive', //Optional
            from: tomorrow,
            inputDate: tomorrow,
            callback: function (val) { //Mandatory
                datePickerEndDateCallback(val);
            }
        };
        $scope.datepickerTimeLimit = {
            titleLabel: 'Choose estimated date', //Optional
            todayLabel: 'Today', //Optional
            closeLabel: 'Close', //Optional
            setLabel: 'Set', //Optional
            errorMsgLabel: 'Please select time.', //Optional
            setButtonType: 'button-assertive', //Optional
            mondayFirst: true, //Optional
            templateType: 'popup', //Optional
            modalHeaderColor: 'bar-positive', //Optional
            modalFooterColor: 'bar-positive', //Optional
            from: tomorrow,
            inputDate: tomorrow,
            callback: function (val) { //Mandatory
                datePickerTimeLimitCallback(val);
            }
        };

        $scope.showQualifications = false;
        $scope.hideQualifications = function () {
            $scope.showQualifications = !$scope.showQualifications;
        };

    });
    