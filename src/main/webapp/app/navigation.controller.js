/**
 * Created by KINCERS on 12/4/2015.
 */
"use strict";

app.controller('NavigationController', function($scope) {

    $scope.mainNav = [{
        name: "States",
        link: "#",
        subtree: [{
            name: "state 1",
            link: "state1"
        },{
            name: "state 2",
            link: "state2"
        }]
    }, {
        name: "No states",
        link: "#",
        subtree: [{
            name: "no state connected",
            link: "#"
        }]
    }, {
        name: "divider",
        link: "#"

    }, {
        name: "State has not been set up",
        link: "#"
    }, {
        name: "divider",
        link: "#"
    }, {
        name: "Here again no state set up",
        link: "#"
    }];

    $scope.utilityNav = [
        {
            name: "Jobs",
            link: "root.job"
        },
        {
            name: "Logs",
            link: "root.log"
        },
        {
            name: "Objects",
            link: "objects"
        },
        {
            name: "Data Objects",
            link: "#",
            subtree: [
                {
                    name: "Files",
                    link: "#"
                },
                {
                    name: "Databases",
                    link: "#"
                },
                {
                    name: "Web Services",
                    link: "#"
                }
            ]
        }
    ];

});
