(function() {

  //var myWebSocket = new WebSocket("ws://localhost:9000/ws-send");
  //var myWebSocket = new WebSocket("ws://localhost:9000/ws-event");
  var myWebSocket = new WebSocket("ws://localhost:9000/ws-sport");

  myWebSocket.onmessage = function(event) {
    console.log("Received data: " + event.data);
  };

  myWebSocket.onopen = function(event) {
    console.log("Connection open...");
  };

  myWebSocket.onclose = function(event) {
    console.log("Connection closed");
  };

  myWebSocket.onerror = function(event) {
    console.log("Error!");
  };

  // myWebSocket.send("Test WebSocket message");
  // myWebSocket.send(JSON.stringify({"request": "Event request"}));

  $('#ping').on('click', function() {
    myWebSocket.send(JSON.stringify({"$type": "ping", "seq": 1}));
  });

  $('#close_conn_btn').on('click', function() {
    myWebSocket.close();
  });

  $('#login_btn').on('click', function() {
    var username = $('#username').val();
    var password = $('#password').val();
    myWebSocket.send(JSON.stringify({"$type": "login", "username": username, "password": password}));
  });

  $('#logout_btn').on('click', function() {
    myWebSocket.send(JSON.stringify({"$type": "logout"}));
  });


  $('#subscribe_games').on('click', function() {
    myWebSocket.send(JSON.stringify({"$type": "subscribe_games"}));
  });

  $('#unsubscribe_games').on('click', function() {
    myWebSocket.send(JSON.stringify({"$type": "unsubscribe_games"}));
  });

  $('#add_game').on('click', function() {
    var game = getFormData();
    delete game['id'];
    var afterId = $('#after_id').val();
    var addMsg = JSON.stringify({"$type": "add_game", "afterId": parseInt(afterId), "game": game});
    console.log('add_game: ' + addMsg);
    myWebSocket.send(addMsg);
  });

  $('#update_game').on('click', function() {
    var game = getFormData();
    console.log('update_game: ' + JSON.stringify(game));
    myWebSocket.send(JSON.stringify({"$type": "update_game", "game": game}));
  });

  $('#remove_game').on('click', function() {
    var game = getFormData();
    console.log('remove_game: ' + JSON.stringify(game));
    myWebSocket.send(JSON.stringify({"$type": "remove_game", "id": game['id']}));
  });

  $('#login_form, #game_form').on('submit', function(event) { event.preventDefault(); });

  function getFormData() {
    var game = {};
    $.each($('#game_form').serializeArray(), function(_, kv) {
      game[kv.name] = kv.value;
    });
    game['id'] = parseInt(game['id']);
    game['participants'] = parseInt(game['participants']);
    return game;
  }

})();