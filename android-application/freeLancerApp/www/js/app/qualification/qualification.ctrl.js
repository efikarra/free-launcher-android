 angular.module('e-lancer.qualification')
.controller('QualificationsBoxCtrl', function ($scope, $state, QualificationService) {
      $scope.qualifications = [];
        $scope.qualifications = QualificationService.list();

        $scope.compareFn = function (obj1, obj2) {
            return obj1.qualId === obj2.qualId;
        };
    })
 .controller('QualificationsListCtrl', function ($scope, QualificationService) {
     $scope.isQualificationsListShown=false;
        $scope.toggleQualifications = function () {
             $scope.isQualificationsListShown=!$scope.isQualificationsListShown;
        };
    });;