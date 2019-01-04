angular.module('e-lancer.search')
    .controller('SearchProjectsCtrl', function ($scope, $state, ProjectService, ExpertService, _, $ionicPopup) {
        console.log("hello from projects search");
        $scope.projects = [];
        if (keyword = "") {
            $scope.searchProjects = function (keyword) {
                $scope.projects = ProjectService.search(function () {
                    if (_.isEmpty($scope.projects)) {
                        $ionicPopup.alert({
                            title: "",
                            template: "No results found!"
                        });
                    }
                });

            };
        } else {
            $scope.searchProjects = function (keyword) {
                $scope.projects = ProjectService.search({
                    keyword: keyword
                }, function () {
                    if (_.isEmpty($scope.projects)) {
                        $ionicPopup.alert({
                            title: "",
                            template: "No results found!"
                        });
                    }
                });

            };
        }
    })

.controller('SearchExpertsCtrl', function ($scope, $state, ProjectService, ExpertService) {
        console.log("hello from expert search");
        $scope.experts = [];
        if (keyword = "") {

            $scope.searchExperts = function (keyword) {
                $scope.experts = ExpertService.search(function () {
                    if (_.isEmpty($scope.experts)) {
                        $ionicPopup.alert({
                            title: "",
                            template: "No results found!"
                        });
                    }
                });

            };

        } else {
            $scope.searchExperts = function (keyword) {
                $scope.experts = ExpertService.search({
                    keyword: keyword
                }, function () {
                    if (_.isEmpty($scope.experts)) {
                        $ionicPopup.alert({
                            title: "",
                            template: "No results found!"
                        });
                    }
                })
            };


        }
    })
    .controller('ProjectDetailsCtrl', function ($scope, $stateParams, ProjectService, $ionicPopup, $ionicLoading, _, ReverseGeocodingService) {

        var geocoder = new google.maps.Geocoder;
        $scope.firstBid = false;
        $scope.bidRestricted = true;
        $scope.isBidsListShown = false;
        $scope.project = {};
        $scope.project.bids = [];
        $scope.toggleBids = function () {
            $scope.isBidsListShown = !$scope.isBidsListShown;
        };
        $scope.project = ProjectService.get({}, {
            id1: $stateParams.projectId
        }, function () {
            $scope.project.bids = ProjectService.bids({
                id1: $scope.project.projId
            }, function () {
                $scope.myBid = _.filter($scope.project.bids, function (obj) {
                    return !_.isEmpty(_.findWhere(obj, {
                        userId: parseInt($scope.userId)
                    }));
                });

                if (_.isEmpty($scope.myBid)) {
                    $scope.firstBid = true;
                } else {
                    $scope.firstBid = false;
                }
                console.log($scope.firstBid);
                if ($scope.project.country !== null || $scope.project.location !== null) {
                    $scope.showLoading();
                    navigator.geolocation.getCurrentPosition(onSuccess, onError);

                } else {
                    $scope.bidRestricted = false;
                }
            });
        });

        function onSuccess(pos) {
            console.log(pos.coords.latitude);
            console.log(pos.coords.longitude);

            var latLng = {
                lat: parseFloat(pos.coords.latitude),
                lng: parseFloat(pos.coords.longitude)
            };
            ReverseGeocodingService.reverseCoords(latLng).then(function (results) {
                processResults(results);
                $scope.hideLoading();
            }, function () {
                $scope.bidRestricted = false;
                window.alert('Geocoder failed due to: ' + status + ". As a result bids are allowed for everyone");
                $scope.hideLoading();
            });

            function processResults(results) {
                var country;
                var level4;
                var level3;
                var level2;

                /*var length_1 = 3
                if (results.length < 3)*/
                length_1 = results.length;
                for (var x = 0; x < length_1; x++) {
                    // console.log("result " + x);
                    for (var y = 0, length_2 = results[x].address_components.length; y < length_2; y++) {
                        for (var t = 0; t < results[x].address_components[y].types.length; t++) {
                            if (results[x].address_components[y].types[t] == 'country') {

                                country = results[x].address_components[y].long_name;


                                console.log(country);
                            }
                            if (results[x].address_components[y].types[t] == 'administrative_area_level_4') {
                                level4 = results[x].address_components[y].long_name;
                                console.log(level4);
                            }
                            if (results[x].address_components[y].types[t] == 'administrative_area_level_3') {
                                level3 = results[x].address_components[y].long_name;
                                console.log(level3);
                            }
                            if (results[x].address_components[y].types[t] == 'administrative_area_level_2') {
                                level2 = results[x].address_components[y].long_name;
                                console.log(level2);
                            }
                            // console.log(results[x].address_components[y].types[t]);
                            if (country && level4 && level3 && level2) {
                                break;
                            }
                        }

                    }
                }
                if ($scope.project.country == country) {
                    if ($scope.project.location == level2 && $scope.project.location == level3 && $scope.project.location == level4) {
                        if ($scope.project.location == level2 || $scope.project.location == level3 || $scope.project.location == level4) {
                            $scope.bidRestricted = false;
                        } else {
                            $scope.bidRestricted = true;
                        }
                    } else {
                        $scope.bidRestricted = false;
                    }
                } else {
                    $scope.bidRestricted = true;
                }



                if ($scope.bidRestricted) {
                    window.alert("You are NOT inside project's location! You cannot bid");
                } else {
                    window.alert('According to your location, you are allowed to bid for this project');
                }

            };


        };

        function onError(position) {
            alert("Location could not defined successfully!" + "As a result bids are allowed");
            $scope.bidRestricted = false;
            $scope.hideLoading();
        };
        $scope.showLoading = function () {
            $ionicLoading.show({
                template: 'Loading your location...'
            });
        };
        $scope.hideLoading = function () {
            $ionicLoading.hide();
        };
    })
    .controller('BidFormCtrl', function ($scope, $stateParams, ExpertService, $ionicModal, $ionicPopup, _, ProjectService, NotificationService) {
        var now = new Date();
        var tomorrow = new Date(now.getTime() + (24 * 60 * 60 * 1000));

        $scope.newBid = {};
        $scope.newBid.expert = {};
        $scope.newBid.project = {};
        $scope.newBid.estTime = new Date();
        $scope.newBid.estTime = tomorrow.getTime();
        //configure date picker
        var datePickerCallback = function (val) {
            if (typeof (val) === 'undefined') {
                console.log('No date selected');
            } else {
                $scope.newBid.estTime = val.getTime();
                $scope.datepickerObject.inputDate = val;
                console.log($scope.newBid.estTime);
            }
        };
        $scope.datepickerObject = {
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
                datePickerCallback(val);
            }
        };
        $scope.saveBid = function () {
            $scope.newBid.expert.userId = $scope.userId;
            $scope.newBid.project.projId = $scope.project.projId;
            $scope.newBid.expert.username = $scope.username;
            console.log($scope.newBid);
            ProjectService.saveBid({
                id1: $scope.project.projId
            }, $scope.newBid, function () {
                $scope.project.bids.push($scope.newBid);
                $scope.firstBid = false;
                var n = {};
                n.message = "A new bid created for project with title: " + $scope.project.title;
                n.title = "Hello " + $scope.project.employer.username + "!";
                n.badge = 1;
                n.userIdsToSend = [];
                n.userIdsToSend.push(parseInt($scope.project.employer.userId));
                NotificationService.sendNotification(n);
                var alertPopup = $ionicPopup.alert({
                    title: 'Your bid was saved successfully!',
                    template: 'If you want to delete or update your new bid,' + '<br> navigate to "My Bids" menu item'
                });
            });
        };

    })
    .controller('ExpertDetailsCtrl', function ($scope, $stateParams, ExpertService, $ionicModal, $ionicPopup, _, NotificationService) {

        $scope.notifyExpert = function () {
            var n = {};
            n.message = "Search and bid for " + $scope.username + "' projects!";
            n.title = "Bid invitation";
            n.badge = 1;
            n.userIdsToSend = [];
            n.userIdsToSend.push(parseInt($stateParams.expertId));
            NotificationService.sendNotification(n, function () {
                var alertPopup = $ionicPopup.alert({
                    title: 'Notification',
                    template: 'Your notification has been sent successfully!'
                });
            });

        }
        $scope.searchedExpert = ExpertService.get({}, {
            id: parseInt($stateParams.expertId)
        }, function () {


            $scope.expertJobsModal = function () {
                $scope.selectedExpert = {};
                $ionicModal.fromTemplateUrl('templates/expertJobs.html', {
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
        });
    })
    .controller('ExpertJobsCtrl', function ($scope, $stateParams, ExpertService, $ionicModal, $ionicPopup, _) {
        $scope.searchedExpert.projects = [];
        $scope.searchedExpert.projects = ExpertService.projects({}, {
            expId: $scope.searchedExpert.userId
        });

    });