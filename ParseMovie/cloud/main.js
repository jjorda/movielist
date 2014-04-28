
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});

Parse.Cloud.afterSave("Movie", function(request) {
var query = new Parse.Query(Parse.Installation);
query.equalTo('movielists', request.object.get("movielist"));
query.notEqualTo('user', request.user);
 Parse.Push.send({

  where: query,
  data: {
    alert: "Usuario " + request.user.get("username") + " ha añadido la pelicula " + request.object.get("name")
  }
}, {
  success: function() {
    // Push was successful
  },
  error: function(error) {
    // Handle error
  }
});
});