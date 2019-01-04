angular.module('e-lancer.myProject')
    .controller('MyProjectsCtrl', function ($scope, EmployerService, ProjectService, $ionicModal, $ionicPopup, _) {
        $scope.groups = {};
        $scope.myProjects = [];
        $scope.myProjects = EmployerService.projects({}, {
            id1: $scope.userId
        }, function () {
            $scope.groups = _.groupBy($scope.myProjects, function (obj) {
                return obj.closed
                $scope.groups2.groups=_.extend($scope.groups2.groups,$scope.groups)
            });

        });
        $scope.deleteMyProjectFunc = function (myOpenProject) {
            var confirmPopup = $ionicPopup.confirm({
                title: 'Delete project',
                template: 'Are you sure you want to permanently delete this project?'
            });
            confirmPopup.then(function (res) {
                if (res) {
                    ProjectService.delete({}, {
                        id1: myOpenProject.projId
                    }, function () {
                        $scope.myProjects = _.reject($scope.myProjects, function (obj) {
                            console.log(obj);
                            console.log(myOpenProject);
                            return (_.isEqual(obj, myOpenProject));
                        });
                        $scope.groups = _.groupBy($scope.myProjects, function (obj) {
                            return obj.closed
                        });

                    });
                }
            });
        };
        $scope.findMyProjectBidsFunc = function (myOpenProject) {
            $scope.currentMyProject = {};
            _.extend($scope.currentMyProject, myOpenProject);
            $ionicModal.fromTemplateUrl('templates/myProject/myProjectBids.html', {
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

    })
    .controller('MyProjectEditCtrl', function ($scope, ProjectService, $state, $ionicPopup, $stateParams, _) {
        $scope.restrictedLocation = {};
        $scope.restrictedLocation.restrictionChoice = {};
        $scope.editProject = {};
        console.log("yeah " + $stateParams.myProjectId);
        $scope.myProject = _.findWhere($scope.myProjects, {
            projId: parseInt($stateParams.myProjectId)
        });
        $scope.editProject = _.clone($scope.myProject);
        $scope.restrictedLocation.restrictionChoice.country = $scope.editProject.country;
        $scope.restrictedLocation.restrictionChoice.location = $scope.editProject.location;

        $scope.removeRestriction = function () {
            $scope.restrictedLocation.restrictionChoice = {};
            $scope.restrictedLocation.restrictionChoice.country = undefined;
            $scope.restrictedLocation.restrictionChoice.location = undefined;
            $scope.editProject.location = undefined;
            $scope.editProject.country = undefined;
            var okPopup = $ionicPopup.alert({
                template: 'Location restriction deleted!'
            });
        };
    })
    .controller('MyProjectEditFormCtrl', function ($scope, $state, $stateParams, ProjectService, $ionicPopup, _, $ionicHistory) {

        $scope.showQualifications = false;
        var now = new Date();
        var tomorrow = new Date(now.getTime() + (24 * 60 * 60 * 1000));

        $scope.hideQualifications = function () {
            $scope.showQualifications = !$scope.showQualifications;
        };
        $scope.editMyProject = function () {

            if (!_.isEqual($scope.myProject, $scope.editProject)) {
                console.log($scope.restrictedLocation.restrictionChoice);
                if (!_.isEmpty($scope.restrictedLocation.restrictionChoice)) {
                    $scope.editProject.country = $scope.restrictedLocation.restrictionChoice.country;
                    $scope.editProject.location = $scope.restrictedLocation.restrictionChoice.location;
                };
                ProjectService.update($scope.editProject, function () {
                    $scope.myProject = _.extend($scope.myProject, $scope.editProject);
                    console.log($scope.myProject);
                    console.log($scope.myProjects);
                    $scope.groups = _.groupBy($scope.myProjects, function (obj) {
                        return obj.closed
                    });
                    $state.transitionTo('app.tabs.myProjects.list', $state.$current.params, {
                        reload: true,
                        inherit: false,
                        notify: true
                    });


                }, function (data) {
                    $scope.errorData = data.data;
                    console.log(data);
                    var alertPopup = $ionicPopup.alert({
                        title: 'Project edit failed!',
                        templateUrl: 'templates/errorsPopup.html',
                        scope: $scope
                    });
                });
            } else {
                $state.go("app.tabs.myProjects.list");
            }
        };
        var datePickerEndDateCallback = function (val) {
            if (typeof (val) === 'undefined') {
                console.log('No date selected');
            } else {
                $scope.editProject.endDate = val.getTime();
                $scope.datepickerEndDate.inputDate = val;
            }
        };
        var datePickerTimeLimitCallback = function (val) {
            if (typeof (val) === 'undefined') {
                console.log('No date selected');
            } else {
                $scope.editProject.timeLimit = val.getTime();
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
            inputDate: new Date($scope.editProject.endDate),
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
            inputDate: new Date($scope.editProject.timeLimit),
            callback: function (val) { //Mandatory
                datePickerTimeLimitCallback(val);
            }
        };
        $scope.$on('$ionicView.afterLeave', function (e) {
            $ionicHistory.clearCache();
        });
    })
    .controller('MyProjectDetailsCtrl', function ($scope, $ionicPopup, $ionicModal, ProjectService, $stateParams, _) {
        $scope.empEvaluations = [];
        $scope.projectExperts = [];

        $scope.empEvaluations = ProjectService.getEmployerEvaluations({}, {
            id1: $stateParams.myProjectId

        });
        $scope.projectExperts = ProjectService.getExperts({}, {
            id1: $stateParams.myProjectId
        });
        $scope.myClosedProject = _.findWhere($scope.myProjects, {
            projId: parseInt($stateParams.myProjectId)
        });
        $scope.evaluateExpert = function (projectExpert) {
            $scope.currentExpert = {};
            $scope.currentExpert = projectExpert;
            $ionicModal.fromTemplateUrl('templates/expertEvaluation.html', {
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
    })
    .controller('EvaluateExpertCtrl', function ($scope, $ionicPopup, ProjectService, $stateParams, _) {
        $scope.newExpertEvaluation = {};
        $scope.newExpertEvaluation.user = $scope.currentExpert;
        $scope.newExpertEvaluation.project = $scope.myClosedProject;
        $scope.firstExpertEval = false;
        $scope.editExpertEval = false;

        $scope.myExpertEvaluations = ProjectService.getExpertEvaluations({
            expId: $scope.currentExpert.userId
        }, {
            id1: $scope.myClosedProject.projId
        }, function () {
            console.log($scope.myExpertEvaluations);
            if (_.isEmpty($scope.myExpertEvaluations)) {
                $scope.firstExpertEval = true;
            } else {
                $scope.myExpertEvaluation = $scope.myExpertEvaluations[0];
                $scope.firstExpertEval = false;
            }
        });
        $scope.editExpertEvaluationFun = function () {
            $scope.editExpertEval = true;
            $scope.newExpertEvaluation = $scope.myExpertEvaluation;
        };
        $scope.cancelEditExpertEvaluationFun = function () {
            $scope.editExpertEval = false;
            $scope.newExpertEvaluation = {};
            $scope.newExpertEvaluation.user = $scope.currentExpert;
            $scope.newExpertEvaluation.project = $scope.myClosedProject;
        };
        $scope.deleteExpertEvaluationFun = function () {
            ProjectService.deleteExpertEvaluation({
                userId:$scope.currentExpert.userId
            }, {
                id1: $scope.myClosedProject.projId
            }, function () {
                $scope.closeModal();
            });
        };
        $scope.saveExpertEvaluation = function () {
            if (!$scope.editExpertEval) {
                ProjectService.saveExpertEvaluation({
                    id1: $scope.myClosedProject.projId
                }, $scope.newExpertEvaluation, function () {
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
            } else {
                ProjectService.updateExpertEvaluation({
                    id1: $scope.myClosedProject.projId
                }, $scope.newExpertEvaluation, function () {
                    $scope.closeModal();
                });
            }
        };

        $scope.cancelEval = function () {
            $scope.closeModal();
        };
    })
    .controller('MyProjectBidsCtrl', function ($scope, $ionicPopup, ProjectService, $state, _, NotificationService,$stateParams) {
        $scope.myProjectBids = [];
        $scope.myProjectBids = ProjectService.bids({}, {
            id1: $scope.currentMyProject.projId
        });

        $scope.chosenExperts = [];
        $scope.bids = {};
        $scope.bids.chosenBids = {};
        $scope.closeProject = function () {
            if (!_.isEmpty($scope.bids.chosenBids)) {
                var myPopup = $ionicPopup.confirm({
                    title: 'Are you sure you want to close this project?',
                    templateUrl: 'templates/chosenBids.html',
                    scope: $scope
                });
                myPopup.then(function (res) {
                    if (res) {
                        $scope.chosenExperts = _.pluck($scope.bids.chosenBids, 'expert');
                        console.log($scope.chosenExperts);
                        ProjectService.saveExperts({
                            id1: $scope.currentMyProject.projId
                        }, $scope.chosenExperts, function () {
                            ProjectService.updateIsClosed({
                                id1: $scope.currentMyProject.projId
                            }, true, function () {
                                $scope.currentMyProject.closed = true;
                               
                               $scope.groups = _.groupBy($scope.myProjects, function (obj) {
                                    return obj.closed
                                });
                            
                                //push notification to cometition winners
                                var n1 = {};
                                n1.message = "You are just hired for project with title: " + $scope.currentMyProject.title;
                                n1.title = "Win competition!";
                                n1.badge = 1;
                                n1.userIdsToSend = [];
                                n1.userIdsToSend = _.pluck($scope.chosenExperts, 'userId');
                                console.log(n1.userIdsToSend);
                                NotificationService.sendNotification(n1);
                                //push notification for cometition closing to all bidders
                                var n2 = {};
                                n2.message = "You are just hired for project with title: " + $scope.currentMyProject.title;
                                n2.title = "Win competition!";
                                n2.badge = 2;
                                n2.userIdsToSend = [];
                                var bidExperts = _.pluck($scope.bids.chosenBids, 'expert');
                                n2.userIdsToSend = _.pluck(bidExperts, 'userId');
                                console.log(n2.userIdsToSend);
                                NotificationService.sendNotification(n2);
                               
                                $scope.closeModal();
                                location.reload();
                                

                            })
                        });

                    }
                });
            } else {
                var confirmPopup = $ionicPopup.alert({
                    title: 'Warning',
                    template: 'You should choose one or more bids in order to close your job competition'
                });
            }
        };
        $scope.compareFn = function (obj1, obj2) {
            return obj1.bidId === obj2.bidId;
        };

    });