var stompClient = null;

var sesionId;

window.onload = function(){
    var socket = new SockJS('/secured/endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
          var url = stompClient.ws._transport.url;
          url = url.replace("ws://trizu.ovh/secured/endpoint/",  "");
          url = url.replace("/websocket", "");
          url = url.replace(/^[0-9]+\//, "");
          sessionId = url;
          stompClient.subscribe('/secured/user/specific-house/' + houseid, function (msgOut) {
        	  		var str = JSON.parse(msgOut.body).content;
					if(str === "connectionError"){
						document.getElementById("error").innerHTML = "Connection Error";
					}else{
						document.getElementById("error").innerHTML = "";
						showGreeting(str);
					}
        		});
          sendName(houseid, null);
    });
	 

}
	
window.onbeforeunload = function(){
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}


function sendName(houseid, pinId) {

	stompClient.send("/secured/user/house/"+ houseid+"/"+pinId, {}, JSON.stringify({'pinId': pinId }));
}

function showGreeting(message) {
	var tr;
	var td;
	var label;
	var textnode; 
	var btn = null;
	var tbody = document.getElementById('tb');
	var table = document.getElementById('table');
	var row = document.getElementsByTagName('tbody')[0];


	if (tbody != null) {
		tbody.parentNode.removeChild(tbody);
	}
	
	tbody = document.createElement('tbody');
	tbody.setAttribute("id", "tb");
	
	var content = JSON.parse(message);
	var i,j;
	
for(i = 0; i < content.pins.length ; i++ ){
	
	tr = document.createElement("tr");
	
	label = document.createElement('label');
	label.className = "badge badge-primary label-large";
	td = document.createElement("td");
	textnode = document.createTextNode(content.pins[i].pinName);
	label.appendChild(textnode);
	td.appendChild(label);
	tr.appendChild(td);
	
	label = document.createElement('label');
	td = document.createElement("td");
	textnode = document.createTextNode(content.pins[i].pinState);
	
	if(content.pins[i].pinState == true){
		label.className = "badge badge-success label-large";
	}
	else{
		label.className = "badge badge-danger label-large";
	}
	label.appendChild(textnode);
	td.appendChild(label);
	tr.appendChild(td);

	if(content.pins[i].pinType.toString() === "true"){
		btn = document.createElement("BUTTON");
		btn.innerHTML = "CHANGE";
		btn.className = "btn btn-warning";
		var id = content.pins[i].pinId.toString();
		btn.setAttribute("onclick", "sendName('"+houseid+"','"+id+"')");
	}
	
	td = document.createElement("td");
	if(btn != null){
		td.appendChild(btn);
		btn = null;
	}
	tr.appendChild(td);
	
	tbody.appendChild(tr);
}
table.appendChild(tbody);
}


