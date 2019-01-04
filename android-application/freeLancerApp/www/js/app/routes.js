angular.module('e-lancer')

.config(function ($stateProvider, $urlRouterProvider, USER_ROLES, $ionicConfigProvider) {
    $ionicConfigProvider.tabs.position("bottom");
    $stateProvider

        .state('login', {
            url: '/login',
            templateUrl: 'templates/login.html',
            controller: 'LoginCtrl'

        })
        .state('register', {
            url: '/register',
            templateUrl: 'templates/register.html',
            controller: 'RegisterCtrl'
        })
        .state('app', {
            url: '/app',
            abstract: true,
            templateUrl: 'templates/menu.html',
            controller: 'AppCtrl'
        })
        .state('app.tabs', {
            url: '/tabs',
            views: {
                'menu': {
                    templateUrl: "templates/tabs.html"
                }
            }
        })
        .state('app.tabs.home', {
            url: "/home",
            views: {
                'home-tab': {
                    templateUrl: "templates/home.html"
                }
            }
        })
        .state('app.tabs.searchProjects', {
            url: "/search/projects",
            abstract: true,
            views: {
                'search-projects-tab': {
                    templateUrl: "templates/search/searchProjects.html",
                    controller: 'SearchProjectsCtrl'
                }
            }
        })
        .state('app.tabs.searchProjects.results', {
            url: "/results",
            views: {
                'search-projects': {
                    templateUrl: "templates/search/projectsResults.html",
                }
            },

        })
        .state('app.tabs.searchProjects.projectId', {
            url: "/:projectId",
            views: {
                'search-projects': {
                    templateUrl: "templates/search/projectDetails.html",
                    controller: 'ProjectDetailsCtrl'
                }
            }
        })
        .state('app.tabs.searchExperts', {
            url: "/search/experts",
            abstract: true,
            views: {
                'search-experts-tab': {
                    templateUrl: "templates/search/searchExperts.html",
                    controller: 'SearchExpertsCtrl'
                }
            }
        })
        .state('app.tabs.searchExperts.results', {
            url: "/results",
            views: {
                'search-experts': {
                    templateUrl: "templates/search/expertsResults.html",
                }
            }
        })
        .state('app.tabs.searchExperts.expertId', {
            url: "/:expertId",
            views: {
                'search-experts': {
                    templateUrl: "templates/search/expertDetails.html",
                    controller: 'ExpertDetailsCtrl'
                }
            }
        })
        .state('app.tabs.myJobs', {
            url: "/myJobs",
            views: {
                'myJobs-tab': {
                    templateUrl: "templates/myJobs.html",
                    controller: 'MyJobsCtrl'
                }
            }
        })

    .state('app.tabs.myJobs.list', {
        url: "/list",
        views: {
            'myJobs': {
                templateUrl: "templates/myJobsList.html",
            }
        }
    })

    .state('app.tabs.myJobs.myJobId', {
            url: "/:myJobId",
            views: {
                'myJobs': {
                    templateUrl: "templates/myJobDetails.html",
                    controller: 'MyJobCtrl'

                }
            }
        })
        .state('app.tabs.myProjects', {
            url: "/myProjects",
            views: {
                'myProjects-tab': {
                    templateUrl: "templates/myProject/myProjects.html",
                    controller: 'MyProjectsCtrl'
                }
            }
        })

    .state('app.tabs.myProjects.list', {
        url: "/list",
        views: {
            'myProjects': {
                templateUrl: "templates/myProject/myProjectsList.html",
            }
        }
    })

    .state('app.tabs.myProjects.myProjectEdit', {
            url: "/edit/:myProjectId",
            abstract:true,
            views: {
                'myProjects': {
                    templateUrl: "templates/myProject/myProjectEdit.html",
                    controller: 'MyProjectEditCtrl'

                }
            }
        })
     .state('app.tabs.myProjects.myProjectEdit.form', {
            url: "/form",
            views: {
                'myProject-form': {
                    templateUrl: "templates/myProject/myProjectEditForm.html",
                    controller: 'MyProjectEditFormCtrl'

                }
            }
        })
     .state('app.tabs.myProjects.myProjectEdit.map', {
            url: "/map",
            views: {
                'myProject-form': {
                    templateUrl: "templates/map.html",
                    controller: 'MapCtrl'

                }
            }
        })
        .state('app.tabs.myProjects.myProjectDetails', {
            url: "/details/:myProjectId",
            views: {
                'myProjects': {
                    templateUrl: "templates/myProject/myProjectDetails.html",
                    controller: 'MyProjectDetailsCtrl'

                }
            }
        })
        .state('app.tabs.myBids', {
            url: "/myBids",
            views: {
                'myBids-tab': {
                    templateUrl: "templates/myBids.html",
                    controller: 'MyBidsCtrl'
                }
            }
        })
        .state('app.tabs.profile', {
            url: "/profile",
            views: {
                'profile-tab': {
                    templateUrl: "templates/profile.html",
                    controller: 'ProfileCtrl'

                }
            }
        })
        .state('app.tabs.newProject', {
            url: "/newProject",
            abstract: true,
            views: {
                'newProject-tab': {
                    templateUrl: "templates/newProject/newProject.html",
                    controller: 'NewProjectCtrl'
                }
            }
        })
        .state('app.tabs.newProject.form', {
            url: "/form",
            views: {
                'newProject': {
                    templateUrl: "templates/newProject/newProjectForm.html",
                    controller: 'NewProjectFormCtrl'
                }
            }
        })
        .state('app.tabs.newProject.map', {
            url: "/map",
            views: {
                'newProject': {
                    templateUrl: "templates/map.html",
                    controller: 'MapCtrl'
                }
            }
        })

        // if none of the above states are matched, use this as the fallback
        // $urlRouterProvider.otherwise('/login');
    $urlRouterProvider.otherwise(function ($injector, $location) {
        var $state = $injector.get("$state");
        $state.go("login");
    });
});