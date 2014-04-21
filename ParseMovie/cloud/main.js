
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
  response.success("Hello world!");
});

Parse.Cloud.afterSave("Movie", function(request) {
 Parse.Push.send({
  channels: [ "MoviePush" ],
  data: {
    alert: "Usuario 1 ha añadido la pelicula " + request.object.get("name")
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