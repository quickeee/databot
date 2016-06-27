/**
 * Created by KINCERS on 12/4/2015.
 */

app.factory('Job', function($resource) {
    return $resource('api/job', {id: "@id"}, {
        //query: { method: "GET", isArray: false },
        'update':       { method:'PUT' }
        //'createmap':    { method:'POST',    url: baseUrl + apiNamespace + '/job/:id/map'},
    });
});