
app.config(function ($stateProvider, $urlRouterProvider) {

    $urlRouterProvider.otherwise("/");

    $stateProvider
        .state('root', {
            url: "/",
            template: "<ui-view></ui-view>",
            data: {
                displayName: "Home"
            }
        })
        // Jobs
        .state('root.job', {
            url: "job",
            templateUrl: "app/module/job/browse.html",
            data: {
                displayName: "Jobs"
            }
        })
        .state('root.job.detail', {
            url: "/:id",
            templateUrl: "app/module/job/detail.html",
            data: {
                displayName: "Details"
            }
        })
        .state('root.job.create', {
            url: "/create",
            templateUrl: "app/module/job/create.html",
            data: {
                displayName: "Create"
            }
        })

        // Logs
        .state('root.log', {
            url: "log",
            templateUrl: "app/module/log/index.html",
            data: {
                displayName: "Logs"
            }
        })


    ;
});