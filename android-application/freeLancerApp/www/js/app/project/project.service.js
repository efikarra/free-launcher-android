angular.module('e-lancer.project')
    .service("ProjectService", function ($resource,SERVER_IP_ADDRESS) {
        return $resource('http://'+SERVER_IP_ADDRESS.ipAddress+'/competitions/projects/:id1/:action1/:id2/:action2', {
            id1: '@id1',
            id2: '@id2'
        }, {
            search: {
                method: 'GET',
                 params: {
                    action1: 'search'
                },
                isArray: true,
                headers: {
                    'Content-Type': 'application/json'
                }

            },
            get: {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                }

            },
            save: {
                method: 'POST',
                 headers: {
                    'Content-Type': 'application/json'
                },
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }

            },
            saveExperts: {
                method: 'POST',
                params: {
                    action1: 'experts'
                },
                 headers: {
                    'Content-Type': 'application/json'
                },
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }

            },
            getExperts: {
                method: 'GET',
                params: {
                    action1: 'experts'
                },
                isArray: true,
                 headers: {
                    'Content-Type': 'application/json'
            }
            },
             update: {
                method: 'PUT',
                  headers: {
                    'Content-Type': 'application/json'
                },
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }

            },
             updateIsClosed: {
                method: 'PUT',
                 params: {action1: 'isClosed'},
                  headers: {
                    'Content-Type': 'application/json'
                },
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }

            },
             delete: {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }

            },
            bids: {
                params: {
                    action1: 'bids'
                },
                method: 'GET',
                isArray: true,
                headers: {
                    'Content-Type': 'application/json'
                }

            },
            saveBid: {
                params: {
                    action1: 'bids'
                },
                method: 'POST',
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }

            },
            updateBid: {
                params: {
                    action1: 'bids'
                },
                method: 'PUT',
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    return angular.toJson(data); // this will go in the body request
                }

            },
            deleteBid: {
                params: {
                    action1: 'bids'
                },
                method: 'DELETE'
            },
            getExpertEvaluations: {
                params: {
                    action1: 'evaluations',
                    action2: 'expertEvaluations'
                },
                method: 'GET',
                isArray: true,
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            updateExpertEvaluation: {
                params: {
                    action1: 'evaluations',
                    action2: 'expertEvaluations'
                },
                method: 'PUT',
                isArray: true,
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }
            },
            saveExpertEvaluation: {
                params: {
                    action1: 'evaluations',
                    action2: 'expertEvaluations'
                },
                method: 'POST',
                isArray: true,
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }
            },
            deleteExpertEvaluation: {
                params: {
                    action1: 'evaluations',
                    action2: 'expertEvaluations'
                },
                method: 'DELETE',
                isArray: true,
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            
            getEmployerEvaluations: {
                params: {
                    action1: 'evaluations',
                    action2: 'employerEvaluations'
                },
                method: 'GET',
                isArray: true,
                headers: {
                    'Content-Type': 'application/json'
                }
            },
            updateEmployerEvaluation: {
                params: {
                    action1: 'evaluations',
                    action2: 'employerEvaluations'
                },
                method: 'PUT',
                isArray: true,
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }
            },
            saveEmployerEvaluation: {
                params: {
                    action1: 'evaluations',
                    action2: 'employerEvaluations'
                },
                method: 'POST',
                isArray: true,
                transformRequest: function (data, headers) {
                    headers = angular.extend({}, headers, {
                        'Content-Type': 'application/json'
                    });
                    console.log(data);
                    console.log(angular.toJson(data));
                    return angular.toJson(data); // this will go in the body request
                }
            },
            deleteEmployerEvaluation: {
                params: {
                    action1: 'evaluations',
                    action2: 'employerEvaluations'
                },
                method: 'DELETE',
                isArray: true,
                headers: {
                    'Content-Type': 'application/json'
                }
            }
        });
    });