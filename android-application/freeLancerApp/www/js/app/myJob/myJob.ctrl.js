angular.module("e-lancer.myJob")
    .controller('MyJobsCtrl', function ($scope, ExpertService) {
        $scope.myJobs = [];
        $scope.myJobs = ExpertService.projects({}, {
            expId: $scope.userId
        });
    })
    .controller('MyJobCtrl', function ($scope, $stateParams, ProjectService, $ionicModal, $ionicPopup, _) {
        $scope.firstEval = true;
        $scope.newEmployerEvaluation = {};
        $scope.newEmployerEvaluation.user = {};
        $scope.newEmployerEvaluation.project = {};
        $scope.newEmployerEvaluation.user.userId = $scope.userId;
        $scope.newEmployerEvaluation.user.username = $scope.username;

        $scope.myJob = _.findWhere($scope.myJobs, {
            projId: parseInt($stateParams.myJobId)
        });
        $scope.newEmployerEvaluation.project.projId = parseInt($stateParams.myJobId.projId);
        $scope.employerEvaluations = ProjectService.getEmployerEvaluations({}, {
            id1: $scope.myJob.projId
        }, function () {
            console.log($scope.employerEvaluations);
            $scope.myEvaluation = _.filter($scope.employerEvaluations, function (obj) {
                return !_.isEmpty(_.findWhere(obj, {
                    userId: parseInt($scope.userId)
                }));
            });
            if (_.isEmpty($scope.myEvaluation)) {
                $scope.firstEval = true;
            } else {
                console.log("yeah");
                $scope.firstEval = false;
            }
        });
        $scope.expertEvaluations = ProjectService.getExpertEvaluations({}, {
            id1: $scope.myJob.projId
        }, function () {
            console.log($scope.expertEvaluations);
        });
        $scope.editEvalFunc = function (evaluation) {
            $scope.editEvaluation = {};
            $scope.editEvaluation = evaluation;
            $ionicModal.fromTemplateUrl('templates/editEvaluation.html', {
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
        $scope.deleteEvalFunc = function (evaluation) {
            console.log(evaluation);
            var confirmPopup = $ionicPopup.confirm({
                title: 'Delete evaluation',
                template: 'Are you sure you want to permanently delete your evaluation?'
            });
            confirmPopup.then(function (res) {
                if (res) {
                    ProjectService.deleteEmployerEvaluation({
                        userId: evaluation.user.userId
                    }, {
                        id1: $scope.myJob.projId,
                    }, function () {
                        console.log($scope.employerEvaluations);
                        console.log(evaluation);
                        $scope.employerEvaluations = _.reject($scope.employerEvaluations, function (obj) {
                            return obj.evalId === evaluation.evalId;
                        });
                        console.log($scope.employerEvaluations);
                        $scope.firstEval = true;
                    });
                }

            });
        };
        $scope.saveEvaluation = function () {
            ProjectService.saveEmployerEvaluation({
                id1: $scope.myJob.projId
            }, $scope.newEmployerEvaluation, function () {
                $scope.employerEvaluations.push($scope.newEmployerEvaluation);
                $scope.newEmployerEvaluation = {};
                $scope.newEmployerEvaluation.user = {};
                $scope.newEmployerEvaluation.project = {};
                $scope.newEmployerEvaluation.user.userId = $scope.userId;
                $scope.newEmployerEvaluation.user.username = $scope.username;
                $scope.newEmployerEvaluation.project.projId = parseInt($stateParams.myJobId.projId);
                $scope.firstEval = false;
            }, function (data) {
                $scope.errorData = data.data;
                console.log(data);
                var alertPopup = $ionicPopup.alert({
                    title: 'Evaluation submit failed!',
                    templateUrl: 'templates/errorsPopup.html',
                    scope: $scope
                });
            });
        };
    })

.controller('EditEmployerEvalCtrl', function ($scope, $stateParams, ProjectService, _) {
    $scope.updateEvaluation = function () {
        console.log($scope.editEvaluation);
        ProjectService.updateEmployerEvaluation({
            id1: $scope.myJob.projId
        }, $scope.editEvaluation, function () {
            $scope.closeModal();
        }, function (data) {
            $scope.errorData = data.data;
            console.log(data);
            var alertPopup = $ionicPopup.alert({
                title: 'Evaluation edit failed!',
                templateUrl: 'templates/errorsPopup.html',
                scope: $scope
            });
        });
    };


});